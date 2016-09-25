package pl.rzrz.hibernate.validator;

import lombok.Data;
import org.junit.Test;
import pl.rzrz.hibernate.validator.Email.EmailAndConfirmation;
import pl.rzrz.hibernate.validator.Email.EmailFields;
import pl.rzrz.hibernate.validator.Email.EmailsMatch;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static pl.rzrz.hibernate.validator.Utils.validate;

public class EmailTests {

    @Data
    @EmailsMatch
    static class User implements EmailAndConfirmation {
        private final String email;
        private final String confirmedEmail;
    }

    @Test
    public void whenEmailsDontMatch_validatorReturnsErrors() {
        Set<ConstraintViolation<User>> errors =
                validate(new User("me@example.com", "you@example.com"));
        assertEquals(1, errors.size());
    }

    @Test
    public void whenEmailsMatch_validatorReturnsNoErrors() {
        Set<ConstraintViolation<User>> errors =
                validate(new User("me@example.com", "me@example.com"));
        assertEquals(0, errors.size());
    }

    @Test
    public void emailFieldMap_returnsCorrectConfirmationFieldName() {
        assertEquals("confirmedEmail", new EmailFields().getConfirmationFieldName());
    }
}
