package com.acuity.client;

import com.acuity.api.AcuityInstance;
import com.acuity.api.Events;
import com.acuity.api.rs.events.impl.drawing.InGameDrawEvent;
import com.acuity.api.rs.query.Interfaces;
import com.acuity.api.rs.query.Npcs;
import com.acuity.api.rs.utils.ContextMenu;
import com.acuity.client.gui.AcuityBotFrame;
import com.google.common.eventbus.Subscribe;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

/**
 * Created by Zach on 5/31/2017.
 */
public class ClientBootstrap {

    private static boolean mesh = false;
    private static int index = 28;

    @Subscribe
    public void testDraw(InGameDrawEvent event){
        Npcs.getNearest(npc -> true).ifPresent(npc -> {
            npc.projectToScreen().ifPresent(screenPolygon -> {
                event.getGraphics().drawPolygon(screenPolygon.toPolygon());
            });
        });

        Interfaces.getLoaded(548, index).ifPresent(interfaceComponent -> {
            interfaceComponent.projectToScreen().ifPresent(screenPolygon -> {
                event.getGraphics().drawPolygon(screenPolygon.toPolygon());
            });
        });
        event.getGraphics().drawString(ContextMenu.getCurrentHotAction(), 300, 300);
        List<String> collect = ContextMenu.getRows();
        for (int i = 0; i < ContextMenu.getRowCount(); i++) {
            event.getGraphics().drawString(collect.get(i), 300, 350 + (15 * i));
        }

        AcuityInstance.getAppletManager().getMouseMiddleMan().getMousePosition().ifPresent(point -> event.getGraphics().fillOval(point.x - 3, point.y - 3, 6, 6));
    }

    @Subscribe
    public void testKeyEvent(KeyEvent e){
        if (e.getKeyChar() == 'c' && e.getID() == KeyEvent.KEY_TYPED){
            Npcs.getNearest("Man").ifPresent(npc -> {
                npc.interact("Talk-to");
            });
        }
        else if (e.getKeyChar() == 'a' && e.getID() == KeyEvent.KEY_TYPED){
            mesh = !mesh;
        }
        else if (e.getKeyChar() == 'r' && e.getID() == KeyEvent.KEY_TYPED){
            Interfaces.getLoaded(548, index).ifPresent(interfaceComponent -> interfaceComponent.interact(""));
        }
        else if (e.getKeyChar() == 't' && e.getID() == KeyEvent.KEY_TYPED){
            index++;
        }
        else if (e.getKeyChar() == 'g' && e.getID() == KeyEvent.KEY_TYPED){
            index--;
        }
        else if (e.getKeyChar() == 'n' && e.getID() == KeyEvent.KEY_TYPED){
            ContextMenu.close();
        }
    }

    public ClientBootstrap() {
        EventQueue.invokeLater(() -> {
            try {
                AcuityInstance.init(new File("C:\\Users\\zgh\\IdeaProjects\\AcuityCore\\acuity-api\\src\\main\\resources\\Injected Gamepack.jar"));
                final AcuityBotFrame botFrame = new AcuityBotFrame(AcuityInstance.getAppletManager().getClient().getApplet());
                botFrame.setVisible(true);
                AcuityInstance.boot();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Events.getRsEventBus().register(this);
        Events.getAcuityEventBus().register(this);
    }

    public static void main(String[] args) {
        new ClientBootstrap();
    }
}