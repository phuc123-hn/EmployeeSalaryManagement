package com.payroll.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// Panel đăng nhập & phân quyền
public class LoginPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, createAccountButton;
    private JLabel messageLabel;

    public LoginPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 248, 255));
        initComponents();
    }

    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề
        JLabel titleLabel = new JLabel("HỆ THỐNG QUẢN LÝ LƯƠNG NHÂN VIÊN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 100, 200));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Đăng nhập để tiếp tục");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        gbc.gridy = 1;
        add(subtitleLabel, gbc);

        // Username
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        JLabel usernameLabel = new JLabel("Tên đăng nhập:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 12));
        add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 12));
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 12));
        add(passwordField, gbc);

        // Message
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        messageLabel = new JLabel("Hãy nhập thông tin để đăng nhập");
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        messageLabel.setForeground(Color.GRAY);
        add(messageLabel, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        loginButton = new JButton("Đăng nhập");
        loginButton.setBackground(new Color(76, 175, 80));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        loginButton.setPreferredSize(new Dimension(120, 40));
        add(loginButton, gbc);

        gbc.gridx = 1;
        createAccountButton = new JButton("Tạo tài khoản");
        createAccountButton.setBackground(new Color(33, 150, 243));
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.setFont(new Font("Arial", Font.BOLD, 12));
        createAccountButton.setPreferredSize(new Dimension(120, 40));
        add(createAccountButton, gbc);

        // Info
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        JLabel infoLabel = new JLabel(
            "<html>" +
            "<b>Admin:</b> username: admin / password: admin123<br>" +
            "<b>User:</b> Hãy tạo tài khoản hoặc sử dụng: user / user123" +
            "</html>"
        );
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        infoLabel.setForeground(new Color(100, 100, 100));
        add(infoLabel, gbc);
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void setMessage(String message, boolean isError) {
        messageLabel.setText(message);
        messageLabel.setForeground(isError ? Color.RED : new Color(76, 175, 80));
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    public void setLoginListener(ActionListener listener) {
        for (ActionListener al : loginButton.getActionListeners()) {
            loginButton.removeActionListener(al);
        }
        loginButton.addActionListener(listener);
    }

    public void setCreateAccountListener(ActionListener listener) {
        for (ActionListener al : createAccountButton.getActionListeners()) {
            createAccountButton.removeActionListener(al);
        }
        createAccountButton.addActionListener(listener);
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getCreateAccountButton() {
        return createAccountButton;
    }
}
