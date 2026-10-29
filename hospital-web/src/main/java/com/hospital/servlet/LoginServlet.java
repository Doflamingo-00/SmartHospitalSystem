package com.hospital.servlet;

import com.hospital.dao.PatientDao;
import com.hospital.entity.Patient;
import com.hospital.util.PasswordUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final PatientDao patientDao = new PatientDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || password == null) {
            req.setAttribute("error", "Email and password are required.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        Patient patient = patientDao.findByEmail(email.trim().toLowerCase());

        if (patient != null && PasswordUtil.check(password, patient.getPasswordHash())) {
            HttpSession session = req.getSession(true);
            session.setAttribute("patient", patient);
            session.setAttribute("patientId", patient.getPatientId());
            session.setAttribute("patientName", patient.getName());
            resp.sendRedirect("dashboard");
        } else {
            req.setAttribute("error", "Invalid email or password.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
