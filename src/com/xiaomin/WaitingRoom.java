package com.xiaomin;

import javax.swing.*;
import java.awt.*;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: qming_c
 * Date: 2017-12-02
 * Time: 20:27
 */
public class WaitingRoom extends JPanel {
    private Chair[] chairs = new Chair[10];

    public WaitingRoom() {
        setLayout(null);

        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        for (int id = 1; id <= 10; id++){
            chairs[id - 1] = new Chair(id);
            add(chairs[id - 1]);
        }

    }

    public Chair[] getChairs() {
        return chairs;
    }
}
