package by.kostyahubarau.assignment.tokenization;

import by.kostyahubarau.assignment.exception.TokenizerException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TokenizerTest {

    @Test
    public void mustTokenizeValidInput() {
        List<Token> tokens = new Tokenizer("123 + 456").tokenize();
        assertThat(tokens, is(Arrays.asList(new NumberToken(123), new Token(TokenType.PLUS), new NumberToken(456))));
    }

    @Test(expected = TokenizerException.class)
    public void mustThrowExceptionForInvalidNumber() {
        new Tokenizer("5.").tokenize();
    }

}
