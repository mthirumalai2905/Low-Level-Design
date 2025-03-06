import java.util.*;

class Compiler {
    public static void main(String[] args) {
        String code = "print 5 + 3;";
        Lexer lexer = new Lexer(code);
        List<Token> tokens = lexer.tokenize();
        Parser parser = new Parser(tokens);
        Node ast = parser.parse();
        Interpreter interpreter = new Interpreter();
        interpreter.evaluate(ast);
    }
}

class Token {
    enum Type { PRINT, NUMBER, PLUS, SEMICOLON }
    Type type; String value;
    Token(Type type, String value) { this.type = type; this.value = value; }
}

class Lexer {
    private String input; private int pos = 0;
    Lexer(String input) { this.input = input; }
    
    List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (pos < input.length()) {
            char c = input.charAt(pos++);
            if (Character.isDigit(c)) tokens.add(new Token(Token.Type.NUMBER, c + ""));
            else if (c == '+') tokens.add(new Token(Token.Type.PLUS, "+"));
            else if (c == ';') tokens.add(new Token(Token.Type.SEMICOLON, ";"));
            else if (input.startsWith("print", pos - 1)) { 
                tokens.add(new Token(Token.Type.PRINT, "print")); 
                pos += 4; 
            }
        }
        return tokens;
    }
}

class Node {
    Token token; Node left, right;
    Node(Token token, Node left, Node right) { this.token = token; this.left = left; this.right = right; }
}

class Parser {
    private List<Token> tokens; private int pos = 0;
    Parser(List<Token> tokens) { this.tokens = tokens; }
    
    Node parse() {
        Token printToken = tokens.get(pos++);
        Node left = new Node(tokens.get(pos++), null, null);
        Token plusToken = tokens.get(pos++);
        Node right = new Node(tokens.get(pos++), null, null);
        return new Node(plusToken, left, right);
    }
}

class Interpreter {
    void evaluate(Node node) {
        if (node.token.type == Token.Type.PLUS)
            System.out.println(Integer.parseInt(node.left.token.value) + Integer.parseInt(node.right.token.value));
    }
}
