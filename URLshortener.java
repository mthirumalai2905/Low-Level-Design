import java.util.*;

class URLShortener {
    private static final String CHAR_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = CHAR_SET.length();
    private static final int SHORT_URL_LENGTH = 7;

    private final Map<String, String> shortToLong = new HashMap<>();
    private final Map<String, String> longToShort = new HashMap<>();
    private final Random random = new Random();

    public String shortenURL(String longURL) {
        if (longToShort.containsKey(longURL)) return longToShort.get(longURL);
        String shortURL;
        do {
            shortURL = generateShortURL();
        } while (shortToLong.containsKey(shortURL));
        shortToLong.put(shortURL, longURL);
        longToShort.put(longURL, shortURL);
        return shortURL;
    }

    public String getOriginalURL(String shortURL) {
        return shortToLong.getOrDefault(shortURL, "URL not found");
    }

    private String generateShortURL() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            sb.append(CHAR_SET.charAt(random.nextInt(BASE)));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        URLShortener urlShortener = new URLShortener();
        String shortURL = urlShortener.shortenURL("https://example.com");
        System.out.println("Short URL: " + shortURL);
        System.out.println("Original URL: " + urlShortener.getOriginalURL(shortURL));
    }
}
