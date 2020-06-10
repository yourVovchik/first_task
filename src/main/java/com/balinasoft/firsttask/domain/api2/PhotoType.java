package com.balinasoft.firsttask.domain.api2;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class PhotoType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 1000)
    private String name;

    @Column(length = 1000)
    private String image;

}
