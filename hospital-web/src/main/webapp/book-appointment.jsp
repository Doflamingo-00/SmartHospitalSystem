<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hospital.entity.*, java.util.List" %>
<%
    if (session.getAttribute("patient") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<Doctor> allDoctors = (List<Doctor>) request.getAttribute("allDoctors");
    Doctor selectedDoctor = (Doctor) request.getAttribute("doctor");
    List<DoctorSchedule> schedules = (List<DoctorSchedule>) request.getAttribute("schedules");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Book Appointment - Smart Hospital</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
        <a class="navbar-brand" href="dashboard">🏥 Smart Hospital</a>
        <div class="d-flex">
            <a href="dashboard" class="btn btn-outline-light btn-sm me-2">Dashboard</a>
            <a href="doctors" class="btn btn-outline-light btn-sm me-2">Doctors</a>
            <a href="logout" class="btn btn-outline-light btn-sm">Logout</a>
        </div>
    </div>
</nav>

<div class="container my-4" style="max-width: 640px;">
    <div class="card shadow">
        <div class="card-body p-4">
            <h3 class="mb-4">Book an Appointment</h3>

            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
            <% } %>

            <form action="book" method="post">
                <div class="mb-3">
                    <label class="form-label">Select Doctor *</label>
                    <select name="doctorId" class="form-select" required id="doctorSelect"
                            onchange="if(this.value) window.location='book?doctorId='+this.value;">
                        <option value="">-- Choose Doctor --</option>
                        <% if (allDoctors != null) {
                               for (Doctor d : allDoctors) { %>
                            <option value="<%= d.getDoctorId() %>"
                                <%= (selectedDoctor != null && selectedDoctor.getDoctorId().equals(d.getDoctorId())) ? "selected" : "" %>>
                                <%= d.getName() %> — <%= d.getSpecialization() %> (₹<%= d.getConsultationFee() %>)
                            </option>
                        <%   }
                           } %>
                    </select>
                </div>

                <% if (selectedDoctor != null && schedules != null && !schedules.isEmpty()) { %>
                    <div class="mb-3">
                        <label class="form-label">Available Schedules</label>
                        <select name="scheduleId" class="form-select" id="scheduleSelect">
                            <option value="">-- Optional: pick a schedule day --</option>
                            <% for (DoctorSchedule s : schedules) { %>
                                <option value="<%= s.getScheduleId() %>">
                                    <%= s.getScheduleDate() %> | <%= s.getStartTime() %> - <%= s.getEndTime() %>
                                    (every <%= s.getSlotDuration() %> min)
                                </option>
                            <% } %>
                        </select>
                    </div>
                <% } %>

                <div class="mb-3">
                    <label class="form-label">Appointment Date & Time *</label>
                    <input type="datetime-local" name="appointmentTime" class="form-control" required>
                    <div class="form-text">Choose a time that matches the doctor's schedule.</div>
                </div>

                <button type="submit" class="btn btn-primary w-100">Confirm Booking</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
