package com.example.database.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Setter
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Impression.TABLE_NAME)
public class Impression {
    public static final String ID = "id";
    public static final String TABLE_NAME = "impression";


    @Id
    @Column(name = ID)
    private UUID id;


    private Integer appId;

    private String countryCode;
    private Integer advertiserId;

}
