package com.acuity.db.domain.vertex.impl;

import com.acuity.db.domain.common.EncryptedString;
import com.acuity.db.domain.vertex.Vertex;

import java.time.LocalDateTime;

/**
 * Created by Zachary Herridge on 8/3/2017.
 */
public class RSAccount extends Vertex {

    private String ownerID;
    private String email;
    private String ign;
    private boolean banned;
    private boolean locked;
    private boolean wrongLogin;
    private LocalDateTime creationTime = LocalDateTime.now();

    private EncryptedString password;

    public RSAccount(String ownerID, String email, String ign, EncryptedString password) {
        this.ownerID = ownerID;
        this.email = email;
        this.ign = ign;
        this.password = password;
    }

    public RSAccount() {
    }

    public String getOwnerID() {
        return ownerID;
    }

    public String getEmail() {
        return email;
    }

    public String getIgn() {
        return ign;
    }

    public EncryptedString getPassword() {
        return password;
    }

    public boolean isBanned() {
        return banned;
    }

    public boolean isLocked() {
        return locked;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public boolean isWrongLogin() {
        return wrongLogin;
    }

    @Override
    public String toString() {
        return "RSAccount{" +
                "_key='" + _key + '\'' +
                ", email='" + email + '\'' +
                ", ign='" + ign + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
