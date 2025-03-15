package Parser.Nodes;

public class Variable extends ASTNode{
    private String value;

    public Variable(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
