package com.xiaomin;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: qming_c
 * Date: 2017-12-02
 * Time: 20:30
 */
public class Client extends JPanel implements Runnable {
    private int id;
    private JLabel jLabel;
    private Bank bank;
    private Chair chair;
    private Chair[] chairs;
    private boolean inStandingFeild = false;

    public Client(int id) {
        this.id = id;
        bank = Bank.getBank();
        draw();
    }

    public int getId(){
        return id;
    }

    private void draw(){
        jLabel = new JLabel(String.valueOf(id));
        jLabel.setBounds(0,0,35,35);
        jLabel.setFont(new Font("宋体", Font.BOLD, 20));
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(jLabel);
        setBackground(Color.CYAN);
        setBorder(BorderFactory.createLineBorder(Color.ORANGE, 5));
        setBounds(0,0,40,40);
    }
    @Override
    public void run() {
        boolean hasGetChair = false;
        synchronized (this){
            bank.getClientsQueue().add(this);
            hasGetChair = getChair();
        }

        if (!hasGetChair){
            inStandingFeild = true;
            bank.getStandingField().addClient();
            while (!getChair()){

            }
            bank.getStandingField().removeClient();

        }
    }

    private  boolean getChair() {
        chairs = bank.getWaitingRoom().getChairs();
        synchronized (chairs) {
            for (int i = 0; i < chairs.length; i++) {
                if (chairs[i].getChairSemaphore().tryAcquire()) {
                    chair = chairs[i];
                    chair.setClient(this);
                    inStandingFeild = false;
                    return true;
                }
            }
        }
        return false;
    }
    public synchronized void toServer(){

            if (inStandingFeild == true){
                    bank.getStandingField().removeClient();
            }else {
                chair.removeClient();
                chair.getChairSemaphore().release();
            }

        }


}
