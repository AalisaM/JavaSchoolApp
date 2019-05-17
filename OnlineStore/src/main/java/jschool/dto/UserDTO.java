package jschool.dto;

import java.sql.Date;
import java.util.Set;

import jschool.validator.annotations.PasswordMatches;
import jschool.validator.annotations.PhoneNumber;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Data
@PasswordMatches
public class UserDTO {
    private int id;

    @Length(min=4, message = "Name is too short")
    private String fullName;

    @Email(message = "Email should be valid")
    private String email;

    private Date birth;

    @Length(min=4, message = "Password is too short")
    private String password;

    private String confirmPassword;

    @PhoneNumber
    private String phone;

    private AddressDTO activeAddressId;
    private Set<AddressDTO> addresses;
    private boolean isAdmin;

}
