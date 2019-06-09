package by.kostyahubarau.assignment.tokenization.model;

import java.util.Objects;

public class Token {

    private final TokenType tokenType;
    private Double value;
    private int startOffset;
    private int endOffset;

    public Token(TokenType tokenType, Double value) {
        if (TokenType.NUM.equals(tokenType)) {
            this.value = Objects.requireNonNull(value);
        } else {
            this.value = null;
        }
        this.tokenType = tokenType;
    }

    public Token(TokenType tokenType, Double value, int startOffset, int endOffset) {
        this(tokenType, value);
        this.startOffset = startOffset;
        this.endOffset = endOffset;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public Double getValue() {
        return value;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return tokenType == token.tokenType &&
                Objects.equals(value, token.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenType, value);
    }

}
