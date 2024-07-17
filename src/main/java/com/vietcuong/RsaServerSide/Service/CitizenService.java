package com.vietcuong.RsaServerSide.Service;

import com.vietcuong.RsaServerSide.entiity.Citizen;
import com.vietcuong.RsaServerSide.exception.CitizenNotFoundException;
import com.vietcuong.RsaServerSide.repository.CitizenRepository;
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
