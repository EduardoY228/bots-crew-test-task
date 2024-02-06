package org.botscrew.testtask.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Degree {
    ASSISTANT(0),
    ASSOCIATE_PROFESSOR(1),
    PROFESSOR(2);

    private final int code;

    public static Degree findByCode(int code) {
        return switch (code) {
            case 0, 1, 2 -> Arrays
                    .stream(Degree.values())
                    .filter(d -> d.getCode() == code)
                    .findFirst()
                    .orElseThrow();
            default -> throw new IllegalArgumentException(MessageFormat.format("Unexpected value: {0}", code));
        };
    }
}
