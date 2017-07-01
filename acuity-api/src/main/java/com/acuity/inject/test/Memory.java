package com.acuity.inject.test;

import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zachary Herridge on 6/30/2017.
 */
public class Memory {

    public static GarbageCollectorMXBean gcBean;

    public static List<GarbageCollectorMXBean> getBeans(){
        System.out.println("gg");
        return new ArrayList<>();
    }
}
