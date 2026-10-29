package com.hospital.servlet;

import com.hospital.dao.AppointmentDao;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/cancel")
public class CancelAppointmentServlet extends HttpServlet {

    private final AppointmentDao appointmentDao = new AppointmentDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("patient") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String idParam = req.getParameter("appointmentId");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                appointmentDao.cancel(id);
            } catch (NumberFormatException ignored) {}
        }
        resp.sendRedirect("dashboard");
    }
}
