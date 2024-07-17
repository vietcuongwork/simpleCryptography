package com.vietcuong.simpleCryptography.Service;

import com.vietcuong.simpleCryptography.entiity.Citizen;
import com.vietcuong.simpleCryptography.exception.CitizenNotFoundException;
import com.vietcuong.simpleCryptography.repository.CitizenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CitizenService {
    private final CitizenRepository citizenRepository;

    public CitizenService(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

    public Citizen getCitizenByIdNumber(String idNumber) throws CitizenNotFoundException {

        Optional<Citizen> citizenOptional = citizenRepository.findByIdNumber(idNumber);
        if (citizenOptional.isPresent()) {
            Citizen citizen = citizenOptional.get();
            return citizen;
        }
        throw new CitizenNotFoundException();
    }
}
