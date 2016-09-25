package pl.rzrz.hibernate.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class Password {

    public interface PasswordAndConfirmation {
        String getPassword();

        String getConfirmedPassword();
    }

    public static class PasswordFields implements SelectFields<PasswordAndConfirmation> {

        @Override
        public Object field(PasswordAndConfirmation obj) {
            return obj.getPassword();
        }

        @Override
        public Object confirmedField(PasswordAndConfirmation obj) {
            return obj.getConfirmedPassword();
        }

        @Override
        public String getConfirmationFieldName() {
            return "confirmedPassword";
        }
    }

    @Documented
    @Target({ TYPE })
    @Retention(RUNTIME)
    @Constraint(validatedBy = { })
    @FieldsMatch(fields = PasswordFields.class)
    public @interface PasswordsMatch {

        String message() default "Passwords must match";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }

}
