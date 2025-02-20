import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// News Entity
class News {
    private String id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime publishedDate;
    private List<String> categories;

    public News(String title, String content, String author, List<String> categories) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.content = content;
        this.author = author;
        this.categories = categories;
        this.publishedDate = LocalDateTime.now();
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public LocalDateTime getPublishedDate() { return publishedDate; }
}

// Repository Interface
interface NewsRepository {
    void save(News news);
    Optional<News> findById(String id);
    List<News> findByCategory(String category);
    List<News> findAll();
}

// In-memory Repository Implementation
class InMemoryNewsRepository implements NewsRepository {
    private List<News> newsStore = new ArrayList<>();

    @Override
    public void save(News news) {
        newsStore.add(news);
    }

    @Override
    public Optional<News> findById(String id) {
        return newsStore.stream()
                .filter(news -> news.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<News> findByCategory(String category) {
        return newsStore.stream()
                .filter(news -> news.getCategories().contains(category))
                .toList();
    }

    @Override
    public List<News> findAll() {
        return new ArrayList<>(newsStore);
    }
}

// News Service
class NewsService {
    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public String createNews(String title, String content, String author, List<String> categories) {
        News news = new News(title, content, author, categories);
        newsRepository.save(news);
        return news.getId();
    }

    public Optional<News> getNewsById(String id) {
        return newsRepository.findById(id);
    }

    public List<News> getNewsByCategory(String category) {
        return newsRepository.findByCategory(category);
    }

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }
}

// Example Usage
public class NewsServiceDemo {
    public static void main(String[] args) {
        NewsRepository repository = new InMemoryNewsRepository();
        NewsService newsService = new NewsService(repository);

        // Create news
        List<String> categories = List.of("Tech", "AI");
        String newsId = newsService.createNews(
            "AI Breakthrough",
            "New AI model achieves record accuracy",
            "John Doe",
            categories
        );

        // Get news by ID
        newsService.getNewsById(newsId)
            .ifPresent(news -> System.out.println("Title: " + news.getTitle()));
    }
}
