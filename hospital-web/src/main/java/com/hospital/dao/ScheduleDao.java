package com.hospital.dao;

import com.hospital.entity.DoctorSchedule;
import com.hospital.util.HibernateUtil;
import org.hibernate.Session;
import java.time.LocalDate;
import java.util.List;

public class ScheduleDao {

    public DoctorSchedule findById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(DoctorSchedule.class, id);
        }
    }

    public List<DoctorSchedule> findByDoctorId(Integer doctorId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM DoctorSchedule s WHERE s.doctor.doctorId = :did " +
                    "AND s.scheduleDate >= :today ORDER BY s.scheduleDate, s.startTime",
                    DoctorSchedule.class)
                    .setParameter("did", doctorId)
                    .setParameter("today", LocalDate.now())
                    .list();
        }
    }

    public List<DoctorSchedule> findUpcoming() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM DoctorSchedule s WHERE s.scheduleDate >= :today " +
                    "ORDER BY s.scheduleDate, s.startTime",
                    DoctorSchedule.class)
                    .setParameter("today", LocalDate.now())
                    .list();
        }
    }
}
