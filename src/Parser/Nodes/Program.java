package Parser.Nodes;

import java.util.List;

public class Program extends ASTNode{
    private List<ASTNode> statements;

    public Program(List<ASTNode> statements) {
        this.statements = statements;
    }

    public List<ASTNode> getStatements() {
        return statements;
    }
}
