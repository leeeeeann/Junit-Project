/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.ArrayList;
import java.util.List;
import static jdk.nashorn.internal.runtime.Debug.id;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import org.mockito.Mockito;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import pojo.Category;
import pojo.NailartUtil;

public class CategoryDAOTest {
    
    private CategoryDAO categoryDAO;
    private Session session;
    private Query query;
    private Transaction transaction; 
    
  public CategoryDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("***** Start Testing *****");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("***** End Testing *****");
    }
    
    @Before
    public void setUp() {
        categoryDAO = new CategoryDAO();
        session = mock(Session.class);  // Mock session
        query = mock(Query.class);  // Mock query
        transaction = mock(Transaction.class); 
        
        SessionFactory sessionFactory = mock(SessionFactory.class);
        when(sessionFactory.openSession()).thenReturn(session);

        // Set the mocked SessionFactory in the utility class
        NailartUtil.setSessionFactory(sessionFactory);

        // Mock query behavior to return an empty list by default
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList<Category>());
        when(session.beginTransaction()).thenReturn(transaction);       
 }
    
    
    @After
    public void tearDown() {
        System.out.println("\n");
    }  
    
    @Test
    public void testRetrieveTblCategories() {
        List<Category> expectedCategories = new ArrayList<>();
        Category category1 = new Category();
        category1.setCategoryId(1);
        category1.setCategoryName("Nail Art");
        expectedCategories.add(category1);

        Category category2 = new Category();
        category2.setCategoryId(2);
        category2.setCategoryName("Manicure");
        expectedCategories.add(category2);


        when(query.list()).thenReturn(expectedCategories);
        List<Category> actualCategories = categoryDAO.retrieveTblCategories();
        verify(session).createQuery("from Category");
        verify(query).list();
        assertEquals(expectedCategories, actualCategories);
    }
    
    @Test
    public void testRetrieveTblCategoriesEmptyList() {
        List<Category> emptyList = new ArrayList<>();
        when(query.list()).thenReturn(emptyList);
        List<Category> actualCategories = categoryDAO.retrieveTblCategories();
        verify(session).createQuery("from Category");
        verify(query).list();
        assertTrue(actualCategories.isEmpty());
    }

    @Test
    public void testRetrieveTblCategoriesException() {
        when(session.createQuery(anyString())).thenThrow(new RuntimeException("Database error"));
        List<Category> actualCategories = categoryDAO.retrieveTblCategories();
        verify(session).createQuery("from Category");
        assertTrue(actualCategories.isEmpty());
    }

    
    @Test
    public void testShowByIdWithValidId() {
        // Parameter untuk pencarian
        int categoryId = 1;

        // Siapkan mock query dan daftar hasil
        Query query = mock(Query.class);
        List<Category> expectedCategories = new ArrayList<>();
        expectedCategories.add(new Category()); // Tambahkan objek Category yang sesuai

        // Mengonfigurasi perilaku query
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setInteger("categoryId", categoryId)).thenReturn(query);
        when(query.list()).thenReturn(expectedCategories);

        // Panggil metode showById
        List<Category> result = categoryDAO.showById(categoryId);

        // Verifikasi hasil
        assertEquals(1, result.size());
        verify(session).createQuery(anyString());
        verify(query).setInteger("categoryId", categoryId);
        verify(query).list();
    }

    @Test
    public void testShowByIdWithInvalidId() {
        // Parameter untuk pencarian
        int categoryId = 999; // ID yang tidak ada di database

        // Siapkan mock query dan daftar hasil
        Query query = mock(Query.class);
        List<Category> expectedCategories = new ArrayList<>(); // Kosongkan daftar

        // Mengonfigurasi perilaku query
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setInteger("categoryId", categoryId)).thenReturn(query);
        when(query.list()).thenReturn(expectedCategories);

        // Panggil metode showById
        List<Category> result = categoryDAO.showById(categoryId);

        // Verifikasi hasil
        assertEquals(0, result.size());
        verify(session).createQuery(anyString());
        verify(query).setInteger("categoryId", categoryId);
        verify(query).list();
    }
    
    @Test
public void testShowByIdException() {
    // Parameter untuk pencarian
    int categoryId = 1;

    // Siapkan mock query
    Query query = mock(Query.class);

    // Mengonfigurasi perilaku query untuk menghasilkan pengecualian
    when(session.createQuery(anyString())).thenReturn(query);
    when(query.setInteger("categoryId", categoryId)).thenReturn(query);
    when(query.list()).thenThrow(new RuntimeException("Database error")); // Simulasi kesalahan

    // Panggil metode showById
    List<Category> result = categoryDAO.showById(categoryId);

    // Verifikasi hasil
    assertTrue(result.isEmpty()); // Pastikan daftar kategori kosong
    verify(session).createQuery(anyString());
    verify(query).setInteger("categoryId", categoryId);
    verify(query).list(); // Pastikan list() dipanggil
}
    
    
    @Test
    public void testGetByID() {
        int categoryId = 1;
        List<Category> expectedCategories = new ArrayList<>();
        Category category1 = new Category();
        category1.setCategoryId(categoryId);
        category1.setCategoryName("Nail Art");
        expectedCategories.add(category1);
        when(query.list()).thenReturn(expectedCategories);
        when(query.setParameter("id", categoryId)).thenReturn(query);
        List<Category> actualCategories = categoryDAO.getByID(categoryId);
        verify(session).createQuery("from Category WHERE category_id = :id");
        verify(query).setParameter("id", categoryId);
        verify(query).list();
        assertEquals(expectedCategories, actualCategories);
    }
    
    @Test
    public void testGetByIDEmptyList() {
        int categoryId = 999; // Non-existent category ID
        List<Category> emptyList = new ArrayList<>();
        when(query.list()).thenReturn(emptyList);
        when(query.setParameter("id", categoryId)).thenReturn(query);
        List<Category> actualCategories = categoryDAO.getByID(categoryId);
        verify(session).createQuery("from Category WHERE category_id = :id");
        verify(query).setParameter("id", categoryId);
        verify(query).list();
        assertTrue(actualCategories.isEmpty());
    }

    @Test
    public void testGetByIDException() {
        int categoryId = 1;
        when(session.createQuery(anyString())).thenThrow(new RuntimeException("Database error"));
        List<Category> actualCategories = categoryDAO.getByID(categoryId);
        verify(session).createQuery("from Category WHERE category_id = :id");
        assertTrue(actualCategories.isEmpty());
    }
    
   @Test
    public void testAddCategory() {
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("Nail Art");
        
        // Panggil metode addCategory
        categoryDAO.addCategory(category);

        // Verifikasi bahwa metode save dan commit dipanggil
        verify(session).save(category);
        verify(transaction).commit();
    }

    @Test
    public void testAddCategoryException() {
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("Nail Art");

        // Simulasi kesalahan saat menyimpan
        doThrow(new RuntimeException("Database error")).when(session).save(category);

        // Panggil metode addCategory
        categoryDAO.addCategory(category);

        // Verifikasi bahwa commit tidak dipanggil
        verify(transaction, never()).commit();
    }

     @Test
    public void testDeleteCategory() {
        Integer categoryId = 1;

        // Simulasikan pemanggilan load untuk mendapatkan objek Category
        Category category = new Category();
        category.setCategoryId(categoryId);
        when(session.load(Category.class, categoryId)).thenReturn(category);

        // Panggil metode deleteCategory
        categoryDAO.deleteCategory(categoryId);

        // Verifikasi bahwa metode delete dan commit dipanggil
        verify(session).delete(category);
        verify(transaction).commit();
    }

    @Test
    public void testDeleteCategoryException() {
        Integer categoryId = 1;

        // Simulasikan kesalahan saat menghapus
        doThrow(new RuntimeException("Database error")).when(session).delete(any(Category.class));

        // Panggil metode deleteCategory
        categoryDAO.deleteCategory(categoryId);

        // Verifikasi bahwa commit tidak dipanggil
        verify(transaction, never()).commit();
    }
    
    @Test
    public void testEditCategory() {
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("Nail Art Updated");
        
        categoryDAO.editCategory(category);

        verify(session).update(category);
        verify(transaction).commit();
    }

    @Test
    public void testEditCategoryException() {
        Category category = new Category();
        category.setCategoryId(1);
        category.setCategoryName("Nail Art Updated");

        doThrow(new RuntimeException("Database error")).when(session).update(category);

        categoryDAO.editCategory(category);

        verify(transaction, never()).commit();
    }

     @Test
    public void testSearchCategoryWithAllParameters() {
        String categoryId = "1";
        String categoryName = "Nail Art";
        String description = "Beautiful nail designs";
        String poster = "\"C:\\Users\\Aspire 7\\Downloads\\menicure.jpg\"";

        Query query = mock(Query.class);
        List<Category> expectedCategories = new ArrayList<>();
        expectedCategories.add(new Category());

        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter("categoryId", Integer.parseInt(categoryId))).thenReturn(query);
        when(query.setParameter("categoryName", categoryName)).thenReturn(query);
        when(query.setParameter("description", description)).thenReturn(query);
        when(query.setParameter("poster", poster)).thenReturn(query);
        when(query.list()).thenReturn(expectedCategories);

        List<Category> result = categoryDAO.searchCategory(categoryId, categoryName, description, poster);

        assertEquals(1, result.size());
        verify(session).createQuery(anyString());
        verify(query).setParameter("categoryId", Integer.parseInt(categoryId));
        verify(query).setParameter("categoryName", categoryName);
        verify(query).setParameter("description", description);
        verify(query).setParameter("poster", poster);
        verify(query).list();
    }

    @Test
    public void testSearchCategoryWithNoParameters() {
        String categoryId = null;
        String categoryName = null;
        String description = null;
        String poster = null;

        Query query = mock(Query.class);
        List<Category> expectedCategories = new ArrayList<>();

        when(session.createQuery(anyString())).thenReturn(query);
        when(query.list()).thenReturn(expectedCategories);

        List<Category> result = categoryDAO.searchCategory(categoryId, categoryName, description, poster);

        assertEquals(0, result.size());
        verify(session).createQuery(anyString());
        verify(query, never()).setParameter(anyString(), any());
        verify(query).list();
    }

    @Test
    public void testSearchCategoryWithSomeParameters() {

        String categoryId = "1";
        String categoryName = null;
        String description = "Beautiful nail designs";
        String poster = "\"C:\\Users\\Aspire 7\\Downloads\\menicure.jpg\"";


        Query query = mock(Query.class);
        List<Category> expectedCategories = new ArrayList<>();
        expectedCategories.add(new Category());


        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter("categoryId", Integer.parseInt(categoryId))).thenReturn(query);
        when(query.setParameter("description", description)).thenReturn(query);
        when(query.list()).thenReturn(expectedCategories);


        List<Category> result = categoryDAO.searchCategory(categoryId, categoryName, description, poster);


        assertEquals(1, result.size());
        verify(session).createQuery(anyString());
        verify(query).setParameter("categoryId", Integer.parseInt(categoryId));
        verify(query).setParameter("description", description);
        verify(query).list();
    }  
}
