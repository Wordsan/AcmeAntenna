package validators;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidatorFactory;

public class HasCustomValidatorsValidator implements ConstraintValidator<HasCustomValidators, Object> {
    private HasCustomValidators constraintAnnotation;

    @Override
    public void initialize(HasCustomValidators constraintAnnotation)
    {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context)
    {
        boolean result = true;

        if (value == null) return true;

        for (Method m : value.getClass().getDeclaredMethods()) {
            CustomValidator annotation;
            if ((annotation = m.getAnnotation(CustomValidator.class)) != null) {
                try {
                    Boolean isValid = (Boolean) m.invoke(value);

                    if (!isValid) {
                        result = false;

                        if (annotation.applyOn().length > 0) {
                            for (String property : annotation.applyOn()) {
                                ConstraintValidatorContext.ConstraintViolationBuilder builder = context.buildConstraintViolationWithTemplate(annotation.message());
                                builder.addNode(property);
                                builder.addConstraintViolation();
                            }
                        } else {
                            ConstraintValidatorContext.ConstraintViolationBuilder builder = context.buildConstraintViolationWithTemplate(annotation.message());
                            builder.addConstraintViolation();
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (!result) context.disableDefaultConstraintViolation();

        return result;
    }
}
