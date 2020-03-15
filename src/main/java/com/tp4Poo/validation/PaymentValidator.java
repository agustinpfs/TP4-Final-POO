package com.tp4Poo.validation;

import org.springframework.stereotype.Component;
import com.tp4Poo.entities.Payment;
@Component(value = "SimplePaymentValidator")
public class PaymentValidator {

    public void validate(Payment payment) throws Exception {
        if (payment.getEvent() == null) {
            throw new Exception("El pago debe tener un evento asociado");
        }
        if (payment.getOwner() == null) {
            throw new Exception("El pago debe tener un propietario asociado");
        }
        if (payment.getCardName().isEmpty()) {
            throw new Exception("Debe introducir el nombre de la tarjeta");
        }
        if (payment.getCardNumber().isEmpty()) {
            throw new Exception("Debe introducir el numero de la tarjeta");
        }
    }

}
