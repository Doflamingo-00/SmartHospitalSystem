package com.hospital.dao;

import com.hospital.entity.Receipt;
import com.hospital.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ReceiptDao {

    public void save(Receipt receipt) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(receipt);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error saving receipt", e);
        }
    }

    public Receipt findById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Receipt.class, id);
        }
    }

    public Receipt findByAppointmentId(Integer appointmentId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Receipt r WHERE r.appointment.appointmentId = :aid", Receipt.class)
                    .setParameter("aid", appointmentId)
                    .uniqueResult();
        }
    }
}
