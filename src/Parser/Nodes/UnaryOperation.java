package Parser.Nodes;

public class UnaryOperation extends ASTNode{
    private String operator;
    private ASTNode operand;

    public UnaryOperation(String operator, ASTNode operand) {
        this.operator = operator;
        this.operand = operand;
    }

    public String getOperator() {
        return operator;
    }

    public ASTNode getOperand() {
        return operand;
    }
}
