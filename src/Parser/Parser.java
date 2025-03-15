package Parser;

import Parser.Nodes.*;
import Parser.Nodes.DataTypes.FloatLiteral;
import Parser.Nodes.DataTypes.NumberLiteral;
import Parser.Nodes.DataTypes.StringLiteral;
import Tokenizer.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int position;
    private Token currentToken;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
        this.currentToken = tokens.get(position);
    }

    public Token advance(){
        position++;
        if(position < tokens.size()){
            currentToken = tokens.get(position);
        }
        return currentToken;
    }

    public Token eat(String tokenType){
        if(currentToken.getType().equals(tokenType)){
            Token returnToken = currentToken;
            advance();
            return returnToken;
        }
        else{
            throw new RuntimeException("Expected: " + tokenType + " but got " + currentToken.getType() + " At line: " + currentToken.getLineNumber());
        }
    }
    public Token eat(String tokenType, String expectedValue){
        if(currentToken.getType().equals(tokenType) && expectedValue.equals(currentToken.getValue())){
            Token returnToken = currentToken;
            advance();
            return returnToken;
        }
        else{
            throw new RuntimeException("Expected: " + expectedValue + " but got " + currentToken.getValue() + " At line: " + currentToken.getLineNumber());
        }
    }

    public Program parse(){
        return program();
    }

    public Program program(){
        eat("KEYWORD", "SUGOD");
        List<ASTNode> program = getStatementList();
        eat("KEYWORD", "KATAPUSAN");
        return new Program(program);
    }

    public List<ASTNode> getStatementList(){
        List<ASTNode> statements = new ArrayList<>();
        while(!currentToken.getValue().equals("KATAPUSAN")){
            ASTNode statement = getStatement();
            statements.add(statement);
        }
        return statements;
    }
    public ASTNode getStatement(){
        ASTNode node = null;
        switch(currentToken.getValue().toString()){
            case "MUGNA":
                node = variableDeclaration();
                break;
            case "IPAKITA":
                node = outputStatement();
                break;
            default:
                node = assignmentStatement();
                break;
        }
        return node;
    }

    private Assignment assignmentStatement() {
        String identifier = (String) eat("IDENTIFIER").getValue(); // Consume variable name
        eat("OPERATOR", "="); // Consume "="
        ASTNode value = expression(); // Parse the right-hand side expression
        return new Assignment(identifier, value);
    }

    private VariableDeclaration variableDeclaration() {
        eat("KEYWORD", "MUGNA");
        String dataType = (String) eat("KEYWORD").getValue();

        List<Declaration> declarations = new ArrayList<>();
        String identifier = (String) eat("IDENTIFIER").getValue();

        ASTNode initialValue = null;
        if (currentToken.getType().equals("OPERATOR") && currentToken.getValue().equals("=")) {
            eat("OPERATOR");
            initialValue = expression();
        }

        declarations.add(new Declaration(identifier, initialValue));

        while (currentToken.getType().equals("COMMA")) {
            eat("COMMA"); // Consume ","
            identifier = (String) eat("IDENTIFIER").getValue();

            initialValue = null;
            if (currentToken.getType().equals("OPERATOR") && currentToken.getValue().equals("=")) {
                eat("OPERATOR");
                initialValue = expression();
            }

            declarations.add(new Declaration(identifier, initialValue));
        }

        return new VariableDeclaration(dataType, declarations);
    }

    private ASTNode expression() {
        return comparativeExpression();
    }

    private ASTNode comparativeExpression() {
        ASTNode node = additiveExpression();

        // Handle comparative operators: <=, >=, <, >, ==, <>
        while (
                currentToken.getType().equals("OPERATOR") &&
                        Arrays.asList("<=", ">=", "<", ">", "==", "<>").contains(currentToken.getValue())
        ) {
            String operator = (String) eat("OPERATOR").getValue();
            ASTNode right = additiveExpression();
            node = new BinaryOperation(node, operator, right);
        }

        return node;
    }

    private ASTNode additiveExpression() {
        ASTNode node = multiplicativeExpression();

        // Handle additive operators: +, -
        while (currentToken.getType().equals("OPERATOR") && Arrays.asList("+", "-").contains(currentToken.getValue())) {
            String operator = (String) eat("OPERATOR").getValue();
            ASTNode right = multiplicativeExpression();
            node = new BinaryOperation(node, operator, right);
        }

        return node;
    }
    private ASTNode multiplicativeExpression() {
        ASTNode node = unaryExpression();

        // Handle multiplicative operators: *, /, %
        while (currentToken.getType().equals("OPERATOR") && Arrays.asList("*", "/", "%").contains(currentToken.getValue())) {
            String operator = (String) eat("OPERATOR").getValue();
            ASTNode right = unaryExpression();
            node = new BinaryOperation(node, operator, right);
        }

        return node;
    }

    private ASTNode unaryExpression() {
        if (currentToken.getType().equals("OPERATOR") && Arrays.asList("+", "-").contains(currentToken.getValue())) {
            String operator = (String) eat("OPERATOR").getValue();
            ASTNode operand = primaryExpression();
            return new UnaryOperation(operator, operand);
        }

        return primaryExpression();
    }

    private ASTNode primaryExpression() {
        if (currentToken.getType().equals("NUMBER")) {
            return new NumberLiteral((int) eat("NUMBER").getValue());
        }

        if (currentToken.getType().equals("FLOAT")) {
            return new FloatLiteral((float) eat("FLOAT").getValue());
        }

        if (currentToken.getType().equals("STRING")) {
            return new StringLiteral((String) eat("STRING").getValue());
        }

        if (currentToken.getType().equals("IDENTIFIER")) {
            return new Variable((String) eat("IDENTIFIER").getValue());
        }

        if (currentToken.getType().equals("PAREN") && currentToken.getValue().equals("(")) {
            eat("PAREN");
            ASTNode expr = expression();
            eat("PAREN");
            return expr;
        }

        throw new RuntimeException("Unexpected token in expression: " + currentToken.getValue());
    }

    private OutputStatement outputStatement() {
        eat("KEYWORD", "IPAKITA");
        eat("COLON");

        List<ASTNode> expressions = new ArrayList<>();
        expressions.add(expression());

        while (currentToken.getType().equals("AMPERSAND")) {
            eat("AMPERSAND");
            expressions.add(expression());
        }

        return new OutputStatement(expressions);
    }
}