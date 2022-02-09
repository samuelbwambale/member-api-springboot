package com.example.memberapi.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "Member's first name should not be empty")
    private String firstName;

    @NotEmpty(message = "Member's last name should not be empty")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "create_ts")
    private LocalDate createTs;

    @Column(name = "last_updated_ts")
    private LocalDate lastUpdatedTs;
}

