package chat.DB.implementations;



import chat.DB.HibernateUtil;
import chat.DB.interfaces.UserDAO;
import chat.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;


/**
 * Created by Владислав on 14.02.2018.
 */
public class UserDAOImpls implements UserDAO {


    public void save(User user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }


    public User find(String log, String pass) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT U FROM User U where U.login=:log and U.pass=:pass");
        query.setParameter("log",log);
        query.setParameter("pass",pass);
        List<User> result = query.list();
        session.getTransaction().commit();
        if(!result.isEmpty()){
            return result.get(0);
        }else return null;
    }


    public User findByLogin(String log) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT U FROM User U where U.login=:log ");
        query.setParameter("log",log);
        List<User> result = query.list();
        session.getTransaction().commit();
        if(!result.isEmpty()){
            return result.get(0);
        }else return null;
    }


    public void markFree(String log, String pass,int port, String ip){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE User set port = :port, isOnline = :online, ip=:ip where login=:login and pass=:password");
        query.setParameter("port",port);
        query.setParameter("online",1);
        query.setParameter("ip",ip);
        query.setParameter("login",log);
        query.setParameter("password",pass);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void markFreeByLogin(String log) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE User set  isOnline = :online where login=:login ");
        query.setParameter("online",1);
        query.setParameter("login",log);
        query.executeUpdate();
        session.getTransaction().commit();
    }


    public User getUser(String log, String pass) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT U FROM User U where U.login=:log and U.pass=:pass");
        query.setParameter("log",log);
        query.setParameter("pass",pass);
        List<User> result = query.list();
        session.getTransaction().commit();
        return result.get(0);
    }

    @Override
    public void setTypeConn(String log,String typeC) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE User set typeConnection = :typeC where login=:login ");
        query.setParameter("typeC",typeC);
        query.setParameter("login",log);
        query.executeUpdate();
        session.getTransaction().commit();
    }


    public User findFreeAgent(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("SELECT U FROM User U where U.role=:role and U.isOnline=:online");
        query.setParameter("role","AGENT");
        query.setParameter("online",1);
        List<User> result = query.list();
        session.getTransaction().commit();
        if (!result.isEmpty()){
            return result.get(0);
        }
        else return null;
    }


    public void markNotFree(String log, String pass){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE User set  isOnline = :online where  login=:login and pass=:password");
        query.setParameter("online",0);
        query.setParameter("login",log);
        query.setParameter("password",pass);
        query.executeUpdate();
        session.getTransaction().commit();
    }


    public void markNotFreeByPort(int port){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE User set isOnline = :online where  port=:port");
        query.setParameter("online",0);
        query.setParameter("port",port);
        query.executeUpdate();
        session.getTransaction().commit();
    }


    public void markFreeByPort(int port) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("UPDATE User set isOnline = :online where  port=:port");
        query.setParameter("online",1);
        query.setParameter("port",port);
        query.executeUpdate();
        session.getTransaction().commit();
    }
}
