package DAO;

import pojo.NailartUtil;
import pojo.Location;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;

public class locationDAO {

    // Retrieve all locations from the database
    public List<Location> retrieveTblLocations() {
        List<Location> listLocations = new ArrayList<>();
        Session session = null;
    try {
        session = NailartUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Location");
        listLocations = query.list();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return listLocations;
}

    // Get location by ID
    public List<Location> getById(Integer id) {
        List<Location> listLocations = new ArrayList<>();
        Session session = null;
        try {
            session = NailartUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Location where id = :id"); // No type specified
            query.setParameter("id", id);
            listLocations = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listLocations;
    }
    
    
    //Searchlocation (by ID, city, or branchlocation)
    public List<Location> searchLocation(String id, String city, String branchLocation) {
        List<Location> locationList = new ArrayList<>();
        Session session = null;
        try {
            session = NailartUtil.getSessionFactory().openSession();
            String queryString = "from Location where 1=1";
            if (id != null && !id.isEmpty()) queryString += " and id = :id";
            if (city != null && !city.isEmpty()) queryString += " and city = :city";
            if (branchLocation != null && !branchLocation.isEmpty()) queryString += " and branchLocation = :branchLocation";

            Query query = session.createQuery(queryString);
            if (id != null && !id.isEmpty()) query.setParameter("id", Integer.parseInt(id));
            if (city != null && !city.isEmpty()) query.setParameter("city", city);
            if (branchLocation != null && !branchLocation.isEmpty()) query.setParameter("branchLocation", branchLocation);

            locationList = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locationList;
    }

    // Method to add a new location
    public void addLocation(Location location) {
        Transaction transaction = null;
        Session session = NailartUtil.getSessionFactory().openSession();
    try {
        transaction = session.beginTransaction();
        session.save(location);
        transaction.commit();
    } catch (Exception e) {
            System.out.println(e);
    }
}

    // Method to edit a location
    public void editLocation(Location location) {
    Transaction transaction = null;
    Session session = NailartUtil.getSessionFactory().openSession();
    try {
            transaction = session.beginTransaction();
            session.update(location);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Method to delete a location
    public void deleteLocation(Integer idLocation) {
        Transaction transaction = null;
        Session session = NailartUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            Location location = (Location) session.load(Location.class, idLocation);
            session.delete(location);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
    }
}

}