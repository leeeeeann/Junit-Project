package DAO;

import pojo.NailartUtil;
import pojo.Login;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class LoginDAO {

    // Retrieve all login details
    public List<Login> retrieveLogin() {
        List<Login> loginList = new ArrayList<>();
        Session session = null;
        try {
            session = NailartUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Login");
            loginList = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginList;
    }


    
    //Searchlocation (by ID, city, or branchlocation)
    public List<Login> searchLogin(String id, String email, String password,String role) {
        List<Login> loginList = new ArrayList<>();
        Session session = null;
        try {
            session = NailartUtil.getSessionFactory().openSession();
            String queryString = "from Login where 1=1";
            if (id != null && !id.isEmpty()) queryString += " and id = :id";
            if (email != null && !email.isEmpty()) queryString += " and email = :email";
            if (password != null && !password.isEmpty()) queryString += " and password = :password";
            if (role != null && !role.isEmpty()) queryString += " and role = :role";

            Query query = session.createQuery(queryString);
            if (id != null && !id.isEmpty()) query.setParameter("id", Integer.parseInt(id));
            if (email != null && !email.isEmpty()) query.setParameter("email", email);
            if (password != null && !password.isEmpty()) query.setParameter("password", password);
            if (role != null && !role.isEmpty()) query.setParameter("role", role);

            loginList = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loginList;
    }
    

    // Add a new login
    public void addLogin(Login login) {
        Transaction transaction = null;
        Session session = NailartUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.save(login);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Edit an existing login
    public void editLogin(Login login) {
    Transaction transaction = null;
    Session session = NailartUtil.getSessionFactory().openSession();
    try {
            transaction = session.beginTransaction();
            session.update(login);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Delete a login by ID
    public void deleteLogin(Integer loginId) {
        Transaction transaction = null;
        Session session = NailartUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            Login login = (Login) session.load(Login.class, loginId);
            session.delete(login);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Get login details by email and password
    public List<Login> getBy(String uEmail, String uPass) {
        List<Login> userList = new ArrayList<>();
        Transaction transaction = null;
        Session session = NailartUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Login where email = :uEmail AND password = :uPass");
            query.setString("uEmail", uEmail);
            query.setString("uPass", uPass);
            userList = query.list();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }

    public List<Login> getById(Integer id) {
        List<Login> loginList = new ArrayList<>();
        Session session = null;
        try {
            session = NailartUtil.getSessionFactory().openSession(); // Membuka session
            Query query = session.createQuery("from Login WHERE category_id = :id"); // Query untuk mengambil data berdasarkan ID
            query.setParameter("id", id);; // Set parameter ID
            loginList = query.list(); // Ambil hasil query
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return loginList;
    }
    
    
   

}
