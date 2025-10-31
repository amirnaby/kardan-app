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
@Entity(name = "Part")
@Table
@SequenceGenerator(name = "part_seq", sequenceName = "part_seq", allocationSize = 1)
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "part_seq")
    private Long id;
    @NotNull @NotBlank
    private String name;
    @NotNull @NotBlank
    private String code;
    @NotNull @NotBlank
    @ManyToOne
    @JoinColumn
    private Project project;
}