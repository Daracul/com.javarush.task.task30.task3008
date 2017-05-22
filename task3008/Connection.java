package com.javarush.task.task30.task3008;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by amalakhov on 22.05.2017.
 */
public class Connection implements Closeable{
    final private Socket socket;
    final private ObjectOutputStream out;
    final private ObjectInputStream in;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        out=new ObjectOutputStream(this.socket.getOutputStream());
        in=new ObjectInputStream(this.socket.getInputStream());
    }

    public void send(Message message) throws IOException{
        synchronized (out){
        out.writeObject(message);}
    }

    public Message receive() throws IOException, ClassNotFoundException {
        synchronized (in) {
            return (Message) in.readObject();

        }
    }

    public SocketAddress getRemoteSocketAddress(){
        return socket.getRemoteSocketAddress();
    }

    @Override
    public void close() throws IOException {
        if (socket!=null){socket.close();}
        if (out!=null){out.close();}
        if (in!=null){in.close();}
    }
}
