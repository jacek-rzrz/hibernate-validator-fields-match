package pl.rzrz.hibernate.validator;

import lombok.Data;
import org.junit.Test;
import pl.rzrz.hibernate.validator.Password.PasswordsMatch;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static pl.rzrz.hibernate.validator.Utils.validate;

public class PasswordTests {

    @Data
    @PasswordsMatch
    static class User implements Password.PasswordAndConfirmation {
        private final String password;
        private final String confirmedPassword;
    }

    @Test
    public void whenPasswordsDontMatch_validatorReturnsErrors() {
        Set<ConstraintViolation<User>> errors = validate(new User("pass", "pass2"));
        assertEquals(1, errors.size());
    }

    @Test
    public void whenPasswordsMatch_validatorReturnsNoErrors() {
        Set<ConstraintViolation<User>> errors = validate(new User("pass", "pass"));
        assertEquals(0, errors.size());
    }

    @Test
    public void passwordFieldMap_returnsCorrectConfirmationFieldName() {
        assertEquals("confirmedPassword", new Password.PasswordFieldMap().getConfirmationFieldName());
    }
}