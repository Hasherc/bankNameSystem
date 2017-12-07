package com.xiaomin;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Semaphore;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: qming_c
 * Date: 2017-12-02
 * Time: 20:27
 */
public class Chair extends JPanel {
    private int id;
    private Client client;
    private JLabel idText;
    private Semaphore chairSemaphore;
    public Chair(int id) {
        this.id = id;
        chairSemaphore = new Semaphore(1);
        draw();
    }
    private void draw(){
        setLayout(null);
        setBounds(60 * (id - 1) + 6,25,50,50);
        idText = new JLabel(String.valueOf(id));
        idText.setBounds(35,35,15,15);
        idText.setFont(new Font("宋体",Font.BOLD, 10));
        setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        add(idText);
    }

    public Semaphore getChairSemaphore() {
        return chairSemaphore;
    }

    public void removeClient() {
        remove(client);
        this.client = null;
        repaint();
    }

    public void setClient(Client client) {
        this.client = client;
        add(client);
        repaint();
    }
}
