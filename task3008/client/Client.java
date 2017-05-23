package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.Connection;
import com.javarush.task.task30.task3008.ConsoleHelper;
import com.javarush.task.task30.task3008.Message;
import com.javarush.task.task30.task3008.MessageType;

import java.io.IOException;

/**
 * Created by amalakhov on 23.05.2017.
 */
public class Client {
    protected Connection connection;
    private volatile boolean clientConnected=false;

    protected String getServerAddress(){
        System.out.println("Введите адрес сервера: ");
        return ConsoleHelper.readString();
    }

    protected int getServerPort(){
        System.out.println("Введите адрес порта: ");
        return ConsoleHelper.readInt();
    }

    protected String getUserName(){
        System.out.println("Введите ваше имя: ");
        return ConsoleHelper.readString();
    }

    protected boolean shouldSendTextFromConsole(){
        return true;
    }

    protected SocketThread getSocketThread() {
        return new SocketThread();
    }

    protected void sendTextMessage(String text){
        try {
            connection.send(new Message(MessageType.TEXT,text));
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время отправки сообщения!");
            clientConnected=false;
        }
    }
    public class SocketThread extends Thread{
    }
}
