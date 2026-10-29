package com.hospital.api.controller;

import com.hospital.api.entity.Receipt;
import com.hospital.api.repository.ReceiptRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receipts")
@CrossOrigin(origins = "*")
public class ReceiptController {

    private final ReceiptRepository receiptRepository;

    public ReceiptController(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @GetMapping("/{id}")
    public Receipt getReceipt(@PathVariable Integer id) {
        return receiptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receipt not found"));
    }
}
