package ru.apress.calculator;

import java.util.Stack;

/**
 * Вспомогательные методы для работы с обратной польской нотацией.
 */
public class PostfixNotationUtils {

    /**
     * Явлется ли символ <b>ch</b> оператором.
     * @param ch символ для проверки
     * @return <b>true</b> если символ является оператором, иначе <b>false</b>
     */
    private static boolean isOperator(char ch) {
        return (ch == '/' || ch == '*' || ch == '-' || ch == '+');
    }

    /**
     * Возвращает приоритет оператора.
     * @param ch символ оператора
     * @return приоритет оператора
     */
    private static int getOpPriority(char ch) {
        if      (ch == '+' || ch == '-') return 1;
        else if (ch == '*' || ch == '/') return 2;
        else                             return -1;
    }

    /**
     * Собирает последовательность символов, представляющих одно число, в одну строчку.
     * Учитывает, что число может состоять из цифр, десятичного разделителя и знаков ±, представляющих
     * унарный минус.
     *
     * @param ch первый символ числа
     * @param str вся строка, из которой будут забираться символы
     * @param index индекс символа <b>ch</b> в строке <b>str</b>
     * @return строка, представляющая число.
     */
    private static String gatherNumber(char ch, String str, int index) {
        int sign = 1;
        StringBuilder sb = new StringBuilder();

        /*
         * Последовательно проверяем все символы и, до тех пор пока они относятся к нашему числу,
         * присоединяем их к строке.
         */
        while (Character.isDigit(ch) || ch == ',' || ch == '.' || ch == '±') {
            sb.append(ch);
            index++;
            if (index >= str.length()) break; //следим за выходом за границы строки
            ch = str.charAt(index);
        }

        return sb.toString();
    }

    /**
     *  Переводит строку <b>number</b> в значение типа double.
     *  Если в начале строки стоят символы унарного минуса ±, то учитываем
     *  знак числа, в соответсвие с количеством унарных минусов.
     * @param number строка с числом
     * @return число типа double.
     */
    private static double parseNumber(String number) {
        int sign = 1;

        char ch = number.charAt(0);

        while (ch == '±'){
            sign *= -1;
            number = number.substring(1);
            ch = number.charAt(0);
        }

        double d = Double.parseDouble(number);
        d *= sign;
        return d;
    }

    /**
     * Переводит строку в инфиксной нотации в постфиксную (обратную польскую) нотацию.
     * @param infixStr строка в инфиксной нотации
     * @return строка в постфиксной нотации, <b>null</b> в случае, если строка <b>infixStr</b> введена
     * некорректно.
     */
    public static String convertInfixToPostfix(String infixStr) {
        boolean mayUnary = true; //может ли следующий оператор быть унарным

        Stack<Character> stack = new Stack<Character>(); //стек операторов и символов

        StringBuilder psb = new StringBuilder(); //билдер выходной строки

        int len = infixStr.length(); //длинна строки

        try {

            for (int i = 0; i < len; i++) {
                char ch = infixStr.charAt(i);

                if (Character.isDigit(ch)) {

                    String number = gatherNumber(ch, infixStr, i);
                    i += number.length() - 1;
                    psb.append(number);
                    psb.append(' ');

                    mayUnary = false;

                } else if (ch == '(') {

                    stack.push(ch);
                    mayUnary = true;

                } else if (ch == ')') {

                    char temp = '\n';

                    /*
                     * пока верхним элементом не станет открывающаяся скобка,
                     * выталкиваем символы в строку
                     */
                    while ((temp = stack.pop()) != '(' ) {
                        psb.append(temp);
                        psb.append(' ');
                    }

                    mayUnary = false;

                } else if (isOperator(ch)) {

                    if (mayUnary && (ch == '+' || ch == '-')) {

                        if (ch == '-') {        //если унарный минус - заменяем его на ±
                            psb.append('±');
                        } else {                //если плюс, то просто опускаем символ
                            continue;
                        }

                    } else {
                        /*
                         * пока приоритет оператора меньше или равен приоритету оператора
                         * на верщине стека, выталкиваем символы из стека в строку
                         */
                        while (stack.size() > 0
                                && getOpPriority(ch) <= getOpPriority(stack.peek())) {
                            psb.append(stack.pop());
                            psb.append(' ');
                        }

                        stack.push(ch);
                    }

                    mayUnary = true;
                }
            }

            while (stack.size() > 0) {
                psb.append(stack.pop());
                psb.append(' ');
            }
            return psb.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Вычисляет значение выражения, записанного в постфиксной нотации.
     * @param postfixStr строка в постфиксной нотации
     * @return строка с результатом, <b>null</b> в случае, если строка <b>postfixStr</b> введена
     * некорректно.
     */
    public static String calculatePostfixExpression(String postfixStr) {

        Stack<Double> stack = new Stack<Double>(); //при вычислении используется стэк
        int len = postfixStr.length(); //длинна строки

        try {
            for (int i = 0; i < len; i++) {
                char ch = postfixStr.charAt(i);

                if (ch == ' ') {
                    /* do nothing */
                } else if (Character.isDigit(ch) || ch == '±') {

                    String number = gatherNumber(ch, postfixStr, i);
                    i += number.length() - 1;
                    double d = parseNumber(number);
                    stack.push(d);

                } else if (isOperator(ch)) {
                    /* выполняем действие в зависимости от оператора */

                    double b = stack.pop();
                    double a = stack.pop();

                    if(ch == '+') {
                        a = a + b;
                    } else if (ch == '-') {
                        a = a - b;
                    } else if (ch == '*') {
                        a = a * b;
                    } else if (ch == '/') {
                        if (b == 0) return null;  //ошибка деления на ноль
                        a = a / b;
                    }

                    stack.push(a);

                } else {
                    return null;
                }
            }

            double result = stack.pop();
            int intRes = (int) result;

            /* Определяем в каком формате выводить число: int или double */
            if (intRes == result) {
                return Integer.toString(intRes);
            } else {
                return Double.toString(result);
            }
        } catch (Exception e) {
            return null;
        }
    }



}
