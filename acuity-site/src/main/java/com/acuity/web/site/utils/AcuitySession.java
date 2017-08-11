package com.acuity.web.site.utils;

import com.acuity.db.domain.vertex.impl.AcuityAccount;
import com.vaadin.server.VaadinSession;

import java.util.Optional;

/**
 * Created by Zachary Herridge on 8/11/2017.
 */
public class AcuitySession {

    public static Optional<AcuityAccount> getAccount(){
        return Optional.ofNullable(VaadinSession.getCurrent().getAttribute(AcuityAccount.class));
    }
}
