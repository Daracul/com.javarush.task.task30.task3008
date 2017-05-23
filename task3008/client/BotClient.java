package com.javarush.task.task30.task3008.client;

/**
 * Created by amalakhov on 23.05.2017.
 */
public class BotClient extends Client {
    public static void main(String[] args) {
        BotClient bot = new BotClient();
        bot.run();
    }
    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        return String.format("date_bot_%d",(int)(Math.random()*100));
    }

    public class BotSocketThread extends SocketThread{

    }
}
