package com.xiaomin;

import javax.swing.*;

import java.awt.*;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: qming_c
 * Date: 2017-12-01
 * Time: 20:13
 */
public class Bank{
    private static Bank bank = new Bank();
    public static Bank getBank(){
        return bank;
    }

    JFrame frame;
    JLabel titleLable;
    JButton enterButton;

    JPanel serverField;
    Server[] servers = new Server[3];
    Icon[] serverImages = new ImageIcon[3];

    private WaitingRoom waitingRoom;
    private StandingField standingField;
    private ConcurrentLinkedQueue<Client> clientsQueue;
    int nextClientId = 1;

    void draw(){
        frame = new JFrame("银行排队叫号系统");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(300,300);
        frame.setSize(650, 600);
        frame.setLayout(null);

        titleLable = new JLabel("欢迎光临");
        titleLable.setBounds(0, 0, 300, 50);
        titleLable.setForeground(Color.red);
        titleLable.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(titleLable);

        enterButton = new JButton("enter");
        enterButton.setBounds(350, 0, 250, 50);
        enterButton.addActionListener(event ->{clientEntered();} );
        frame.add(enterButton);

        serverField = new JPanel();
        serverField.setBounds(0, 50, 650, 220);
        serverField.setLayout(null);
        frame.add(serverField);

        initPicture();
        for (int i = 1; i <= 3; i++){
            servers[i - 1] = new Server(i , serverImages[i - 1]);
            servers[i - 1].setBounds(10 * i + 200 * (i - 1), 10,200, 200);
            serverField.add(servers[i - 1]);
        }

        waitingRoom = new WaitingRoom();
        waitingRoom.setBounds(25, 300, 602, 100);
        frame.add(waitingRoom);

        standingField = new StandingField();
        standingField.setBounds(25, 450,600, 100);
        frame.add(standingField);

        frame.setVisible(true);
    }
    void initPicture(){
        serverImages[0] = new ImageIcon("image\\leijun.jpg");
        serverImages[1] = new ImageIcon("image\\mayun.jpg");
        serverImages[2] = new ImageIcon("image\\mahuateng.jpg");
    }

    public static void main(String[] args) {
        Bank bank = getBank();
        bank.draw();

        bank.init();
    }

    private void init(){

        clientsQueue = new ConcurrentLinkedQueue<>();

        for (int i = 0;i < servers.length; i++){
            Thread thread = new Thread(servers[i]);
            thread.start();
        }
    }

    public ConcurrentLinkedQueue<Client> getClientsQueue() {
        return clientsQueue;
    }

    public void clientEntered(){
        Thread thread = new Thread(new Client(nextClientId++));
        thread.start();
    }

    public WaitingRoom getWaitingRoom() {
        return waitingRoom;
    }

    public StandingField getStandingField() {
        return standingField;
    }

}
