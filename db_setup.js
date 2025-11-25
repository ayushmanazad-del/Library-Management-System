// Run this in mongosh: mongosh db_setup.js

use library;

db.createCollection("members");
db.createCollection("books");
db.createCollection("transactions");

// Seed Admin User
db.members.insertOne({
    "username": "admin",
    "password": "password123", // In production, hash this!
    "role": "ADMIN",
    "firstName": "Super",
    "lastName": "Admin"
});

// Seed a test book
db.books.insertOne({
    "title": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "isbn": "9780743273565",
    "availableCopies": 5,
    "totalCopies": 5
});

print("Database 'library' initialized with Admin user and test data.");