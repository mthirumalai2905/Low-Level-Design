import java.util.*;

// concrete class of our subject
class URLShortener {
    private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"; // 62 unique characters
    private static final int BASE = 62; // Base  
    private Map<String, String> longToShort; // Mappings  from l -> s
    private Map<String, String> shortToLong; // Mappings from  s- > l
    private long counter; // counter to make sure that we get an unique shortened url avoiding collisions

  // constructor initializing the instance of the class
    public URLShortener() {
        longToShort = new HashMap<>();
        shortToLong = new HashMap<>();
        counter = 1;
    }


  // simple encode method
    private String encodeBase62(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(BASE62_CHARS.charAt((int) (num % BASE)));
            num /= BASE;
        }
        return sb.reverse().toString();
    }

    private long decodeBase62(String shortURL) {
        long num = 0;
        for (char c : shortURL.toCharArray()) {
            num = num * BASE + BASE62_CHARS.indexOf(c);
        }
        return num;
    }

    public String shortenURL(String longURL) {
        if (longToShort.containsKey(longURL)) {
            return longToShort.get(longURL);
        }

        String shortURL = encodeBase62(counter);
        longToShort.put(longURL, shortURL);
        shortToLong.put(shortURL, longURL);
        counter++;

        return shortURL;
    }

    public String getOriginalURL(String shortURL) {
        return shortToLong.getOrDefault(shortURL, "URL not found");
    }

    public static void main(String[] args) {
        URLShortener urlShortener = new URLShortener();

        String longURL = "https://example.com/very-long-url";
        String shortURL = urlShortener.shortenURL(longURL);
        System.out.println("Shortened URL: " + shortURL);
        
        String originalURL = urlShortener.getOriginalURL(shortURL);
        System.out.println("Original URL: " + originalURL);
    }
}
