package com.tp4Poo.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.tp4Poo.entities.Payment;
import com.tp4Poo.entities.Registration;
import com.tp4Poo.services.PaymentService;

@Component
@Primary
public class RegistrationPaymentValidator extends ValidationDecorator{
    
    @Autowired
    private PaymentService paymentService;

    public RegistrationPaymentValidator(
            @Qualifier(value = "inviteValidator")
            IRegistrationValidator delegate) {
        super(delegate);
    }
    
    @Override
    public void validate(Registration registration) throws Exception{
        if(registration.getEvent().getCost()>0){
            Payment payment =paymentService.findPaymentByEventAndUser(registration.getEvent(), registration.getUser());
            if (payment == null) throw new Exception("Pago sin realizar");
        }
        //valida que exista un pago registrado antes de aprobar la inscripcion
        super.validate(registration);
    }
    
}
