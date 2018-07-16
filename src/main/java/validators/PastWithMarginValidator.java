package validators;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class PastWithMarginValidator implements ConstraintValidator<PastWithMargin, Date> {
    private PastWithMargin constraintAnnotation;
    private ValidatorFactory validatorFactory;

    @Override
    public void initialize(PastWithMargin constraintAnnotation)
    {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context)
    {
        if (value == null) return true;
        return value.before(new Date(new Date().getTime() + 30000));
    }
}
