package pl.rzrz.hibernate.validator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class Utils {

    private static Validator validator;

    static {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    static <T> Set<ConstraintViolation<T>> validate(T obj) {
        return validator.validate(obj);
    }
}
