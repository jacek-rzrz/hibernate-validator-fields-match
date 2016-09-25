package pl.rzrz.hibernate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FieldMatchValidator implements ConstraintValidator<FieldsMatch, Object> {

    private SelectFields selectFields;

    @Override
    public void initialize(FieldsMatch constraintAnnotation) {
        try {
            this.selectFields = constraintAnnotation.fields().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        Object field = selectFields.field(obj);
        Object confirmedField = selectFields.confirmedField(obj);
        if(Objects.equals(field, confirmedField)) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode(selectFields.getConfirmationFieldName())
                .addConstraintViolation();

        return false;
    }
}