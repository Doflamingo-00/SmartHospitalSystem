<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hospital.entity.Doctor, java.util.List" %>
<%
    if (session.getAttribute("patient") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<Doctor> doctors = (List<Doctor>) request.getAttribute("doctors");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Doctors - Smart Hospital</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
        <a class="navbar-brand" href="dashboard">🏥 Smart Hospital</a>
        <div class="d-flex">
            <a href="dashboard" class="btn btn-outline-light btn-sm me-2">Dashboard</a>
            <a href="book" class="btn btn-light btn-sm me-2">Book</a>
            <a href="logout" class="btn btn-outline-light btn-sm">Logout</a>
        </div>
    </div>
</nav>

<div class="container my-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Our Doctors</h2>
        <form class="d-flex" method="get" action="doctors">
            <select name="specialization" class="form-select me-2" style="width:auto">
                <option value="">All Specializations</option>
                <option value="Cardiology">Cardiology</option>
                <option value="Dermatology">Dermatology</option>
                <option value="Orthopedics">Orthopedics</option>
                <option value="Pediatrics">Pediatrics</option>
                <option value="Neurology">Neurology</option>
            </select>
            <button class="btn btn-primary">Filter</button>
        </form>
    </div>

    <div class="row g-4">
        <% if (doctors != null) {
               for (Doctor d : doctors) { %>
            <div class="col-md-4">
                <div class="card doctor-card h-100">
                    <div class="card-body">
                        <h5 class="card-title"><%= d.getName() %></h5>
                        <span class="badge bg-info text-dark mb-2"><%= d.getSpecialization() %></span>
                        <p class="mb-1 text-muted">📞 <%= d.getPhone() != null ? d.getPhone() : "N/A" %></p>
                        <p class="fw-bold text-primary">₹ <%= d.getConsultationFee() %></p>
                        <a href="book?doctorId=<%= d.getDoctorId() %>" class="btn btn-primary btn-sm">Book Appointment</a>
                    </div>
                </div>
            </div>
        <%   }
           } %>
    </div>
</div>
</body>
</html>
