package com.xiaomin;

import javax.swing.*;
import java.awt.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: qming_c
 * Date: 2017-12-02
 * Time: 13:35
 */


public class Server extends JPanel implements Runnable {
    boolean busy;
    private int id;
    private Icon iconImage;
    private JLabel idField;
    private JLabel messageField;
    private JLabel iconField;
    private JButton workButton;
    private JButton nextClientButton;
    private Bank bank;
    private ConcurrentLinkedQueue<Client> clientQueue;
    private JPanel clientPanel;
    private Client client;
    private volatile boolean isStart = false;

    public Server(int id, Icon iconImage) { ;
        this.id = id;
        this.iconImage = iconImage;
        bank = Bank.getBank();
        init();

    }
    private void init(){
        setLayout(null);
        initIdField();
        initMessageField();
        initIconField();
        initWorkButton();
        initClientPanel();
        setBorder(BorderFactory.createLineBorder(Color.pink));
    }
    private void initMessageField() {
        messageField = new JLabel();
        messageField.setHorizontalAlignment(SwingConstants.CENTER);
        messageField.setBounds(10, 10, 180, 20);
        messageField.setFont(new Font("楷体", Font.PLAIN, 11));
        this.add(messageField);
    }
    private void initIconField(){
        iconField = new JLabel(iconImage);
        iconField.setBounds(50, 40, 100, 100);
        this.add(iconField);
    }

    private void initIdField() {
        String text = "";
        if (id == 1){
            text = "一号窗口";
        }else if (id == 2){
            text = "二号窗口";
        }else if (id == 3){
            text = "三号窗口";
        }

        idField = new JLabel(text);
        idField.setBounds(0, 145, 200, 15);
        idField.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(idField);
    }
    private void initWorkButton(){
        workButton = new JButton("开始办理");
        workButton.setBounds(10, 170, 80, 29);
        workButton.addActionListener(e -> {
            if (client != null) isStart = true;
        });
        workButton.setFont(new Font("宋体", Font.PLAIN, 10));
        this.add(workButton);
    }
    private void initClientPanel(){
        clientPanel = new JPanel();
        clientPanel.setLayout(null);
        clientPanel.setBorder(BorderFactory.createLineBorder(Color.PINK));
        clientPanel.setBounds(110, 160, 40, 40);
        this.add(clientPanel);
    }

    public JLabel getMessageField() {
        return messageField;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    @Override
    public void run() {
        clientQueue = bank.getClientsQueue();
        while (client == null){
            client = clientQueue.poll();
            if (client == null) {
                continue;
            } else {
                setMessage("请 " + client.getId() + " 号顾客到 " + id + " 号窗口办理业务");
                client.toServer();
                clientPanel.add(client);
                repaint();
                while (isStart == false) {

                }
                try {
                    Thread.sleep(3 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clientPanel.remove(client);
                client = null;
                isStart = false;
                repaint();
            }
        }
    }
    private void setMessage(String text){
        messageField.setText(text);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
