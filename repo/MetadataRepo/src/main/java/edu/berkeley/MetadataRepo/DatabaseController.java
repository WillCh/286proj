package edu.berkeley.MetadataRepo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * A class that connects to a MongoDB database and handles all interactions with the database
 */
public class DatabaseController
{

    private static MongoClient mongoClient = new MongoClient( "localhost" );
    private static MongoDatabase database = mongoClient.getDatabase("test");

    public static void dump()
    {
        MongoCollection<Document> collection = database.getCollection("testData");
        for (Document d : collection.find())
            System.out.println(d.toJson());
    }

    public static void commit(String file, String json)
    {
    }

    public static void show(String file)
    {
    }
}
