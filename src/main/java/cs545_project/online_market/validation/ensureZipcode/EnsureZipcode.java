package cs545_project.online_market.validation.ensureZipcode;

import javax.validation.Payload;
import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EnsureZipcodeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnsureZipcode {

    String message() default "Zip Code must be have 5 digit!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean decimal() default false;

}
