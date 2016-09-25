package pl.rzrz.hibernate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FieldMatchValidator implements ConstraintValidator<FieldsMatch, Object> {

    private FieldMap fieldMap;

    @Override
    public void initialize(FieldsMatch constraintAnnotation) {
        try {
            this.fieldMap = constraintAnnotation.fieldMap().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        Object field = fieldMap.field(obj);
        Object confirmedField = fieldMap.confirmedField(obj);
        if(Objects.equals(field, confirmedField)) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode(fieldMap.getConfirmationFieldName())
                .addConstraintViolation();

        return false;
    }
}