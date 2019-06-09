package by.kostyahubarau.assignment.tokenization;

import by.kostyahubarau.assignment.exception.TokenizerException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

    // Accepts one of the following formats: "1", ".1", "0.1".
    private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d+(\\.\\d+)?)|(\\.\\d+)");

    private final String input;
    private int currentCharacterIndex;

    public Tokenizer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        return !StringUtils.isAllBlank(input) ? tokenizeNonBlankInput() : Collections.<Token>emptyList();
    }

    private List<Token> tokenizeNonBlankInput() {
        LinkedList<Token> tokens = new LinkedList<>();

        while (currentCharacterIndex < input.length()) {
            consumeWhitespaces();
            if (currentCharacterBelongsToNumber()) {
                tokens.add(consumeNumber());
            } else {
                tokens.add(consumeOperator());
            }
        }

        return tokens;
    }

    private boolean currentCharacterBelongsToNumber() {
        return Character.isDigit(input.charAt(currentCharacterIndex)) || input.charAt(currentCharacterIndex) == '.';
    }

    private Token consumeNumber() {
        Matcher matcher = NUMBER_PATTERN.matcher(input).region(currentCharacterIndex, input.length());
        if (matcher.lookingAt()) {
            String match = matcher.group();
            currentCharacterIndex += match.length();
            return new NumberToken(Double.valueOf(match));
        } else {
            throw new TokenizerException("Malformed token starting at position", currentCharacterIndex, currentCharacterIndex + 1, input);
        }
    }

    private Token consumeOperator() {
        TokenType tokenType;

        switch (input.charAt(currentCharacterIndex)) {
            case '+':
                tokenType = TokenType.PLUS;
                break;
            case '-':
                tokenType = TokenType.MINUS;
                break;
            case '*':
                tokenType = TokenType.MUL;
                break;
            case '/':
                tokenType = TokenType.DIV;
                break;
            case '^':
                tokenType = TokenType.POW;
                break;
            case '(':
                tokenType = TokenType.LPAREN;
                break;
            case ')':
                tokenType = TokenType.RPAREN;
                break;
            default:
                throw new TokenizerException("Malformed token at position", currentCharacterIndex, currentCharacterIndex + 1, input);
        }
        currentCharacterIndex++;

        return new Token(tokenType);
    }

    private void consumeWhitespaces() {
        while (Character.isWhitespace(input.charAt(currentCharacterIndex))) {
            currentCharacterIndex++;
        }
    }

}
