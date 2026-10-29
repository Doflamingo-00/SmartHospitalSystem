package com.hospital.api.controller;

import com.hospital.api.entity.Doctor;
import com.hospital.api.entity.DoctorSchedule;
import com.hospital.api.repository.DoctorRepository;
import com.hospital.api.repository.DoctorScheduleRepository;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final DoctorScheduleRepository scheduleRepository;

    public DoctorController(DoctorRepository doctorRepository,
                            DoctorScheduleRepository scheduleRepository) {
        this.doctorRepository = doctorRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findByActiveTrue();
    }

    @GetMapping("/{id}")
    public Doctor getDoctor(@PathVariable Integer id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    @GetMapping("/{id}/schedule")
    public List<DoctorSchedule> getDoctorSchedule(@PathVariable Integer id) {
        return scheduleRepository
                .findByDoctorIdAndScheduleDateGreaterThanEqualOrderByScheduleDateAscStartTimeAsc(
                        id, LocalDate.now());
    }

    @GetMapping("/specialization/{spec}")
    public List<Doctor> getBySpecialization(@PathVariable String spec) {
        return doctorRepository.findBySpecializationAndActiveTrue(spec);
    }
}
