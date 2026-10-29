package com.hospital.api.controller;

import com.hospital.api.entity.Appointment;
import com.hospital.api.repository.AppointmentRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;

    public AppointmentController(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/{id}")
    public Appointment getAppointment(@PathVariable Integer id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    @GetMapping
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    @PostMapping
    public Appointment create(@RequestBody Map<String, Object> body) {
        Appointment appt = new Appointment();
        appt.setPatientId((Integer) body.get("patientId"));
        // doctor will be set via ID in a fuller implementation
        appt.setStatus(Appointment.Status.PENDING);
        return appointmentRepository.save(appt);
    }
}
