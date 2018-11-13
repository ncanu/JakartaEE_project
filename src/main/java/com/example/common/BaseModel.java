package com.example.common;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class BaseModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
