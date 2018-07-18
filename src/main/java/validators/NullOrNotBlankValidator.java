package validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NullOrNotBlankValidator implements ConstraintValidator<NullOrNotBlank, CharSequence> {
    @Override
    public void initialize(NullOrNotBlank constraintAnnotation)
    {

    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context)
    {
        if (value == null) return true;
        if (value.toString().trim().length() > 0) return true;
        return false;
    }
}
