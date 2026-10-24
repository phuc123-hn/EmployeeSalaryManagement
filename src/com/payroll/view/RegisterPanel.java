package com.payroll.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Panel để tạo tài khoản mới
 */
public class RegisterPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private JTextField nameField;
    private JTextField addressField;
    private JTextField phoneField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> employeeTypeCombo;
    private JButton registerButton;
    private JButton backButton;
    private JLabel messageLabel;

    public RegisterPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 248, 255));
        initComponents();
    }

    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tiêu đề
        JLabel titleLabel = new JLabel("TẠO TÀI KHOẢN MỚI");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 100, 200));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        int row = 1;

        // Name
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Họ tên:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        add(nameField, gbc);
        row++;

        // Address
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1;
        addressField = new JTextField(20);
        add(addressField, gbc);
        row++;

        // Phone
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(20);
        add(phoneField, gbc);
        row++;

        // Employee Type
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Loại nhân viên:"), gbc);
        gbc.gridx = 1;
        employeeTypeCombo = new JComboBox<>(new String[]{
            "SalaryEmployee",
            "HourlyEmployee",
            "DailyEmployee"
        });
        add(employeeTypeCombo, gbc);
        row++;

        // Username
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        add(usernameField, gbc);
        row++;

        // Password
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        add(passwordField, gbc);
        row++;

        // Confirm Password
        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Xác nhận mật khẩu:"), gbc);
        gbc.gridx = 1;
        confirmPasswordField = new JPasswordField(20);
        add(confirmPasswordField, gbc);
        row++;

        // Message
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        messageLabel = new JLabel("Nhập thông tin để tạo tài khoản");
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        messageLabel.setForeground(Color.GRAY);
        add(messageLabel, gbc);
        row++;

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        registerButton = new JButton("Tạo tài khoản");
        registerButton.setBackground(new Color(76, 175, 80));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 12));
        registerButton.setPreferredSize(new Dimension(130, 40));
        add(registerButton, gbc);

        gbc.gridx = 1;
        backButton = new JButton("Quay lại");
        backButton.setBackground(new Color(158, 158, 158));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 12));
        backButton.setPreferredSize(new Dimension(130, 40));
        add(backButton, gbc);
    }

    public String getName() {
        return nameField.getText().trim();
    }

    public String getAddress() {
        return addressField.getText().trim();
    }

    public String getPhone() {
        return phoneField.getText().trim();
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getConfirmPassword() {
        return new String(confirmPasswordField.getPassword());
    }

    public String getEmployeeType() {
        return (String) employeeTypeCombo.getSelectedItem();
    }

    public void setMessage(String message, boolean isError) {
        messageLabel.setText(message);
        messageLabel.setForeground(isError ? Color.RED : new Color(76, 175, 80));
    }

    public void clearFields() {
        nameField.setText("");
        addressField.setText("");
        phoneField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        employeeTypeCombo.setSelectedIndex(0);
    }

    public void setRegisterListener(ActionListener listener) {
        for (ActionListener al : registerButton.getActionListeners()) {
            registerButton.removeActionListener(al);
        }
        registerButton.addActionListener(listener);
    }

    public void setBackListener(ActionListener listener) {
        for (ActionListener al : backButton.getActionListeners()) {
            backButton.removeActionListener(al);
        }
        backButton.addActionListener(listener);
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
