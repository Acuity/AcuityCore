package com.acuity.db.services.impl;

import com.acuity.db.AcuityDB;
import com.acuity.db.domain.vertex.impl.bot_clients.BotClient;
import com.acuity.db.services.DBCollectionService;
import com.arangodb.entity.DocumentCreateEntity;
import com.arangodb.model.DocumentCreateOptions;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Zachary Herridge on 8/4/2017.
 */
public class BotClientService extends DBCollectionService<BotClient> {

    private static final BotClientService INSTANCE = new BotClientService();

    public static BotClientService getInstance() {
        return INSTANCE;
    }

    public BotClientService() {
        super(AcuityDB.DB_NAME, "BotClient", BotClient.class);
    }

    public List<BotClient> getJoinedByOwnerID(String ownerID){
        String query = "for client in BotClient\n" +
                "    filter client.ownerID == @ownerID\n" +
                "    let assignment = (\n" +
                "        for edge in AssignedTo\n" +
                "        filter edge._to == client._id\n" +
                "        limit 1\n" +
                "        return edge\n" +
                "        )\n" +
                "    let config = document(BotClientConfig, client._key)\n" +
                "  return merge(client, {\n" +
                "        \"assignedAccount\" : document(first(assignment)._from),\n" +
                "        \"clientConfig\" : config,\n" +
                "        \"assignedScript\" : document(config.assignedScriptID),\n" +
                "        \"assignedProxy\" : document(config.assignedProxyID),\n" +
                "        \"machine\" : document(client.machineID)\n" +
                "        })";
        return getDB().query(query, Collections.singletonMap("ownerID", ownerID), null, BotClient.class).asListRemaining();
    }

    public Optional<BotClient> getJoinedByID(String clientID){
        String query = "let client = document(@clientID)\n" +
                "let assignment = (\n" +
                "    for edge in AssignedTo\n" +
                "    filter edge._to == client._id\n" +
                "    limit 1\n" +
                "    return edge\n" +
                "    )\n" +
                "let config = document(BotClientConfig, client._key)\n" +
                "return merge(client, {\n" +
                "    \"assignedAccount\" : document(first(assignment)._from),\n" +
                "    \"clientConfig\" : config,\n" +
                "    \"assignedScript\" : document(config.assignedScriptID),\n" +
                "    \"assignedProxy\" : document(config.assignedProxyID),\n" +
                "    \"machine\" : document(client.machineID)\n" +
                "    })";

        Stream<BotClient> clientID1 = getDB().query(query, Collections.singletonMap("clientID", clientID), null, BotClient.class).asListRemaining().stream();
        return clientID1.filter(Objects::nonNull).findAny();
    }

    public Optional<BotClient> registerClient(String key, String ownerID, String machineID){
        DocumentCreateEntity<BotClient> createEntity = getCollection().insertDocument(new BotClient(key, ownerID, machineID), new DocumentCreateOptions().returnNew(true));
        return Optional.ofNullable(createEntity.getNew());
    }

    public void removeClient(String botClientKey){
        getCollection().deleteDocument(botClientKey);
        RSAccountAssignmentService.getInstance().removeByToID(getCollectionName() + "/" + botClientKey);
        BotClientConfigService.getInstance().removeConfig(botClientKey);
    }
}
