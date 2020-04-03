package com.marcinrosol.carrental.validation.enums;


import com.marcinrosol.carrental.models.Enums.CarType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CarTypeSubSetValidator.class)
public @interface CarTypeSubset {
    CarType[] anyOf();
    String message() default  "must be any of {anyOf}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
