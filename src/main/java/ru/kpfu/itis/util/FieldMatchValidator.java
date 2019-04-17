package ru.kpfu.itis.util;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String errorMessage;

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        errorMessage = constraintAnnotation.message();
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            final BeanWrapperImpl bean = new BeanWrapperImpl(value);
            final Object firstObj = bean.getPropertyValue(firstFieldName);
            final Object secondObj = bean.getPropertyValue(secondFieldName);
            boolean returnValue = firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
            if (!returnValue) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(errorMessage).addPropertyNode(secondFieldName).addConstraintViolation();
            }
            return returnValue;
        } catch (final Exception ignore) {
            //ignore
        }
        return true;
    }
}
