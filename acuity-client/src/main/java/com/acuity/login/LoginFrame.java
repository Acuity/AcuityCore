package com.acuity.login;

import com.acuity.client.gui.AcuityFrame;
import com.acuity.client.util.StyleConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Eclipseop.
 * Date: 8/13/2017.
 */
public class LoginFrame extends AcuityFrame {

	private static final Logger logger = LoggerFactory.getLogger(LoginFrame.class);

	public LoginFrame() {
		setSize(400, 200);

		add(createPadding(), BorderLayout.WEST);
		add(createPadding(), BorderLayout.EAST);

		add(buildFields(), BorderLayout.CENTER);
		add(buildLoginButton(), BorderLayout.SOUTH);
	}

	private JButton buildLoginButton() {
		final JButton loginButton = createButton("Login");

		loginButton.addActionListener(e -> {

		});

		return loginButton;
	}

	private Panel buildFields() {
		final Panel panel = new Panel(new GridLayout(4, 1));

		panel.add(createLabel("Email:"));
		final JTextField emailField = createTextField(false);

		panel.add(emailField);

		panel.add(createLabel("Password:"));
		final JTextField passwordField = createTextField(true);

		panel.add(passwordField);

		return panel;
	}

	private JLabel createLabel(final String text) {
		final JLabel jLabel = new JLabel(text);

		jLabel.setOpaque(true);
		jLabel.setForeground(Color.WHITE);
		jLabel.setBackground(StyleConstants.ACUITY_DARK_GREY);

		return jLabel;
	}

	private JTextField createTextField(boolean password) {
		final JTextField jTextField = password ? new JPasswordField() : new JTextField();

		jTextField.setForeground(Color.white);
		jTextField.setBackground(StyleConstants.ACUITY_LIGHT_GREY);
		jTextField.setBorder(BorderFactory.createSoftBevelBorder(2));

		return jTextField;
	}

	private JButton createButton(final String text) {
		final JButton jButton = new JButton(text);

		jButton.setForeground(Color.white);
		jButton.setBackground(StyleConstants.ACUITY_DARK_GREY);
		jButton.setRolloverEnabled(false);
		jButton.setBorderPainted(false);

		return jButton;
	}

	private JPanel createPadding() {
		final JPanel padding = new JPanel();
		padding.setSize(20, 200);
		padding.setBackground(StyleConstants.ACUITY_DARK_GREY);
		return padding;
	}
}
