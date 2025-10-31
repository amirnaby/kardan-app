package com.niam.kardan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull @NotBlank
    private String name;
    @NotNull @NotBlank
    private String code;
    @NotNull @NotBlank
    @OneToOne
    private Operation operation;
}