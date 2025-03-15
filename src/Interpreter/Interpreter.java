package Interpreter;

import Parser.Nodes.*;
import Parser.Nodes.DataTypes.FloatLiteral;
import Parser.Nodes.DataTypes.NumberLiteral;
import Parser.Nodes.DataTypes.StringLiteral;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interpreter {
    // Symbol table to store variable values
    private final Map<String, Object> symbolTable = new HashMap<>();

    public void interpret(Program program) {
        // Execute each statement in the program
        for (ASTNode statement : program.getStatements()) {
            executeStatement(statement);
        }
    }

    private void executeStatement(ASTNode statement) {
        if (statement instanceof VariableDeclaration) {
            executeVariableDeclaration((VariableDeclaration) statement);
        } else if (statement instanceof Assignment) {
            executeAssignment((Assignment) statement);
        } else if (statement instanceof OutputStatement) {
            executeOutputStatement((OutputStatement) statement);
        } else {
            throw new RuntimeException("Unsupported statement: " + statement.getClass().getSimpleName());
        }
    }

    private void executeVariableDeclaration(VariableDeclaration declaration) {
        // Store the variable(s) in the symbol table
        for (Declaration decl : declaration.getDeclarationList()) {
            String identifier = decl.getIdentifier();
            ASTNode initialValue = decl.getInitialValue();

            if (initialValue != null) {
                // Evaluate the initial value and store it in the symbol table
                Object value = evaluateExpression(initialValue);
                symbolTable.put(identifier, value);
            } else {
                // Initialize the variable to null if no initial value is provided
                symbolTable.put(identifier, null);
            }
        }
    }

    private void executeAssignment(Assignment assignment) {
        // Evaluate the right-hand side expression and store it in the symbol table
        String identifier = assignment.getIdentifier();
        Object value = evaluateExpression(assignment.getValue());
        symbolTable.put(identifier, value);
    }

    private void executeOutputStatement(OutputStatement outputStatement) {
        // Evaluate each expression in the output statement and print the result
        for (ASTNode expression : outputStatement.getExpressions()) {
            Object value = evaluateExpression(expression);
            System.out.print(value);
        }
        System.out.println(); // Print a newline after the output
    }

    private Object evaluateExpression(ASTNode expression) {
        if (expression instanceof NumberLiteral) {
            return ((NumberLiteral) expression).getValue();
        } else if (expression instanceof FloatLiteral) {
            return ((FloatLiteral) expression).getValue();
        } else if (expression instanceof StringLiteral) {
            return ((StringLiteral) expression).getValue();
        } else if (expression instanceof Variable) {
            String identifier = ((Variable) expression).getValue();
            if (!symbolTable.containsKey(identifier)) {
                throw new RuntimeException("Undefined variable: " + identifier);
            }
            return symbolTable.get(identifier);
        } else if (expression instanceof BinaryOperation) {
            return evaluateBinaryOperation((BinaryOperation) expression);
        } else if (expression instanceof UnaryOperation) {
            return evaluateUnaryOperation((UnaryOperation) expression);
        } else {
            throw new RuntimeException("Unsupported expression: " + expression.getClass().getSimpleName());
        }
    }

    private Object evaluateBinaryOperation(BinaryOperation binaryOperation) {
        Object left = evaluateExpression(binaryOperation.getLeft());
        Object right = evaluateExpression(binaryOperation.getRight());
        String operator = binaryOperation.getOperator();

        // Handle arithmetic operations
        if (operator.equals("+")) {
            return add(left, right);
        } else if (operator.equals("-")) {
            return subtract(left, right);
        } else if (operator.equals("*")) {
            return multiply(left, right);
        } else if (operator.equals("/")) {
            return divide(left, right);
        } else if (operator.equals("%")) {
            return modulo(left, right);
        }

        // Handle comparative operations
        else if (operator.equals("<")) {
            return (compare(left, right) < 0)?"OO":"DILI";
        } else if (operator.equals("<=")) {
            return compare(left, right) <= 0?"OO":"DILI";
        } else if (operator.equals(">")) {
            return compare(left, right) > 0?"OO":"DILI";
        } else if (operator.equals(">=")) {
            return compare(left, right) >= 0?"OO":"DILI";
        } else if (operator.equals("==")) {
            return compare(left, right) == 0?"OO":"DILI";
        } else if (operator.equals("<>")) {
            return compare(left, right) != 0?"OO":"DILI";
        }

        throw new RuntimeException("Unsupported binary operator: " + operator);
    }

    private Object evaluateUnaryOperation(UnaryOperation unaryOperation) {
        Object operand = evaluateExpression(unaryOperation.getOperand());
        String operator = unaryOperation.getOperator();

        if (operator.equals("+")) {
            return operand;
        } else if (operator.equals("-")) {
            return negate(operand);
        }

        throw new RuntimeException("Unsupported unary operator: " + operator);
    }

    // Helper methods for arithmetic operations
    private Object add(Object left, Object right) {
        if (left instanceof Integer && right instanceof Integer) {
            return (Integer) left + (Integer) right;
        } else if (left instanceof Float && right instanceof Float) {
            return (Float) left + (Float) right;
        } else if (left instanceof Integer && right instanceof Float) {
            return (Integer) left + (Float) right;
        } else if (left instanceof Float && right instanceof Integer) {
            return (Float) left + (Integer) right;
        } else if (left instanceof String || right instanceof String) {
            return left.toString() + right.toString();
        }
        throw new RuntimeException("Unsupported operands for addition: " + left + ", " + right);
    }

    private Object subtract(Object left, Object right) {
        if (left instanceof Integer && right instanceof Integer) {
            return (Integer) left - (Integer) right;
        } else if (left instanceof Float && right instanceof Float) {
            return (Float) left - (Float) right;
        } else if (left instanceof Integer && right instanceof Float) {
            return (Integer) left - (Float) right;
        } else if (left instanceof Float && right instanceof Integer) {
            return (Float) left - (Integer) right;
        }
        throw new RuntimeException("Unsupported operands for subtraction: " + left + ", " + right);
    }

    private Object multiply(Object left, Object right) {
        if (left instanceof Integer && right instanceof Integer) {
            return (Integer) left * (Integer) right;
        } else if (left instanceof Float && right instanceof Float) {
            return (Float) left * (Float) right;
        } else if (left instanceof Integer && right instanceof Float) {
            return (Integer) left * (Float) right;
        } else if (left instanceof Float && right instanceof Integer) {
            return (Float) left * (Integer) right;
        }
        throw new RuntimeException("Unsupported operands for multiplication: " + left + ", " + right);
    }

    private Object divide(Object left, Object right) {
        if (left instanceof Integer && right instanceof Integer) {
            return (Integer) left / (Integer) right;
        } else if (left instanceof Float && right instanceof Float) {
            return (Float) left / (Float) right;
        } else if (left instanceof Integer && right instanceof Float) {
            return (Integer) left / (Float) right;
        } else if (left instanceof Float && right instanceof Integer) {
            return (Float) left / (Integer) right;
        }
        throw new RuntimeException("Unsupported operands for division: " + left + ", " + right);
    }

    private Object modulo(Object left, Object right) {
        if (left instanceof Integer && right instanceof Integer) {
            return (Integer) left % (Integer) right;
        }
        throw new RuntimeException("Unsupported operands for modulo: " + left + ", " + right);
    }

    private Object negate(Object operand) {
        if (operand instanceof Integer) {
            return -(Integer) operand;
        } else if (operand instanceof Float) {
            return -(Float) operand;
        }
        throw new RuntimeException("Unsupported operand for negation: " + operand);
    }

    // Helper method for comparative operations
    private int compare(Object left, Object right) {
        if (left instanceof Number && right instanceof Number) {
            double leftValue = ((Number) left).doubleValue();
            double rightValue = ((Number) right).doubleValue();
            return Double.compare(leftValue, rightValue);
        }

        throw new RuntimeException("Unsupported operands for comparison: " + left + ", " + right);
    }
}