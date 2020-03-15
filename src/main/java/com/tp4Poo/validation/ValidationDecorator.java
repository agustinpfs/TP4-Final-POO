package com.tp4Poo.validation;

import com.tp4Poo.entities.Registration;

public abstract class ValidationDecorator implements IRegistrationValidator{
    
    private IRegistrationValidator delegate;
    
    public ValidationDecorator(IRegistrationValidator delegate){
        this.delegate = delegate;
    }

    @Override
    public void validate(Registration registration) throws Exception{
        delegate.validate(registration);
    }
    
    
}
