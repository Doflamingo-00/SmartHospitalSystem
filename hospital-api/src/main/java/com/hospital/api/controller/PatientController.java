package com.hospital.api.controller;

import com.hospital.api.entity.Appointment;
import com.hospital.api.repository.AppointmentRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    private final AppointmentRepository appointmentRepository;

    public PatientController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/{id}/appointments")
    public List<Appointment> getPatientAppointments(@PathVariable Integer id) {
        return appointmentRepository.findByPatientIdOrderByAppointmentTimeDesc(id);
    }
}
