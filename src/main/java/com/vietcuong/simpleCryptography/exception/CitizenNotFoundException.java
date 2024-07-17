package com.vietcuong.simpleCryptography.exception;

public class CitizenNotFoundException extends RuntimeException{
    public CitizenNotFoundException(){
        super("Citizen not found");
    }
}
