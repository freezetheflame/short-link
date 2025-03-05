package com.example.short_link.linkage.dao;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Setter
@Getter
@Table(name = "short_links")
public class ShortLinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalUrl;

    @Column
    private String shortKey;

    @Column
    private Date createdAt;
}
