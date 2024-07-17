package com.vietcuong.RsaServerSide.entiity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "citizen")
public class Citizen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "")
    private Integer id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "id_number")
    private String idNumber;

}
