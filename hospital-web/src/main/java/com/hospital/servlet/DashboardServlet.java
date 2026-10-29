package com.hospital.servlet;

import com.hospital.dao.AppointmentDao;
import com.hospital.entity.Appointment;
import com.hospital.entity.Patient;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private final AppointmentDao appointmentDao = new AppointmentDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("patient") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        Patient patient = (Patient) session.getAttribute("patient");
        List<Appointment> appointments = appointmentDao.findByPatientId(patient.getPatientId());

        req.setAttribute("appointments", appointments);
        req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
    }
}
