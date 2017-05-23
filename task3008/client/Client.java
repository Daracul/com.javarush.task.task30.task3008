package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.Connection;

/**
 * Created by amalakhov on 23.05.2017.
 */
public class Client {
    protected Connection connection;
    private volatile boolean clientConnected=false;
    public class SocketThread extends Thread{
    }
}
