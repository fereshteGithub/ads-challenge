package com.example.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Setter
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Click.TABLE_NAME)
public class Click {
    public static final String ID = "id";
    public static final String TABLE_NAME = "click";
    public static final String IMPRESSION = "fk_impression_id";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID)
    private Integer id;


    private double revenue;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = IMPRESSION)
    private Impression impression;
}
