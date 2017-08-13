package com.acuity.api.rs.utils;

import com.acuity.api.input.direct.keyboard.Keyboard;
import com.acuity.api.rs.query.Interfaces;
import com.acuity.api.rs.wrappers.peers.interfaces.InterfaceComponent;

import java.awt.event.KeyEvent;
import java.util.Optional;

/**
 * Created by Zachary Herridge on 7/27/2017.
 */
public class Tabs {

    public enum Tab {

        // TODO: 7/27/2017 Change the hot keys to be based on the varps in indexs 1224 and 1226
        CLAN_CHAT(KeyEvent.VK_F7, 28),
        FRIENDS_LIST(KeyEvent.VK_F8, 29),
        IGNORE_LIST(KeyEvent.VK_F9, 30),
        LOGOUT(null, 31),
        OPTIONS(KeyEvent.VK_F10, 32),
        EMOTES(KeyEvent.VK_F11, 33),
        MUSIC_PLAYER(KeyEvent.VK_F12, 34),
        COMBAT(KeyEvent.VK_F1, 45),
        STATS(KeyEvent.VK_F2, 46),

        QUEST_LIST(KeyEvent.VK_F3, 47),
        INVENTORY(KeyEvent.VK_ESCAPE, 48),
        EQUIPMENT(KeyEvent.VK_F4, 49),
        PRAYER(KeyEvent.VK_F5, 50),
        MAGIC(KeyEvent.VK_F6, 51);

        private final int index;
        private final Integer hotkey;

        Tab(Integer hotkey, int index) {
            this.hotkey = hotkey;
            this.index = index;
        }

        public static Tab getOpen() {
            for (Tab tab : values()) {
                if (tab.isOpen()) {
                    return tab;
                }
            }
            return null;
        }

        public Optional<InterfaceComponent> getComponent() {
            return Interfaces.getLoaded(548, index);
        }

        public boolean isOpen() {
            return getComponent().map(m -> m.getSpriteID() != -1).orElse(false);
        }

        public ActionResult open() {
            if (isOpen()) {
                return ActionResult.SUCCESS;
            }
            if (Varps.get(281, 0) >= 1000 && hotkey != null) {
                Keyboard.getDriver().type((char) (int) hotkey);
            }
            else {
                // TODO: 7/11/2017  getComponent().ifPresent(component -> AcuityInstance.getAppletManager().getMouseMiddleMan().dispatchClick(component.getPoint(), true));
            }
            return isOpen() ? ActionResult.SUCCESS : ActionResult.FAILURE;
        }
    }
}
