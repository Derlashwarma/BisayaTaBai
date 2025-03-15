package Parser.Nodes;

import java.util.List;

public class VariableDeclaration extends ASTNode {
    public String dataType;
    public List<Declaration> declarationList;

    public VariableDeclaration(String dataType, List<Declaration> declarationList) {
        this.dataType = dataType;
        this.declarationList = declarationList;
    }

    public String getDataType() {
        return dataType;
    }

    public List<Declaration> getDeclarationList() {
        return declarationList;
    }
}
