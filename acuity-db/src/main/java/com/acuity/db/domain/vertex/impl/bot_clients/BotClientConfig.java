package com.acuity.db.domain.vertex.impl.bot_clients;

import com.acuity.db.domain.vertex.Vertex;
import com.acuity.db.domain.vertex.impl.Proxy;
import com.acuity.db.domain.vertex.impl.scripts.Script;
import com.acuity.db.services.impl.BotClientService;
import com.google.common.base.MoreObjects;

/**
 * Created by Zach on 8/5/2017.
 */
public class BotClientConfig extends Vertex {

    private String ownerID;
    private String botClientID;

    private String assignedScriptID;
    private String assignedProxyID;

    //Joins
    private Script script;
    private Proxy proxy;

    public BotClientConfig(String ownerID, String botClientKey) {
        this._key = botClientKey;
        this.ownerID =  ownerID;
        this.botClientID = BotClientService.getInstance().getCollectionName() + "/" + botClientKey;
    }

    public BotClientConfig() {
    }

    public String getAssignedProxyID() {
        return assignedProxyID;
    }

    public String getAssignedScriptID() {
        return assignedScriptID;
    }

    public String getBotClientID() {
        return botClientID;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public Script getScript() {
        return script;
    }

    public Proxy getProxy() {
        return proxy;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ownerID", ownerID)
                .add("botClientID", botClientID)
                .add("assignedScriptID", assignedScriptID)
                .add("assignedProxyID", assignedProxyID)
                .add("script", script)
                .add("proxy", proxy)
                .toString();
    }
}
