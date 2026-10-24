package com.payroll.view;

import com.payroll.model.*;
import com.payroll.service.SalaryCalculator;
import com.payroll.repository.DataRepository;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QLLuongVien extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private List<Employee> employees;
    private List<AttendanceRecord> attendanceRecords;
    private JTable table, attendanceTable;
    private DefaultTableModel tableModel, attendanceModel;
    private JTextField nameField, idField, salaryField, searchField, phoneField;
    private JComboBox<String> typeCombo, provinceCombo;
    private JButton addButton, viewButton, deleteButton, searchButton, clearSearchButton, exportButton, logoutButton;
    private JButton checkInButton, checkOutButton, viewAttendanceButton, closeMonthButton;
    private JLabel totalLabel, countLabel, currentUserLabel;
    private JTabbedPane tabbedPane;
    private String currentUsername;
    private String currentRole;
    private JSpinner monthSpinner, yearSpinner;

    private static final String[] PROVINCES = {
        "-- Chọn tỉnh/thành --", "Hà Nội", "Hồ Chí Minh", "Đà Nẵng", "Hải Phòng", 
        "Cần Thơ", "Bắc Ninh", "Hải Dương", "Hưng Yên", "Thái Nguyên",
        "Lạng Sơn", "Quảng Ninh", "Bắc Giang", "Yên Bái", "Tuyên Quang",
        "Lào Cai", "Cao Bằng", "Điện Biên", "Sơn La", "Hòa Bình",
        "Thanh Hóa", "Nghệ An", "Hà Tĩnh", "Quảng Bình", "Quảng Trị",
        "Thừa Thiên - Huế", "Quảng Nam", "Quảng Ngãi", "Bình Định", "Phú Yên",
        "Khánh Hòa", "Ninh Thuận", "Bình Thuận", "Đồng Nai", "Bà Rịa - Vũng Tàu",
        "Long An", "Tiền Giang", "Bến Tre", "Trà Vinh", "Vĩnh Long",
        "An Giang", "Kiên Giang", "Hậu Giang", "Sóc Trăng", "Bạc Liêu"
    };

    private static final Object[][] SALARY_REFERENCE = {
        {"Lương cố định (Nhân viên)", "1,000,000 - 3,000,000 VND/tháng"},
        {"Lương cố định (Trưởng phòng)", "3,000,000 - 5,000,000 VND/tháng"},
        {"Trả theo giờ (Thường)", "30,000 - 50,000 VND/giờ"},
        {"Trả theo giờ (Chuyên môn)", "50,000 - 100,000 VND/giờ"},
        {"Trả theo ngày (Lao động phổ thông)", "100,000 - 150,000 VND/ngày"},
        {"Trả theo ngày (Kỹ thuật", "200,000 - 300,000 VND/ngày"}
    };

    public QLLuongVien() {
        // Tải dữ liệu từ database
        employees = DataRepository.loadEmployees();
        attendanceRecords = DataRepository.loadAttendanceRecords();
        
        System.out.println("✓ Loaded " + employees.size() + " employees from database");
        
        // Nếu không có dữ liệu, tạo dữ liệu mẫu
        if (employees.isEmpty()) {
            System.out.println("ℹ️ No employees found, creating default data...");
            initializeDefaultData();
            DataRepository.saveEmployees(employees);
            DataRepository.saveAttendanceRecords(attendanceRecords);
        } else {
            System.out.println("✓ Database is ready with " + employees.size() + " employees");
        }
        
        showLoginDialog();
    }

    private void initializeDefaultData() {
        employees.add(new SalaryEmployee("Nguyễn Văn A", "Hà Nội", "0123456789", 1001, 5_000_000, "admin", "admin123", "ADMIN"));
        employees.add(new SalaryEmployee("Trần Thị B", "Hồ Chí Minh", "0987654321", 1002, 3_000_000, "user", "user123", "USER"));
        employees.add(new HourlyEmployee("Lê Văn C", "Đà Nẵng", "0912345678", 1003, 50_000, 160, "user2", "pass123", "USER"));
        employees.add(new DailyEmployee("Phạm Thị D", "Hải Phòng", "0931234567", 1004, 150_000, 22, "user3", "pass123", "USER"));
    }

    private void showLoginDialog() {
        JFrame loginFrame = new JFrame("Đăng Nhập Hệ Thống");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(500, 400);
        loginFrame.setLocationRelativeTo(null);

        LoginPanel loginPanel = new LoginPanel();

        loginPanel.setLoginListener(e -> {
            String username = loginPanel.getUsername();
            String password = loginPanel.getPassword();

            boolean found = false;
            for (Employee emp : employees) {
                if (emp.getUsername() != null && emp.getUsername().equals(username) && emp.verifyPassword(password)) {
                    currentUsername = username;
                    currentRole = emp.getRole();
                    found = true;
                    loginFrame.dispose();
                    initGUI();
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setSize(1400, 900);
                    setLocationRelativeTo(null);
                    setVisible(true);
                    break;
                }
            }

            if (!found) {
                loginPanel.setMessage("❌ Tên đăng nhập hoặc mật khẩu không đúng!", true);
                loginPanel.clearFields();
            }
        });

        loginPanel.setCreateAccountListener(e -> {
            showRegisterPanel(loginFrame);
        });

        loginFrame.add(loginPanel);
        loginFrame.setVisible(true);
    }

    /**
     * Hiển thị RegisterPanel để tạo tài khoản
     */
    private void showRegisterPanel(JFrame loginFrame) {
        JFrame registerFrame = new JFrame("Tạo tài khoản mới");
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registerFrame.setSize(500, 650);
        registerFrame.setLocationRelativeTo(null);

        RegisterPanel registerPanel = new RegisterPanel();

        registerPanel.setRegisterListener(e -> {
            // Validate input
            String name = registerPanel.getName();
            String address = registerPanel.getAddress();
            String phone = registerPanel.getPhone();
            String username = registerPanel.getUsername();
            String password = registerPanel.getPassword();
            String confirmPassword = registerPanel.getConfirmPassword();
            String employeeType = registerPanel.getEmployeeType();

            // Validation
            if (name.isEmpty()) {
                registerPanel.setMessage("❌ Vui lòng nhập họ tên!", true);
                return;
            }

            if (username.isEmpty()) {
                registerPanel.setMessage("❌ Vui lòng nhập tên đăng nhập!", true);
                return;
            }

            // Check if username already exists
            for (Employee emp : employees) {
                if (emp.getUsername() != null && emp.getUsername().equals(username)) {
                    registerPanel.setMessage("❌ Tên đăng nhập đã tồn tại!", true);
                    return;
                }
            }

            if (password.isEmpty()) {
                registerPanel.setMessage("❌ Vui lòng nhập mật khẩu!", true);
                return;
            }

            if (password.length() < 6) {
                registerPanel.setMessage("❌ Mật khẩu phải ít nhất 6 ký tự!", true);
                return;
            }

            if (!password.equals(confirmPassword)) {
                registerPanel.setMessage("❌ Mật khẩu không khớp!", true);
                return;
            }

            // Create new account based on employee type
            Employee newEmployee = null;
            int nextId = getNextEmployeeId();

            switch (employeeType) {
                case "SalaryEmployee":
                    newEmployee = new SalaryEmployee(name, address, phone, nextId, 3000000.0, username, password, "USER");
                    break;
                case "HourlyEmployee":
                    newEmployee = new HourlyEmployee(name, address, phone, nextId, 50000.0, 160, username, password, "USER");
                    break;
                case "DailyEmployee":
                    newEmployee = new DailyEmployee(name, address, phone, nextId, 100000.0, 20, username, password, "USER");
                    break;
            }

            if (newEmployee != null) {
                // Save to database
                DataRepository.saveEmployee(newEmployee);
                employees.add(newEmployee);

                registerPanel.setMessage("✅ Tạo tài khoản thành công! ID: " + nextId, false);
                registerPanel.clearFields();

                // Auto-close after 2 seconds
                Timer timer = new Timer(2000, event -> registerFrame.dispose());
                timer.setRepeats(false);
                timer.start();
            }
        });

        registerPanel.setBackListener(e -> registerFrame.dispose());

        registerFrame.add(registerPanel);
        registerFrame.setVisible(true);
    }

    /**
     * Lấy ID tiếp theo cho nhân viên mới
     */
    private int getNextEmployeeId() {
        int maxId = 1000;
        for (Employee emp : employees) {
            if (emp.getId() > maxId) {
                maxId = emp.getId();
            }
        }
        return maxId + 1;
    }

    private void initGUI() {
        setTitle("HỆ THỐNG QUẢN LÝ LƯƠNG NHÂN VIÊN v2.1 - " + currentUsername.toUpperCase());
        setFont(new Font("Arial", Font.PLAIN, 12));

        // Panel Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(33, 150, 243));
        headerPanel.setPreferredSize(new Dimension(0, 50));
        
        currentUserLabel = new JLabel("👤 Người dùng: " + currentUsername + " (" + currentRole + ")");
        currentUserLabel.setFont(new Font("Arial", Font.BOLD, 12));
        currentUserLabel.setForeground(Color.WHITE);
        headerPanel.add(currentUserLabel, BorderLayout.WEST);

        logoutButton = new JButton("🚪 Đăng xuất");
        logoutButton.setBackground(new Color(244, 67, 54));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(e -> logout());
        headerPanel.add(logoutButton, BorderLayout.EAST);

        tabbedPane = new JTabbedPane();

        if ("ADMIN".equals(currentRole)) {
            JPanel mainPanel = createMainPanel();
            tabbedPane.addTab("👨‍💼 Quản Lý Nhân Viên", mainPanel);
        }

        JPanel attendancePanel = createAttendancePanel();
        tabbedPane.addTab("⏱️ Chấm Công", attendancePanel);

        JPanel referencePanel = createReferencePanel();
        tabbedPane.addTab("📊 Bảng Lương Tham Khảo", referencePanel);

        if ("USER".equals(currentRole)) {
            JPanel myPayslipPanel = createMyPayslipPanel();
            tabbedPane.addTab("💳 Lương của tôi", myPayslipPanel);
        }

        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private void logout() {
        // Lưu dữ liệu trước khi thoát
        DataRepository.saveEmployees(employees);
        DataRepository.saveAttendanceRecords(attendanceRecords);
        
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            new QLLuongVien();
        }
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        // ===== PANEL NHẬP THÔNG TIN =====
        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("1. Nhập Thông Tin Nhân Viên"));
        inputPanel.setBackground(new Color(240, 248, 255));

        inputPanel.add(new JLabel("Mã nhân viên:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Họ và tên:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Số điện thoại:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);

        inputPanel.add(new JLabel("Tỉnh/Thành phố:"));
        provinceCombo = new JComboBox<>(PROVINCES);
        inputPanel.add(provinceCombo);

        inputPanel.add(new JLabel("Loại nhân viên:"));
        typeCombo = new JComboBox<>(new String[]{"Lương cố định", "Trả theo giờ", "Trả theo ngày"});
        inputPanel.add(typeCombo);

        inputPanel.add(new JLabel("Lương/Mức giá (VND):"));
        salaryField = new JTextField();
        inputPanel.add(salaryField);

        addButton = new JButton("Thêm Nhân Viên");
        addButton.setBackground(new Color(76, 175, 80));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 12));
        addButton.addActionListener(e -> addEmployee());
        inputPanel.add(addButton);

        JButton clearButton = new JButton("Xóa Sạch");
        clearButton.setBackground(new Color(158, 158, 158));
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(e -> clearFields());
        inputPanel.add(clearButton);

        // ===== PANEL TÌM KIẾM & TÍNH LƯƠNG =====
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("2. Tìm Kiếm & Tính Lương"));
        searchPanel.setBackground(new Color(240, 248, 255));

        searchPanel.add(new JLabel("Tìm theo tên, mã, SĐT, tỉnh:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);

        searchButton = new JButton("🔍 Tìm");
        searchButton.setBackground(new Color(33, 150, 243));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(e -> searchEmployeeWithThreading());
        searchPanel.add(searchButton);

        clearSearchButton = new JButton("❌ Xóa Lọc");
        clearSearchButton.setBackground(new Color(255, 152, 0));
        clearSearchButton.setForeground(Color.WHITE);
        clearSearchButton.addActionListener(e -> clearSearch());
        searchPanel.add(clearSearchButton);

        // Thêm Close Month button
        closeMonthButton = new JButton("🔐 Chót Lương Tháng");
        closeMonthButton.setBackground(new Color(156, 39, 176));
        closeMonthButton.setForeground(Color.WHITE);
        closeMonthButton.addActionListener(e -> closeMonth());
        searchPanel.add(closeMonthButton);

        // ===== PANEL BUTTON QUẢN LÝ =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("3. Quản Lý"));
        buttonPanel.setBackground(new Color(240, 248, 255));

        viewButton = new JButton("📊 Xuất Toàn Bộ");
        viewButton.setBackground(new Color(76, 175, 80));
        viewButton.setForeground(Color.WHITE);
        viewButton.addActionListener(e -> viewEmployees());
        buttonPanel.add(viewButton);

        exportButton = new JButton("📁 Xuất CSV");
        exportButton.setBackground(new Color(0, 150, 136));
        exportButton.setForeground(Color.WHITE);
        exportButton.addActionListener(e -> exportToCSV());
        buttonPanel.add(exportButton);

        deleteButton = new JButton("🗑️ Xóa Tất Cả");
        deleteButton.setBackground(new Color(244, 67, 54));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(e -> deleteAll());
        buttonPanel.add(deleteButton);

        // ===== PANEL BẢNG DỮ LIỆU =====
        String[] columns = {"Mã NV", "Họ và tên", "SĐT", "Tỉnh/Thành", "Loại", "Mức Giá", "Lương Gross", "Bảo Hiểm", "Thuế PIT", "Lương Net"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 10));
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(100, 150, 255));
        table.getTableHeader().setBackground(new Color(33, 150, 243));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("4. Danh Sách Nhân Viên"));

        // ===== PANEL THỐNG KÊ =====
        JPanel summaryPanel = new JPanel(new GridLayout(2, 2, 20, 5));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("5. Thống Kê"));
        summaryPanel.setBackground(new Color(240, 248, 255));

        totalLabel = new JLabel("💰 Tổng Lương Gross: 0.00 VND");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 13));
        totalLabel.setForeground(new Color(0, 100, 200));
        summaryPanel.add(totalLabel);

        countLabel = new JLabel("👥 Tổng Nhân Viên: 0");
        countLabel.setFont(new Font("Arial", Font.BOLD, 13));
        countLabel.setForeground(new Color(76, 175, 80));
        summaryPanel.add(countLabel);

        // ===== LAYOUT CHÍNH =====
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(summaryPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel createAttendancePanel() {
        JPanel attendancePanel = new JPanel(new BorderLayout(10, 10));
        attendancePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        attendancePanel.setBackground(Color.WHITE);

        JPanel checkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        checkPanel.setBorder(BorderFactory.createTitledBorder("⏰ Check-in / Check-out"));
        checkPanel.setBackground(new Color(240, 248, 255));

        checkInButton = new JButton("🟢 Check-in");
        checkInButton.setBackground(new Color(76, 175, 80));
        checkInButton.setForeground(Color.WHITE);
        checkInButton.setFont(new Font("Arial", Font.BOLD, 14));
        checkInButton.setPreferredSize(new Dimension(150, 50));
        checkInButton.addActionListener(e -> checkInEmployee());
        checkPanel.add(checkInButton);

        checkOutButton = new JButton("🔴 Check-out");
        checkOutButton.setBackground(new Color(244, 67, 54));
        checkOutButton.setForeground(Color.WHITE);
        checkOutButton.setFont(new Font("Arial", Font.BOLD, 14));
        checkOutButton.setPreferredSize(new Dimension(150, 50));
        checkOutButton.addActionListener(e -> checkOutEmployee());
        checkPanel.add(checkOutButton);

        viewAttendanceButton = new JButton("📋 Xem Chấm Công");
        viewAttendanceButton.setBackground(new Color(33, 150, 243));
        viewAttendanceButton.setForeground(Color.WHITE);
        viewAttendanceButton.setFont(new Font("Arial", Font.BOLD, 12));
        viewAttendanceButton.addActionListener(e -> viewAttendanceRecords());
        checkPanel.add(viewAttendanceButton);

        String[] attColumns = {"Ngày", "Mã NV", "Tên NV", "Check-in", "Check-out", "Giờ làm việc"};
        attendanceModel = new DefaultTableModel(attColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        attendanceTable = new JTable(attendanceModel);
        attendanceTable.setFont(new Font("Arial", Font.PLAIN, 11));
        attendanceTable.setRowHeight(25);
        attendanceTable.getTableHeader().setBackground(new Color(33, 150, 243));
        attendanceTable.getTableHeader().setForeground(Color.WHITE);
        attendanceTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(attendanceTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📊 Bảng Chấm Công"));

        attendancePanel.add(checkPanel, BorderLayout.NORTH);
        attendancePanel.add(scrollPane, BorderLayout.CENTER);

        return attendancePanel;
    }

    private JPanel createReferencePanel() {
        JPanel refPanel = new JPanel(new BorderLayout(10, 10));
        refPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        refPanel.setBackground(Color.WHITE);

        String[] columns = {"Chức vụ/Loại", "Mức Lương Tham Khảo"};
        DefaultTableModel salaryReferenceModel = new DefaultTableModel(SALARY_REFERENCE, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable salaryReferenceTable = new JTable(salaryReferenceModel);
        salaryReferenceTable.setFont(new Font("Arial", Font.PLAIN, 12));
        salaryReferenceTable.setRowHeight(30);
        salaryReferenceTable.getTableHeader().setBackground(new Color(33, 150, 243));
        salaryReferenceTable.getTableHeader().setForeground(Color.WHITE);
        salaryReferenceTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(salaryReferenceTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Bảng Lương Tham Khảo Ngành"));

        JLabel infoLabel = new JLabel(
            "<html><b>Hướng dẫn:</b><br>" +
            "• Dữ liệu được tự động lưu vào file<br>" +
            "• Chót lương tháng sẽ tính tự động giờ/ngày từ chấm công<br>" +
            "• Mật khẩu được hash SHA-256 để bảo mật<br>" +
            "• Cập nhật năm: 2024-2025</html>"
        );
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        refPanel.add(scrollPane, BorderLayout.CENTER);
        refPanel.add(infoLabel, BorderLayout.SOUTH);

        return refPanel;
    }

    private JPanel createMyPayslipPanel() {
        JPanel payslipPanel = new JPanel(new BorderLayout(10, 10));
        payslipPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        payslipPanel.setBackground(Color.WHITE);

        Employee currentEmployee = null;
        for (Employee emp : employees) {
            if (emp.getUsername() != null && emp.getUsername().equals(currentUsername)) {
                currentEmployee = emp;
                break;
            }
        }

        if (currentEmployee != null) {
            double grossSalary = currentEmployee.calculateGrossSalary();
            double insurance = currentEmployee.calculateInsurance();
            double tax = currentEmployee.calculateTax();
            double netSalary = currentEmployee.calculateNetSalary();

            JTextArea payslipText = new JTextArea();
            payslipText.setFont(new Font("Courier New", Font.PLAIN, 12));
            payslipText.setEditable(false);
            payslipText.setLineWrap(true);
            payslipText.setWrapStyleWord(true);
            payslipText.setText(
                "================== PHIẾU LƯƠNG ==================\n" +
                "Mã NV: " + currentEmployee.getId() + "\n" +
                "Tên: " + currentEmployee.getName() + "\n" +
                "Địa chỉ: " + currentEmployee.getAddress() + "\n" +
                "SĐT: " + currentEmployee.getPhone() + "\n" +
                "=" + "=".repeat(43) + "\n\n" +
                "LƯƠNG GROSS (Brutto): " + String.format("%.0f", grossSalary) + " VND\n" +
                "Bảo hiểm xã hội (5%): " + String.format("%.0f", TaxInsurance.calculateSocialInsurance(grossSalary)) + " VND\n" +
                "Bảo hiểm y tế (1.5%): " + String.format("%.0f", TaxInsurance.calculateHealthInsurance(grossSalary)) + " VND\n" +
                "Tổng bảo hiểm: " + String.format("%.0f", insurance) + " VND\n" +
                "-" + "-".repeat(43) + "\n" +
                "Thuế PIT (Thu nhập cá nhân): " + String.format("%.0f", tax) + " VND\n" +
                "=" + "=".repeat(43) + "\n" +
                "LƯƠNG NET (Thực lĩnh): " + String.format("%.0f", netSalary) + " VND\n" +
                "=" + "=".repeat(43) + "\n\n" +
                "Ngày xuất: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())
            );

            JScrollPane scrollPane = new JScrollPane(payslipText);
            scrollPane.setBorder(BorderFactory.createTitledBorder("Thông tin Lương Của Tôi"));

            payslipPanel.add(scrollPane, BorderLayout.CENTER);
        } else {
            JLabel errorLabel = new JLabel("Không tìm thấy thông tin nhân viên!");
            payslipPanel.add(errorLabel, BorderLayout.CENTER);
        }

        return payslipPanel;
    }

    private void closeMonth() {
        JDialog dialog = new JDialog(this, "Chót Lương Tháng", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        panel.add(new JLabel("Chọn Tháng:"));
        monthSpinner = new JSpinner(new SpinnerNumberModel(LocalDate.now().getMonthValue(), 1, 12, 1));
        panel.add(monthSpinner);
        
        panel.add(new JLabel("Chọn Năm:"));
        yearSpinner = new JSpinner(new SpinnerNumberModel(LocalDate.now().getYear(), 2020, 2050, 1));
        panel.add(yearSpinner);
        
        JButton submitButton = new JButton("Chót Lương");
        submitButton.setBackground(new Color(76, 175, 80));
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(e -> {
            int month = (int) monthSpinner.getValue();
            int year = (int) yearSpinner.getValue();
            performCloseMonth(month, year);
            dialog.dispose();
        });
        panel.add(submitButton);
        
        JButton cancelButton = new JButton("Hủy");
        cancelButton.addActionListener(e -> dialog.dispose());
        panel.add(cancelButton);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void performCloseMonth(int month, int year) {
        for (Employee emp : employees) {
            SalaryCalculator.updateEmployeeHoursFromAttendance(emp, month, year, attendanceRecords);
        }
        
        DataRepository.saveEmployees(employees);
        JOptionPane.showMessageDialog(this, "✅ Đã chót lương tháng " + month + "/" + year + " thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        viewEmployees();
    }

    private void addEmployee() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String province = (String) provinceCombo.getSelectedItem();
            double salary = Double.parseDouble(salaryField.getText());
            String type = (String) typeCombo.getSelectedItem();

            if (name.isEmpty() || phone.isEmpty() || province.equals("-- Chọn tỉnh/thành --")) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (Employee e : employees) {
                if (e.getId() == id) {
                    JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            Employee emp = null;
            if ("Lương cố định".equals(type)) {
                emp = new SalaryEmployee(name, province, phone, id, salary, "user" + id, "pass123", "USER");
            } else if ("Trả theo giờ".equals(type)) {
                emp = new HourlyEmployee(name, province, phone, id, salary, 160, "user" + id, "pass123", "USER");
            } else if ("Trả theo ngày".equals(type)) {
                emp = new DailyEmployee(name, province, phone, id, salary, 22, "user" + id, "pass123", "USER");
            }

            if (emp != null) {
                employees.add(emp);
                DataRepository.saveEmployees(employees);
                clearFields();
                viewEmployees();
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!\nUsername: " + emp.getUsername() + "\nPassword: pass123", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewEmployees() {
        tableModel.setRowCount(0);
        double totalGrossSalary = 0;
        double totalInsurance = 0;
        double totalTax = 0;

        for (Employee emp : employees) {
            String type = getEmployeeType(emp);
            double rate = getEmployeeRate(emp);
            double grossSalary = emp.calculateGrossSalary();
            double insurance = emp.calculateInsurance();
            double tax = emp.calculateTax();
            double netSalary = emp.calculateNetSalary();

            Object[] row = {
                emp.getId(),
                emp.getName(),
                emp.getPhone(),
                emp.getAddress(),
                type,
                String.format("%.0f", rate),
                String.format("%.0f", grossSalary),
                String.format("%.0f", insurance),
                String.format("%.0f", tax),
                String.format("%.0f", netSalary)
            };
            tableModel.addRow(row);
            totalGrossSalary += grossSalary;
            totalInsurance += insurance;
            totalTax += tax;
        }

        totalLabel.setText(String.format("💰 Tổng Lương Gross: %.0f VND | Bảo hiểm: %.0f VND | Thuế: %.0f VND", totalGrossSalary, totalInsurance, totalTax));
        countLabel.setText(String.format("👥 Tổng Nhân Viên: %d", employees.size()));
    }

    private void searchEmployeeWithThreading() {
        String keyword = searchField.getText().toLowerCase().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                tableModel.setRowCount(0);
                double totalGrossSalary = 0;
                double totalInsurance = 0;
                double totalTax = 0;
                int count = 0;

                for (Employee emp : employees) {
                    String name = emp.getName().toLowerCase();
                    String id = String.valueOf(emp.getId());
                    String phone = emp.getPhone().toLowerCase();
                    String address = emp.getAddress().toLowerCase();

                    if (name.contains(keyword) || id.contains(keyword) || 
                        phone.contains(keyword) || address.contains(keyword)) {
                        
                        String type = getEmployeeType(emp);
                        double rate = getEmployeeRate(emp);
                        double grossSalary = emp.calculateGrossSalary();
                        double insurance = emp.calculateInsurance();
                        double tax = emp.calculateTax();
                        double netSalary = emp.calculateNetSalary();

                        Object[] row = {
                            emp.getId(),
                            emp.getName(),
                            emp.getPhone(),
                            emp.getAddress(),
                            type,
                            String.format("%.0f", rate),
                            String.format("%.0f", grossSalary),
                            String.format("%.0f", insurance),
                            String.format("%.0f", tax),
                            String.format("%.0f", netSalary)
                        };
                        tableModel.addRow(row);
                        totalGrossSalary += grossSalary;
                        totalInsurance += insurance;
                        totalTax += tax;
                        count++;
                    }
                }

                final int finalCount = count;
                final double finalTotal = totalGrossSalary;
                final double finalInsurance = totalInsurance;
                final double finalTax = totalTax;

                SwingUtilities.invokeLater(() -> {
                    totalLabel.setText(String.format("💰 Tổng Lương Gross: %.0f VND | Bảo hiểm: %.0f VND | Thuế: %.0f VND", finalTotal, finalInsurance, finalTax));
                    countLabel.setText(String.format("👥 Kết quả tìm: %d", finalCount));
                    
                    if (finalCount == 0) {
                        JOptionPane.showMessageDialog(QLLuongVien.this, "Không tìm thấy kết quả phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                });

                return null;
            }
        };
        worker.execute();
    }

    private void clearSearch() {
        searchField.setText("");
        viewEmployees();
    }

    private void checkInEmployee() {
        String idStr = JOptionPane.showInputDialog(this, "Nhập mã nhân viên để check-in:");
        if (idStr == null) return;

        try {
            int empId = Integer.parseInt(idStr);
            for (Employee emp : employees) {
                if (emp.getId() == empId) {
                    AttendanceRecord record = new AttendanceRecord(empId);
                    record.checkIn();
                    attendanceRecords.add(record);
                    DataRepository.saveAttendanceRecords(attendanceRecords);
                    JOptionPane.showMessageDialog(this, "✅ Check-in thành công lúc " + 
                        new SimpleDateFormat("HH:mm:ss").format(new Date()), "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "❌ Không tìm thấy nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkOutEmployee() {
        String idStr = JOptionPane.showInputDialog(this, "Nhập mã nhân viên để check-out:");
        if (idStr == null) return;

        try {
            int empId = Integer.parseInt(idStr);
            for (AttendanceRecord record : attendanceRecords) {
                if (record.getEmployeeId() == empId && record.isCheckedIn() && !record.isCheckedOut()) {
                    record.checkOut();
                    DataRepository.saveAttendanceRecords(attendanceRecords);
                    JOptionPane.showMessageDialog(this, "✅ Check-out thành công lúc " + 
                        new SimpleDateFormat("HH:mm:ss").format(new Date()) + 
                        "\n⏱️ Giờ làm việc: " + String.format("%.2f", record.getHoursWorked()) + " giờ",
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "❌ Không tìm thấy bản check-in!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewAttendanceRecords() {
        attendanceModel.setRowCount(0);
        for (AttendanceRecord record : attendanceRecords) {
            Employee emp = null;
            for (Employee e : employees) {
                if (e.getId() == record.getEmployeeId()) {
                    emp = e;
                    break;
                }
            }
            if (emp != null) {
                Object[] row = {
                    record.getDate(),
                    record.getEmployeeId(),
                    emp.getName(),
                    record.getCheckInTime() != null ? new SimpleDateFormat("HH:mm:ss").format(java.sql.Timestamp.valueOf(record.getCheckInTime())) : "N/A",
                    record.getCheckOutTime() != null ? new SimpleDateFormat("HH:mm:ss").format(java.sql.Timestamp.valueOf(record.getCheckOutTime())) : "N/A",
                    String.format("%.2f", record.getHoursWorked())
                };
                attendanceModel.addRow(row);
            }
        }
    }

    private void exportToCSV() {
        if (employees.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String fileName = "DanhSachLuongNhanVien_" + sdf.format(new Date()) + ".csv";

            StringBuilder sb = new StringBuilder();
            sb.append("Mã NV,Họ và tên,Số điện thoại,Tỉnh/Thành,Loại,Mức Giá,Lương Gross,Bảo Hiểm,Thuế PIT,Lương Net\n");

            double totalGrossSalary = 0;
            double totalInsurance = 0;
            double totalTax = 0;
            for (Employee emp : employees) {
                String type = getEmployeeType(emp);
                double rate = getEmployeeRate(emp);
                double grossSalary = emp.calculateGrossSalary();
                double insurance = emp.calculateInsurance();
                double tax = emp.calculateTax();
                double netSalary = emp.calculateNetSalary();

                sb.append(emp.getId()).append(",")
                  .append("\"").append(emp.getName()).append("\"").append(",")
                  .append(emp.getPhone()).append(",")
                  .append("\"").append(emp.getAddress()).append("\"").append(",")
                  .append(type).append(",")
                  .append(String.format("%.0f", rate)).append(",")
                  .append(String.format("%.0f", grossSalary)).append(",")
                  .append(String.format("%.0f", insurance)).append(",")
                  .append(String.format("%.0f", tax)).append(",")
                  .append(String.format("%.0f", netSalary)).append("\n");

                totalGrossSalary += grossSalary;
                totalInsurance += insurance;
                totalTax += tax;
            }

            sb.append("\n");
            sb.append("THỐNG KÊ,,,,,,,,,\n");
            sb.append("Tổng Lương Gross,,,,,,").append(String.format("%.0f", totalGrossSalary)).append("\n");
            sb.append("Tổng Bảo Hiểm,,,,,,").append(String.format("%.0f", totalInsurance)).append("\n");
            sb.append("Tổng Thuế PIT,,,,,,").append(String.format("%.0f", totalTax)).append("\n");
            sb.append("Tổng Nhân Viên,,,,,,").append(employees.size()).append("\n");
            sb.append("Ngày Xuất,,,,,,").append(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())).append("\n");

            try (FileWriter fw = new FileWriter(fileName)) {
                fw.write(sb.toString());
            }

            JOptionPane.showMessageDialog(this, "✅ Xuất file CSV thành công!\nTên file: " + fileName, "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Lỗi khi xuất file: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteAll() {
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn xóa tất cả nhân viên?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            employees.clear();
            tableModel.setRowCount(0);
            totalLabel.setText("💰 Tổng Lương Gross: 0.00 VND | Bảo hiểm: 0.00 VND | Thuế: 0.00 VND");
            countLabel.setText("👥 Tổng Nhân Viên: 0");
            clearFields();
            DataRepository.saveEmployees(employees);
            JOptionPane.showMessageDialog(this, "Xóa tất cả dữ liệu thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String getEmployeeType(Employee emp) {
        if (emp instanceof SalaryEmployee) return "Lương cố định";
        if (emp instanceof HourlyEmployee) return "Trả theo giờ";
        if (emp instanceof DailyEmployee) return "Trả theo ngày";
        return "Không xác định";
    }

    private double getEmployeeRate(Employee emp) {
        if (emp instanceof SalaryEmployee) return ((SalaryEmployee) emp).getMonthlySalary();
        if (emp instanceof HourlyEmployee) return ((HourlyEmployee) emp).getHourlyRate();
        if (emp instanceof DailyEmployee) return ((DailyEmployee) emp).getDailyRate();
        return 0;
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        phoneField.setText("");
        salaryField.setText("");
        typeCombo.setSelectedIndex(0);
        provinceCombo.setSelectedIndex(0);
        searchField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QLLuongVien());
    }
}
