package com.managment.businessinvoice.exception;

public class AdminAlreadyExistException extends  RuntimeException{
    public AdminAlreadyExistException(){}
    public AdminAlreadyExistException(String message){
        super(message);
    }
}
