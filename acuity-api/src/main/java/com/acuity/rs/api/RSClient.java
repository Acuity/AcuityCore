package com.acuity.rs.api;

import com.acuity.api.rs.wrappers.engine.Client;
import java.lang.String;

//Generated

public interface RSClient extends RSGameEngine {

    //Static field.
    int getAudioEffectCount();

    //Static field.
    RSAudioEffect[] getAudioEffects();

    //Static field.
    int getBaseScenceX();

    //Static field.
    int getBaseSceneY();

    //Static field.
    int[] getBoostedSkillLevels();

    //Static field.
    int getCameraPitch();

    //Static field.
    int getCameraX();

    //Static field.
    int getCameraY();

    //Static field.
    int getCameraYaw();

    //Static field.
    int getCameraZ();

    //Static field.
    int getCanvasHeight();

    //Static field.
    int getCanvasWidth();

    //Static field.
    RSChatMessages getChatMessages();

    //Static field.
    String getClanChatName();

    //Static field.
    String getClanChatOwner();

    //Static field.
    byte getClanChatRank();

    //Static field.
    int getClanMateCount();

    //Static field.
    RSClanMember[] getClanMates();

    //Static field.
    int getClickX();

    //Static field.
    int getClickY();

    //Static field.
    RSCollisionData[] getCollisionMaps();

    //Static field.
    int getCurrentWorld();

    //Static field.
    int getCurrentWorldMask();

    //Static field.
    int getCursorState();

    //Static field.
    int getDestinationX();

    //Static field.
    int getDestinationY();

    //Static field.
    int getDrawingAreaBottom();

    //Static field.
    int getDrawingAreaHeight();

    //Static field.
    int[] getDrawingAreaPixels();

    //Static field.
    int getDrawingAreaRight();

    //Static field.
    int getDrawingAreaTop();

    //Static field.
    int getDrawingAreaWidth();

    //Static field.
    int getEngineCycle();

    //Static field.
    RSFont getFont_p12full();

    //Static field.
    int getFps();

    //Static field.
    int getFriendCount();

    //Static field.
    RSFriendsListMember[] getFriends();

    //Static field.
    int getGameState();

    //Static field.
    RSGrandExchangeOffer[] getGrandExchangeOffers();

    //Static field.
    RSNodeDeque[][][] getGroundItemDeque();

    //Static field.
    int getHintArrowNpcIndex();

    //Static field.
    int getHintArrowType();

    //Static field.
    int getHintArrowX();

    //Static field.
    int getHintArrowY();

    //Static field.
    int getIgnoreCount();

    //Static field.
    RSIgnoreListMember[] getIgnores();

    //Static field.
    RSIndexData getIndexTextures();

    //Static field.
    RSHashTable getInterfaceFlags();

    //Static field.
    int[] getInterfaceHeights();

    //Static field.
    int[] getInterfaceItemTriggers();

    //Static field.
    RSHashTable getInterfaceNodes();

    //Static field.
    int[] getInterfacePositionX();

    //Static field.
    int[] getInterfacePositionY();

    //Static field.
    int getInterfaceRoot();

    //Static field.
    RSInterfaceComponent[][] getInterfaces();

    //Static field.
    int[] getInterfaceWidths();

    //Static field.
    RSHashTable getItemContainers();

    //Static field.
    RSNodeTable getItemModelCache();

    //Static field.
    int getItemSelectionState();

    //Static field.
    RSNodeTable getItemSpriteCache();

    //Static field.
    long getLastClickTime();

    //Static field.
    int getLatestSelectedItemIndex();

    //Static field.
    String getLatestSelectedItemName();

    //Static field.
    RSPlayer getLocalPlayer();

    //Static field.
    int getLoginIndex();

    //Static field.
    String getLoginResponse1();

    //Static field.
    String getLoginResponse2();

    //Static field.
    String getLoginResponse3();

    //Static field.
    int getLoginState();

    //Static field.
    int getMapRotation();

    //Static field.
    int getMapScale();

    //Static field.
    String[] getMenuActions();

    //Static field.
    int getMenuHeight();

    //Static field.
    int[] getMenuOpcodes();

    //Static field.
    int[] getMenuPrimaryArgs();

    //Static field.
    int getMenuRowCount();

    //Static field.
    int[] getMenuSecondaryArgs();

    //Static field.
    String[] getMenuTargets();

    //Static field.
    int[] getMenuTertiaryArgs();

    //Static field.
    int getMenuWidth();

    //Static field.
    int getMenuX();

    //Static field.
    int getMenuY();

    //Static field.
    int getMinimapOffset();

    //Static field.
    RSModIcon[] getModIcons();

    //Static field.
    int getMouseIdleTime();

    //Static field.
    RSMouseRecorder getMouseRecorder();

    //Static field.
    int getMouseX();

    //Static field.
    int getMouseY();

    //Static field.
    int[] getNpcIndices();

    //Static field.
    RSNodeTable getNpcModelCache();

    //Static field.
    RSNPC[] getNpcs();

    //Static field.
    int getOnCursorCount();

    //Static field.
    int[] getOnSursorUids();

    //Static field.
    RSPacketBuffer getPacket();

    //Static field.
    RSPacketBuffer getPacket2();

    //Static field.
    int getPacketId();

    //Static field.
    int getPacketLength();

    //Static field.
    String getPassword();

    //Static field.
    int getPendingClickX();

    //Static field.
    int getPendingClickY();

    //Static field.
    int getPendingMouseX();

    //Static field.
    RSNodeDeque getPendingSpawns();

    //Static field.
    int getPlane();

    //Static field.
    String[] getPlayerActions();

    //Static field.
    int getPlayerIndex();

    //Static field.
    int[] getPlayerMenuTypes();

    //Static field.
    RSPlayer[] getPlayers();

    //Static field.
    RSNodeDeque getProjectilesDeque();

    //Static field.
    int[] getRealSkillLevels();

    //Static field.
    int getRights();

    //Static field.
    int getRunEnergy();

    //Static field.
    RSScene getSceneGraph();

    //Static field.
    byte[][][] getSceneRenderRules();

    //Static field.
    int getSelectedRegionTileX();

    //Static field.
    int getSelectedRegionTileY();

    //Static field.
    int[] getSkillExperiences();

    //Static field.
    RSSocket getSocket();

    //Static field.
    int getSocketState();

    //Static field.
    int getSpellTargetFlags();

    //Static field.
    RSNodeTable getSpriteCache();

    //Static field.
    int[] getTempVarps();

    //Static field.
    int[][][] getTileHeights();

    //Static field.
    String getUsername();

    //Static field.
    int[] getVarps();

    //Static field.
    int getViewportHeight();

    //Static field.
    int getViewportScale();

    //Static field.
    int getViewportWidth();

    //Static field.
    int getWeight();

    //Static field.
    String getWorldDomain();

    //Static field.
    RSWorld[] getWorlds();

    Client getWrapper();

    //Static field.
    int[][] getXteaKeys();

    //Static method.
    void invokeGameDraw(RSInterfaceComponent var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8);

    //Static method.
    RSItemComposite invokeGetItemDefinition(int var0);

    //Static method.
    void invokeGroundItemSpawned(int var0, int var1);

    //Static method.
    boolean invokeLoadWorlds();

    //Static method.
    void invokeSetRasterBuffer(int var0, int var1, int var2);

    //Static field.
    boolean isDynamicRegion();

    //Static field.
    boolean isLowMemory();

    //Static field.
    boolean isMembersWorld();

    //Static field.
    boolean isMenuOpen();

    //Static field.
    boolean[] isPlayerOptionsPriorities();

    //Static field.
    boolean[][] isRenderArea();

    //Static field.
    boolean isResized();

    //Static field.
    boolean[] isValidInterfaces();

    //Static field.
    boolean isViewportWalking();

    //Static field.
    void setLoginIndex(int loginIndex);

    //Static field.
    void setLoginResponse1(java.lang.String loginResponse1);

    //Static field.
    void setLoginResponse2(java.lang.String loginResponse2);

    //Static field.
    void setLoginResponse3(java.lang.String loginResponse3);

    //Static field.
    void setPassword(java.lang.String password);

    //Static field.
    void setUsername(java.lang.String username);
}
