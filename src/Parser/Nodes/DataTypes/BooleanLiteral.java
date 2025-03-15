package Parser.Nodes.DataTypes;

import Parser.Nodes.ASTNode;

public class BooleanLiteral extends ASTNode {
    public boolean booleanValue;
    public String stringValue;

    public BooleanLiteral(String stringValue){
        this.stringValue = stringValue.toUpperCase();
        if(this.stringValue.equals("OO")){
            booleanValue = true;
        }
        else if(this.stringValue.equals("DILI")){
            booleanValue = false;
        }
        else{
            throw new RuntimeException(this.stringValue + "is not a valid TINUOD value");
        }
    }
}
