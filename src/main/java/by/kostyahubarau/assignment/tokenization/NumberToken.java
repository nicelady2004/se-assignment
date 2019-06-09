package by.kostyahubarau.assignment.tokenization;

import java.util.Objects;

public class NumberToken extends Token {

    private final double value;

    public NumberToken(double value) {
        super(TokenType.NUM);
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberToken that = (NumberToken) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
