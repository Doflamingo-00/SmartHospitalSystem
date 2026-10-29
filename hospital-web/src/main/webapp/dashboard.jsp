<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hospital.entity.Appointment, java.util.List" %>
<%
    if (session.getAttribute("patient") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String patientName = (String) session.getAttribute("patientName");
    List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointments");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dashboard - Smart Hospital</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
        <a class="navbar-brand" href="dashboard">🏥 Smart Hospital</a>
        <div class="d-flex align-items-center">
            <span class="text-white me-3">Hello, <%= patientName %></span>
            <a href="doctors" class="btn btn-outline-light btn-sm me-2">Doctors</a>
            <a href="book" class="btn btn-light btn-sm me-2">Book Appointment</a>
            <a href="logout" class="btn btn-outline-light btn-sm">Logout</a>
        </div>
    </div>
</nav>

<div class="container my-4">
    <h2 class="mb-4">My Appointments</h2>

    <% if (appointments == null || appointments.isEmpty()) { %>
        <div class="alert alert-info">
            You have no appointments yet.
            <a href="book" class="alert-link">Book your first appointment</a>
        </div>
    <% } else { %>
        <div class="table-responsive">
            <table class="table table-hover bg-white shadow-sm rounded">
                <thead class="table-primary">
                    <tr>
                        <th>#</th>
                        <th>Doctor</th>
                        <th>Specialization</th>
                        <th>Date & Time</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                <% int i = 1;
                   for (Appointment a : appointments) { %>
                    <tr>
                        <td><%= i++ %></td>
                        <td><%= a.getDoctor().getName() %></td>
                        <td><%= a.getDoctor().getSpecialization() %></td>
                        <td><%= a.getAppointmentTime().toString().replace("T", " ") %></td>
                        <td>
                            <span class="badge status-<%= a.getStatus() %>"><%= a.getStatus() %></span>
                        </td>
                        <td>
                            <% if (a.getStatus() == Appointment.Status.PENDING || a.getStatus() == Appointment.Status.CONFIRMED) { %>
                                <form action="cancel" method="post" style="display:inline"
                                      onsubmit="return confirm('Cancel this appointment?');">
                                    <input type="hidden" name="appointmentId" value="<%= a.getAppointmentId() %>">
                                    <button type="submit" class="btn btn-sm btn-outline-danger">Cancel</button>
                                </form>
                            <% } else { %>
                                —
                            <% } %>
                        </td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    <% } %>
</div>
</body>
</html>
