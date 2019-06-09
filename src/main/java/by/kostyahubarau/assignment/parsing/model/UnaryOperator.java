package by.kostyahubarau.assignment.parsing.model;

import java.util.Objects;

public class UnaryOperator extends ArithmeticExpression {

    private final ArithmeticExpression operand;

    public UnaryOperator(OperationType operationType, ArithmeticExpression operand) {
        if (!OperationType.MINUS.equals(operationType)) {
            throw new IllegalArgumentException("Illegal unary operation!");
        }
        this.operand = Objects.requireNonNull(operand);
    }

    @Override
    public Double evaluate() {
        return -operand.evaluate();
    }

}
