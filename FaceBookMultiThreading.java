import java.util.*;
import java.util.concurrent.*; // concurrent package

class Facebook {
    private final Map<Integer, Set<Integer>> followers;
    private final Map<Integer, List<Post>> userPosts;
    private final AtomicInteger timestamp; //atmomic integer
    private final ReentrantReadWriteLock lock; // locking mechanism
    
    public Facebook() {
        followers = new ConcurrentHashMap<>();
        userPosts = new ConcurrentHashMap<>();
        timestamp = new AtomicInteger(0);
        lock = new ReentrantReadWriteLock();
    }
    
    public void follow(int followerId, int followeeId) {
        followers.computeIfAbsent(followerId, k -> ConcurrentHashMap.newKeySet()).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        followers.getOrDefault(followerId, Collections.emptySet()).remove(followeeId);
    }
    
    public int createPost(int userId, String content) {
        lock.writeLock().lock(); // thread has entered now so lock it 
        try {
            userPosts.computeIfAbsent(userId, k -> Collections.synchronizedList(new ArrayList<>()));
            Post post = new Post(userId, content, timestamp.getAndIncrement());
            userPosts.get(userId).add(post);
            return post.id;
        } finally {
            lock.writeLock().unlock(); // unclock it so that other thread can use this critical section
        }
    }
    
    public boolean deletePost(int userId, int postId) {
        lock.writeLock().lock();
        try {
            List<Post> posts = userPosts.getOrDefault(userId, Collections.emptyList());
            return posts.removeIf(p -> p.id == postId);
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    public List<Post> getFeed(int userId, int limit) {
        lock.readLock().lock();
        try {
            PriorityQueue<Post> pq = new PriorityQueue<>((a, b) -> b.timestamp - a.timestamp);
            
            Set<Integer> following = followers.getOrDefault(userId, Collections.emptySet());
            following.add(userId);
            
            for (int followee : following) {
                List<Post> posts = userPosts.getOrDefault(followee, Collections.emptyList());
                pq.addAll(posts);
            }
            
            List<Post> feed = new ArrayList<>();
            while (!pq.isEmpty() && feed.size() < limit) {
                feed.add(pq.poll());
            }
            return feed;
        } finally {
            lock.readLock().unlock();
        }
    }
    
    static class Post {
        private static final AtomicInteger idCounter = new AtomicInteger(0);
        int id, userId, timestamp;
        String content;
        
        public Post(int userId, String content, int timestamp) {
            this.id = idCounter.getAndIncrement();
            this.userId = userId;
            this.content = content;
            this.timestamp = timestamp;
        }
        
        @Override
        public String toString() {
            return "Post[id=" + id + ", userId=" + userId + ", content=" + content + "]";
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Facebook fb = new Facebook();
        
        int post1 = fb.createPost(1, "Hello World!");
        int post2 = fb.createPost(2, "Hello from user 2!");
        
        fb.follow(1, 2);
        System.out.println(fb.getFeed(1, 10)); 
        
        fb.unfollow(1, 2);
        System.out.println(fb.getFeed(1, 10));
        
        fb.deletePost(1, post1);
        System.out.println(fb.getFeed(1, 10));
    }
}
