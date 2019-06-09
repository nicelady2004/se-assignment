package by.kostyahubarau.assignment.tokenization;

import java.util.Objects;

public class Token {

    private final TokenType tokenType;

    public Token(TokenType tokenType) {
        if (TokenType.NUM.equals(tokenType) && !NumberToken.class.equals(this.getClass())) {
            throw new IllegalArgumentException("Number tokens must be of class " + NumberToken.class);
        }
        this.tokenType = tokenType;
    }

    public boolean isNumber() {
        return TokenType.NUM.equals(tokenType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return tokenType == token.tokenType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenType);
    }

}
