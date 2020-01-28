package com.repostats.ghbackend.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Search implements Serializable {
    private static final long serialVersionUID = 6964638534739427842L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String searchedUsername;

    @ManyToOne
    private User owner;
}
