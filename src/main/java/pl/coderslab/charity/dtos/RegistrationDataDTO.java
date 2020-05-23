package pl.coderslab.charity.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.ScriptAssert;
import pl.coderslab.charity.validation.UniqueEmail;

import javax.validation.constraints.*;

// _this means the class/method that the annotation refer to (is above)
@ScriptAssert(lang = "javascript", script = "_this.password.equals(_this.rePassword)", reportOn = "rePassword")
public class RegistrationDataDTO {

    @NotBlank
    @Size(min = 2, max = 30)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 40)
    private String lastName;
    @NotBlank
    @Email
    @UniqueEmail
    private String email;
    @NotBlank @Size(min = 4, max = 12)
    private String password;
    @NotBlank @Size(min = 4, max = 12)
    private String rePassword;
    @NotNull
    @AssertTrue
    private Boolean termsAcceptance;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public Boolean getTermsAcceptance() {
        return termsAcceptance;
    }

    public void setTermsAcceptance(Boolean termsAcceptance) {
        this.termsAcceptance = termsAcceptance;
    }

    @Override
    public String toString() {
        return "RegistrationDataDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", rePassword='" + rePassword + '\'' +
                ", termsAcceptance=" + termsAcceptance +
                '}';
    }
}
