package chat.client.View;


import chat.entity.User;
import chat.client.ClientConnection;
import chat.client.View.AgentView.ChatAgent;
import chat.client.View.UserViews.MainUserView;

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
public class SignIn {

    Scanner in = new Scanner(System.in);
    ClientConnection clientConnection  = null;
    Socket socket = null;
    ObjectOutputStream objectOutputStream = null;
    ObjectInputStream objectInputStream = null;


    public void doSignIn(){
        System.out.println("");
        System.out.println("Type your login");
        String login = in.nextLine();
        System.out.println("Type your password");
        String pass = in.nextLine();
        String sha1 =null;
        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(pass.getBytes("UTF-8"), 0, pass.length());
            sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {        }
        try {
            clientConnection  = new ClientConnection();
            socket = clientConnection.getConnection();
            objectOutputStream = clientConnection.getOutStream(socket);
            objectOutputStream.flush();
            objectInputStream = clientConnection.getInStream(socket);
            objectOutputStream.writeObject(login+" "+sha1+" "+socket.getLocalPort()+" "+socket.getInetAddress());
            objectOutputStream.flush();
            objectOutputStream.writeUTF("Authorization");
            objectOutputStream.flush();
            Object object = objectInputStream.readObject();

            if(object==null){
                System.out.println("Login or password have mistake. Retype please");
                doSignIn();
            }
            else if(((User)object).getRole().equals("CLIENT")){
                MainUserView mainUserView = new MainUserView((User)object,objectOutputStream, objectInputStream);
                mainUserView.showMenuUser();
            }
            else if(((User)object).getRole().equals("AGENT")){
                ChatAgent chatAgent = new ChatAgent((User)object,objectOutputStream, objectInputStream);
                chatAgent.startChat();
            }
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
    }
}
