package com.hospital.api.repository;

import com.hospital.api.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByPatientIdOrderByAppointmentTimeDesc(Integer patientId);
    List<Appointment> findByDoctorDoctorIdOrderByAppointmentTime(Integer doctorId);
}
