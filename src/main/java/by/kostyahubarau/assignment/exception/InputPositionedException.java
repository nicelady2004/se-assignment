package by.kostyahubarau.assignment.exception;

public class InputPositionedException extends RuntimeException {

    private final String input;
    private final int startOffset;
    private final int endOffset;

    public InputPositionedException(String message, int startOffset, int endOffset, String input) {
        super(message);
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

}
