package by.kostyahubarau.assignment.parsing;

import by.kostyahubarau.assignment.exception.InputPositionedException;
import by.kostyahubarau.assignment.parsing.model.*;
import by.kostyahubarau.assignment.tokenization.model.Token;
import by.kostyahubarau.assignment.tokenization.model.TokenType;

import java.util.List;

public class Parser {

    private final String input;
    private final List<Token> tokens;
    private int currentTokenIndex;

    public Parser(String input, List<Token> tokens) {
        this.input = input;
        this.tokens = tokens;
    }

    /**
     * expression --> thirdPriorityExpression {( "+" | "-" ) thirdPriorityExpression}
     * thirdPriorityExpression --> secondPriorityExpression {( "*" | "/" ) secondPriorityExpression}
     * secondPriorityExpression --> firstPriorityExpression ["^" firstPriorityExpression]
     * firstPriorityExpression --> v | "(" expression ")" | "-" expression
     */
    public ArithmeticExpression parse() {
        ArithmeticExpression arithmeticExpression = expression();
        assertAllConsumed();
        return arithmeticExpression;
    }

    private ArithmeticExpression expression() {
        ArithmeticExpression arithmeticExpression = thirdPriorityExpression();
        while (TokenType.PLUS.equals(tokenToConsumeType()) || TokenType.MINUS.equals(tokenToConsumeType())) {
            OperationType operationType = consume().getTokenType().toOperationType();
            ArithmeticExpression arithmeticExpressionSubtree = thirdPriorityExpression();
            arithmeticExpression = new BinaryOperator(operationType, arithmeticExpression, arithmeticExpressionSubtree);
        }
        return arithmeticExpression;
    }

    private ArithmeticExpression thirdPriorityExpression() {
        ArithmeticExpression arithmeticExpression = secondPriorityExpression();
        while (TokenType.MUL.equals(tokenToConsumeType()) || TokenType.DIV.equals(tokenToConsumeType())) {
            OperationType operationType = consume().getTokenType().toOperationType();
            ArithmeticExpression arithmeticExpressionSubtree = secondPriorityExpression();
            arithmeticExpression = new BinaryOperator(operationType, arithmeticExpression, arithmeticExpressionSubtree);
        }
        return arithmeticExpression;
    }

    private ArithmeticExpression secondPriorityExpression() {
        ArithmeticExpression arithmeticExpression = firstPriorityExpression();
        if (TokenType.POW.equals(tokenToConsumeType())) {
            OperationType operationType = consume().getTokenType().toOperationType();
            ArithmeticExpression arithmeticExpressionSubtree = firstPriorityExpression();
            arithmeticExpression = new BinaryOperator(operationType, arithmeticExpression, arithmeticExpressionSubtree);
        }
        return arithmeticExpression;
    }

    private ArithmeticExpression firstPriorityExpression() {
        ArithmeticExpression arithmeticExpression = null;
        if (TokenType.NUM.equals(tokenToConsumeType())) {
            arithmeticExpression = new Operand(consume().getValue());
        } else if (TokenType.LPAREN.equals(tokenToConsumeType())) {
            consume();
            arithmeticExpression = expression();
            assertClosingParenthesisAndConsume();
        } else if (TokenType.MINUS.equals(tokenToConsumeType())) {
            arithmeticExpression = new UnaryOperator(consume().getTokenType().toOperationType(), thirdPriorityExpression());
        } else {
            fail("Unexpected token found in input");
        }
        return arithmeticExpression;
    }

    private Token consume() {
        return currentTokenIndex < tokens.size() ? tokens.get(currentTokenIndex++) : null;
    }

    private Token tokenToConsume() {
        return currentTokenIndex < tokens.size() ? tokens.get(currentTokenIndex) : null;
    }

    private TokenType tokenToConsumeType() {
        Token tokenToConsume = tokenToConsume();
        return tokenToConsume != null ? tokenToConsume.getTokenType() : null;
    }

    private void assertAllConsumed() {
        if (currentTokenIndex < tokens.size()) {
            fail("Non-consumed token found in input");
        }
    }

    private void assertClosingParenthesisAndConsume() {
        if (!TokenType.RPAREN.equals(tokenToConsumeType())) {
            fail("\")\" expected");
        } else {
            consume();
        }
    }

    private void fail(String reason) {
        Token tokenToConsume = tokenToConsume();
        int startOffset = tokenToConsume != null ? tokenToConsume.getStartOffset() : input.length();
        int endOffset = tokenToConsume != null ? tokenToConsume.getEndOffset() : startOffset + 1;
        throw new InputPositionedException(reason, startOffset, endOffset, input);
    }

}
