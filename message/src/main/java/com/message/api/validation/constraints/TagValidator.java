package com.message.api.validation.constraints;

import com.message.api.entity.TagEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class TagValidator implements ConstraintValidator<TagValidation, String>
{

    static final List<String> list = Arrays.asList(TagEnum.TAG1.getValue(),TagEnum.TAG2.getValue(),TagEnum.TAG3.getValue());

    @Override
    public void initialize(TagValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    @Override
    public boolean isValid(String tagName, ConstraintValidatorContext cxt) {
        return StringUtils.contains((CharSequence) list,tagName);
    }
}
