package org.jameva.cronometro.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Cronometro extends JFrame implements ActionListener {
    private JButton btnIniciar;
    private JButton btnDetener;
    private JPanel panel;
    private JLabel segundos;
    boolean iniciarCronometro = false;
    private ExecutorService servicioHilo = Executors.newSingleThreadExecutor();

    public Cronometro() {
        setContentPane(panel);
        setSize(300, 300);
        setVisible(true);
        setLocationRelativeTo(null);
        btnDetener.addActionListener(this);
        btnIniciar.addActionListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btnIniciar) {
            btnIniciar.setEnabled(false);
            if(!servicioHilo.isTerminated()) {
                asignarRun(servicioHilo);
            } else {
                servicioHilo = Executors.newSingleThreadExecutor();
                asignarRun(servicioHilo);
            }
            return;
        }
        btnIniciar.setEnabled(true);
        iniciarCronometro = false;
        servicioHilo.shutdown();
    }


    public void ejecutar() {

        System.out.println("Hilo actual: " + Thread.currentThread().getName()); // obtengo el nombre del hilo

        while(iniciarCronometro) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            String segun = segundos.getText();
            if (segundos.getText().contains(":")) {
                segun = "0";
            }
            segundos.setText(String.valueOf(1 + Integer.parseInt(segun)));
        }
        segundos.setText("0 : 0");
    }

    public void asignarRun(ExecutorService servicioHilo) { // método para reutilizar ciertas caracteristicas
        iniciarCronometro = true;
        servicioHilo.submit(this::ejecutar);
    }
}
