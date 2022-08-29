package com.balinasoft.firsttask.domain;

import com.balinasoft.firsttask.domain.api2.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "image")
@Setter
@Getter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String url;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column
    double lat;

    @Column
    double lng;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;
}
