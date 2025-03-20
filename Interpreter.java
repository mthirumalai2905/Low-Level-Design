import java.util.*;

class Lexer {
    private String input;
    private int pos = 0;

    public Lexer(String input) {
        this.input = input.replaceAll("\\s+", ""); // Remove spaces
    }

    public List<String> tokenize() {
        List<String> tokens = new ArrayList<>();
        while (pos < input.length()) {
            char c = input.charAt(pos);
            if (Character.isDigit(c)) {
                StringBuilder num = new StringBuilder();
                while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
                    num.append(input.charAt(pos++));
                }
                tokens.add(num.toString());
            } else {
                tokens.add(String.valueOf(c));
                pos++;
            }
        }
        return tokens;
    }
}

class Parser {
    private List<String> tokens;
    private int pos = 0;

    public Parser(List<String> tokens) {
        this.tokens = tokens;
    }

    public int parseExpression() {
        int result = parseTerm();
        while (pos < tokens.size()) {
            String op = tokens.get(pos);
            if (!op.equals("+") && !op.equals("-")) break;
            pos++;
            int nextTerm = parseTerm();
            result = op.equals("+") ? result + nextTerm : result - nextTerm;
        }
        return result;
    }

    private int parseTerm() {
        return Integer.parseInt(tokens.get(pos++));
    }
}

public class SimpleInterpreter {
    public static void main(String[] args) {
        String input = "10 + 5 - 3";
        Lexer lexer = new Lexer(input);
        List<String> tokens = lexer.tokenize();
        Parser parser = new Parser(tokens);
        int result = parser.parseExpression();
        System.out.println("Result: " + result);
    }
}
