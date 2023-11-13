import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AutoClicker {

    Thread clickThread;
    boolean started = false;
    JTextField textField;

    public AutoClicker() {
        ui();
    }

    private void ui() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JButton button = new JButton("Start");
        JButton stop = new JButton("Stop");
        JTextField textField = new JTextField("",10);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent key) {
                if(key.getID() == KeyEvent.KEY_PRESSED && key.getKeyCode()==KeyEvent.VK_F1) {
                    if(!started) {
                        try {
                            start(Double.parseDouble(textField.getText()));
                        } catch (NumberFormatException ex) {
                            start(1);
                        }
                    } else
                        stop();
                }
                return false;
            }
        });
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    start(Double.parseDouble(textField.getText()));
                } catch (NumberFormatException ex) {
                    start(1);
                }
        }});

        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stop();
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

    private void start(double seconds) {
        if(!started) {
                    System.out.println(seconds);
                    clickThread = new Thread(new Runnable() {
                        public void run() {
                            autoclick(seconds);
                        }
                    });
                    started = true;
                    clickThread.start();
                }
    }

    private void stop() {
        if(clickThread!=null) 
                clickThread.interrupt();
            started = false;
    }

    private void autoclick(double seconds) {
        try {
            while(true) {
                Robot r = new Robot();
                int button = InputEvent.BUTTON1_DOWN_MASK;
                r.mousePress(button);
                Thread.sleep(10);
                r.mouseRelease(button);
                Thread.sleep((int)(seconds*1000));
            }
        } catch (Exception e) {
            System.out.println("*************");
            e.getStackTrace();
        }
    }

    public static void main(String[] args)  {
        new AutoClicker();
    }
}
