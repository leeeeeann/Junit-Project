package pojo;

import DAO.LoginDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginTest {
    private Login login;
    private Login logins;
    private Login login1;
    private LoginDAO mockLoginDAO;
    private LoginDAO loginDAO;

    @Before
    public void setUp() {
        mockLoginDAO = Mockito.mock(LoginDAO.class);
        login = new Login(mockLoginDAO);
        login1 = new Login();
        logins = new Login(1, "test@example.com", "password123", "user");
        
    }

    @Test
    public void testLoginSuccessful() {
        String testEmail = "test@example.com";
        String testPassword = "password";
        login.setEmail(testEmail);
        login.setPassword(testPassword);

        List<Login> mockUsers = new ArrayList<>();
        Login user = new Login(); 
        user.setEmail(testEmail);
        user.setPassword(testPassword);
        mockUsers.add(user);

        when(mockLoginDAO.getBy(testEmail, testPassword)).thenReturn(mockUsers);
        String result = login.Login_s();
        assertEquals("List", result);
        assertEquals(testEmail, login.getEmail());
        assertEquals(testPassword, login.getPassword());
    }

    @Test
    public void testLoginNoUserFound() {
        String testEmail = "test@example.com";
        String testPassword = "wrongpassword";
        login.setEmail(testEmail);
        login.setPassword(testPassword);

        when(mockLoginDAO.getBy(testEmail, testPassword)).thenReturn(new ArrayList<>());
        String result = login.Login_s();
        assertEquals("Login_error", result);
    }

    @Test
    public void testLoginException() {
        String testEmail = "test@example.com";
        String testPassword = "password";
        login.setEmail(testEmail);
        login.setPassword(testPassword);

        doThrow(new RuntimeException("Database error")).when(mockLoginDAO).getBy(testEmail, testPassword);
        String result = login.Login_s();
        assertEquals("Login_error", result);
    }
    
    @Test
    public void testSetLoginDAO() {
        login.setLoginDAO(mockLoginDAO);
        assertNotNull(login.getLoginDAO());
    }
    
    @Test
    public void testLoginConstructor() {
        assertEquals(Integer.valueOf(1), logins.getId());
        assertEquals("test@example.com", logins.getEmail());
        assertEquals("password123", logins.getPassword());
        assertEquals("user", logins.getRole());
    }
    
    @Test
    public void testLogout() {
        System.out.println("Test Logout");
        HttpSession session = new HttpSessionStub();
        Login login = new Login();
        String result = login.logout(session);
        assertEquals("index", result);
        assertTrue(((HttpSessionStub) session).isInvalidated());
    }
    
    @Test
public void testGetAllLoginRecords() {
    System.out.println("Test Get All Login Records");
    LoginDAO mockLoginDAO = new LoginDAO() {
        @Override
        public List<Login> retrieveLogin() {
            List<Login> logins = new ArrayList<>();
            logins.add(new Login(3, "john@gmail.com", "johnpass", "admin"));
            logins.add(new Login(4, "jane@gmail.com", "janepass", "user"));
            return logins;
        }
    };
    
    Login login = new Login();
    login.setLoginDAO(mockLoginDAO);
    List<Login> result = login.getAllLoginRecords();
    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals(1, (int) result.get(0).getId());
    assertEquals("leann@gmail.com", result.get(0).getEmail());
    assertEquals("leeee", result.get(0).getPassword());
    assertEquals("Admin", result.get(0).getRole());
    assertEquals(2, (int) result.get(1).getId());
    assertEquals("parhan@gmail.com", result.get(1).getEmail());
    assertEquals("hannnn", result.get(1).getPassword());
    assertEquals("Admin", result.get(1).getRole());
}

@Test
public void testGetAllLogin() {
    System.out.println("Test Get All Login Data");
    LoginDAO mockLoginDAO = new LoginDAO() {
        @Override
        public List<Login> retrieveLogin() {
            List<Login> logins = new ArrayList<>();
            logins.add(new Login(1, "ulak@gmail.com", "uak", "admin"));
            logins.add(new Login(2, "leciw@gmail.com", "cantik", "user"));
            return logins;
        }
    };
    
    Login login = new Login();
    login.setLoginDAO(mockLoginDAO);
    List<Login> result = login.getAllLogin();
    assertNotNull(result);
    assertEquals(4, result.size());
    assertEquals(1, (int) result.get(0).getId());
    assertEquals("leann@gmail.com", result.get(0).getEmail());
    assertEquals("leeee", result.get(0).getPassword());
    assertEquals("Admin", result.get(0).getRole());
    assertEquals(2, (int) result.get(1).getId());
    assertEquals("parhan@gmail.com", result.get(1).getEmail());
    assertEquals("hannnn", result.get(1).getPassword());
    assertEquals("Admin", result.get(1).getRole());
}

 @Test
    public void testSearchLoginSuccess() {
        // Given
        String testEmail = "test@example.com";
        String testPassword = "password123";
        String testRole = "admin";
        
        login.setEmail(testEmail);
        login.setPassword(testPassword);
        login.setRole(testRole);
        List<Login> mockUsers = new ArrayList<>();
        mockUsers.add(new Login(1, testEmail, testPassword, testRole));
        
        when(mockLoginDAO.searchLogin(null, testEmail, testPassword, testRole)).thenReturn(mockUsers);
        List<Login> result = login.searchLogin();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testEmail, result.get(0).getEmail()); 
        assertEquals(testPassword, result.get(0).getPassword()); 
        assertEquals(testRole, result.get(0).getRole());
    }

    @Test
    public void testSearchLoginNoResult() {
        // Given
        String testEmail = "test@example.com";
        String testPassword = "password123";
        String testRole = "admin";
        
        login.setEmail(testEmail);
        login.setPassword(testPassword);
        login.setRole(testRole);
        when(mockLoginDAO.searchLogin(null, testEmail, testPassword, testRole)).thenReturn(new ArrayList<>());
        List<Login> result = login.searchLogin();
        assertNotNull(result); // Ensure the result is not null
        assertEquals(0, result.size()); // Ensure no results are returned
    }

    @Test
    public void testSearchLoginWithException() {
        String testEmail = "test@example.com";
        String testPassword = "password123";
        String testRole = "admin";
        
        login.setEmail(testEmail);
        login.setPassword(testPassword);
        login.setRole(testRole);
        when(mockLoginDAO.searchLogin(null, testEmail, testPassword, testRole)).thenThrow(new RuntimeException("Database error"));

        List<Login> result = null;
        try {
            result = login.searchLogin();
        } catch (Exception e) {
            assertEquals("Database error", e.getMessage());
        }
        assertEquals(null, result);
    }
    
    @Test
public void testLoadLoginWithValidId() {
    // Given
    Integer testId = 1;
    String testEmail = "test@example.com";
    String testPassword = "password123";
    String testRole = "admin";
    
    login.setId(testId);
    List<Login> mockLoginList = new ArrayList<>();
    mockLoginList.add(new Login(testId, testEmail, testPassword, testRole));
    when(mockLoginDAO.getById(testId)).thenReturn(mockLoginList);
    login.loadLogin();
    assertEquals(testEmail, login.getEmail());
    assertEquals(testPassword, login.getPassword());
    assertEquals(testRole, login.getRole());
}

@Test
public void testLoadLoginWithNoResults() {
    Integer testId = 1;
    login.setId(testId); 
    when(mockLoginDAO.getById(testId)).thenReturn(new ArrayList<>());
    login.loadLogin();
    assertNull(login.getEmail());
    assertNull(login.getPassword());
    assertNull(login.getRole());
}

@Test
public void testLoadLoginWithNullId() {
    login.setId(null);
    login.loadLogin();
    verify(mockLoginDAO, never()).getById(Mockito.anyInt());
    assertNull(login.getEmail());
    assertNull(login.getPassword());
    assertNull(login.getRole());
}

@Test
public void testGetAndSetSearchCriteria() {
    String testSearchCriteria = "admin";
    login.setSearchCriteria(testSearchCriteria); 
    String result = login.getSearchCriteria(); 
    assertNotNull(result); 
    assertEquals(testSearchCriteria, result); 
}

@Test
public void testSetAndGetLoginList() {
    List<Login> testLoginList = new ArrayList<>();
    testLoginList.add(new Login(1, "user1@example.com", "password1", "admin"));
    testLoginList.add(new Login(2, "user2@example.com", "password2", "user"));
    login1.setLoginList(testLoginList); 
    List<Login> result = login1.getLoginList(); 

    assertNotNull(result);
    assertEquals(3, result.size());
    assertEquals("leann@gmail.com", result.get(0).getEmail()); 
    assertEquals("leeee", result.get(0).getPassword()); 
    assertEquals("Admin", result.get(0).getRole());
}

@Test
public void testGetLoginListRetrieveFromDAO() {
    // Given
    List<Login> mockLoginList = new ArrayList<>();
    mockLoginList.add(new Login(3, "user3@example.com", "password3", "admin"));
    mockLoginList.add(new Login(4, "user4@example.com", "password4", "user"));

    // Mocking the DAO to return the mock list
    when(mockLoginDAO.retrieveLogin()).thenReturn(mockLoginList);
    login.setLoginList(null);
    login.setSearchCriteria(null);
    List<Login> result = login.getLoginList();
    assertNotNull(result); 
    assertEquals(2, result.size()); 
    assertEquals("user3@example.com", result.get(0).getEmail()); 
    assertEquals("password3", result.get(0).getPassword());
    assertEquals("admin", result.get(0).getRole()); 
}

@Test
public void testGetLoginListWithSearchCriteria() {
    // Given
    List<Login> mockLoginList = new ArrayList<>();
    mockLoginList.add(new Login(5, "user5@example.com", "password5", "user"));
    login.setSearchCriteria("user5");
    when(mockLoginDAO.retrieveLogin()).thenReturn(mockLoginList);
    List<Login> result = login.getLoginList();
    assertNotNull(result); // Ensure the result is not null
    assertEquals(1, result.size()); // Ensure only one result is returned
    assertEquals("user5@example.com", result.get(0).getEmail()); // Check user's email
    assertEquals("password5", result.get(0).getPassword()); // Check user's password
}

 @Test
    public void testFilterLoginWithSearchCriteria() {
        Login login = new Login();
        login.setSearchCriteria("techi@gmail.com");
        
        List<Login> mockLoginList = new ArrayList<>();
        mockLoginList.add(new Login(1, "admin@example.com", "adminpass", "admin"));
        
        login.setLoginList(mockLoginList);
        login.filterLogin(null);
        assertNotNull(login.getLoginList()); // Ensure loginList is not null
    }

    @Test
    public void testFilterLoginWithoutSearchCriteria() {
        Login login = new Login();
        login.setSearchCriteria("");
        
        List<Login> mockLoginList = new ArrayList<>();
        mockLoginList.add(new Login(1, "admin@example.com", "adminpass", "admin"));
        mockLoginList.add(new Login(2, "4dmin@example.com", "4dminpass", "admin"));
        
        login.setLoginList(mockLoginList);
        login.filterLogin(null);
        assertNotNull(login.getLoginList()); // Ensure loginList is not null
        assertEquals(4, login.getLoginList().size()); // Ensure two results are returned
        assertEquals("leann@gmail.com", login.getLoginList().get(0).getEmail());
    }
    @Test
    public void testFilterLoginNullSearchCriteria() {
        login.setSearchCriteria(null);
        AjaxBehaviorEvent mockEvent = Mockito.mock(AjaxBehaviorEvent.class);
        List<Login> mockLoginList = new ArrayList<>();
        mockLoginList.add(new Login(4, "user1@example.com", "userpass1", "user"));
        mockLoginList.add(new Login(5, "user2@example.com", "userpass2", "admin"));

        when(mockLoginDAO.retrieveLogin()).thenReturn(mockLoginList);
        login.filterLogin(mockEvent);
        assertNotNull(login.getLoginList()); // Ensure loginList is not null
        assertEquals(2, login.getLoginList().size()); // Ensure two results are returned
        assertEquals("user1@example.com", login.getLoginList().get(0).getEmail()); // Check the email of the first user
        assertEquals("user2@example.com", login.getLoginList().get(1).getEmail()); // Check the email of the second user
    }

    @Test
public void testInitWithValidId() {
    Integer testId = 1;
    login.setId(testId); 
    List<Login> mockLoginData = new ArrayList<>();
    mockLoginData.add(new Login(testId, "test@example.com", "password123", "admin"));
    when(mockLoginDAO.getById(testId)).thenReturn(mockLoginData);
    login.init(); 
    assertEquals("test@example.com", login.getEmail());
    assertEquals("password123", login.getPassword());
    assertEquals("admin", login.getRole());
}

@Test
public void testInitWithNoResults() {
    Integer testId = 2;
    login.setId(testId); 
    when(mockLoginDAO.getById(testId)).thenReturn(new ArrayList<>());
    login.init(); 
    assertNull(login.getEmail()); // Pastikan email null
    assertNull(login.getPassword()); // Pastikan password null
    assertNull(login.getRole()); // Pastikan role null
}

@Test
public void testInitWithNullId() {
    login.setId(null); 
    login.init();
    verify(mockLoginDAO, never()).getById(Mockito.anyInt()); // Pastikan DAO tidak dipanggil
    assertNull(login.getEmail()); // Pastikan email null
    assertNull(login.getPassword()); // Pastikan password null
    assertNull(login.getRole()); // Pastikan role null
}

@Test
public void testGetByIdWithValidId() {
    Integer testId = 1; 
    List<Login> mockLoginData = new ArrayList<>();
    mockLoginData.add(new Login(testId, "test@example.com", "password123", "admin"));
    when(mockLoginDAO.getById(testId)).thenReturn(mockLoginData);
    String result = login.getById(testId); 
    assertEquals("admindetailedit", result); // Pastikan hasilnya benar
    assertEquals(testId, Integer.valueOf(login.getId())); // Pastikan id di-set dengan benar
    assertEquals("test@example.com", login.getEmail()); // Pastikan email di-set
    assertEquals("password123", login.getPassword()); // Pastikan password di-set
    assertEquals("admin", login.getRole()); // Pastikan role di-set
}

@Test
public void testGetByIdWithNoResults() {
    Integer testId = 2; 
    when(mockLoginDAO.getById(testId)).thenReturn(new ArrayList<>());
    String result = login.getById(testId); 
    assertEquals("index", result); // Pastikan hasilnya benar
    assertNull(login.getEmail()); // Pastikan email null
    assertNull(login.getPassword()); // Pastikan password null
    assertNull(login.getRole()); // Pastikan role null
}

@Test
public void testGetByIdWithNullId() {
    Integer testId = 0; 
    when(mockLoginDAO.getById(testId)).thenReturn(new ArrayList<>());
    String result = login.getById(testId); 
    assertEquals("index", result); // Pastikan hasilnya benar
    assertNull(login.getEmail()); // Pastikan email null
    assertNull(login.getPassword()); // Pastikan password null
    assertNull(login.getRole()); // Pastikan role null
}

  @Test
    public void testsaveLogin() {
    System.out.println("Test Save Login Admin");
    Login log = new Login();
        log.setId(3);
        log.setEmail("aoll@gmail.com");
        log.setPassword("ul");
        log.setRole("Admin");
    String expResult = "admindetail";
    String result = log.saveLogin();
    assertEquals(expResult, result);
  }
    
    @Test
    public void testeditLogin() {
    System.out.println("editLogin");
    Integer id = 1;
    Login log = new Login();
        log.setEmail("techi@gmail.com");
        log.setPassword("techi123");
        log.setRole("admin");
    String expResult = "admindetail.xhtml?faces-redirect=true";
    String result = log.editLogin();
    assertEquals(expResult, result);
  }
    
    @Test
    public void testdeleteLogin() {
    System.out.println("deleteLogin");
    Login instance = new Login();
    instance.setId(100);
    String result = instance.deleteLogin();
    String expResult = null;
    assertEquals(expResult, result);
}
}
