package by.kostyahubarau.assignment.tokenization;

import by.kostyahubarau.assignment.exception.InputPositionedException;
import by.kostyahubarau.assignment.tokenization.model.Token;
import by.kostyahubarau.assignment.tokenization.model.TokenType;
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
        return !StringUtils.isAllBlank(input) ? tokenizeNonBlankInput() : Collections.emptyList();
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
            return new Token(TokenType.NUM, Double.valueOf(match));
        } else {
            throw new InputPositionedException("Malformed token starting at position", currentCharacterIndex, currentCharacterIndex + 1, input);
        }
    }

    private Token consumeOperator() {
        TokenType tokenType = TokenType.fromCharacterRepresentation(input.charAt(currentCharacterIndex));

        if (tokenType == null) {
            throw new InputPositionedException("Malformed token at position", currentCharacterIndex, currentCharacterIndex + 1, input);
        }

        currentCharacterIndex++;

        return new Token(tokenType, null);
    }

    private void consumeWhitespaces() {
        while (Character.isWhitespace(input.charAt(currentCharacterIndex))) {
            currentCharacterIndex++;
        }
    }

}
