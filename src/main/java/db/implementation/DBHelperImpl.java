package db.implementation;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import db.DBHelper;
import helper.Config;
import model.MedicineModel;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.LinkedList;

public class DBHelperImpl implements DBHelper {
    private static MongoCollection<Document> collection = null;
    private static MongoDatabase db = null;
    private static MongoClient mongoClient = null;

    public DBHelperImpl() {
        createConnection();
        getDatabase();
        createCollection();
    }

    public MongoClient createConnection() {
        return mongoClient = new MongoClient(Config.DATABASE_HOST, Config.DATABASE_PORT); // Create new instance of Mongo client
//        return mongoClient = new MongoClient(new MongoClientURI(Config.MONGO_URI)); // Create new instance of Mongo client
    }

    public void closeConnection() {
        if (getClient() != null) {
            getClient().close();
        }
    }

    public MongoClient getClient() {
        return mongoClient;
    }

    public MongoCollection<Document> createCollection() {
        if (!isCollectionExists(Config.COLLECTION_NAME)) { // Check if collection already exists
            db.createCollection(Config.COLLECTION_NAME); // Create new collection
        }

        collection = db.getCollection(Config.COLLECTION_NAME); // Choose collection

        return getCollection();
    }

    public MongoDatabase getDatabase() {
        return db = mongoClient.getDatabase(Config.DATABASE_NAME); // Get database
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }

    public LinkedList<MedicineModel> getAllDocuments() {
        LinkedList<MedicineModel> documents = new LinkedList<MedicineModel>();

        for (Document document : getCollection().find().sort(new Document(Config.DB.COLUMN_NAME, 1))) {
            documents.add(MedicineModel.fromMongoDocument(document));
        }

        return documents;
    }

    public LinkedList<MedicineModel> getAllDocuments(String sortFilter) {
        LinkedList<MedicineModel> documents = new LinkedList<MedicineModel>();

        for (Document document : getCollection().find().sort(new Document(sortFilter, 1))) {
            documents.add(MedicineModel.fromMongoDocument(document));
        }

        return documents;
    }

    public MedicineModel getDocumentById(String id) {
        final MedicineModel[] model = {null};

        getCollection().find(new Document(Config.DB.COLUMN_ID, new ObjectId(id))).forEach(new Block<Document>() {
            public void apply(Document document) {
                model[0] = MedicineModel.fromMongoDocument(document);
            }
        });

        return model[0];
    }

    public boolean isCollectionExists(final String collectionName) {
        MongoIterable<String> collectionNames = db.listCollectionNames();
        for (final String name : collectionNames) {
            if (name.equalsIgnoreCase(collectionName)) {
                return true;
            }
        }
        return false;
    }

    public void deleteDocument(String docId) {
        getCollection().deleteOne(new Document(Config.DB.COLUMN_ID, new ObjectId(docId)));
    }

    public void deleteDocuments(String[] ids) {
        for (int i = 0; i < ids.length; i++) {
            getCollection().deleteOne(new Document(Config.DB.COLUMN_ID, new ObjectId(ids[i])));
        }
    }

    public void addDocument(final MedicineModel model) {
        Document dbModel = new Document(Config.DB.COLUMN_NAME, model.getMedicineName())
                .append(Config.DB.COLUMN_INDICATION, model.getIndication())
                .append(Config.DB.COLUMN_CONTRAINDICATION, model.getContraindication())
                .append(Config.DB.COLUMN_SALES_FORM, model.getSalesForm());

        getCollection().insertOne(dbModel);
    }

    public void updateDocument(final String docId, MedicineModel model) {
        getCollection().updateOne(new Document(Config.DB.COLUMN_ID, new ObjectId(docId)),
                new Document("$set", new Document(Config.DB.COLUMN_NAME, model.getMedicineName())
                        .append(Config.DB.COLUMN_INDICATION, model.getIndication())
                        .append(Config.DB.COLUMN_CONTRAINDICATION, model.getContraindication())
                        .append(Config.DB.COLUMN_SALES_FORM, model.getSalesForm())));
    }
}