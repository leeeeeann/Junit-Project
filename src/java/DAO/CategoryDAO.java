package DAO;

import pojo.NailartUtil;
import pojo.Category;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CategoryDAO {

    // Method to retrieve all Category records
    public List<Category> retrieveTblCategories() {
        List<Category> categoryList = new ArrayList<>();
        Session session = null;
        try {
            session = NailartUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Category");
            categoryList = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryList;
    }
    
     // Get Schedule by ID
    public List<Category> getByID(int id) {
        List<Category> categoryList = new ArrayList<>();
        Session session = null;
        try {
            session = NailartUtil.getSessionFactory().openSession(); // Membuka session
            Query query = session.createQuery("from Category WHERE category_id = :id"); // Query untuk mengambil data berdasarkan ID
            query.setParameter("id", id);; // Set parameter ID
            categoryList = query.list(); // Ambil hasil query
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return categoryList;
    }
    
    public List<Category> showById(int id) {
        List<Category> categoryList = new ArrayList<>();
        Transaction transaction = null;
        Session session = NailartUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Category where categoryId=:categoryId");
            query.setInteger("categoryId", id);
            categoryList = query.list();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return categoryList;
    }
    
    
    // Search Categories (by categoryId, categoryName, or description)
    public List<Category> searchCategory(String categoryId, String categoryName, String description, String poster) {
        List<Category> categoryList = new ArrayList<>();
        Session session = null;
        try {
            session = NailartUtil.getSessionFactory().openSession();
            String queryString = "from Category where 1=1";
            if (categoryId != null && !categoryId.isEmpty()) queryString += " and categoryId = :categoryId";
            if (categoryName != null && !categoryName.isEmpty()) queryString += " and categoryName = :categoryName";
            if (description != null && !description.isEmpty()) queryString += " and description = :description";
            if (poster != null && !poster.isEmpty()) queryString += " and poster = :poster";

            Query query = session.createQuery(queryString);
            if (categoryId != null && !categoryId.isEmpty()) query.setParameter("categoryId", Integer.parseInt(categoryId));
            if (categoryName != null && !categoryName.isEmpty()) query.setParameter("categoryName", categoryName);
            if (description != null && !description.isEmpty()) query.setParameter("description", description);
            if (poster != null && !poster.isEmpty()) query.setParameter("poster", poster);
            categoryList = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryList;
    }
    
    public void addCategory(Category category) {
    Transaction transaction = null;
    Session session = NailartUtil.getSessionFactory().openSession();
    try {
        transaction = session.beginTransaction();
        session.save(category);
        transaction.commit();
    } catch (Exception e) {
            System.out.println(e);
    }
}

    // Edit Schedule
    public void editCategory(Category category) {
    Transaction transaction = null;
    Session session = NailartUtil.getSessionFactory().openSession();
    try {
            transaction = session.beginTransaction();
            session.update(category);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
        // Delete Schedule
    public void deleteCategory(Integer categoryId) {
        Transaction transaction = null;
        Session session = NailartUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            Category category = (Category) session.load(Category.class, categoryId);
            session.delete(category);
            transaction.commit();
    } catch (Exception e) {
            System.out.println(e);
        }
    }
}