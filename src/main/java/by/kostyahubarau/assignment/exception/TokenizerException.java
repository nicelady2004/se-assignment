package by.kostyahubarau.assignment.exception;

public class TokenizerException extends RuntimeException {

    private final String input;
    private final int startOffset;
    private final int endOffset;

    public TokenizerException(String message, int startOffset, int endOffset, String input) {
        super(message);
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.input = input;
    }

}
