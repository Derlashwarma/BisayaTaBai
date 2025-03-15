package Parser.Nodes;

import java.util.List;

public class OutputStatement extends ASTNode{
    List<ASTNode> expressions;

    public OutputStatement(List<ASTNode> statements) {
        this.expressions = statements;
    }

    public List<ASTNode> getExpressions() {
        return expressions;
    }
}
