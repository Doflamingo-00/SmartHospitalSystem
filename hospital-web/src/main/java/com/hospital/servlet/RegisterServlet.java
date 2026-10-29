package com.hospital.servlet;

import com.hospital.dao.PatientDao;
import com.hospital.entity.Patient;
import com.hospital.util.PasswordUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final PatientDao patientDao = new PatientDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        String dob = req.getParameter("dob");

        if (name == null || name.isBlank() || email == null || email.isBlank()
                || password == null || password.length() < 6) {
            req.setAttribute("error", "Please fill all required fields. Password must be at least 6 characters.");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        if (patientDao.findByEmail(email.trim()) != null) {
            req.setAttribute("error", "Email is already registered. Please login.");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }

        Patient patient = new Patient();
        patient.setName(name.trim());
        patient.setEmail(email.trim().toLowerCase());
        patient.setPhone(phone != null ? phone.trim() : null);
        patient.setPasswordHash(PasswordUtil.hash(password));

        if (dob != null && !dob.isBlank()) {
            try {
                patient.setDateOfBirth(LocalDate.parse(dob));
            } catch (Exception ignored) {}
        }

        try {
            patientDao.save(patient);
            resp.sendRedirect("login.jsp?msg=registered");
        } catch (Exception e) {
            req.setAttribute("error", "Registration failed. Please try again.");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}
