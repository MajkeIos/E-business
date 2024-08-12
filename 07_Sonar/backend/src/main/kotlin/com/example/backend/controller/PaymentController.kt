package com.example.backend.controller

import com.example.backend.model.Payment
import com.example.backend.service.PaymentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/payments")
class PaymentController(private val paymentService: PaymentService) {

    @PostMapping
    fun createPayment(
        @RequestParam basketId: Long,
        @RequestParam amount: Double,
        @RequestParam paymentMethod: String
    ): ResponseEntity<Payment> {
        val payment = paymentService.createPayment(basketId, amount, paymentMethod) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(payment)
    }

    @GetMapping("/{id}")
    fun getPaymentById(@PathVariable id: Long): ResponseEntity<Payment> {
        val payment = paymentService.getPaymentById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(payment)
    }

    @PutMapping("/{id}")
    fun updatePaymentStatus(@PathVariable id: Long, @RequestParam status: String): ResponseEntity<Payment> {
        val updatedPayment = paymentService.updatePaymentStatus(id, status) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(updatedPayment)
    }
}