package com.managment.businessinvoice.exception;

public class CustomerAlreadyExistException extends  RuntimeException{
    public CustomerAlreadyExistException(){}
    public CustomerAlreadyExistException(String message){
        super(message);
    }
}
