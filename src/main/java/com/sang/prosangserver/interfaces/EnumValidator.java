package com.sang.prosangserver.interfaces;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.sang.prosangserver.enums.Roles;
import com.sang.prosangserver.enums.lyric.LyricStatuses;

public class EnumValidator implements ConstraintValidator<EnumValidation, Integer> {
	
    private List<Integer> acceptedValues;

    @Override
    public void initialize(EnumValidation annotation) {
    	if (annotation.enumClass() == Roles.class) {
    		acceptedValues = Stream.of(Roles.values())
                    .map(Roles::getId)
                    .collect(Collectors.toList());
    	} else if (annotation.enumClass() == LyricStatuses.class) {
			acceptedValues = Stream.of(LyricStatuses.values())
				.map(LyricStatuses::getId)
				.collect(Collectors.toList());
		}
		else {
    		acceptedValues = null;
    	}
    }
        
	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if (acceptedValues == null) {
			return false;
		}
		return acceptedValues.contains(value);
	}
	
}
