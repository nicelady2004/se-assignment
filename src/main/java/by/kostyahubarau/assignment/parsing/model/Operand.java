package by.kostyahubarau.assignment.parsing.model;

public class Operand extends ArithmeticExpression {

    private final double value;

    public Operand(double value) {
        this.value = value;
    }

    @Override
    public Double evaluate() {
        return value;
    }

}
