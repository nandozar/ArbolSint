package nodo;

import java.util.Stack;

class ArbolSintactico {
    private Nodo raiz;

    public void construirArbol(String expresion) {
        if (estaBalanceada(expresion)) {
            raiz = construirDesdeExpresion(expresion);
        } else {
            raiz = null; // O manejar de otra forma si se requiere
        }
    }

    public Nodo getRaiz() {
        return raiz;
    }

    private Nodo construirDesdeExpresion(String expresion) {
        Stack<Nodo> operandos = new Stack<>();
        Stack<String> operadores = new Stack<>();

        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            if (Character.isDigit(c) || Character.isLetter(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < expresion.length() && (Character.isDigit(expresion.charAt(i)) || Character.isLetter(expresion.charAt(i)))) {
                    sb.append(expresion.charAt(i++));
                }
                i--;
                operandos.push(new NodoHoja(sb.toString()));
            } else if (c == '(') {
                operadores.push(String.valueOf(c));
            } else if (c == ')') {
                while (!operadores.isEmpty() && !operadores.peek().equals("(")) {
                    operandos.push(crearNodoOperador(operadores.pop(), operandos.pop(), operandos.pop()));
                }
                operadores.pop();
            } else if (esOperador(c)) {
                while (!operadores.isEmpty() && prioridad(operadores.peek()) >= prioridad(String.valueOf(c))) {
                    operandos.push(crearNodoOperador(operadores.pop(), operandos.pop(), operandos.pop()));
                }
                operadores.push(String.valueOf(c));
            }
        }

        while (!operadores.isEmpty()) {
            operandos.push(crearNodoOperador(operadores.pop(), operandos.pop(), operandos.pop()));
        }

        return operandos.pop();
    }

    private NodoOperador crearNodoOperador(String operador, Nodo derecho, Nodo izquierdo) {
        return new NodoOperador(operador, izquierdo, derecho);
    }

    private boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int prioridad(String operador) {
        switch (operador) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return -1;
        }
    }

    public boolean estaBalanceada(String expresion) {
        Stack<Character> stack = new Stack<>();
        for (char c : expresion.toCharArray()) {
            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                if (stack.isEmpty() || stack.pop() != '(') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}

