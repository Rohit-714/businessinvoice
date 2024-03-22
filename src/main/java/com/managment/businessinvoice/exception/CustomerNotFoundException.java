package com.managment.businessinvoice.exception;

public class CustomerNotFoundException extends  RuntimeException{
    public CustomerNotFoundException(){}
    public CustomerNotFoundException(String message){
        super(message);
    }
}
