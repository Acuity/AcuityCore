package com.acuity.db.domain.vertex.impl;

import com.acuity.db.domain.vertex.Vertex;
import com.acuity.db.util.Json;
import com.google.common.base.MoreObjects;

import java.time.LocalDateTime;

/**
 * Created by Zachary Herridge on 8/3/2017.
 */
public class MessagePackage extends Vertex {

    private LocalDateTime creationTimestamp = LocalDateTime.now();
    private LocalDateTime insertTimestamp;
    private String destinationKey;

    private int messageType = Type.UNKNOWN;

    private String bodyType;
    private String bodyJSON;

    public MessagePackage() {

    }

    public MessagePackage(int messageType, String destinationKey) {
        this.destinationKey = destinationKey;
        this.messageType = messageType;
    }

    public String getDestinationKey() {
        return destinationKey;
    }

    public <T> T getBodyAs(Class<T> tClass){
        return Json.GSON.fromJson(bodyJSON, tClass);
    }

    public MessagePackage setBody(Object object){
        this.bodyType = "class:" + object.getClass().getName();
        this.bodyJSON = Json.GSON.toJson(object);
        return this;
    }

    public int getMessageType() {
        return messageType;
    }

    public MessagePackage setBody(String bodyType, String bodyJSON) {
        this.bodyType = bodyType;
        this.bodyJSON = bodyJSON;
        return this;
    }

    public void setInsertTimestamp(LocalDateTime insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("creationTimestamp", creationTimestamp)
                .add("insertTimestamp", insertTimestamp)
                .add("destinationKey", destinationKey)
                .add("messageType", messageType)
                .add("bodyType", bodyType)
                .add("bodyJSON", bodyJSON)
                .toString();
    }

    public interface Type {
        int UNKNOWN = 0;
        int LOGIN = 1;
        int GOOD_LOGIN = 2;
        int BAD_LOGIN = 3;
        int DIRECT = 4;
        int ACCOUNT_ASSIGNMENT_CHANGE = 5;
        int MACHINE_INFO = 6;
    }
}
