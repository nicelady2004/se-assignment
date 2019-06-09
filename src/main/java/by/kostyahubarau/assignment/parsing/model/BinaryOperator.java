package by.kostyahubarau.assignment.parsing.model;

import java.util.Objects;

public class BinaryOperator extends ArithmeticExpression {

    private final OperationType operationType;
    private final ArithmeticExpression leftOperand;
    private final ArithmeticExpression rightOperand;

    public BinaryOperator(OperationType operationType, ArithmeticExpression leftOperand, ArithmeticExpression rightOperand) {
        this.operationType = Objects.requireNonNull(operationType);
        this.leftOperand = Objects.requireNonNull(leftOperand);
        this.rightOperand = Objects.requireNonNull(rightOperand);
    }

    @Override
    public Double evaluate() {
        double result;
        switch (operationType) {
            case PLUS:
                result = leftOperand.evaluate() + rightOperand.evaluate();
                break;
            case MINUS:
                result = leftOperand.evaluate() - rightOperand.evaluate();
                break;
            case MUL:
                result = leftOperand.evaluate() * rightOperand.evaluate();
                break;
            case DIV:
                result = leftOperand.evaluate() / rightOperand.evaluate();
                break;
            case POW:
                result = Math.pow(leftOperand.evaluate(), rightOperand.evaluate());
                break;
            default:
                throw new IllegalArgumentException("Illegal operation type!");
        }
        return result;
    }

}
