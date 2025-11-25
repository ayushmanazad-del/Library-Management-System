package com.library.repository;

import com.library.db.DBConnector;
import com.library.model.Transaction;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionRepository {
    private final MongoCollection<Transaction> collection;

    public TransactionRepository() {
        this.collection = DBConnector.getInstance().getDatabase().getCollection("transactions", Transaction.class);
    }

    public void save(Transaction transaction) {
        collection.insertOne(transaction);
    }
    
    // Find active (not returned) transactions for a specific member
    public List<Transaction> findActiveByMemberId(ObjectId memberId) {
        return collection.find(
            Filters.and(
                Filters.eq("memberId", memberId),
                Filters.eq("isReturned", false)
            )
        ).into(new ArrayList<>());
    }

    public void markReturned(ObjectId transactionId) {
        collection.updateOne(
                Filters.eq("_id", transactionId),
                Updates.combine(
                        Updates.set("isReturned", true),
                        Updates.set("returnDate", new Date())
                )
        );
    }
}