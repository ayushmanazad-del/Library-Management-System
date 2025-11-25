package com.library.model;

import org.bson.types.ObjectId;
import java.util.Date;

public class Transaction {
    private ObjectId id;
    private ObjectId memberId;
    private ObjectId bookId;
    private Date issueDate;
    private Date returnDate;
    private boolean isReturned;

    public Transaction() {}

    public Transaction(ObjectId memberId, ObjectId bookId) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.issueDate = new Date();
        this.isReturned = false;
    }

    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }
    public ObjectId getMemberId() { return memberId; }
    public void setMemberId(ObjectId memberId) { this.memberId = memberId; }
    public ObjectId getBookId() { return bookId; }
    public void setBookId(ObjectId bookId) { this.bookId = bookId; }
    public Date getIssueDate() { return issueDate; }
    public void setIssueDate(Date issueDate) { this.issueDate = issueDate; }
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public boolean isReturned() { return isReturned; }
    public void setReturned(boolean returned) { isReturned = returned; }
}
