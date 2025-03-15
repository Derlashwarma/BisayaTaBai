package Parser.Nodes;

public class BinaryOperation extends ASTNode {

    private ASTNode left;
    private String operator;
    private ASTNode right;

    public BinaryOperation(ASTNode left, String operator, ASTNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public ASTNode getLeft() {
        return left;
    }

    public String getOperator() {
        return operator;
    }

    public ASTNode getRight() {
        return right;
    }
}
