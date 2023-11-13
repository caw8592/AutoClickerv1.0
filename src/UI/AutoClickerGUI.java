package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import AutoClicker.AutoClicker;

public class AutoClickerGUI {

    Thread clickThread;
    boolean started = false;

    public AutoClickerGUI() {

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JButton button = new JButton("Start");
        JButton stop = new JButton("Stop");

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!started) {
                    clickThread = new Thread(new Runnable() {
                        public void run() {
                            new AutoClicker();
                        }
                    });
                    started = true;
                    clickThread.start();
                }
        }});

        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(clickThread!=null) 
                    clickThread.interrupt();
                started = false;
            }
        });

        panel.add(button);
        panel.add(stop);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new AutoClickerGUI();
    }
}
