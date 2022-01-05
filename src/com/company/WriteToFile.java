package com.company;

import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {

    public WriteToFile(Paddle left, Paddle right, String nameLeft, String nameRight) {
        try {
            FileWriter writer = new FileWriter("Score_List.txt", true);
            writer.append("\n " + nameLeft + " " + left.getPoint() + " vs " + right.getPoint() + " " + nameRight);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
