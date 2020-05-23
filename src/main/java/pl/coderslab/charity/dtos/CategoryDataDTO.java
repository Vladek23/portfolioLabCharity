package pl.coderslab.charity.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CategoryDataDTO {

    @NotNull
    @Positive
    @Digits(integer = 4, fraction = 0)
    @UniqueElements
    private Long id;
    @NotBlank
    @Length(min = 3, max = 40)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryDataDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
