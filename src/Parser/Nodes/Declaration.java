package Parser.Nodes;

public class Declaration extends ASTNode {
    private String identifier;
    private ASTNode initialValue;

    public Declaration(String identifier, ASTNode initialValue) {
        this.identifier = identifier;
        this.initialValue = initialValue;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ASTNode getInitialValue() {
        return initialValue;
    }
}
