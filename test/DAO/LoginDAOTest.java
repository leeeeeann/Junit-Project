package DAO;

import DAO.LoginDAO;
import java.util.ArrayList;
import java.util.List;
import static jdk.nashorn.internal.runtime.Debug.id;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import pojo.Category;
import pojo.Login;
import pojo.NailartUtil;

public class LoginDAOTest {
    
    private Session session;
    private Transaction tx;
    private Query query;
    private LoginDAO loginDAO;
    
    public LoginDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        loginDAO = new LoginDAO();
        session = mock(Session.class);  // Mock session
        query = mock(Query.class);  // Mock query
        tx = mock(Transaction.class); 
        
        SessionFactory sessionFactory = mock(SessionFactory.class);
        when(sessionFactory.openSession()).thenReturn(session);

        NailartUtil.setSessionFactory(sessionFactory);

        when(session.createQuery(anyString())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList<Login>());
        when(session.beginTransaction()).thenReturn(tx);
    }
    
    @After
    public void tearDown() {
    }

     @Test
    public void testGetByValidCredentials() {
        String email = "test@example.com";
        String password = "password123";

        Query query = mock(Query.class);
        List<Login> expectedLogins = new ArrayList<>();
        expectedLogins.add(new Login()); 
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setString("uEmail", email)).thenReturn(query);
        when(query.setString("uPass", password)).thenReturn(query);
        when(query.list()).thenReturn(expectedLogins);

        List<Login> result = loginDAO.getBy(email, password);

        assertEquals(1, result.size());
        verify(session).createQuery(anyString());
        verify(query).setString("uEmail", email);
        verify(query).setString("uPass", password);
        verify(query).list();
    }

    @Test
    public void testGetByInvalidCredentials() {

        String email = "invalid@example.com";
        String password = "wrongpassword";
        Query query = mock(Query.class);
        List<Login> expectedLogins = new ArrayList<>(); 

        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setString("uEmail", email)).thenReturn(query);
        when(query.setString("uPass", password)).thenReturn(query);
        when(query.list()).thenReturn(expectedLogins);

        List<Login> result = loginDAO.getBy(email, password);

        // Verifikasi hasil
        assertEquals(0, result.size());
        verify(session).createQuery(anyString());
        verify(query).setString("uEmail", email);
        verify(query).setString("uPass", password);
        verify(query).list();
    }

    @Test
public void testGetByException() {
    // Parameter untuk pencarian
    String email = "test@example.com";
    String password = "password123";
    when(session.createQuery(anyString())).thenThrow(new RuntimeException("Database error"));

    List<Login> result = loginDAO.getBy(email, password);
    assertTrue(result.isEmpty()); 
    verify(session).createQuery(anyString()); 
}


    @Test
    public void testRetrieveLogin() {
    List<Login> expectedLoginList = new ArrayList<>();
    Login login1 = new Login();
    login1.setId(1);
    login1.setEmail("leciw@gmail.com");
    expectedLoginList.add(login1);

    Login login2 = new Login();
    login2.setId(2);
    login2.setEmail("leciw@gmail.com");
    expectedLoginList.add(login2);

    Query query = mock(Query.class);
    when(session.createQuery("from Login")).thenReturn(query); 
    when(query.list()).thenReturn(expectedLoginList); // Mock pengembalian daftar login

    List<Login> actualLoginList = loginDAO.retrieveLogin();
    verify(session).createQuery("from Login");
    verify(query).list();
    assertEquals(expectedLoginList, actualLoginList);
}

@Test
public void testRetrieveLoginEmptyList() {
    List<Login> emptyList = new ArrayList<>();
    Query query = mock(Query.class);
    when(session.createQuery("from Login")).thenReturn(query); // Mock pemanggilan createQuery
    when(query.list()).thenReturn(emptyList);

    List<Login> actualLoginList = loginDAO.retrieveLogin(); 
    verify(session).createQuery("from Login");
    verify(query).list();
    assertTrue(actualLoginList.isEmpty());
}

@Test
public void testRetrieveLoginException() {
    when(session.createQuery(anyString())).thenThrow(new RuntimeException("Database error")); 

    List<Login> actualLoginList = loginDAO.retrieveLogin(); 

    verify(session).createQuery("from Login");
    assertTrue(actualLoginList.isEmpty());
}

        
    @Test
    public void testGetById() {
    Integer loginId = 1;
    List<Login> expectedLogins = new ArrayList<>();
    Login login1 = new Login();
    login1.setId(loginId);
    login1.setEmail("user@example.com");
    expectedLogins.add(login1);
    when(query.list()).thenReturn(expectedLogins);
    when(query.setParameter("id", loginId)).thenReturn(query);

    List<Login> actualLogins = loginDAO.getById(loginId);
    verify(session).createQuery("from Login WHERE category_id = :id");
    verify(query).setParameter("id", loginId);
    verify(query).list();
    assertEquals(expectedLogins, actualLogins);
}

@Test
public void testGetByIdEmptyList() {
    Integer loginId = 999; 
    List<Login> emptyList = new ArrayList<>();
    when(query.list()).thenReturn(emptyList);
    when(query.setParameter("id", loginId)).thenReturn(query);

    List<Login> actualLogins = loginDAO.getById(loginId);
    verify(session).createQuery("from Login WHERE category_id = :id");
    verify(query).setParameter("id", loginId);
    verify(query).list();
    assertTrue(actualLogins.isEmpty());
}

@Test
public void testGetByIdException() {
    Integer loginId = 1;
    when(session.createQuery(anyString())).thenThrow(new RuntimeException("Database error"));

    List<Login> actualLogins = loginDAO.getById(loginId);
    verify(session).createQuery("from Login WHERE category_id = :id");
    assertTrue(actualLogins.isEmpty());
}

     @Test
    public void testAddLogin() {
        Login login = new Login();
        login.setEmail("test@example.com");
        login.setPassword("testuser");
        login.setRole("USER");
        loginDAO.addLogin(login);
        verify(session).save(login);
        verify(tx).commit();
    }

    @Test
    public void testAddLoginException() {
        Login login = new Login();
        login.setEmail("test@example.com");
        login.setPassword("testuser");
        login.setRole("USER");
        doThrow(new RuntimeException("Database error")).when(session).save(login);
        loginDAO.addLogin(login);
        verify(tx, never()).commit();
    }

    
    @Test
    public void testDeleteLogin() {
        Integer loginId = 1;
        Login login = new Login();
        login.setEmail("test@example.com");
        login.setPassword("testuser");
        login.setRole("USER");
        when(session.load(Login.class, loginId)).thenReturn(login);
        loginDAO.deleteLogin(loginId);
        verify(session).delete(login);
        verify(tx).commit();
    }

    @Test
    public void testDeleteLoginException() {
        Integer loginId = 1;
        when(session.load(Login.class, loginId)).thenThrow(new RuntimeException("Database error"));
        loginDAO.deleteLogin(loginId);
        verify(tx, never()).commit();
    }
    
    @Test
    public void testEditLogin() {
        Login login = new Login();
        login.setEmail("test@example.com");
        login.setPassword("testuser");
        login.setRole("USER");
        
        loginDAO.editLogin(login);
        verify(session).update(login);
        verify(tx).commit();
    }

    @Test
    public void testEditLoginException() {
        Login login = new Login();
        login.setEmail("test@example.com");
        login.setPassword("testuser");
        login.setRole("USER");
        doThrow(new RuntimeException("Database error")).when(session).update(login);
        loginDAO.editLogin(login);
        verify(tx, never()).commit();
    }

    @Test
    public void testSearchLoginWithAllParameters() {
        String id = "1";
        String email = "test@example.com";
        String password = "password123";
        String role = "USER";
        Query query = mock(Query.class);
        List<Login> expectedLogins = new ArrayList<>();
        expectedLogins.add(new Login());
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter("id", Integer.parseInt(id))).thenReturn(query);
        when(query.setParameter("email", email)).thenReturn(query);
        when(query.setParameter("password", password)).thenReturn(query);
        when(query.setParameter("role", role)).thenReturn(query);
        when(query.list()).thenReturn(expectedLogins);
        List<Login> result = loginDAO.searchLogin(id, email, password, role);

        assertEquals(1, result.size());
        verify(session).createQuery(anyString());
        verify(query).setParameter("id", Integer.parseInt(id));
        verify(query).setParameter("email", email);
        verify(query).setParameter("password", password);
        verify(query).setParameter("role", role);
        verify(query).list();
    }

    @Test
    public void testSearchLoginWithNoParameters() {
        // Parameter untuk pencarian
        String id = null;
        String email = null;
        String password = null;
        String role = null;

        Query query = mock(Query.class);
        List<Login> expectedLogins = new ArrayList<>();
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.list()).thenReturn(expectedLogins);
        List<Login> result = loginDAO.searchLogin(id, email, password, role);
        assertEquals(0, result.size());
        verify(session).createQuery(anyString());
        verify(query, never()).setParameter(anyString(), any());
        verify(query).list();
    }

    @Test
    public void testSearchLoginWithSomeParameters() {
        // Parameter untuk pencarian
        String id = "1";
        String email = null;
        String password = "password123";
        String role = "USER";

        // Siapkan mock query dan daftar hasil
        Query query = mock(Query.class);
        List<Login> expectedLogins = new ArrayList<>();
        expectedLogins.add(new Login());

        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter("id", Integer.parseInt(id))).thenReturn(query);
        when(query.setParameter("password", password)).thenReturn(query);
        when(query.setParameter("role", role)).thenReturn(query);
        when(query.list()).thenReturn(expectedLogins);

        List<Login> result = loginDAO.searchLogin(id, email, password, role);

        assertEquals(1, result.size());
        verify(session).createQuery(anyString());
        verify(query).setParameter("id", Integer.parseInt(id));
        verify(query).setParameter("password", password);
        verify(query).setParameter("role", role);
        verify(query).list();
    }


}
