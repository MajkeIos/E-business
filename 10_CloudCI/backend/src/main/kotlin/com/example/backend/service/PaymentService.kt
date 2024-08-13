package com.example.backend.service

import com.example.backend.model.Payment
import com.example.backend.repository.BasketRepository
import com.example.backend.repository.PaymentRepository
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val basketRepository: BasketRepository
) {

    fun createPayment(basketId: Long, amount: Double, paymentMethod: String): Payment? {
        val basket = basketRepository.findById(basketId).orElse(null) ?: return null

        val payment = Payment(
            amount = amount,
            paymentMethod = paymentMethod,
            status = "Pending",
            basket = basket
        )

        return paymentRepository.save(payment)
    }

    fun getPaymentById(id: Long): Payment? {
        return paymentRepository.findById(id).orElse(null)
    }

    fun updatePaymentStatus(id: Long, status: String): Payment? {
        val payment = paymentRepository.findById(id).orElse(null) ?: return null
        val updatedPayment = payment.copy(status = status)
        return paymentRepository.save(updatedPayment)
    }
}