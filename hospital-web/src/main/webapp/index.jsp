<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Smart Hospital - Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
        <a class="navbar-brand" href="index.jsp">🏥 Smart Hospital</a>
        <div class="d-flex">
            <a href="login.jsp" class="btn btn-outline-light me-2">Login</a>
            <a href="register.jsp" class="btn btn-light">Register</a>
        </div>
    </div>
</nav>

<section class="hero text-center">
    <div class="container">
        <h1 class="display-4 fw-bold">Smart Hospital Appointment System</h1>
        <p class="lead mt-3">Book appointments with top doctors, manage your health records, and get digital receipts — all in one place.</p>
        <a href="register.jsp" class="btn btn-light btn-lg mt-3 me-2">Get Started</a>
        <a href="login.jsp" class="btn btn-outline-light btn-lg mt-3">Patient Login</a>
    </div>
</section>

<div class="container my-5">
    <div class="row g-4">
        <div class="col-md-4">
            <div class="card h-100 text-center p-4">
                <div class="fs-1 mb-3">👨‍⚕️</div>
                <h5>Find Doctors</h5>
                <p class="text-muted">Browse specialists by department and book available slots instantly.</p>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card h-100 text-center p-4">
                <div class="fs-1 mb-3">📅</div>
                <h5>Easy Booking</h5>
                <p class="text-muted">Select date & time, avoid double-booking, and get confirmation instantly.</p>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card h-100 text-center p-4">
                <div class="fs-1 mb-3">🧾</div>
                <h5>Digital Receipts</h5>
                <p class="text-muted">Receive printable digital receipts for every consultation.</p>
            </div>
        </div>
    </div>
</div>

<footer class="footer">
    <div class="container">
        <p class="mb-0">© 2026 Smart Hospital Appointment System — Student Project Demo</p>
    </div>
</footer>
</body>
</html>
