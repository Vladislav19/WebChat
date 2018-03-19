package chat.serverTCP;


import chat.DB.implementations.UserDAOImpls;
import chat.DB.interfaces.UserDAO;
import chat.entity.Chat;
import chat.entity.Repository;
import chat.entity.SocketContain;
import chat.entity.User;
import chat.serverTCP.Logic.MainLogic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerConnetion {
    UserDAO userDAO = new UserDAOImpls();
    private static final int port  = 6667;
    int db;

    public ServerConnetion(int db) {
        this.db = db;
    }

    public ServerConnetion() {
    }

    public void startServer(){
        ServerSocket srvSocket = null;

        try {
            try {
                int i = 0;
                InetAddress ia = InetAddress.getByName("localhost");
                srvSocket = new ServerSocket(port, 0, ia);

                System.out.println("Server started\n\n");

                while(true) {
                    Socket socket = srvSocket.accept();
                    System.err.println("Client accepted");

                    ObjectOutputStream dos = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream dis = new ObjectInputStream (socket.getInputStream());
                    SocketContain contain = new SocketContain();
                    contain.setSocket(socket);
                    contain.setOis(dis);
                    contain.setOus(dos);
                    Repository.getInstance().sockets.add(contain);

                    new Server(db).setSocket(socket,dos,dis);
                }
            } catch(Exception e) {
                System.out.println("Exception : " + e);
            }
        }
        finally {
            try {
                if (srvSocket != null)
                    srvSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

}
