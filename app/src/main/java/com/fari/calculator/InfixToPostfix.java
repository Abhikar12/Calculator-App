package com.fari.calculator;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class InfixToPostfix
{
    public String str;
    String[] arrayOfNum;

    public InfixToPostfix(String string)
    {
        str = string;
        arrayOfNum = new String[str.length()];
    }

    int prec(String op)
    {
        switch (op)
        {
            case "+":
            case "-":
                return 1;

            case "*":
            case "/":
            case "%":
                return 2;
        }
        return -1;
    }

    void sortForNums()
    {
        String cur = "";
        int indexForArray = -1;
        str += " ";
        for(int i=0; i<str.length(); i++)
        {
            if (Character.isDigit(str.charAt(i)) || str.charAt(i) == '.')
                cur += str.charAt(i);
            else if(str.charAt(i)=='(' || str.charAt(i)==')')
            {
                if(cur != "")
                {
                    arrayOfNum[++indexForArray] = cur;
                    cur = "";
                }
                arrayOfNum[++indexForArray] = Character.toString(str.charAt(i));
            }
            else
            {
                if(cur != "")
                {
                    arrayOfNum[++indexForArray] = cur;
                    cur = "";
                }
                if(str.charAt(i) != ' ')
                    arrayOfNum[++indexForArray] = Character.toString(str.charAt(i));
            }
        }
    }

    String[] infixToPostfix()
    {
        Stack<String> stack = new Stack<String>();
        sortForNums();
        String result[] = new String[arrayOfNum.length];
        int index = 0;

        for(int i=0; i<arrayOfNum.length; i++) {
            String cur = arrayOfNum[i];

            if (cur == null || cur.compareTo(" ") == 0) {
            } else {
                if (cur.compareTo("(") == 0)
                    stack.push(cur);

                else if (cur.compareTo(")") == 0) {
                    while (!stack.isEmpty() && (stack.peek().compareTo("(")!=0) )
                        result[index++] = stack.pop();
                    stack.pop();
                } else if (cur.compareTo("+") == 0 || cur.compareTo("-") == 0 || cur.compareTo("*") == 0
                        || cur.compareTo("/") == 0 || cur.compareTo("%") == 0) {
                    System.out.println(cur + "  operator");
                    while (!stack.isEmpty() && prec(cur) <= prec(stack.peek()))
                    {
//                        if (stack.peek() == "(")
//                            return (new String[0]);
                        result[index++] = stack.pop();
                    }
                    stack.push(cur);
                } else {
                    System.out.println(cur + "  operand");
                    result[index++] = cur;
                }
            }
        }
        while(!stack.isEmpty())
            result[index++] = stack.pop();

        // remove null values from String[] result before returning
        List<String> list = new ArrayList<>();
        for(String s: result)
            if(s!=null && s.length()>0)
                list.add(s);

        //coversion of arrayList to ArrayofStrings
        String res[] = new String[list.size()];
        for(int i=0;i<list.size();i++)
            res[i] = list.get(i);

        return res;
    }

    String evaluationOfPostfix()
    {
        String[] result = infixToPostfix();
        Stack<Double> stack = new Stack<>();

        for(int i=0;i<result.length;i++)
        {
            String cur = result[i];

            if(cur.compareTo("+")==0 || cur.compareTo("-")==0 || cur.compareTo("*")==0 || cur.compareTo("/")==0 || cur.compareTo("%")==0)
            {
                double b = stack.pop();
                double a = stack.pop();
                switch(cur)
                {
                    case "+": stack.push(a+b); break;
                    case "-": stack.push(a-b); break;
                    case "*": stack.push(a*b); break;
                    case "/": stack.push(a/b); break;
                    case "%": stack.push(a%b); break;
                }
            }
            else
                stack.push(Double.parseDouble(cur));
        }
        return (Double.toString(stack.pop()));
    }
}
