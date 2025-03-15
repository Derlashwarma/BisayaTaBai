package Parser.Nodes.DataTypes;

import Parser.Nodes.ASTNode;

public class StringLiteral extends ASTNode {
    private String value;
    public StringLiteral(String value){
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
