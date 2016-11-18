package db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.MedicineModel;
import org.bson.Document;

import java.util.LinkedList;

public interface DBHelper {
    MongoClient createConnection();

    void closeConnection();

    MongoDatabase getDatabase();

    MongoClient getClient();

    MongoCollection<Document> createCollection();

    MongoCollection<Document> getCollection();

    boolean isCollectionExists(final String collectionName);

    LinkedList<MedicineModel> getAllDocuments();

    MedicineModel getDocumentById(final String id);

    void addDocument(final MedicineModel model);

    void deleteDocument(final String docId);

    void deleteDocuments(final String[] ids);

    void updateDocument(final String docId, final MedicineModel model);
}
