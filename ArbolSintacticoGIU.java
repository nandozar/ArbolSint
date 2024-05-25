/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArbolSintacticoGIU extends JFrame {
    private ArbolSintactico arbolSintactico;
    private JTextField expresionTextField;
    private JLabel resultadoLabel;
    private ArbolPanel arbolPanel;

    public ArbolSintacticoGIU() {
        arbolSintactico = new ArbolSintactico();
        setTitle("Árbol Sintáctico");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        expresionTextField = new JTextField(20);
        JButton construirButton = new JButton("Construir Árbol");
        resultadoLabel = new JLabel();

        inputPanel.add(new JLabel("Expresión:"));
        inputPanel.add(expresionTextField);
        inputPanel.add(construirButton);
        inputPanel.add(resultadoLabel);

        construirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expresion = expresionTextField.getText();
                if (!expresion.isEmpty()) {
                    if (arbolSintactico.estaBalanceada(expresion)) {
                        arbolSintactico.construirArbol(expresion);
                        arbolPanel.setArbol(arbolSintactico.getRaiz());
                        resultadoLabel.setText("Expresión balanceada y árbol construido.");
                    } else {
                        resultadoLabel.setText("Expresión no balanceada.");
                        arbolPanel.setArbol(null); // Clear the tree
                    }
                    arbolPanel.repaint();
                }
            }
        });

        arbolPanel = new ArbolPanel();
        JScrollPane scrollPane = new JScrollPane(arbolPanel);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ArbolSintacticoGIU frame = new ArbolSintacticoGIU();
            frame.setVisible(true);
        });
    }

    class ArbolPanel extends JPanel {
        private Nodo arbol;

        public void setArbol(Nodo arbol) {
            this.arbol = arbol;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (arbol != null) {
                dibujarNodo(g, arbol, getWidth() / 2, 30, getWidth() / 4);
            }
        }

        private void dibujarNodo(Graphics g, Nodo nodo, int x, int y, int xOffset) {
            if (nodo instanceof NodoOperador) {
                NodoOperador operador = (NodoOperador) nodo;
                g.drawString(nodo.getValor(), x, y);
                if (operador.getIzquierdo() != null) {
                    g.drawLine(x, y, x - xOffset, y + 50);
                    dibujarNodo(g, operador.getIzquierdo(), x - xOffset, y + 50, xOffset / 2);
                }
                if (operador.getDerecho() != null) {
                    g.drawLine(x, y, x + xOffset, y + 50);
                    dibujarNodo(g, operador.getDerecho(), x + xOffset, y + 50, xOffset / 2);
                }
            } else if (nodo instanceof NodoHoja) {
                g.drawString(nodo.getValor(), x, y);
            }
        }
    }
}

