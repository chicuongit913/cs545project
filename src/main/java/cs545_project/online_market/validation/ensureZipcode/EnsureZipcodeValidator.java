package cs545_project.online_market.validation.ensureZipcode;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnsureZipcodeValidator implements ConstraintValidator<EnsureZipcode, Object> {
    private EnsureZipcode ensureNumber;

    @Override
    public void initialize(EnsureZipcode constraintAnnotation) {
        this.ensureNumber = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        // Initialize it.
        String regex = ensureNumber.decimal() ? "-?[0-9][0-9\\.\\,]*" : "-?[0-9]+";
        String data = String.valueOf(value);
        return data.matches(regex) && data.length() == 5;
    }

}
