package Parser.Nodes.DataTypes;

import Parser.Nodes.ASTNode;

public class FloatLiteral extends ASTNode {
    private float value;
    public FloatLiteral(float value){
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}
