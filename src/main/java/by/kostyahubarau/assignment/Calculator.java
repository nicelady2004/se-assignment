package by.kostyahubarau.assignment;

import by.kostyahubarau.assignment.parsing.Parser;
import by.kostyahubarau.assignment.parsing.model.ArithmeticExpression;
import by.kostyahubarau.assignment.tokenization.Tokenizer;
import by.kostyahubarau.assignment.tokenization.model.Token;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Scanner;

public class Calculator {

    public void readExecutePrintLoop() {
        Scanner scanner = new Scanner(System.in);

        boolean stop = false;
        do {
            System.out.print("> ");
            String arithmeticExpressionString = scanner.nextLine();
            if (!StringUtils.isAllBlank(arithmeticExpressionString)) {
                List<Token> tokens = new Tokenizer(arithmeticExpressionString).tokenize();
                ArithmeticExpression arithmeticExpression = new Parser(arithmeticExpressionString, tokens).parse();
                System.out.print("= ");
                System.out.println(arithmeticExpression.evaluate());
            } else {
                stop = true;
            }
        } while (!stop);
    }

}
