package DAO;

import pojo.Schedule;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import pojo.NailartUtil;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {

public List<Schedule> retrieveTblSchedules() {
    List<Schedule> scheduleList = new ArrayList<>();
    Session session = null;
    try {
        session = NailartUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Schedule");
        scheduleList = query.list();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return scheduleList;
}


    // Get Schedule by ID
    public List<Schedule> getByID(int scheduleId) {
        List<Schedule> scheduleList = new ArrayList<>();
        Session session = null;
        try {
            session = NailartUtil.getSessionFactory().openSession(); // Membuka session
            Query query = session.createQuery("from Schedule where id = :id"); // Query untuk mengambil data berdasarkan ID
            query.setParameter("id", scheduleId); // Set parameter ID
            scheduleList = query.list(); // Ambil hasil query
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scheduleList;
    }

    // Search Schedule (by ID, time, or services)
    public List<Schedule> searchSchedule(String id, String time, String services) {
    List<Schedule> scheduleList = new ArrayList<>();
    Session session = null;
    try {
        session = NailartUtil.getSessionFactory().openSession();
        String queryString = "from Schedule where 1=1";
        if (id != null && !id.isEmpty()) queryString += " and id = :id";
        if (time != null && !time.isEmpty()) queryString += " and time = :time";
        if (services != null && !services.isEmpty()) queryString += " and services = :services";
        
        Query query = session.createQuery(queryString.toString());
        if (id != null && !id.isEmpty()) query.setParameter("id", Integer.parseInt(id));
        if (time != null && !time.isEmpty()) query.setParameter("time", time);
        if (services != null && !services.isEmpty()) query.setParameter("services", services);
        
        scheduleList = query.list();
    } catch (Exception e) {
        e.printStackTrace(); // Tangani semua kesalahan selama query
    }
    
    return scheduleList;
}


    // Add Schedule
    public void addSchedule(Schedule schedule) {
    Transaction transaction = null;
    Session session = NailartUtil.getSessionFactory().openSession();
    try {
        transaction = session.beginTransaction();
        session.save(schedule);
        transaction.commit();
    } catch (Exception e) {
            System.out.println(e);
    }
}


    public void editSchedule(Schedule schedule) {
    Transaction transaction = null;
    Session session = NailartUtil.getSessionFactory().openSession();
    try {
            transaction = session.beginTransaction();
            session.update(schedule);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    // Delete Schedule
    public void deleteSchedule(int scheduleId) {
        Transaction transaction = null;
        Session session = NailartUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            Schedule schedule = (Schedule) session.load(Schedule.class, scheduleId);
            session.delete(schedule);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    }
    
    