package org.botscrew.testtask.exception;

import org.botscrew.testtask.entity.Lector;

import java.text.MessageFormat;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class<?> entityClass) {
        super(MessageFormat.format("{0} not found", entityClass.getSimpleName()));
    }
}
