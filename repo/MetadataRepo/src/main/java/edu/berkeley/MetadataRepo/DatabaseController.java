package edu.berkeley.MetadataRepo;


import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        MongoCollection<Document> collection = database.getCollection("testData");

        // Find a document with the given name
        Document fdoc = new Document("file", file);
        FindIterable<Document> found = collection.find(fdoc);

        if (found.iterator().hasNext()) {
            // If a document is found, it should be the only one
            Document doc = found.iterator().next();
            ArrayList<Document> metadataList  = (ArrayList<Document>) doc.get("metadata");

            /*
            // This section of code would show ALL metadata commits ever
            System.out.println("Metadata for " + file + ":");
            for (Document d : metadataList)
                System.out.println(d.toJson());
            */
            System.out.println("Most recent metadata for " + file + ":");
            // Get size of metadata array (last element show be the most up-to-date entry
            int currMetadataIndex = metadataList.size() - 1;
            System.out.println(metadataList.get(currMetadataIndex).toJson());
        }
        else
        {
            System.out.println("No file with that name.");
        }

    }

    /** Using O(n) time to search. Assumes the metadata is at most one degree nested */
    public static void find(String keyWord, String time)
    {
        boolean checkTime = false;
        long startTime = 0;
        long endTime = 0;
        long compareTime = 0;
        Date date;
//        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf  = new SimpleDateFormat("MM/dd/yy");

        if (time != "None") {
            checkTime = true;
            try {
                date = sdf.parse(time);
            } catch (ParseException e) {
                System.out.println("Time should be in MM/dd/yy format.");
                return;
            }
            startTime = date.getTime();
            endTime = startTime + 864000000;
        }
        MongoCollection<Document> collection = database.getCollection("testData");
        for (Document d : collection.find()) {
            ArrayList<Document> metadataList = (ArrayList<Document>) d.get("metadata");
            for (int i = 0; i < metadataList.size(); i++ ) {
                if (checkTime) {
                    compareTime = ((Date) metadataList.get(i).get("timestamp")).getTime();
                    if (compareTime < startTime || compareTime > endTime) break;
                }
                if (metadataList.get(i).get(keyWord) != null) {
                    System.out.println(metadataList.get(i).toJson());
                } else {
                    for(String key : metadataList.get(i).keySet()) {
                        if (metadataList.get(i).get(key).getClass() == Document.class) {
                            if (((Document) metadataList.get(i).get(key)).get(keyWord) != null) {
                                System.out.println(metadataList.get(i).toJson());
                            }
                        }
                    }
                }
            }
        }
    }

    public static void clear()
    {
        MongoCollection<Document> collection = database.getCollection("testData");
        collection.drop();
        database.createCollection("testData");
    }
}
