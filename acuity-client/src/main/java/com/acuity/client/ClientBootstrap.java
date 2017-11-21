package com.acuity.client;

import com.acuity.api.AcuityInstance;
import com.acuity.api.Events;
import com.acuity.api.input.SmartActions;
import com.acuity.api.rs.events.impl.drawing.InGameDrawEvent;
import com.acuity.api.rs.query.Npcs;
import com.acuity.api.rs.query.SceneElements;
import com.acuity.api.rs.utils.Camera;
import com.acuity.api.rs.utils.ContextMenu;
import com.acuity.api.rs.utils.LocalPlayer;
import com.acuity.api.rs.utils.Projection;
import com.acuity.api.rs.wrappers.common.locations.screen.ScreenPolygon;
import com.acuity.api.rs.wrappers.peers.rendering.Model;
import com.acuity.client.gui.AcuityBotFrame;
import com.acuity.common.util.AcuityDir;
import com.google.common.eventbus.Subscribe;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Zach on 5/31/2017.
 */
public class ClientBootstrap {

    private static boolean mesh = false;

    @Subscribe
    public void testDraw(InGameDrawEvent event){
        if (ContextMenu.isOpen()){
            for (int i = 0; i < ContextMenu.getRowCount(); i++) {
                ContextMenu.getBounds(i).ifPresent(screenLocationShape -> event.getGraphics().drawPolygon(screenLocationShape.getPolygon()));
            }
        }

        event.getGraphics().drawString(ContextMenu.getCurrentHotAction(), 300, 300);

        List<String> collect = ContextMenu.getItems();
        for (int i = 0; i < ContextMenu.getRowCount(); i++) {
            event.getGraphics().drawString(collect.get(i), 300, 350 + (15 * i));
        }

        AcuityInstance.getAppletManager().getMouseMiddleMan().getMousePosition().ifPresent(point -> {
            event.getGraphics().setColor(Color.RED);
            event.getGraphics().fillOval(point.x - 3, point.y - 3, 6, 6);
        });
    }

    @Subscribe
    public void testKeyEvent(KeyEvent e){
        if (e.getKeyChar() == 'c' && e.isControlDown()){
            SmartActions.INSTANCE.clear();
        }
        else if (e.getKeyChar() == 'a' && e.getID() == KeyEvent.KEY_TYPED){
            mesh = !mesh;
        }
        else if (e.getKeyChar() == 'n' && e.getID() == KeyEvent.KEY_TYPED){
            Npcs.getNearest("Man").ifPresent(npc -> {
                npc.interact("Talk-to");
            });
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