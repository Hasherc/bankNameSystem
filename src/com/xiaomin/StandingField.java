package com.xiaomin;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Semaphore;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: qming_c
 * Date: 2017-12-02
 * Time: 20:28
 */
public class StandingField extends JPanel{
    private volatile int count = 0;
    private JLabel text;
    public StandingField() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        text = new JLabel();
        set();
        text.setFont(new Font("楷体", Font.BOLD, 50));
        text.setHorizontalAlignment(SwingConstants.CENTER);
        add(text,SwingConstants.CENTER);
    }
    public synchronized void addClient(){
        count++;
        set();

    }
    public synchronized void removeClient(){
        count--;
        set();
        repaint();

    }
    private void set(){
        text.setText("当前站区总人数：" + count);
    }

}
