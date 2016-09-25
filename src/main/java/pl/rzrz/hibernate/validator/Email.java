package pl.rzrz.hibernate.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class Email {

    public interface EmailAndConfirmation {

        String getEmail();

        String getConfirmedEmail();
    }

    public static class EmailFields implements SelectFields<EmailAndConfirmation> {

        @Override
        public Object field(EmailAndConfirmation obj) {
            return obj.getEmail();
        }

        @Override
        public Object confirmedField(EmailAndConfirmation obj) {
            return obj.getConfirmedEmail();
        }

        @Override
        public String getConfirmationFieldName() {
            return "confirmedEmail";
        }
    }

    @Documented
    @Target({ TYPE })
    @Retention(RUNTIME)
    @Constraint(validatedBy = { })
    @FieldsMatch(fields = EmailFields.class)
    public @interface EmailsMatch {
        String message() default "Passwords must match";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }
}
