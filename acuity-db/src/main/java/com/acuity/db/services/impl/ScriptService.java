package com.acuity.db.services.impl;

import com.acuity.db.AcuityDB;
import com.acuity.db.domain.vertex.impl.AcuityAccount;
import com.acuity.db.domain.vertex.impl.scripts.Script;
import com.acuity.db.services.DBCollectionService;
import com.arangodb.entity.BaseDocument;
import com.arangodb.model.DocumentUpdateOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zachary Herridge on 8/7/2017.
 */
public class ScriptService extends DBCollectionService<Script> {

    private static final ScriptService INSTANCE = new ScriptService();

    public static ScriptService getInstance() {
        return INSTANCE;
    }

    public ScriptService() {
        super(AcuityDB.DB_NAME, "Script", Script.class);
    }



    public List<Script> getByAccess(String acuityID, int accessLevel, int rank) {
        String query = "for script in Script\n" +
                "filter script.ownerID == @acuityID || script.accessLevel == @accessLevel || @rankAccess\n" +
                "return merge(script, {\n" +
                "    \"author\" : document(script.ownerID),\n" +
                "    \"added\" : document(AddedScript, concat(@acuityKey, \":\", script._key))\n" +
                "})\n";


        Map<String, Object> args = new HashMap<>();
        args.put("acuityID", acuityID);
        args.put("acuityKey", acuityID.isEmpty() ? "" : acuityID.split("/")[1]);
        args.put("accessLevel", accessLevel);
        args.put("rankAccess", rank == AcuityAccount.Rank.ADMIN);
        return getDB().query(query, args, null, Script.class).asListRemaining();
    }

    public void setAttribute(String scriptKey, String attributeName, Object value) {
        final BaseDocument document = new BaseDocument();
        document.addAttribute(attributeName, value);
        getCollection().updateDocument(scriptKey, document, new DocumentUpdateOptions().keepNull(true));
    }
}
