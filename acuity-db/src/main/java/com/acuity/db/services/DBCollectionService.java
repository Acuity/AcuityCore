package com.acuity.db.services;

import com.acuity.db.AcuityDB;
import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.model.DocumentUpdateOptions;
import javafx.util.Pair;

import java.util.*;

/**
 * Created by Zachary Herridge on 8/4/2017.
 */
public class DBCollectionService<T> {

    protected final String dbName;
    protected final String dbCollectionName;
    protected Class<T> type;

    public DBCollectionService(String dbName, String dbCollectionName, Class<T> type) {
        this.dbName = dbName;
        this.dbCollectionName = dbCollectionName;
        this.type = type;
    }

    public Optional<T> getByKey(String key){
        return Optional.ofNullable(getCollection().getDocument(key, type));
    }

    public Optional<T> getByID(String id){
        if (id == null) return Optional.empty();
        return Optional.ofNullable(getDB().getDocument(id, type));
    }

    public ArangoDatabase getDB(){
        return AcuityDB.getDB().db(dbName);
    }

    public ArangoCollection getCollection(){
        return getDB().collection(dbCollectionName);
    }

    public String getCollectionID(){
        return getCollection().getInfo().getId();
    }

    public String getCollectionName() {
        return getCollection().getInfo().getName();
    }

    public List<T> getByOwner(String ownerID) {
        String query = "FOR entity IN @@collection " +
                "FILTER entity.ownerID == @ownerID " +
                "RETURN entity";
        Map<String, Object> args = new HashMap<>();
        args.put("ownerID", ownerID);
        args.put("@collection", dbCollectionName);
        ArangoCursor<T> system = getDB().query(query, args, null, type);
        return system.asListRemaining();
    }

    public DocumentCreateEntity<T> insert(T entity) {
        if (entity == null) return null;
        return getCollection().insertDocument(entity);
    }

    public void setField(String key, String field, Object value) {
        final BaseDocument document = new BaseDocument();
        document.addAttribute(field, value);
        getCollection().updateDocument(key, document, new DocumentUpdateOptions().keepNull(true));
    }

    public void setFields(String key, Pair... pairs) {
        final BaseDocument document = new BaseDocument();
        Arrays.stream(pairs).forEach(stringObjectPair -> document.addAttribute(String.valueOf(stringObjectPair.getKey()), stringObjectPair.getValue()));
        getCollection().updateDocument(key, document, new DocumentUpdateOptions().keepNull(true));
    }
}
