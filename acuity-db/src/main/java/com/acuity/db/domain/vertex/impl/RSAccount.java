package com.acuity.db.domain.vertex.impl;

import com.acuity.db.domain.common.EncryptedString;
import com.acuity.db.domain.common.tracking.RSAccountState;
import com.acuity.db.domain.vertex.Vertex;
import com.google.common.base.MoreObjects;

import java.time.LocalDateTime;
import java.util.Optional;

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

    private RSAccountState state;
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

    public Optional<RSAccountState> getState() {
        return Optional.ofNullable(state);
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
        return MoreObjects.toStringHelper(this)
                .add("ownerID", ownerID)
                .add("email", email)
                .add("ign", ign)
                .add("banned", banned)
                .add("locked", locked)
                .add("wrongLogin", wrongLogin)
                .add("creationTime", creationTime)
                .add("password", password)
                .toString();
    }
}
