package com.hospital.api.repository;

import com.hospital.api.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    List<Doctor> findByActiveTrue();
    List<Doctor> findBySpecializationAndActiveTrue(String specialization);
}
