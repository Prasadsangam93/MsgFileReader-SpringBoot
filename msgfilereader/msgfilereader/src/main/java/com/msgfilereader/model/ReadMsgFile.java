package com.msgfilereader.model;

import org.apache.poi.hsmf.MAPIMessage;
import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadMsgFile {

    public static void main(String[] args) {
        try {
            File msgFile = new File("C:\\Users\\Sreenivas Bandaru\\Downloads\\at0yd-zv85p (1).msg");

            // Read .msg file using FileInputStream
            FileInputStream fis = new FileInputStream(msgFile);
            MAPIMessage msg = new MAPIMessage(fis);

            // Extract email details
            System.out.println("Subject: " + msg.getSubject());
            System.out.println("Sender: " + msg.getDisplayFrom());
            System.out.println("To: " + msg.getDisplayTo());
            System.out.println("CC: " + msg.getDisplayCC());
            System.out.println("BBC :"+msg.getDisplayBCC());
            System.out.println("Date: " + msg.getMessageDate());
            System.out.println("Body:\n" + msg.getTextBody());

            // Close file input stream
            fis.close();

        } catch (IOException | ChunkNotFoundException e) {
            e.printStackTrace();
        }
    }
}
