import java.util.*;

class Twitter {
    private Map<Integer, List<Tweet>> userTweets;
    private Map<Integer, Set<Integer>> followers;
    private int timeStamp;

    public Twitter() {
        userTweets = new HashMap<>();
        followers = new HashMap<>();
        timeStamp = 0;
    }

    private static class Tweet {
        int tweetId;
        int timeStamp;

        public Tweet(int tweetId, int timeStamp) {
            this.tweetId = tweetId;
            this.timeStamp = timeStamp;
        }
    }

    public void postTweet(int userId, int tweetId) {
        userTweets.putIfAbsent(userId, new ArrayList<>());
        userTweets.get(userId).add(new Tweet(tweetId, timeStamp++));
    }

    public List<Integer> getNewsFeed(int userId) {
        Set<Integer> followedUsers = new HashSet<>(followers.getOrDefault(userId, new HashSet<>()));
        followedUsers.add(userId);

        PriorityQueue<Tweet> maxHeap = new PriorityQueue<>((a, b) -> b.timeStamp - a.timeStamp);
        
        for (int followedUser : followedUsers) {
            List<Tweet> tweets = userTweets.get(followedUser);
            if (tweets != null) {
                for (int i = Math.max(0, tweets.size() - 10); i < tweets.size(); i++) {
                    maxHeap.offer(tweets.get(i));
                }
            }
        }

        List<Integer> newsFeed = new ArrayList<>();
        while (!maxHeap.isEmpty() && newsFeed.size() < 10) {
            newsFeed.add(maxHeap.poll().tweetId);
        }

        return newsFeed;
    }

    public void follow(int followerId, int followeeId) {
        if (followerId == followeeId) return;  // Avoid self-following
        followers.putIfAbsent(followerId, new HashSet<>());
        followers.get(followerId).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        followers.getOrDefault(followerId, new HashSet<>()).remove(followeeId);
    }
}
