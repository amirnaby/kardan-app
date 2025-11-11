package com.niam.kardan.model.basedata;

import com.niam.kardan.model.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BaseData extends Auditable {
    @NotNull
    @NotBlank
    private String name;
    private String description;
    @Column(unique = true, nullable = false, updatable = false)
    private String code;

    public BaseData(String code) {
        this.code = code;
    }

    public static <T extends BaseData> T ofCode(Class<T> type, String code) {
        try {
            T instance = type.getDeclaredConstructor().newInstance();
            instance.setCode(code);
            return instance;
        } catch (Exception e) {
            throw new IllegalStateException("Cannot instantiate " + type.getSimpleName(), e);
        }
    }
}