package com.acuity.db.domain.vertex.impl.bot_clients;

import com.acuity.db.domain.vertex.Vertex;
import com.acuity.db.domain.vertex.impl.Machine;
import com.acuity.db.domain.vertex.impl.Proxy;
import com.acuity.db.domain.vertex.impl.RSAccount;
import com.acuity.db.domain.vertex.impl.scripts.Script;
import com.acuity.db.services.impl.AcuityAccountService;

import java.time.LocalDateTime;

/**
 * Created by Zachary Herridge on 8/3/2017.
 */
public class BotClient extends Vertex {

    private String ownerID;
    private LocalDateTime connectionTime = LocalDateTime.now();

    private String machineID;

    private String ip;

    //Joins
    private RSAccount assignedAccount;
    private Script assignedScript;
    private BotClientConfig clientConfig;
    private Proxy assignedProxy;
    private Machine machine;

    public BotClient(String key, String ownerKey, String machineID, String ip) {
        this._key = key;
        this.ip = ip;
        this.ownerID = AcuityAccountService.getInstance().getCollectionName() + "/" + ownerKey;
        this.machineID = machineID;
    }

    public BotClient() {
    }

    public String getOwnerID() {
        return ownerID;
    }

    public LocalDateTime getConnectionTime() {
        return connectionTime;
    }

    public Script getAssignedScript() {
        return assignedScript;
    }

    public RSAccount getAssignedAccount() {
        return assignedAccount;
    }

    public Machine getMachine() {
        return machine;
    }

    public String getIP() {
        return ip;
    }

    public String getMachineID() {
        return machineID;
    }

    public Proxy getAssignedProxy() {
        return assignedProxy;
    }

    public BotClientConfig getClientConfig() {
        return clientConfig;
    }

    @Override
    public String toString() {
        return "BotClient{" +
                "ownerID='" + ownerID + '\'' +
                ", connectionTime=" + connectionTime +
                '}';
    }
}
