package com.niam.kardan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Entity(name = "Plan")
@Table
@SequenceGenerator(name = "plan_seq", sequenceName = "plan_seq", allocationSize = 1)
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plan_seq")
    private Long id;
    @NotNull @NotBlank
    private String name;
    @NotNull @NotBlank
    private String code;
    @NotNull @NotBlank
    @ManyToOne
    @JoinColumn
    private Part part;
    @NotNull @NotBlank
    @ManyToOne
    @JoinColumn
    private Operation operation;
    @NotNull @NotBlank
    private Integer priority;
    @NotNull @NotBlank
    private String username;
    private LocalDateTime dueDate;
}