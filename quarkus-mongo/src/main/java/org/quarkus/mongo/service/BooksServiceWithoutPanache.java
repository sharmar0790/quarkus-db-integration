package org.quarkus.mongo.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.quarkus.mongo.model.Books;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

@ApplicationScoped
public class BooksServiceWithoutPanache {

    @Inject
    MongoClient mongoClient;

    public List<Books> list(){
        List<Books> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Books Books = new Books();
                Books.setName(document.getString("name"));
                Books.setId(document.getLong("id"));
                list.add(Books);
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    public void add(Books Books){
        Document document = new Document()
                .append("name", Books.getName())
                .append("id", Books.getId());
        getCollection().insertOne(document);
    }

    public void update(Books Books){
        Bson filter = eq("id", Books.getId());
        Bson updateOperation = set("name", Books.getName());
        getCollection().updateOne(filter, updateOperation);
    }

    public void delete(Books Books){
        Bson filter = eq("id", Books.getId());
        getCollection().deleteOne(filter);
    }
    private MongoCollection getCollection(){
        return mongoClient.getDatabase("Books").getCollection("Books");
    }
}
