import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        System.out.println(calc(scan.nextLine()));
    }
    public static String getOperator(String input) throws Exception {
        String symbol = "";

        if (input.contains("+")) symbol += "+";
        if (input.contains("-")) symbol += "-";
        if (input.contains("*")) symbol += "*";
        if (input.contains("/")) symbol += "/";
        if ((symbol.length() != 1) || (input.indexOf(symbol) != input.lastIndexOf(symbol)))
            throw new Exception("Калькулятор принимает только 2 операнда и один оператор (+, -, *, /) между ними");
        return symbol;
    }
    public static Boolean checkSystem(String[] input) throws Exception {
        if ((input[0].contains("I") || input[0].contains("V") || input[0].contains("X")))
            if (input[1].contains("I") || input[1].contains("V") || input[1].contains("X")) return true;
            else throw new Exception("Используются разные системы счисления!");
        else
            if (input[1].contains("I") || input[1].contains("V") || input[1].contains("X"))
                throw new Exception("Используются разные системы счисления!");
        return false;
    }
    public static int getNumber(String operand) {
        int result = 0;
        for (int i = 0; i < operand.length(); i++) {
            if (operand.charAt(i) == 'I'){
                if (i < operand.length()-1)
                    if (operand.charAt(i+1) == 'V') { result += 4; i++; break; }
                    else {
                        if (operand.charAt(i+1) == 'X' && i < operand.length() - 1) { result += 9; i++; break; }
                    }
                result += 1;
            }
            if (operand.charAt(i) == 'V') result += 5;
            if (operand.charAt(i) == 'X') result += 10;
        }
        return result;
    }
    public static String getRome (int number){
        String result = "";
        while (number >= 10) {
            if (number % 10 >= 0) {result += "X"; number -= 10;}
        }
        if (number >= 5 && number % 5 >= 0 ) {result += "V"; number -= 5;}
        if (number >= 4 && number % 4 == 0 ) {result += "IV"; number -= 4;}
        while (number > 0) {
            result += "I";
            number -=1;
        }
        return result;
    }
    public static String calc(String input) throws Exception {
        String result = "";
        String symbol = getOperator(input);
        String[] operation = input.replaceAll("\\s","").split("\\" + symbol);
        if (operation.length != 2) throw new Exception("Калькулятор принимает 2 операнда!");
        Boolean rome = checkSystem(operation);
        int numberFst = 0;
        int numberScn = 0;
        if (!rome) {
            numberFst = Integer.parseInt(operation[0].trim());
            numberScn = Integer.parseInt(operation[1].trim());
        }
        else {
            numberFst = getNumber(operation[0].trim());
            numberScn = getNumber(operation[1].trim());
        }
        if (numberFst < 1 | numberFst > 10 | numberScn < 1 | numberScn > 10
            | Integer.toString(numberFst).trim().equals("") | Integer.toString(numberScn).trim().equals(""))
            throw new Exception("Калькулятор принимает числа только от 1 до 10 включительно!");
        switch (symbol) {
            case "+": result = Integer.toString(numberFst + numberScn); break;
            case "-": result = Integer.toString(numberFst - numberScn); break;
            case "/": result = Integer.toString(numberFst / numberScn); break;
            case "*": result = Integer.toString(numberFst * numberScn); break;
        }
        if (rome) {
            if (Integer.parseInt(result) < 1) throw new Exception("В римской системе счисления только положительные числа!");
            result = getRome(Integer.parseInt(result));
        }
        return result;
    }
}
