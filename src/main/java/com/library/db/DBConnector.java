package com.library.db;

    import com.mongodb.ConnectionString;
    import com.mongodb.MongoClientSettings;
    import com.mongodb.client.MongoClient;
    import com.mongodb.client.MongoClients;
    import com.mongodb.client.MongoDatabase;
    import org.bson.codecs.configuration.CodecRegistry;
    import org.bson.codecs.pojo.PojoCodecProvider;

    import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
    import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

    public class DBConnector {
        private static DBConnector instance;
        private final MongoClient mongoClient;
        private final MongoDatabase database;

        private DBConnector() {
            // Configure POJO Codec
            CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
            CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

            // Get URI from Env or default to localhost
            String connectionString = System.getenv("MONGODB_URI");
            if (connectionString == null || connectionString.isEmpty()) {
                connectionString = "mongodb://localhost:27017";
            }

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .codecRegistry(codecRegistry)
                    .build();

            this.mongoClient = MongoClients.create(settings);
            this.database = mongoClient.getDatabase("library");
        }

        public static synchronized DBConnector getInstance() {
            if (instance == null) {
                instance = new DBConnector();
            }
            return instance;
        }

        public MongoDatabase getDatabase() {
            return database;
        }

        public void close() {
            if (mongoClient != null) {
                mongoClient.close();
                System.out.println("MongoDB Connection Closed.");
            }
        }
    }
