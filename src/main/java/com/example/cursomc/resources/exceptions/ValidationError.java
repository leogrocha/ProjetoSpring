package com.example.cursomc.resources.exceptions;

import java.util.List;
import java.util.ArrayList;

public class ValidationError extends StandarError{
    public static long serialVersionUID=1L;

    private List<FieldMessage> errors = new ArrayList<>();

    public  ValidationError(Integer status,String msg,Long timeStamp){
        super(status, msg, timeStamp);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String message){
        errors.add(new FieldMessage(fieldName, message));
    }    
}
