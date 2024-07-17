package com.vietcuong.simpleCryptography.repository;

import com.vietcuong.simpleCryptography.entiity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CitizenRepository extends JpaRepository<Citizen, Integer> {
    Optional<Citizen> findByIdNumber(String idNumber);
}
