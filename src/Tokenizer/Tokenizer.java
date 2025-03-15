package Tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tokenizer {
    private String input;
    private int position;
    private char currentChar;
    private List<Token> tokens;
    private int lineNumber;

    public Tokenizer(String input) {
        this.input = input;
        this.position = 0;
        this.currentChar = position < input.length() ? input.charAt(position) : '\0';
        this.tokens = new ArrayList<>();
        this.lineNumber = 1;
    }

    private void advance() {
        if(currentChar == '\n'){
            lineNumber++;
        }
        position++;
        if (position < input.length()) {
            currentChar = input.charAt(position);
        } else {
            currentChar = '\0';
        }
    }

    private void skipWhitespace() {
        while (currentChar != '\0' && Character.isWhitespace(currentChar)) {
            advance();
        }
    }

    private void skipComment() {
        lineNumber++;
        while (currentChar != '\0' && currentChar != '\n') {
            advance();
        }
        if (currentChar == '\n') {
            advance();
        }
    }

    private Token number() {
        StringBuilder result = new StringBuilder();
        boolean isFloat = false;

        // Parse the integer part
        while (currentChar != '\0' && Character.isDigit(currentChar)) {
            result.append(currentChar);
            advance();
        }

        // Check for a decimal point
        if (currentChar == '.') {
            isFloat = true;
            result.append(currentChar);
            advance();

            // Parse the fractional part
            while (currentChar != '\0' && Character.isDigit(currentChar)) {
                result.append(currentChar);
                advance();
            }
        }

        // Handle invalid number formats (e.g., multiple decimal points)
        if (result.toString().matches(".*\\..*\\..*")) {
            throw new RuntimeException("Invalid number format: " + result.toString() + " at line " + lineNumber);
        }

        // Determine if the number is an integer or a float
        if (isFloat) {
            return new Token("FLOAT", Float.parseFloat(result.toString()), lineNumber);
        } else {
            return new Token("NUMBER", Integer.parseInt(result.toString()), lineNumber);
        }
    }

    private float floatNumber(){
        StringBuilder result = new StringBuilder();
        while (currentChar != '\0' && Character.isDigit(currentChar) || currentChar == '.') {
            if(result.toString().contains(".")){
                throw new NumberFormatException("Invalid Number Format");
            }
            result.append(currentChar);
            advance();
        }
        return Float.parseFloat(result.toString());
    }

    private String identifier() {
        StringBuilder result = new StringBuilder();
        if (Character.isLetter(currentChar) || currentChar == '_') {
            result.append(currentChar);
            advance();
            while (currentChar != '\0' && (Character.isLetterOrDigit(currentChar) || currentChar == '_')) {
                result.append(currentChar);
                advance();
            }
        }
        return result.toString();
    }

    private String string() {
        StringBuilder result = new StringBuilder();
        char quoteChar = currentChar;
        advance();
        while (currentChar != '\0' && currentChar != quoteChar) {
            result.append(currentChar);
            advance();
        }
        if (currentChar == quoteChar) {
            advance();
        }
        return result.toString();
    }

    public List<Token> tokenize() {
        while (currentChar != '\0') {
            if (Character.isWhitespace(currentChar)) {
                skipWhitespace();
                continue;
            }

            if (currentChar == '-' && position + 1 < input.length() && input.charAt(position + 1) == '-') {
                advance();
                advance();
                skipComment();
                continue;
            }

            if (Character.isLetter(currentChar) || currentChar == '_') {
                String identifier = identifier();

                // Check if it's a keyword
                List<String> keywords = Arrays.asList(
                        "SUGOD", "KATAPUSAN", "MUGNA", "NUMERO", "LETRA", "TINUOD",
                        "TIPIK", "IPAKITA", "DAWAT", "KUNG", "KUNG WALA", "KUNG DILI",
                        "PUNDOK", "ALANG SA", "UG", "O", "DILI", "OO"
                );

                if (keywords.contains(identifier)) {
                    tokens.add(new Token("KEYWORD", identifier,lineNumber));
                } else {
                    tokens.add(new Token("IDENTIFIER", identifier,lineNumber));
                }
                continue;
            }

            // Handle numbers
            if (Character.isDigit(currentChar)) {
                tokens.add(number());
                continue;
            }

            // Handle strings
            if (currentChar == '"' || currentChar == '\'') {
                String str = string();
                tokens.add(new Token("STRING", str, lineNumber));
                continue;
            }

            // Handle special characters and operators
            switch (currentChar) {
                case '=':
                    advance();
                    if (currentChar == '=') {
                        tokens.add(new Token("OPERATOR", "==", lineNumber));
                        advance();
                    } else {
                        tokens.add(new Token("OPERATOR", "=", lineNumber));
                    }
                    break;
                case '+':
                    advance();
                    if (currentChar == '+') {
                        tokens.add(new Token("OPERATOR", "++",lineNumber));
                        advance();
                    } else {
                        tokens.add(new Token("OPERATOR", "+",lineNumber));
                    }
                    break;
                case '-':
                    advance();
                    tokens.add(new Token("OPERATOR", "-",lineNumber));
                    break;
                case '*':
                    advance();
                    tokens.add(new Token("OPERATOR", "*",lineNumber));
                    break;
                case '/':
                    advance();
                    tokens.add(new Token("OPERATOR", "/",lineNumber));
                    break;
                case '%':
                    advance();
                    tokens.add(new Token("OPERATOR", "%",lineNumber));
                    break;
                case '>':
                    advance();
                    if (currentChar == '=') {
                        tokens.add(new Token("OPERATOR", ">=",lineNumber));
                        advance();
                    } else {
                        tokens.add(new Token("OPERATOR", ">",lineNumber));
                    }
                    break;
                case '<':
                    advance();
                    if (currentChar == '=') {
                        tokens.add(new Token("OPERATOR", "<=",lineNumber));
                        advance();
                    } else if (currentChar == '>') {
                        tokens.add(new Token("OPERATOR", "<>",lineNumber));
                        advance();
                    } else {
                        tokens.add(new Token("OPERATOR", "<",lineNumber));
                    }
                    break;
                case '(':
                    advance();
                    tokens.add(new Token("PAREN", "(",lineNumber));
                    break;
                case ')':
                    advance();
                    tokens.add(new Token("PAREN", ")",lineNumber));
                    break;
                case '{':
                    advance();
                    tokens.add(new Token("BRACE", "{",lineNumber));
                    break;
                case '}':
                    advance();
                    tokens.add(new Token("BRACE", "}",lineNumber));
                    break;
                case '[':
                    advance();
                    tokens.add(new Token("BRACKET", "[",lineNumber));
                    break;
                case ']':
                    advance();
                    tokens.add(new Token("BRACKET", "]",lineNumber));
                    break;
                case '&':
                    advance();
                    tokens.add(new Token("AMPERSAND", "&",lineNumber));
                    break;
                case '$':
                    advance();
                    tokens.add(new Token("DOLLAR", "$",lineNumber));
                    break;
                case ',':
                    advance();
                    tokens.add(new Token("COMMA", ",",lineNumber));
                    break;
                case ':':
                    advance();
                    tokens.add(new Token("COLON", ":",lineNumber));
                    break;
                default:
                    throw new RuntimeException("Unexpected character: " + currentChar);
            }
        }

        tokens.add(new Token("EOF", null, lineNumber));
        return tokens;
    }
}
