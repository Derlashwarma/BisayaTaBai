package Parser.Nodes;

public class Assignment extends ASTNode{
    private String identifier;
    private ASTNode value;

    public Assignment(String identifier, ASTNode value) {
        this.identifier = identifier;
        this.value = value;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ASTNode getValue() {
        return value;
    }
}
