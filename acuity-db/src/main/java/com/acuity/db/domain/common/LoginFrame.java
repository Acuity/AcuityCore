package com.acuity.db.domain.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Eclipseop.
 * Date: 8/13/2017.
 */
public class LoginFrame extends AcuityFrame {


	public static final Color ACUITY_DARK_GREY = new Color(42,49,66);
	public static final Color ACUITY_MED_GREY = new Color(44,51,69);
	public static final Color ACUITY_LIGHT_GREY = new Color(48,56,75);
	public static final Color ACUITY_BLUE = new Color(59,175,218);

	private static final Logger logger = LoggerFactory.getLogger(LoginFrame.class);

    private JButton loginButton;
    private JTextField emailField;
    private JTextField passwordField;

	public LoginFrame() {
		setSize(400, 200);

		add(createPadding(), BorderLayout.WEST);
		add(createPadding(), BorderLayout.EAST);

		add(buildFields(), BorderLayout.CENTER);
        loginButton = buildLoginButton();
        add(loginButton, BorderLayout.SOUTH);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

    public JButton getLoginButton() {
        return loginButton;
    }

    private JButton buildLoginButton() {
        return createButton("Login");
	}

    public JTextField getEmailField() {
        return emailField;
    }

    public JTextField getPasswordField() {
        return passwordField;
    }

    private Panel buildFields() {
		final Panel panel = new Panel(new GridLayout(4, 1));

		panel.add(createLabel("Email:"));
		emailField = createTextField(false);

		panel.add(emailField);

		panel.add(createLabel("Password:"));
		passwordField = createTextField(true);

		panel.add(passwordField);

		return panel;
	}

	private JLabel createLabel(final String text) {
		final JLabel jLabel = new JLabel(text);

		jLabel.setOpaque(true);
		jLabel.setForeground(Color.WHITE);
		jLabel.setBackground(ACUITY_DARK_GREY);

		return jLabel;
	}

	private JTextField createTextField(boolean password) {
		final JTextField jTextField = password ? new JPasswordField() : new JTextField();

		jTextField.setForeground(Color.white);
		jTextField.setBackground(ACUITY_LIGHT_GREY);
		jTextField.setBorder(BorderFactory.createSoftBevelBorder(2));

		return jTextField;
	}

	private JButton createButton(final String text) {
		final JButton jButton = new JButton(text);

		jButton.setForeground(Color.white);
		jButton.setBackground(ACUITY_DARK_GREY);
		jButton.setRolloverEnabled(false);
		jButton.setBorderPainted(false);

		return jButton;
	}

	private JPanel createPadding() {
		final JPanel padding = new JPanel();
		padding.setSize(20, 200);
		padding.setBackground(ACUITY_DARK_GREY);
		return padding;
	}
}
