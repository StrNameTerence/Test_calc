import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Calculate {
    static Boolean FLAG = true;

    static String calc(String input) {
        if (input.equalsIgnoreCase("Выход")) {
            FLAG = false;
            return "Готово";
        }
        int divisorIndex = Service.divisor(input);
        int number1 = Service.findNumber1(input, divisorIndex);
        int number2 = Service.findNumber2(input, divisorIndex);
        return Service.arithmetic(input, number1, number2, divisorIndex);

    }

    public static void main(String[] args) {
        System.out.println("Для выхода введите: Выход");
        while (FLAG) {
            try {
                System.out.println("Введите математическое выражение:");
                System.out.println(calc(Service.scanLine()));
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



class Service {
    private static boolean roman = false;

    static String scanLine() {
        Scanner scan = new Scanner(System.in);
        String full = scan.nextLine();

        Pattern p = Pattern.compile("[IVXLCDM]");
        Pattern p1 = Pattern.compile("[0-9]");
        Matcher mat1 = p1.matcher(full.toUpperCase());
        Matcher mat = p.matcher(full.toUpperCase());
        if (mat.find() && mat1.find()) {
            System.out.println("Некорректное выражение");
            throw new ArithmeticException();
        }
        return full;
    }


    static int divisor(String scanLine) {
        int stop = 0;
        for (int i = 0; i < scanLine.length(); i++) {
            if (scanLine.charAt(i) == '-' || scanLine.charAt(i) == '+' || scanLine.charAt(i) == '*' || scanLine.charAt(i) == '/') {
                stop = i;
                break;
            }
        }
        return stop;
    }


    static int getNumberRomanOrArabic(String st) {
        int number = 0;
        if (st.matches("[0-9]+")) {
            number = Integer.parseInt(st);
            roman = false;

        } else if (st.toUpperCase().matches("[IVXLCDM]+")) {
            for (Romans in : Romans.values()) {
                if (st.toUpperCase().equals(in.getKey())) {
                    number = in.getValue();
                    roman = true;
                    break;
                }
            }

        } else {
            number = -1;
        }
        return number;
    }

    static int findNumber1(String scanLine, int divisor) {
        String st1 = (scanLine.substring(0, divisor)).trim();
        return getNumberRomanOrArabic(st1);
    }


    static int findNumber2(String scanLine, int divisor1) {
        String st1 = (scanLine.substring((divisor1 + 1)).trim());
        return getNumberRomanOrArabic(st1);
    }


    static String romanToArabic(int arabic) {
        StringBuilder s = new StringBuilder();
        if (arabic > 0) {
            s.append("Ваш ответ: ");
            int b = 0;
            while (arabic != b) {
                for (Romans in : Romans.values()) {
                    if (arabic <= in.getValue()) {
                        int index = in.ordinal();
                        if (arabic < in.getValue()) {
                            index = (in.ordinal() - 1);
                        }
                        arabic -= Romans.values()[index].getValue();
                        s.append(Romans.values()[index].getKey());
                        break;
                    }
                }
            }
        } else {
            s.append("В римской системе исчиления нет 0 и отрицательных чисел.");
        }
        return s.toString();
    }


    static String arithmetic(String scanLine, int number1, int number2, int divisor) {
        if (number1 > 0 && number2 > 0 && number1 < 11 && number2 < 11) {
            switch (scanLine.charAt(divisor)) {
                case '+' -> {
                    if (roman) {
                        return romanToArabic(number1 + number2);
                    }
                    return "Ответ: " + (number1 + number2);
                }
                case '-' -> {
                    if (roman) {
                        return romanToArabic(number1 - number2);
                    }
                    return "Ответ: " + (number1 - number2);
                }
                case '*' -> {
                    if (roman) {
                        return romanToArabic(number1 * number2);
                    }
                    return "Ответ: " + (number1 * number2);
                }
                case '/' -> {
                    if (roman) {
                        return romanToArabic(number1 / number2);
                    }
                    return "Ответ: " + (number1 / number2);
                }
            }
        } else if (number1 == -1 && divisor == 0) {
            throw new ArithmeticException();
        } else {
            System.out.println("Укажите числа от 1 до 10 или I до X включительно");
            throw new ArithmeticException();

        }
        return null;
    }

}

enum Romans {
    I("I", 1), II("II", 2), III("III", 3), IV("IV", 4), V("V", 5),
    VI("VI", 6), VII("VII", 7), VIII("VIII", 8), IX("IX", 9), X("X", 10),
    XL("XL", 40), L("L", 50), XC("XC", 90), C("C", 100);


    private int value;
    private String key;

    Romans(String key, int value) {
        this.value = value;
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}