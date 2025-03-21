import java.util.*;

class Facebook {
    private Map<Integer, Set<Integer>> followers;
    private Map<Integer, List<Post>> userPosts;
    private int timestamp;
    
    public Facebook() {
        followers = new HashMap<>();
        userPosts = new HashMap<>();
        timestamp = 0;
    }
    
    public void follow(int followerId, int followeeId) {
        followers.computeIfAbsent(followerId, k -> new HashSet<>()).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        followers.getOrDefault(followerId, new HashSet<>()).remove(followeeId);
    }
    
    public int createPost(int userId, String content) {
        Post post = new Post(userId, content, timestamp++);
        userPosts.computeIfAbsent(userId, k -> new ArrayList<>()).add(post);
        return post.id;
    }
    
    public boolean deletePost(int userId, int postId) {
        List<Post> posts = userPosts.getOrDefault(userId, new ArrayList<>());
        return posts.removeIf(p -> p.id == postId);
    }
    
    public List<Post> getFeed(int userId, int limit) {
        PriorityQueue<Post> pq = new PriorityQueue<>((a, b) -> b.timestamp - a.timestamp);
        
        Set<Integer> following = followers.getOrDefault(userId, new HashSet<>());
        following.add(userId); 
        
        for (int followee : following) {
            List<Post> posts = userPosts.getOrDefault(followee, new ArrayList<>());
            pq.addAll(posts);
        }
        
        List<Post> feed = new ArrayList<>();
        while (!pq.isEmpty() && feed.size() < limit) {
            feed.add(pq.poll());
        }
        return feed;
    }
    
    static class Post {
        private static int idCounter = 0;
        int id, userId, timestamp;
        String content;
        
        public Post(int userId, String content, int timestamp) {
            this.id = idCounter++;
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
