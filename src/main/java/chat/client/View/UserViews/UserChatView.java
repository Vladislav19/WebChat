package chat.client.View.UserViews;



import chat.entity.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Владислав on 14.02.2018.
 */
public class UserChatView {

    User user;
    int port;String ip;
    String result;
    Scanner in = new Scanner(System.in);
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    Socket socket = null;
    int i=0;int j=0;

    public UserChatView(User user, ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        this.user = user;
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
    }

    public void showChat() throws IOException {
        if(j==0){
            System.out.println("User Chat+\n");
        }
        objectOutputStream.writeObject(user.getLogin()+" TCP");
        objectOutputStream.flush();
        objectOutputStream.writeUTF("setTypeConnection");
        objectOutputStream.flush();
        try {
            while (true) {
                objectOutputStream.writeObject(user);
                objectOutputStream.flush();
                objectOutputStream.writeUTF("GetFreeAgentPort");
                objectOutputStream.flush();

                result = objectInputStream.readUTF();

                if (result.equals("not found free agent")) {
                    if (j == 0) {
                        System.out.println("Wait a free agent");
                    }
                    j++;
                    Thread.sleep(1000);
                }
                else {
                    break;
                }
            }
        }   catch (IOException ex){
        }   catch (InterruptedException e) {
        }

        System.out.println("Type your message");

        Thread threadRead = new Thread(() -> {
            while(true){
                try {
                    String ask1 = objectInputStream.readUTF();
                    System.out.println(ask1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        threadRead.start();

        while (true){
            if(in.hasNext()) {
                String respons = in.nextLine();
                try {
                    objectOutputStream.writeObject(respons);
                    objectOutputStream.flush();
                    objectOutputStream.writeUTF("SendMessage " + user.getLogin());
                    objectOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }
            }
    }

}
