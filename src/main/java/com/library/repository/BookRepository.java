package com.library.repository;

import com.library.db.DBConnector;
import com.library.model.Book;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private final MongoCollection<Book> collection;

    public BookRepository() {
        this.collection = DBConnector.getInstance().getDatabase().getCollection("books", Book.class);
    }

    public List<Book> findAll() {
        return collection.find().into(new ArrayList<>());
    }
    
    public Book findById(ObjectId id) {
        return collection.find(Filters.eq("_id", id)).first();
    }

    public void save(Book book) {
        collection.insertOne(book);
    }

    // Atomic decrement operation
    public boolean decrementCopies(ObjectId bookId) {
        var result = collection.updateOne(
                Filters.and(Filters.eq("_id", bookId), Filters.gt("availableCopies", 0)),
                Updates.inc("availableCopies", -1)
        );
        return result.getModifiedCount() > 0;
    }

    // Atomic increment operation
    public void incrementCopies(ObjectId bookId) {
        collection.updateOne(
                Filters.eq("_id", bookId),
                Updates.inc("availableCopies", 1)
        );
    }
}