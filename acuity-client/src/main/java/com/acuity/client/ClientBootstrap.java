package com.acuity.client;

import com.acuity.api.AcuityInstance;
import com.acuity.api.Events;
import com.acuity.api.input.SmartActions;
import com.acuity.api.rs.events.impl.drawing.InGameDrawEvent;
import com.acuity.api.rs.query.Npcs;
import com.acuity.api.rs.query.SceneElements;
import com.acuity.api.rs.utils.LocalPlayer;
import com.acuity.api.rs.utils.Projection;
import com.acuity.api.rs.wrappers.peers.rendering.Model;
import com.acuity.client.gui.AcuityBotFrame;
import com.acuity.common.util.AcuityDir;
import com.google.common.eventbus.Subscribe;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by Zach on 5/31/2017.
 */
public class ClientBootstrap {

    @Subscribe
    public void testDraw(InGameDrawEvent event){


        AcuityInstance.getAppletManager().getMouseMiddleMan().getMousePosition().ifPresent(point -> {
            event.getGraphics().fillOval(point.x - 3, point.y - 3, 6, 6);
        });



        Npcs.getNearest("Man").ifPresent(npc -> {
            npc.getCachedModel().map(Model::streamPoints).ifPresent(screenLocationStream -> screenLocationStream.forEach(screenLocation -> {
                event.getGraphics().fillOval(screenLocation.getX() - 3, screenLocation.getY() - 3, 6, 6);
            }));
        });

    }

    @Subscribe
    public void testKeyEvent(KeyEvent e){
        if (e.getKeyChar() == 'c' && e.isControlDown()){
            SmartActions.INSTANCE.clear();
        }
        else if (e.getKeyChar() == 'a' && e.getID() == KeyEvent.KEY_TYPED){
        }
        else if (e.getKeyChar() == 'n' && e.getID() == KeyEvent.KEY_TYPED){
            Npcs.getNearest("Man").ifPresent(npc -> {
                npc.interact("");
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

                //     MouseDataCollector.INSTANCE.start();
                //     SmartActions.INSTANCE.start();
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