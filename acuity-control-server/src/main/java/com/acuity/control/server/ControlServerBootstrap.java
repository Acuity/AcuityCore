package com.acuity.control.server;

import com.acuity.db.AcuityDB;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by Zachary Herridge on 8/3/2017.
 */
public class ControlServerBootstrap {

    private static AcuityWSServer acuityWSServer;

    public static void bootStrap(){
        try {
            AcuityDB.init();
            Events.start();
            acuityWSServer = new AcuityWSServer(8015);
            acuityWSServer.setConnectionLostTimeout(5);
            acuityWSServer.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void stop(){
        AcuityDB.stop();
        Events.stop();
        try {
            acuityWSServer.stop();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        bootStrap();
    }

}
