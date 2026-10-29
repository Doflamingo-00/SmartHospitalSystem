package com.hospital.servlet;

import com.hospital.dao.*;
import com.hospital.entity.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/book")
public class BookAppointmentServlet extends HttpServlet {

    private final AppointmentDao appointmentDao = new AppointmentDao();
    private final DoctorDao doctorDao = new DoctorDao();
    private final ReceiptDao receiptDao = new ReceiptDao();
    private final ScheduleDao scheduleDao = new ScheduleDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("patient") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String doctorIdParam = req.getParameter("doctorId");
        if (doctorIdParam != null) {
            try {
                int doctorId = Integer.parseInt(doctorIdParam);
                Doctor doctor = doctorDao.findById(doctorId);
                req.setAttribute("doctor", doctor);
                req.setAttribute("schedules", scheduleDao.findByDoctorId(doctorId));
            } catch (NumberFormatException ignored) {}
        }

        req.setAttribute("allDoctors", doctorDao.findAllActive());
        req.getRequestDispatcher("/book-appointment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("patient") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        Patient patient = (Patient) session.getAttribute("patient");

        try {
            int doctorId = Integer.parseInt(req.getParameter("doctorId"));
            String timeStr = req.getParameter("appointmentTime"); // yyyy-MM-ddTHH:mm
            String scheduleIdStr = req.getParameter("scheduleId");

            LocalDateTime appointmentTime = LocalDateTime.parse(timeStr,
                    DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            // Double-booking prevention
            if (appointmentDao.isSlotTaken(doctorId, appointmentTime)) {
                req.setAttribute("error", "This time slot is already booked. Please choose another slot.");
                req.setAttribute("allDoctors", doctorDao.findAllActive());
                req.getRequestDispatcher("/book-appointment.jsp").forward(req, resp);
                return;
            }

            Doctor doctor = doctorDao.findById(doctorId);
            if (doctor == null || !Boolean.TRUE.equals(doctor.getActive())) {
                req.setAttribute("error", "Selected doctor is not available.");
                req.getRequestDispatcher("/book-appointment.jsp").forward(req, resp);
                return;
            }

            Appointment appt = new Appointment();
            appt.setPatient(patient);
            appt.setDoctor(doctor);
            appt.setAppointmentTime(appointmentTime);
            appt.setStatus(Appointment.Status.PENDING);

            if (scheduleIdStr != null && !scheduleIdStr.isBlank()) {
                DoctorSchedule schedule = scheduleDao.findById(Integer.parseInt(scheduleIdStr));
                appt.setSchedule(schedule);
            }

            appointmentDao.save(appt);

            // Generate digital receipt
            Receipt receipt = new Receipt();
            receipt.setAppointment(appt);
            receipt.setAmount(doctor.getConsultationFee());
            receipt.setReceiptNumber("RCP-" + System.currentTimeMillis());
            receipt.setPaymentStatus(Receipt.PaymentStatus.PENDING);
            receiptDao.save(receipt);

            resp.sendRedirect("receipt?id=" + receipt.getReceiptId());

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Booking failed: " + e.getMessage());
            req.setAttribute("allDoctors", doctorDao.findAllActive());
            req.getRequestDispatcher("/book-appointment.jsp").forward(req, resp);
        }
    }
}
