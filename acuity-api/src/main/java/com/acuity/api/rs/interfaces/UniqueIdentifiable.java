package com.acuity.api.rs.interfaces;

import com.acuity.api.rs.utils.UIDs;

public interface UniqueIdentifiable extends Identifiable{

    UIDs.UID getUID();

    default int getID(){
        return getUID().getEntityID();
    }
}
