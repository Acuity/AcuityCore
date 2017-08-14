package com.acuity.client.gui;

import com.acuity.api.rs.utils.Projection;
import com.acuity.client.util.StyleConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Eclipseop.
 * Date: 8/10/2017.
 */
public class AcuityFrame extends JFrame implements MouseListener, MouseMotionListener {

	private static final Logger logger = LoggerFactory.getLogger(AcuityFrame.class);

	private Point mouseCords = null;

	public AcuityFrame() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setIconImage(getImage("icon2.png"));
		setLocationRelativeTo(null);
		setTitle("AcuityBotting");
		setUndecorated(true);
		setLayout(new BorderLayout());

		add(createTopPanel(), BorderLayout.NORTH);
	}

	private Panel createTopPanel() {
		final Panel panel = new Panel(new BorderLayout());
		panel.setBackground(StyleConstants.ACUITY_BLUE);
		panel.setPreferredSize(new Dimension(Projection.APPLET_SIZE.width, 30));

		final Panel leftPanel = new Panel(new FlowLayout(FlowLayout.LEFT));
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
		leftPanel.add(new JLabel(new ImageIcon(getIconImage())));

		final Panel rightPanel = new Panel(new FlowLayout(FlowLayout.RIGHT));

		final JButton minimizeButton = new AcuityButton(new ImageIcon(getImage("min.png")));
		minimizeButton.addActionListener(e -> setState(Frame.ICONIFIED));
		rightPanel.add(minimizeButton);

		final JButton closeButton = new AcuityButton(new ImageIcon(getImage("close.png")));
		closeButton.addActionListener(e -> System.exit(0));
		rightPanel.add(closeButton);

		panel.add(leftPanel, BorderLayout.WEST);
		panel.add(rightPanel, BorderLayout.EAST);

		return panel;
	}

	private Image getImage(final String name) {
		try {
			return ImageIO.read(new File(
					getClass().getResource("/" + name).toURI()
			));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseCords = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseCords = null;
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		final Point mouse = e.getLocationOnScreen();
		setLocation(mouse.x - mouseCords.x, mouse.y - mouseCords.y);
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	private static class AcuityButton extends JButton {

		public AcuityButton(final ImageIcon imageIcon) {
			super(imageIcon);
			setFocusPainted(false);
			setContentAreaFilled(false);
			setBorderPainted(false);
			setSize(16, 16);
		}
	}
}
