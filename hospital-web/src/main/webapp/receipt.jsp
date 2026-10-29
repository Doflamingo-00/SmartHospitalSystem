<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hospital.entity.Receipt" %>
<%
    if (session.getAttribute("patient") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    Receipt receipt = (Receipt) request.getAttribute("receipt");
    if (receipt == null) {
        response.sendRedirect("dashboard");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Receipt - Smart Hospital</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <style>
        @media print {
            .no-print { display: none !important; }
            body { background: white; }
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary no-print">
    <div class="container">
        <a class="navbar-brand" href="dashboard">🏥 Smart Hospital</a>
        <div class="d-flex">
            <a href="dashboard" class="btn btn-outline-light btn-sm me-2">Dashboard</a>
            <a href="logout" class="btn btn-outline-light btn-sm">Logout</a>
        </div>
    </div>
</nav>

<div class="container my-5" style="max-width: 600px;">
    <div class="receipt-box">
        <div class="text-center mb-4">
            <h3>🏥 Smart Hospital</h3>
            <p class="text-muted mb-0">Digital Consultation Receipt</p>
        </div>

        <hr>

        <table class="table table-borderless">
            <tr>
                <td><strong>Receipt No.</strong></td>
                <td class="text-end"><%= receipt.getReceiptNumber() %></td>
            </tr>
            <tr>
                <td><strong>Issued At</strong></td>
                <td class="text-end"><%= receipt.getIssuedAt().toString().replace("T", " ").substring(0, 16) %></td>
            </tr>
            <tr>
                <td><strong>Patient</strong></td>
                <td class="text-end"><%= receipt.getAppointment().getPatient().getName() %></td>
            </tr>
            <tr>
                <td><strong>Doctor</strong></td>
                <td class="text-end"><%= receipt.getAppointment().getDoctor().getName() %></td>
            </tr>
            <tr>
                <td><strong>Specialization</strong></td>
                <td class="text-end"><%= receipt.getAppointment().getDoctor().getSpecialization() %></td>
            </tr>
            <tr>
                <td><strong>Appointment</strong></td>
                <td class="text-end"><%= receipt.getAppointment().getAppointmentTime().toString().replace("T", " ") %></td>
            </tr>
            <tr>
                <td><strong>Status</strong></td>
                <td class="text-end">
                    <span class="badge status-<%= receipt.getAppointment().getStatus() %>">
                        <%= receipt.getAppointment().getStatus() %>
                    </span>
                </td>
            </tr>
            <tr>
                <td><strong>Amount</strong></td>
                <td class="text-end fw-bold fs-5">₹ <%= receipt.getAmount() %></td>
            </tr>
            <tr>
                <td><strong>Payment Status</strong></td>
                <td class="text-end"><%= receipt.getPaymentStatus() %></td>
            </tr>
        </table>

        <hr>
        <p class="text-center text-muted small mb-0">
            Thank you for choosing Smart Hospital. This is a computer-generated receipt.
        </p>
    </div>

    <div class="text-center mt-4 no-print">
        <button onclick="window.print()" class="btn btn-primary me-2">🖨️ Print Receipt</button>
        <a href="dashboard" class="btn btn-outline-secondary">Back to Dashboard</a>
    </div>
</div>
</body>
</html>
