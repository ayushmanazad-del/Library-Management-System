package com.library.viewmodel;

import com.library.model.Book;
import com.library.model.Member;
import com.library.model.Transaction;
import com.library.service.LMSService;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.text.SimpleDateFormat;

public class MainViewModel {
    private final LMSService service;
    private final Member currentUser;

    // Data for "All Books" Catalog
    private final ObservableList<Book> books = FXCollections.observableArrayList();
    
    // Data for "My Issued Books"
    private final ObservableList<IssuedBookInfo> issuedBooks = FXCollections.observableArrayList();
    
    // Properties for Admin Form
    private final StringProperty titleInput = new SimpleStringProperty();
    private final StringProperty authorInput = new SimpleStringProperty();
    private final StringProperty isbnInput = new SimpleStringProperty();
    private final StringProperty statusMessage = new SimpleStringProperty();

    public MainViewModel(Member currentUser) {
        this.service = new LMSService();
        this.currentUser = currentUser;
        loadBooks();
        loadIssuedBooks();
    }

    public void loadBooks() {
        books.setAll(service.getAllBooks());
    }
    
    public void loadIssuedBooks() {
        issuedBooks.clear();
        var transactions = service.getActiveTransactions(currentUser.getId());
        
        for (Transaction t : transactions) {
            Book b = service.getBook(t.getBookId());
            if (b != null) {
                issuedBooks.add(new IssuedBookInfo(t, b));
            }
        }
    }

    public void addBook() {
        if (isAdmin()) {
            Book newBook = new Book(
                titleInput.get(), 
                authorInput.get(), 
                isbnInput.get(), 
                5 
            );
            service.addBook(newBook);
            statusMessage.set("Book added successfully.");
            loadBooks(); 
            clearInputs();
        } else {
            statusMessage.set("Permission Denied: Admins only.");
        }
    }
    
    public void issueSelectedBook(Book selectedBook) {
        if (selectedBook == null) {
            statusMessage.set("No book selected.");
            return;
        }
        
        // Prevent issuing if copies are 0 (Handled by DB, but good for UI feedback)
        if (selectedBook.getAvailableCopies() <= 0) {
            statusMessage.set("Book is out of stock.");
            return;
        }
        
        boolean success = service.issueBook(currentUser.getId(), selectedBook.getId());
        if (success) {
            statusMessage.set("Book issued to " + currentUser.getUsername());
            loadBooks();        // Update catalog count
            loadIssuedBooks();  // Update 'My Books' list
        } else {
            statusMessage.set("Failed: Book unavailable.");
        }
    }
    
    public void returnSelectedBook(IssuedBookInfo info) {
        if (info == null) {
            statusMessage.set("No book selected to return.");
            return;
        }
        
        service.returnBook(info.getTransaction().getId(), info.getBook().getId());
        statusMessage.set("Book returned successfully.");
        loadBooks();        // Update catalog count
        loadIssuedBooks();  // Remove from 'My Books' list
    }

    private void clearInputs() {
        titleInput.set("");
        authorInput.set("");
        isbnInput.set("");
    }
    
    public boolean isAdmin() {
        return currentUser.getRole().name().equals("ADMIN");
    }

    // Property Accessors
    public ObservableList<Book> getBooks() { return books; }
    public ObservableList<IssuedBookInfo> getIssuedBooks() { return issuedBooks; }
    
    public StringProperty titleInputProperty() { return titleInput; }
    public StringProperty authorInputProperty() { return authorInput; }
    public StringProperty isbnInputProperty() { return isbnInput; }
    public StringProperty statusMessageProperty() { return statusMessage; }
    public Member getCurrentUser() { return currentUser; }
    
    // Inner class to display issued books in TableView
    public static class IssuedBookInfo {
        private final SimpleStringProperty bookTitle;
        private final SimpleStringProperty issueDate;
        private final Transaction transaction;
        private final Book book;

        public IssuedBookInfo(Transaction t, Book b) {
            this.transaction = t;
            this.book = b;
            this.bookTitle = new SimpleStringProperty(b.getTitle());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            this.issueDate = new SimpleStringProperty(sdf.format(t.getIssueDate()));
        }

        public String getBookTitle() { return bookTitle.get(); }
        public String getIssueDate() { return issueDate.get(); }
        public Transaction getTransaction() { return transaction; }
        public Book getBook() { return book; }
    }
}