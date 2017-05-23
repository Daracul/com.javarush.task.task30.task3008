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

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

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

    public void run(){
        Thread thread =getSocketThread();
        thread.setDaemon(true);
        thread.start();
        synchronized (this){
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Произошла ошибка во время ожидания соединения. ");
                clientConnected=false;
            }
        }
        if (clientConnected){
            System.out.println("Соединение установлено. Для выхода наберите команду ‘exit’.");
            while (clientConnected){
                String text = ConsoleHelper.readString();
                if (text.equals("exit")){
                    clientConnected=false;
                    break;
                }
                if (shouldSendTextFromConsole()){
                    sendTextMessage(text);
                }
            }
        }
        else if (!clientConnected){
            System.out.println("Произошла ошибка во время работы клиента.");
        }

    }

    public class SocketThread extends Thread{
        protected void processIncomingMessage(String message){
            System.out.println(message);
        }

        protected void informAboutAddingNewUser(String userName){
            System.out.println("Приветствуем нового пользователя "+userName);
        }

        protected void informAboutDeletingNewUser(String userName){
            System.out.println(userName + " покинул нас :(");
        }

        protected void notifyConnectionStatusChanged(boolean clientConnected){
            Client.this.clientConnected=clientConnected;
            synchronized (Client.this){
                Client.this.notify();
            }
        }

    }
}
