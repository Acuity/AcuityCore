package com.acuity.control.server.servlet;

import com.acuity.control.server.ControlServerBootstrap;
import spark.servlet.SparkApplication;

/**
 * Created by Zachary Herridge on 8/11/2017.
 */
public class ControlServlet implements SparkApplication {
    @Override
    public void init() {
        ControlServerBootstrap.bootStrap();
    }

    @Override
    public void destroy() {

    }
}
