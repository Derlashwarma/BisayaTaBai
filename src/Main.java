import Parser.Nodes.ASTNode;
import Parser.Nodes.Program;
import Tokenizer.*;
import Parser.*;
import Interpreter.Interpreter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String input =
                """
                        SUGOD
                                MUGNA NUMERO xyz, abc=100, asd
                                MUGNA TIPIK t=23.34
                                MUGNA LETRA a = 'S'
                                MUGNA TINUOD as= t<=abc
                                MUGNA NUMERO suwayi = -200+(3-6)*34
                                xyz = abc + 23
                                IPAKITA: as & " Hello Kitty " &xyz& " " & a & " [] " & suwayi
                        KATAPUSAN
                """;

        Tokenizer tokenizer = new Tokenizer(input);
        List<Token> tokens = tokenizer.tokenize();


        Parser parser = new Parser(tokens);
        Program ast = parser.parse();
//
        Interpreter interpreter = new Interpreter();
        interpreter.interpret(ast);

    }
}