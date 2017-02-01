package com.balinasoft.firsttask.system.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = InDateRangeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface InDateRange {
    // used to get later in the resource bundle the i18n text
    String message() default "Date not in range";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // min value, we for now just a string
    String min() default "1900-01-01";

    // max date value we support
    String max() default "2999-12-31";
}