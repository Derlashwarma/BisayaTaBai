package Tokenizer;

public class Token {
    private String type;
    private Object value;
    private final int lineNumber;

    public Token(String type, Object value, int lineNUmber) {
        this.type = type;
        this.value = value;
        this.lineNumber = lineNUmber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type='" + type + '\'' +
                ", value=" + value +
                ", lineNumber=" + lineNumber +
                '}';
    }
}
