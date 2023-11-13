

import java.awt.*;
import java.awt.event.InputEvent;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AutoClicker {

    Thread clickThread;
    boolean started = false;

    double seconds = 1;

    public AutoClicker() {
        ui();
        //autoclick();
    }

    private void ui() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JButton button = new JButton("Start");
        JButton stop = new JButton("Stop");
        JFormattedTextField textField = new JFormattedTextField("Enter a Num");
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!started) {
                    try {
                        seconds = Double.parseDouble(textField.getText());
                    } catch (NumberFormatException ex) {
                        seconds = 1;
                    }
                    System.out.println(seconds);
                    clickThread = new Thread(new Runnable() {
                        public void run() {
                            autoclick();
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
        panel.add(textField);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void autoclick() {
        try {
            while(true) {
                Robot r = new Robot();
                int button = InputEvent.BUTTON1_DOWN_MASK;
                System.out.println("*********************");
                r.mousePress(button);
                //Thread.sleep(1);
                r.mouseRelease(button);
                Thread.sleep((int)(seconds*1000));
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static void main(String[] args)  {
        new AutoClicker();
    }
}
