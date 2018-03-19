package chat.serverWEB.controller;

import chat.DB.implementations.UserDAOImpls;
import chat.DB.interfaces.UserDAO;


import chat.entity.*;
import chat.serverTCP.Controllers.Command.Impl.SendMessageCommand;
import chat.serverTCP.Logic.MainLogic;
import chat.serverTCP.ServerConnetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.SQLException;


/**
 * Created by Владислав on 07.03.2018.
 */
@Controller
public class MainControllerWeb {
    UserDAO userDAO = new UserDAOImpls();
    User user;
    Response response = new Response();
    MainLogic mainLogic = new MainLogic();

    @Autowired
    ShaPasswordEncoder encoder;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String start(){
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(){
        return "login";
    }

    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    public String registPage(){return "registration";}

    @RequestMapping(value = "/chatClient", method = RequestMethod.GET)
    public String chatPageClient(Model model){
        model.addAttribute("mes","Wait a free agent");
        return "chatClient";
    }

    @RequestMapping(value = "/chatAgent", method = RequestMethod.GET)
    public String chatPageAgent(Model model){
        model.addAttribute("mes","Wait a free client");
        return "chatAgent";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String  registration(@RequestParam String login,@RequestParam String password){
        user = new User();
        user.setLogin(login);
        String hashPass = encoder.encodePassword(password,null);
        user.setPass(hashPass);
        user.setRole(UserRoleEnum.CLIENT.name());
        try {
            userDAO.save(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "index";
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
    public @ResponseBody
    Response sendMessage(@RequestParam String text,@RequestParam String login){
        System.out.println(text+" "+login);
        SendMessageCommand sendMessageCommand = new SendMessageCommand(text,login);
        System.out.println(Repository.getInstance().sockets.size());
        try {
            sendMessageCommand.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.setText(text);
        return  response;
    }


    @RequestMapping(value = "/findFreeAgent", method = RequestMethod.GET)
    public @ResponseBody
    Response findFreeAgent(@RequestParam String text){
        System.out.println(text);
        user = userDAO.findByLogin(text);
        userDAO.markFreeByLogin(text);
        userDAO.setTypeConn(text,"WEB");
        while(true){
           String s = mainLogic.searchFreeAgent(user);
           if(s.equals("success")){
               break;
           }else {
               try {
                   Thread.sleep(3000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        }
        response.setText("Connection established");
        return  response;
    }

    @RequestMapping(value = "/waitClient", method = RequestMethod.GET)
    public @ResponseBody
    Response waitClient(@RequestParam String text){
        while(true){
            for (Chat c:Repository.getInstance().chats) {
                if(c.getLoginAgent().equals(text)){
                    response.setText("Client come");
                    break;
                }
            }
            if(response.getText().equals("Client come")){
                break;
            }
        }
        return  response;
    }

    @RequestMapping(value = "/giveMessageAgent", method = RequestMethod.GET)
    public @ResponseBody
    Response giveMessage(@RequestParam String text){
        response.setText(null);
        for (Chat c:Repository.getInstance().chats) {
            if(c.getLoginAgent().equals(text)){
                response.setText(c.getMessageHistoryClient().getFirst());
                c.getMessageHistoryClient().removeFirst();
            }
        }
        if(response.getText().equals(null)){
            giveMessage(text);
        }
        return  response;
    }

    @RequestMapping(value = "/giveMessageClient", method = RequestMethod.GET)
    public @ResponseBody
    Response giveMessageC(@RequestParam String text){
        response.setText(null);
        for (Chat c:Repository.getInstance().chats) {
            if(c.getLoginClient().equals(text)){
                response.setText(c.getMessageHistoryAgent().getFirst());
                c.getMessageHistoryAgent().removeFirst();
            }
        }
        if(response.getText().equals(null)){
            giveMessage(text);
        }
        return  response;
    }

}
