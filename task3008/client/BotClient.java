package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
            if (message!=null&&!message.isEmpty()&&message.contains(":")) {
                String sender = message.substring(0, message.indexOf(":"));
                String otherText = message.substring(message.indexOf(":") + 2);
                Calendar calendar = new GregorianCalendar();
                SimpleDateFormat format=null;
                if (otherText.equals("дата")) {
                    format = new SimpleDateFormat("d.MM.YYYY");
                } else if (otherText.equals("день")) {
                    format = new SimpleDateFormat("d");
                } else if (otherText.equals("месяц")) {
                    format = new SimpleDateFormat("MMMM");
                } else if (otherText.equals("год")) {
                    format = new SimpleDateFormat("YYYY");
                } else if (otherText.equals("время")) {
                    format = new SimpleDateFormat("H:mm:ss");
                } else if (otherText.equals("час")) {
                    format = new SimpleDateFormat("H");
                } else if (otherText.equals("минуты")) {
                    format = new SimpleDateFormat("m");
                } else if (otherText.equals("секунды")) {
                    format = new SimpleDateFormat("s");
                }
                if (format!=null){
                    sendTextMessage(String.format("Информация для %s: %s", sender, format.format(calendar.getTime())));
                }
            }

        }
    }
}
