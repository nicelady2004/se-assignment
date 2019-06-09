package by.kostyahubarau.assignment.tokenization.model;

import by.kostyahubarau.assignment.parsing.model.OperationType;

import java.util.Arrays;
import java.util.Objects;

public enum TokenType {

    PLUS('+'),
    MINUS('-'),
    MUL('*'),
    DIV('/'),
    POW('^'),
    LPAREN('('),
    RPAREN(')'),
    NUM(null);

    private final Character representation;

    TokenType(Character representation) {
        this.representation = representation;
    }

    public OperationType toOperationType() {
        return OperationType.valueOf(name());
    }

    public static TokenType fromCharacterRepresentation(Character representation) {
        Objects.requireNonNull(representation);
        return Arrays.stream(values())
                .filter(tokenType -> representation.equals(tokenType.representation))
                .findFirst()
                .orElse(null);
    }

}
