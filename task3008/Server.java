package com.javarush.task.task30.task3008;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by amalakhov on 22.05.2017.
 */
public class Server {
    private static Map<String,Connection> connectionMap = new ConcurrentHashMap<>();
    public static void main(String[] args) {
        int port=ConsoleHelper.readInt();
        ServerSocket serverSocket=null;
        try {
            serverSocket=new ServerSocket(port);
            System.out.println("Сервер запущен.");
            do{
                new Handler(serverSocket.accept()).start();
            }
            while (true);
        }
        catch (Exception e){
            System.out.println(e);
            if (serverSocket!=null){
                try{
                    serverSocket.close();
                }
                catch (IOException ex){}
            }
        }
    }

    public static void sendBroadcastMessage(Message message){
        for (String key:connectionMap.keySet()){
            try {
                connectionMap.get(key).send(message);
            }
            catch (IOException e){
                System.out.println("Не удалось отправить сообщение.");
            }
        }
    }

    private static class Handler extends Thread{
        private Socket socket;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException{
            while (true) {
                connection.send(new Message(MessageType.NAME_REQUEST));
                Message nameMessage = connection.receive();
                if ((nameMessage.getType()==MessageType.USER_NAME)&&(!nameMessage.getData().isEmpty())) {
                    String userName=nameMessage.getData();
                    if (!connectionMap.containsKey(userName)){
                        connectionMap.put(userName, connection);
                        connection.send(new Message(MessageType.NAME_ACCEPTED));
                        return userName;
                    }
                }
            }
        }
    }
}
