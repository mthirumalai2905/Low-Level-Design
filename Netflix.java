import java.util.*;

class User {
    private String userId;
    private String name;
    private String email;
    private Subscription subscription;
    private List<String> watchHistory;
    private List<String> watchlist;

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.subscription = null;
        this.watchHistory = new ArrayList<>();
        this.watchlist = new ArrayList<>();
    }

    public void subscribe(Subscription subscription) {
        this.subscription = subscription;
    }

    public boolean hasActiveSubscription() {
        return subscription != null && subscription.isActive();
    }

    public void addToWatchlist(String movieId) {
        watchlist.add(movieId);
    }

    public void watchMovie(String movieId) {
        if (hasActiveSubscription()) {
            watchHistory.add(movieId);
            System.out.println(name + " watched " + movieId);
        } else {
            System.out.println("Subscription required to watch movies.");
        }
    }
}

class Movie {
    private String movieId;
    private String title;
    private String genre;
    private double rating;

    public Movie(String movieId, String title, String genre, double rating) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }
}

class Subscription {
    private String plan;
    private double price;
    private boolean active;

    public Subscription(String plan, double price) {
        this.plan = plan;
        this.price = price;
        this.active = true;
    }

    public boolean isActive() {
        return active;
    }

    public void cancelSubscription() {
        this.active = false;
    }
}

class Netflix {
    private Map<String, User> users;
    private Map<String, Movie> movies;

    public Netflix() {
        this.users = new HashMap<>();
        this.movies = new HashMap<>();
    }

    public void addUser(String userId, String name, String email) {
        users.put(userId, new User(userId, name, email));
    }

    public void addMovie(String movieId, String title, String genre, double rating) {
        movies.put(movieId, new Movie(movieId, title, genre, rating));
    }

    public void subscribeUser(String userId, String plan, double price) {
        if (users.containsKey(userId)) {
            users.get(userId).subscribe(new Subscription(plan, price));
        }
    }

    public void watchMovie(String userId, String movieId) {
        if (users.containsKey(userId) && movies.containsKey(movieId)) {
            users.get(userId).watchMovie(movieId);
        } else {
            System.out.println("Invalid user or movie ID.");
        }
    }

    public List<String> recommendMovies(String userId) {
        if (!users.containsKey(userId)) return Collections.emptyList();

        User user = users.get(userId);
        if (user.hasActiveSubscription()) {
            return movies.values().stream()
                    .sorted(Comparator.comparingDouble(Movie::getRating).reversed())
                    .limit(3)
                    .map(m -> m.getGenre() + " - " + m.getRating())
                    .toList();
        } else {
            System.out.println("Subscription required for recommendations.");
            return Collections.emptyList();
        }
    }
}

public class NetflixSystem {
    public static void main(String[] args) {
        Netflix netflix = new Netflix();
        netflix.addUser("U1", "Alice", "alice@example.com");
        netflix.addMovie("M1", "Inception", "Sci-Fi", 8.8);
        netflix.addMovie("M2", "Interstellar", "Sci-Fi", 8.6);
        netflix.addMovie("M3", "The Dark Knight", "Action", 9.0);

        netflix.subscribeUser("U1", "Premium", 15.99);
        netflix.watchMovie("U1", "M1");

        System.out.println("Recommended movies: " + netflix.recommendMovies("U1"));
    }
}
