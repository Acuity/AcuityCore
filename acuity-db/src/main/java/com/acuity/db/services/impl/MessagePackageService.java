package com.acuity.db.services.impl;

import com.acuity.db.AcuityDB;
import com.acuity.db.domain.vertex.impl.MessagePackage;
import com.acuity.db.services.DBCollectionService;
import com.arangodb.entity.DocumentCreateEntity;

import java.time.LocalDateTime;

/**
 * Created by Zachary Herridge on 8/4/2017.
 */
public class MessagePackageService extends DBCollectionService<MessagePackage> {

    private static final MessagePackageService INSTANCE = new MessagePackageService();

    public static MessagePackageService getInstance() {
        return INSTANCE;
    }

    public MessagePackageService() {
        super(AcuityDB.DB_NAME, "MessagePackage", MessagePackage.class);
    }

    @Override
    public DocumentCreateEntity<MessagePackage> insert(MessagePackage messagePackage){
        if (messagePackage.getDestinationKey() == null) return null;
        messagePackage.setInsertTimestamp(LocalDateTime.now());
        return getCollection().insertDocument(messagePackage);
    }

    public void delete(MessagePackage message){
        delete(message.getKey());
    }

    public void delete(String messageKey){
        getCollection().deleteDocument(messageKey);
    }

}
