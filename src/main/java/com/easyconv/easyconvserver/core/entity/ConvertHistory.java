package com.easyconv.easyconvserver.core.entity;

import javax.persistence.*;

@Entity
public class ConvertHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
