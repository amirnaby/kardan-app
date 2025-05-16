package com.niam.kardan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Entity(name = "Machine")
@Table
@SequenceGenerator(name = "Machine_seq", sequenceName = "Machine_seq", allocationSize = 1)
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Machine_seq")
    private Long id;
    private String name;
    private String code;
    @OneToOne
    private Operation operation;
}