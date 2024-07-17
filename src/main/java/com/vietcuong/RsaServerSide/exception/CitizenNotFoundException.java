package com.vietcuong.RsaServerSide.exception;

public class CitizenNotFoundException extends RuntimeException{
    public CitizenNotFoundException(){
        super("Citizen not found");
    }
}
