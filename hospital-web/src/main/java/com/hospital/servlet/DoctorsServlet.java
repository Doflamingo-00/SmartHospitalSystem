package com.hospital.servlet;

import com.hospital.dao.DoctorDao;
import com.hospital.entity.Doctor;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/doctors")
public class DoctorsServlet extends HttpServlet {

    private final DoctorDao doctorDao = new DoctorDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("patient") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String specialization = req.getParameter("specialization");
        List<Doctor> doctors;

        if (specialization != null && !specialization.isBlank()) {
            doctors = doctorDao.findBySpecialization(specialization.trim());
        } else {
            doctors = doctorDao.findAllActive();
        }

        req.setAttribute("doctors", doctors);
        req.getRequestDispatcher("/doctors.jsp").forward(req, resp);
    }
}
