package com.hospital.dao;

import com.hospital.entity.Appointment;
import com.hospital.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentDao {

    public void save(Appointment appointment) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(appointment);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error saving appointment", e);
        }
    }

    public Appointment findById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Appointment.class, id);
        }
    }

    public boolean isSlotTaken(int doctorId, LocalDateTime time) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                    "SELECT COUNT(a) FROM Appointment a " +
                    "WHERE a.doctor.doctorId = :docId " +
                    "AND a.appointmentTime = :time " +
                    "AND a.status <> 'CANCELLED'", Long.class)
                    .setParameter("docId", doctorId)
                    .setParameter("time", time)
                    .uniqueResult();
            return count != null && count > 0;
        }
    }

    public List<Appointment> findByPatientId(Integer patientId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Appointment a WHERE a.patient.patientId = :pid ORDER BY a.appointmentTime DESC",
                    Appointment.class)
                    .setParameter("pid", patientId)
                    .list();
        }
    }

    public List<Appointment> findByDoctorId(Integer doctorId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Appointment a WHERE a.doctor.doctorId = :did ORDER BY a.appointmentTime",
                    Appointment.class)
                    .setParameter("did", doctorId)
                    .list();
        }
    }

    public void update(Appointment appointment) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(appointment);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error updating appointment", e);
        }
    }

    public void cancel(Integer appointmentId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Appointment appt = session.get(Appointment.class, appointmentId);
            if (appt != null) {
                appt.setStatus(Appointment.Status.CANCELLED);
                session.merge(appt);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error cancelling appointment", e);
        }
    }
}
