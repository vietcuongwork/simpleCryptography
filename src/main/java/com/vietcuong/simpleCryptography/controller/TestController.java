package com.vietcuong.simpleCryptography.controller;

import com.vietcuong.simpleCryptography.ServerRSAUtil;
import com.vietcuong.simpleCryptography.Service.CitizenService;
import com.vietcuong.simpleCryptography.entiity.Citizen;
import com.vietcuong.simpleCryptography.entiity.Message;
import com.vietcuong.simpleCryptography.repository.CitizenRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController {

    private final ServerRSAUtil rsa;
    private final CitizenRepository citizenRepository;
    private final CitizenService citizenService;

    public TestController(ServerRSAUtil rsa, CitizenRepository citizenRepository, CitizenService citizenService) {
        this.rsa = rsa;
        this.citizenRepository = citizenRepository;
        this.citizenService = citizenService;
        this.rsa.initKeys(); // Initialize RSA keys when TestController is instantiated
    }

/*    @GetMapping("/test")
    public List<String> test() {
        String plainMessage = "Hello";
        List<String> result = new ArrayList<>();
        try {
            String encryptedMessage = rsa.encrypt(plainMessage);
            String decryptedMessage = rsa.decrypt(encryptedMessage);
            result.add(encryptedMessage);
            result.add(decryptedMessage);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }*/

/*    @PostMapping("/testDecrypt")
    public String testDecrypt(
            @RequestBody
            Message encryptedMessage) {
        try {
            System.out.println("Encrypted Message: " + encryptedMessage.getMessage());
            String decryptedMessage = rsa.decrypt(encryptedMessage.getMessage());
            System.out.println("Decrypted Message: " + decryptedMessage);
            return decryptedMessage;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }*/

/*
    @PostMapping("/testEncrypt")
    public String testEncrypt(
            @RequestBody
            Message message) {
        try {
            return rsa.encrypt(message.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }*/

    @PostMapping("/addCitizen")
    public Citizen addCitizen(
            @RequestBody
            Citizen citizen) {
        try {
            return citizenRepository.save(citizen);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/addMultipleCitizen")
    public List<Citizen> addCitizen(
            @RequestBody
            List<Citizen> citizens) {
        try {
            for (Citizen citizen : citizens) {
                citizenRepository.save(citizen);
            }
            return citizenRepository.findAll();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/getAllCitizen")
    public List<Citizen> getAllCitizen() {
        try {
            List<Citizen> citizens = citizenRepository.findAll();
            return citizens;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    @PostMapping("/getCitizenByIdNumber/{idNumber}")
    public Citizen getCitizenById(@PathVariable String idNumber){
        return citizenService.getCitizenByIdNumber(idNumber);
    }

    @PostMapping("/getCitizenName")
    public String encryptedCitizenName(
            @RequestBody
            Message citizenInfo) {
        try {

            String encryptedIdNumber = citizenInfo.getMessage();
            System.out.println(encryptedIdNumber);
            String decryptedIdNumber = rsa.decrypt(encryptedIdNumber);
            System.out.println(decryptedIdNumber);
            System.out.println(citizenService.getCitizenByIdNumber(decryptedIdNumber));
            return rsa.encrypt(citizenService.getCitizenByIdNumber(decryptedIdNumber)
                    .getFullName(), rsa.getClientPublicKey());
        } catch (Exception e) {
            return e.getClass() + e.getMessage();
        }
    }
}
