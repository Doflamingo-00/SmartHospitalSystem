package com.hospital.dao;

import com.hospital.entity.Doctor;
import com.hospital.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class DoctorDao {

    public void save(Doctor doctor) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(doctor);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error saving doctor", e);
        }
    }

    public Doctor findById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Doctor.class, id);
        }
    }

    public List<Doctor> findAllActive() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Doctor d WHERE d.active = true ORDER BY d.name", Doctor.class)
                    .list();
        }
    }

    public List<Doctor> findBySpecialization(String specialization) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM Doctor d WHERE d.active = true AND d.specialization = :spec ORDER BY d.name",
                    Doctor.class)
                    .setParameter("spec", specialization)
                    .list();
        }
    }

    public void update(Doctor doctor) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(doctor);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error updating doctor", e);
        }
    }
}
