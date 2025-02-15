import java.util.*;

class Book {
    private String title, author, isbn;
    private boolean isAvailable;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
    }

    public String getIsbn() { return isbn; }
    public boolean isAvailable() { return isAvailable; }
    public void borrow() { isAvailable = false; }
    public void returnBook() { isAvailable = true; }
    @Override public String toString() { return title + " by " + author; }
}

class Library {
    private Map<String, Book> books = new HashMap<>();

    public void addBook(Book book) { books.put(book.getIsbn(), book); }
    public void removeBook(String isbn) { books.remove(isbn); }
    public Book findBook(String isbn) { return books.get(isbn); }

    public boolean borrowBook(String isbn) {
        Book book = books.get(isbn);
        if (book != null && book.isAvailable()) {
            book.borrow();
            return true;
        }
        return false;
    }

    public void returnBook(String isbn) {
        Book book = books.get(isbn);
        if (book != null) book.returnBook();
    }
}

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.addBook(new Book("Clean Code", "Robert C. Martin", "123"));
        library.addBook(new Book("Design Patterns", "GoF", "456"));

        System.out.println(library.borrowBook("123") ? "Book borrowed!" : "Not available");
        library.returnBook("123");
    }
}
