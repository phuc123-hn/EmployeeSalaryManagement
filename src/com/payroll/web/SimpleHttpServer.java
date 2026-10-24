package com.payroll.web;

import com.payroll.model.*;
import com.payroll.repository.DataRepository;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Simple HTTP Server for Payroll System
 * No Spring Boot needed - Pure Java HTTP Server
 * Access: http://localhost:8080
 */
public class SimpleHttpServer {
    private static final int PORT = 8080;
    private static HttpServer server;
    
    public static void main(String[] args) throws Exception {
        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        
        // Static files handler
        server.createContext("/", new FileHandler());
        
        // API endpoints
        server.createContext("/api/employees", new EmployeesHandler());
        server.createContext("/api/login", new LoginHandler());
        server.createContext("/api/salary/", new SalaryHandler());
        
        server.setExecutor(null);
        server.start();
        
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║  HỆ THỐNG QUẢN LÝ LƯƠNG - WEB VERSION                 ║");
        System.out.println("║  ✅ Server đang chạy                                  ║");
        System.out.println("║                                                        ║");
        System.out.println("║  🌐 Truy cập: http://localhost:8080                   ║");
        System.out.println("║                                                        ║");
        System.out.println("║  Press CTRL+C để dừng                                 ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
    }
    
    // ==================== FILE HANDLER ====================
    
    static class FileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            if (path.equals("/") || path.isEmpty()) {
                path = "/index.html";
            }
            
            try {
                String filePath = "src/resources/static" + path;
                byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
                
                String contentType = getContentType(path);
                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                
                exchange.sendResponseHeaders(200, fileContent.length);
                exchange.getResponseBody().write(fileContent);
                exchange.close();
            } catch (Exception e) {
                sendError(exchange, 404, "File not found");
            }
        }
        
        private String getContentType(String path) {
            if (path.endsWith(".html")) return "text/html";
            if (path.endsWith(".css")) return "text/css";
            if (path.endsWith(".js")) return "application/javascript";
            if (path.endsWith(".json")) return "application/json";
            return "text/plain";
        }
    }
    
    // ==================== LOGIN HANDLER ====================
    
    static class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            enableCORS(exchange);
            
            if ("POST".equals(exchange.getRequestMethod())) {
                String body = readRequestBody(exchange);
                
                try {
                    // Parse JSON
                    String username = extractJsonValue(body, "username");
                    String password = extractJsonValue(body, "password");
                    
                    // Verify
                    List<Employee> employees = DataRepository.loadEmployees();
                    for (Employee emp : employees) {
                        if (emp.getUsername() != null && 
                            emp.getUsername().equals(username) && 
                            emp.verifyPassword(password)) {
                            
                            String response = String.format(
                                "{\"success\":true,\"username\":\"%s\",\"role\":\"%s\",\"employeeId\":%d,\"name\":\"%s\"}",
                                emp.getUsername(), emp.getRole(), emp.getId(), emp.getName()
                            );
                            sendResponse(exchange, 200, response);
                            return;
                        }
                    }
                    
                    sendResponse(exchange, 401, "{\"success\":false,\"message\":\"Invalid credentials\"}");
                } catch (Exception e) {
                    sendError(exchange, 400, "Bad request: " + e.getMessage());
                }
            } else {
                sendError(exchange, 405, "Method not allowed");
            }
        }
    }
    
    // ==================== EMPLOYEES HANDLER ====================
    
    static class EmployeesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            enableCORS(exchange);
            
            String method = exchange.getRequestMethod();
            
            if ("GET".equals(method)) {
                List<Employee> employees = DataRepository.loadEmployees();
                double totalGross = 0;
                for (Employee emp : employees) {
                    totalGross += emp.calculateGrossSalary();
                }
                
                StringBuilder json = new StringBuilder("[");
                boolean first = true;
                for (Employee emp : employees) {
                    if (!first) json.append(",");
                    json.append(employeeToJson(emp));
                    first = false;
                }
                json.append("]");
                
                sendResponse(exchange, 200, json.toString());
            } else {
                sendError(exchange, 405, "Method not allowed");
            }
        }
        
        private String employeeToJson(Employee emp) {
            return String.format(
                "{\"id\":%d,\"name\":\"%s\",\"phone\":\"%s\",\"address\":\"%s\",\"username\":\"%s\",\"role\":\"%s\",\"grossSalary\":%.0f,\"netSalary\":%.0f}",
                emp.getId(), emp.getName(), emp.getPhone(), emp.getAddress(), emp.getUsername(), 
                emp.getRole(), emp.calculateGrossSalary(), emp.calculateNetSalary()
            );
        }
    }
    
    // ==================== SALARY HANDLER ====================
    
    static class SalaryHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            enableCORS(exchange);
            
            if ("GET".equals(exchange.getRequestMethod())) {
                String path = exchange.getRequestURI().getPath();
                int empId = Integer.parseInt(path.replace("/api/salary/", ""));
                
                Employee emp = DataRepository.findEmployeeById(empId);
                if (emp != null) {
                    String response = String.format(
                        "{\"success\":true,\"employeeId\":%d,\"name\":\"%s\",\"address\":\"%s\",\"phone\":\"%s\",\"grossSalary\":%.0f,\"insurance\":%.0f,\"tax\":%.0f,\"netSalary\":%.0f}",
                        emp.getId(), emp.getName(), emp.getAddress(), emp.getPhone(),
                        emp.calculateGrossSalary(), emp.calculateInsurance(), emp.calculateTax(), emp.calculateNetSalary()
                    );
                    sendResponse(exchange, 200, response);
                } else {
                    sendResponse(exchange, 404, "{\"success\":false,\"message\":\"Employee not found\"}");
                }
            } else {
                sendError(exchange, 405, "Method not allowed");
            }
        }
    }
    
    // ==================== HELPERS ====================
    
    static void enableCORS(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
    }
    
    static String readRequestBody(HttpExchange exchange) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        StringBuilder body = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            body.append(line);
        }
        return body.toString();
    }
    
    static String extractJsonValue(String json, String key) {
        String pattern = "\"" + key + "\":\"([^\"]*)\"";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(json);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }
    
    static void sendResponse(HttpExchange exchange, int code, String content) throws IOException {
        byte[] bytes = content.getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(code, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }
    
    static void sendError(HttpExchange exchange, int code, String message) throws IOException {
        String content = String.format("{\"error\":\"%s\"}", message);
        sendResponse(exchange, code, content);
    }
}
