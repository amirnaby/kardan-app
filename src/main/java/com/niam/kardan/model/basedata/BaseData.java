package com.niam.kardan.model.basedata;

import com.niam.kardan.model.Auditable;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseData extends Auditable {
    @NotNull
    @NotBlank
    private String name;
    private String description;
    @Column(unique = true)
    @NotNull
    @NotBlank
    private String code;
}