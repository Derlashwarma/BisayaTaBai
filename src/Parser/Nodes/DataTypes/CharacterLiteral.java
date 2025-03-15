package Parser.Nodes.DataTypes;

import Parser.Nodes.ASTNode;

public class CharacterLiteral extends ASTNode {
    private char value;

    public CharacterLiteral(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }
}
