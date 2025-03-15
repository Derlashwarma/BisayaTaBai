package Parser.Nodes.DataTypes;

import Parser.Nodes.ASTNode;

public class NumberLiteral extends ASTNode {
    private int value;
    public NumberLiteral(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
