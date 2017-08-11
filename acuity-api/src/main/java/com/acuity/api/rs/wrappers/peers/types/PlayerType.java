package com.acuity.api.rs.wrappers.peers.types;

import com.acuity.api.annotations.ClientInvoked;
import com.acuity.api.rs.wrappers.common.PlayerEquipment;
import com.acuity.rs.api.RSPlayerType;
import com.google.common.base.Preconditions;


/**
 * Created by Zachary Herridge on 6/27/2017.
 */
public class PlayerType {

    private RSPlayerType rsPlayerType;

    @ClientInvoked
    public PlayerType(RSPlayerType rsPlayerType) {
        this.rsPlayerType = Preconditions.checkNotNull(rsPlayerType);
    }

    public PlayerEquipment getEquipment(){
        return new PlayerEquipment(rsPlayerType.getEquipmentIDs());
    }

    public boolean isFemale(){
        return rsPlayerType.isFemale();
    }

    public boolean isMale(){
        return !rsPlayerType.isFemale();
    }


    public RSPlayerType getRsPlayerType() {
        return rsPlayerType;
    }
}
