package com.acuity.client.gui;

import com.acuity.api.rs.utils.Projection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.applet.Applet;
import java.awt.*;

/**
 * Created by Eclipseop.
 * Date: 8/13/2017.
 */
public class AcuityBotFrame extends AcuityFrame {

	private static final Logger logger = LoggerFactory.getLogger(AcuityBotFrame.class);

	public AcuityBotFrame(final Applet applet) {
		setSize(Projection.APPLET_SIZE.width, Projection.APPLET_SIZE.height + 31);

		add(applet, BorderLayout.CENTER);
	}
}
