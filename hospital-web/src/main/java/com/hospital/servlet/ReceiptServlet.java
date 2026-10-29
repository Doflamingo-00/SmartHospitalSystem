package com.hospital.servlet;

import com.hospital.dao.ReceiptDao;
import com.hospital.entity.Receipt;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/receipt")
public class ReceiptServlet extends HttpServlet {

    private final ReceiptDao receiptDao = new ReceiptDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("patient") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.sendRedirect("dashboard");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            Receipt receipt = receiptDao.findById(id);
            if (receipt == null) {
                resp.sendRedirect("dashboard");
                return;
            }
            req.setAttribute("receipt", receipt);
            req.getRequestDispatcher("/receipt.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect("dashboard");
        }
    }
}
