package com.acuity.db.domain.vertex.impl.scripts;

import com.acuity.db.domain.edge.impl.AddedScript;
import com.acuity.db.domain.vertex.Vertex;
import com.acuity.db.domain.vertex.impl.AcuityAccount;
import com.google.common.base.MoreObjects;

import java.time.LocalDateTime;
import java.util.HashSet;

/**
 * Created by Zachary Herridge on 8/7/2017.
 */
public class Script extends Vertex {

    private String ownerID;
    private String title;
    private String category;
    private String desc;
    private LocalDateTime creationTimestamp = LocalDateTime.now();
    private LocalDateTime lastUpdateTimestamp;

    private int scriptRev = 1;
    private int accessLevel = Access.PUBLIC.getCode();

    private String jarURL;

    //Joins
    private AcuityAccount author;
    private AddedScript added;

    private HashSet<String> localPaths = new HashSet<>();

    public Script(String ownerID, String title, String category, String desc, int accessLevel) {
        this.ownerID = ownerID;
        this.title = title;
        this.category = category;
        this.desc = desc;
        this.accessLevel = accessLevel;
    }

    public Script() {
    }

    public AddedScript getAdded() {
        return added;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public String getDesc() {
        return desc;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public int getScriptRev() {
        return scriptRev;
    }

    public AcuityAccount getAuthor() {
        return author;
    }

    public HashSet<String> getLocalPaths() {
        return localPaths;
    }

    public LocalDateTime getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(LocalDateTime lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public String getJarURL() {
        return jarURL;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ownerID", ownerID)
                .add("title", title)
                .add("category", category)
                .add("desc", desc)
                .add("creationTimestamp", creationTimestamp)
                .add("lastUpdateTimestamp", lastUpdateTimestamp)
                .add("scriptRev", scriptRev)
                .add("accessLevel", accessLevel)
                .add("jarURL", jarURL)
                .add("author", author)
                .add("added", added)
                .add("localPaths", localPaths)
                .toString();
    }

    public enum  Access {
        PUBLIC(1, "Public"),
        PRIVATE(2, "Private"),
        REMOVED_BY_OWNER(3, "Removed"),
        REMOVED_BY_ADMIN(4, "Removed");

        private int code;
        private String displayName;

        Access(int code, String displayName) {
            this.code = code;
            this.displayName = displayName;
        }

        public int getCode() {
            return code;
        }

        public String getDisplayName() {
            return displayName;
        }

        public static Access getByCode(int code){
            for (Access access : values()) {
                if (access.getCode() == code) return access;
            }
            return null;
        }
    }
}
