/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import pojo.Reservation;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.NailartUtil;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Aspire7
 */
public class ReservationDAO {

    public List<Reservation> retrieveTblReservation() {
        List listReservation = new ArrayList();
        Transaction transaction = null;
        Session session = NailartUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Reservation");
            listReservation = query.list();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
        return listReservation;
    }

    public List<Reservation> getbyID(int idReservation) {
    List<Reservation> listReservation = new ArrayList<>();
    Transaction transaction = null;
    Session session = NailartUtil.getSessionFactory().openSession();
    
    try {
        transaction = session.beginTransaction();
        Query query = session.createQuery("from Reservation where id = :id");
        query.setParameter("id", idReservation);
        Reservation reservation = (Reservation) query.uniqueResult();
        
        // Tambahkan reservasi yang ditemukan ke dalam daftar
        if (reservation != null) {
            listReservation.add(reservation);
        }

        transaction.commit();
    } catch (Exception e) {
        System.out.println(e);
    }
    return listReservation;
}
 
    
    public List<Reservation> ShowByID(int id) {
        List<Reservation> usersList = new ArrayList<Reservation>();
        Transaction transaction = null;
        Session session = NailartUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Reservation where id=:id");
            query.setInteger("id", id);
            usersList = query.list();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return usersList;
    }


    public void addReservation(Reservation reservation) {
        Transaction transaction = null;
        Session session = NailartUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.save(reservation);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void editUser(Reservation reservation) {
        Transaction transaction = null;
        Session session = NailartUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.update(reservation);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteUser(Integer idReservation) {
        Transaction transaction = null;
        Session session = NailartUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            Reservation reservation = (Reservation) session.load(Reservation.class, new Integer(idReservation));
            session.delete(reservation);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
       
      
}
