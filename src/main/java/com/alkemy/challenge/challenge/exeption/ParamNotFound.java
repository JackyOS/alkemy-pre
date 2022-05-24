package com.alkemy.challenge.challenge.exeption;

public class ParamNotFound extends RuntimeException {

    public ParamNotFound(String error){
        super(error);
    }
}
