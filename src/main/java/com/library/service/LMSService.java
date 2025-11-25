package com.library.service;

import com.library.model.Book;
import com.library.model.Member;
import com.library.model.Transaction;
import com.library.repository.BookRepository;
import com.library.repository.MemberRepository;
import com.library.repository.TransactionRepository;
import org.bson.types.ObjectId;

import java.util.List;

public class LMSService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final TransactionRepository transactionRepository;

    public LMSService() {
        this.bookRepository = new BookRepository();
        this.memberRepository = new MemberRepository();
        this.transactionRepository = new TransactionRepository();
    }

    public Member authenticateUser(String username, String password) {
        Member member = memberRepository.findByUsername(username);
        if (member != null && member.getPassword().equals(password)) {
            return member;
        }
        return null;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public Book getBook(ObjectId id) {
        return bookRepository.findById(id);
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public boolean issueBook(ObjectId memberId, ObjectId bookId) {
        if (bookRepository.decrementCopies(bookId)) {
            Transaction transaction = new Transaction(memberId, bookId);
            transactionRepository.save(transaction);
            return true;
        }
        return false;
    }

    public void returnBook(ObjectId transactionId, ObjectId bookId) {
        transactionRepository.markReturned(transactionId);
        bookRepository.incrementCopies(bookId);
    }
    
    public List<Transaction> getActiveTransactions(ObjectId memberId) {
        return transactionRepository.findActiveByMemberId(memberId);
    }
}