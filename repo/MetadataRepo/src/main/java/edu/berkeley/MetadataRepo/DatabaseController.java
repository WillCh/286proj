package edu.berkeley.MetadataRepo;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;

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

    public static void commit(String file, String jsonMetadata)
    {
        MongoCollection<Document> collection = database.getCollection("testData");

        // Find a document with the given name
        Document fdoc = new Document("file", file);
        FindIterable<Document> found = collection.find(fdoc);

        if (found.iterator().hasNext())
        {
            // If a document is found, it should be the only one
            Document doc = found.iterator().next();
            ArrayList<Document> metadataList  = (ArrayList<Document>) doc.get("metadata");
            Document metadata = Document.parse(jsonMetadata);
            metadata.append("timestamp", new Date());
            metadataList.add(metadata);

            // Updates the metadata
            collection.updateOne(fdoc, new Document("$set", new Document("metadata", metadataList)));
        }
        else
        {
            // Document not found, creating new document
            Document doc = new Document();
            doc.append("file", file);
            ArrayList<Document> metadataList = new ArrayList<Document>();
            Document metadata = Document.parse(jsonMetadata);
            metadata.append("timestamp", new Date());
            metadataList.add(metadata);
            doc.append("metadata", metadataList);

            // Inserts the new document to the db
            collection.insertOne(doc);
        }
    }

    public static void show(String file)
    {
    }

    public static void clear()
    {
        MongoCollection<Document> collection = database.getCollection("testData");
        collection.drop();
        database.createCollection("testData");
    }
}
