package jschool.validator;
import  jschool.dto.UserDTO;
import jschool.validator.annotations.PasswordMatches;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * validator for password match chec
 * */
@Service
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        UserDTO userDto = (UserDTO) value;
        if (!Objects.isNull(userDto.getConfirmPassword()) && (!Objects.isNull(userDto.getPassword()))) {
            return userDto.getPassword().equals(userDto.getConfirmPassword());
        }
        return true;
    }

}
