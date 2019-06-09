package by.kostyahubarau.assignment.tokenization;

import by.kostyahubarau.assignment.exception.InputPositionedException;
import by.kostyahubarau.assignment.tokenization.model.Token;
import by.kostyahubarau.assignment.tokenization.model.TokenType;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TokenizerTest {

    @Test
    public void mustTokenizeValidInput() {
        List<Token> tokens = new Tokenizer("123 + 456").tokenize();
        assertThat(
                tokens,
                is(
                        Arrays.asList(
                                new Token(TokenType.NUM, 123d),
                                new Token(TokenType.PLUS, null),
                                new Token(TokenType.NUM, 456d))
                )
        );
    }

    @Test(expected = InputPositionedException.class)
    public void mustThrowExceptionForInvalidNumber() {
        new Tokenizer("1.1.").tokenize();
    }

}
