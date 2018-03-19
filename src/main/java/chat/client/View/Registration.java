package chat.client.View;


import chat.entity.User;
import chat.client.Client;
import chat.client.ClientConnection;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Created by Владислав on 14.02.2018.
 */
public class Registration {
    Scanner in = new Scanner(System.in);
    ClientConnection clientConnection  = null;
    Socket socket = null;
    ObjectOutputStream objectOutputStream = null;
    ObjectInputStream objectInputStream = null;
    String pass;String md5Hex;String role ;
    String sha1 = null;


    public void doRegistr() throws IOException {
        System.out.println("Choose u role 1-user and 2-agent");
        switch (in.nextLine()){
            case "1":role="CLIENT";break;
            case "2":role="AGENT";break;
            default:
                System.out.println("Choose 1 or 2");
                doRegistr();
        }
        Switch(role);
    }

    public void Switch(String str){
        User user = new User();
        System.out.println("Type your login");
        user.setLogin(in.nextLine());
        System.out.println("Type your password");
        pass = in.nextLine();
        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(pass.getBytes("UTF-8"), 0, pass.length());
            sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {        }
        user.setPass(sha1.toLowerCase());
        user.setRole(str);
        user.setIsOnline(0);
        sendUserData(user);
    }

    public  void sendUserData(User user){
        try {
            clientConnection  = new ClientConnection();
            socket = clientConnection.getConnection();
            objectOutputStream = clientConnection.getOutStream(socket);
            objectInputStream = clientConnection.getInStream(socket);
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
            objectOutputStream.writeUTF("Registration");
            objectOutputStream.flush();
            String str = objectInputStream.readUTF();
            System.out.println(str);
            clientConnection.closeConnection(socket);
            Client.menu();
        } catch (IOException e) {
        }
    }
}

