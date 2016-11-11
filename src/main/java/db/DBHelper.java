package db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.istack.internal.Nullable;
import model.MedicineModel;
import org.bson.Document;

import java.util.ArrayList;
import java.util.LinkedList;

public interface DBHelper {
    @Nullable
    MongoClient createConnection();

    void closeConnection();

    @Nullable
    MongoDatabase getDatabase();

    @Nullable
    MongoClient getClient();

    @Nullable
    MongoCollection<Document> createCollection();

    @Nullable
    MongoCollection<Document> getCollection();

    boolean isCollectionExists(final String collectionName);

    @Nullable
    LinkedList<MedicineModel> getAllDocuments();

    MedicineModel getDocumentById(final String id);

    void addDocument(final MedicineModel model);

    void deleteDocument(final String docId);

    void deleteDocuments(final String[] ids);

    void updateDocument(final String docId, final MedicineModel model);
}
