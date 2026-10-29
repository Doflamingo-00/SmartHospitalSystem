package com.hospital.api.repository;

import com.hospital.api.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {
    Optional<Receipt> findByAppointmentId(Integer appointmentId);
}
