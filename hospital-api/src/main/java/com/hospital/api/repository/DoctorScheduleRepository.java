package com.hospital.api.repository;

import com.hospital.api.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Integer> {
    List<DoctorSchedule> findByDoctorIdAndScheduleDateGreaterThanEqualOrderByScheduleDateAscStartTimeAsc(
            Integer doctorId, LocalDate date);
}
