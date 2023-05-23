package com.demo.ecommerce.validators;

import com.demo.ecommerce.exceptions.ObjectNotValidException;
import com.demo.ecommerce.model.Product;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ObjectValidator<T> {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    private final Logger log = LoggerFactory.getLogger(ObjectValidator.class);

    public void validate (T objectToValidate) {
        Set<ConstraintViolation<T>> violations = validator.validate(objectToValidate);

        if(!violations.isEmpty()){
            var errorMessages = violations
                    .stream()
                    .collect(Collectors.groupingBy(
                            violation -> violation.getPropertyPath().toString(),
                            Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList())));

            // Throw exception
            throw new ObjectNotValidException(errorMessages);
        }
    }
}
