package com.library.repository;

    import com.library.db.DBConnector;
    import com.library.model.Member;
    import com.mongodb.client.MongoCollection;
    import com.mongodb.client.model.Filters;

    public class MemberRepository {
        private final MongoCollection<Member> collection;

        public MemberRepository() {
            this.collection = DBConnector.getInstance().getDatabase().getCollection("members", Member.class);
        }

        public Member findByUsername(String username) {
            return collection.find(Filters.eq("username", username)).first();
        }
    }