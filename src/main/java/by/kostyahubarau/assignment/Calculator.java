package by.kostyahubarau.assignment;

import by.kostyahubarau.assignment.exception.InputPositionedException;
import by.kostyahubarau.assignment.parsing.Parser;
import by.kostyahubarau.assignment.parsing.model.ArithmeticExpression;
import by.kostyahubarau.assignment.tokenization.Tokenizer;
import by.kostyahubarau.assignment.tokenization.model.Token;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Scanner;

public class Calculator {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";

    public void readExecutePrintLoop() {
        Scanner scanner = new Scanner(System.in);

        boolean stop = false;
        do {
            System.out.print(green("> "));
            String arithmeticExpressionString = scanner.nextLine();
            if (!StringUtils.isAllBlank(arithmeticExpressionString)) {
                ArithmeticExpression arithmeticExpression = parseSafe(arithmeticExpressionString);
                Double result = null;
                try {
                    result = arithmeticExpression != null ? arithmeticExpression.evaluate() : null;
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                if (result != null) {
                    System.out.print(green("= "));
                    System.out.println(green(String.valueOf(arithmeticExpression.evaluate())));
                }
            } else {
                stop = true;
            }
        } while (!stop);
    }

    private ArithmeticExpression parseSafe(String arithmeticExpressionString) {
        try {
            List<Token> tokens = new Tokenizer(arithmeticExpressionString).tokenize();
            return new Parser(arithmeticExpressionString, tokens).parse();
        } catch (InputPositionedException e) {
            System.err.println(redBackground(e.getMessage() + ":"));
            System.err.println(e.getInput());
            System.err.println(red(StringUtils.leftPad(StringUtils.repeat('^', e.getEndOffset() - e.getStartOffset()), e.getEndOffset())));
            return null;
        }
    }

    private String green(String text) {
        return ANSI_GREEN + text + ANSI_RESET;
    }

    private String red(String text) {
        return ANSI_RED + text + ANSI_RESET;
    }

    private String redBackground(String text) {
        return ANSI_WHITE + ANSI_RED_BACKGROUND + text + ANSI_RESET;
    }

}
