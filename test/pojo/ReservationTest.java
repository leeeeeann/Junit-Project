/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import DAO.ReservationDAO;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReservationTest {
    
    private Reservation reservation;
    private ReservationDAO mockReservationDAO;
    private ReservationDAO testReservationDAO;
     
    public ReservationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        reservation = new Reservation();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testgetReservationList() {
    System.out.println("Test Get All Record");
    Reservation instance = new Reservation();
    int expResult = 2;
    List<Reservation> result = instance.getReservationList();
    assertEquals(expResult, result.size());
    }
    
    @Test
    public void testsaveReservation() {
    System.out.println("Test Save Reservation");
    Reservation reser = new Reservation();
        reser.setId(3);
        reser.setFirstName("diya");
        reser.setLastName("uak");
        reser.setPhone("12345678");
        reser.setEmail("uak@gmail.com");
        reser.setWorkshop("Nail Art - [10:00 AM - 12:00 AM]");
    String expResult = "reserved";
    String result = reser.saveReservation();
    assertEquals(expResult, result);
  }
    
    @Test
  public void testeditUser() {
    System.out.println("editUser");
    Integer id = 2;
    Reservation reser = new Reservation();
        reser.setFirstName("Techi");
        reser.setLastName("Kariska");
        reser.setEmail("sari@gmail.com");
        reser.setPhone("081298765432");
        reser.setWorkshop("Nail Art - [10:00 AM - 12:00 AM]");   
    String expResult = "reservation.xhtml?faces-redirect=true";
    String result = reser.editUser();
    assertEquals(expResult, result);
  }
  
  @Test
    public void testdeleteUser() {
    System.out.println("deleteUser");
    Reservation instance = new Reservation();
    instance.setId(11);
    String result = instance.deleteUser();
    String expResult = null;
    assertEquals(expResult, result);
}
    
    @Test
    public void testSetReservationList() {
    System.out.println("Test Set Reservation List");
    Reservation instance = new Reservation();

    List<Reservation> sampleList = new ArrayList<>();
    sampleList.add(new Reservation(1, "John", "Doe", "123456789", "john.doe@example.com", "Workshop A"));
    sampleList.add(new Reservation(2, "Jane", "Doe", "987654321", "jane.doe@example.com", "Workshop B"));
    
    instance.setReservationList(sampleList);
    
    assertEquals(sampleList, instance.getReservationList());
}
    
    @Test
    public void testConstructor() {
    System.out.println("Test Reservation Constructor");
    Integer id = 1;
    String firstName = "John";
    String lastName = "Doe";
    String phone = "123456789";
    String email = "john.doe@example.com";
    String workshop = "Workshop A";
    Reservation instance = new Reservation(id, firstName, lastName, phone, email, workshop);
    assertEquals(id, instance.getId());
    assertEquals(firstName, instance.getFirstName());
    assertEquals(lastName, instance.getLastName());
    assertEquals(phone, instance.getPhone());
    assertEquals(email, instance.getEmail());
    assertEquals(workshop, instance.getWorkshop());
}
    
    @Test
    public void testGetAllRecords() {
        System.out.println("Test Get All Records");
        class TestReservationDAO extends ReservationDAO {
            @Override
            public List<Reservation> retrieveTblReservation() {
                List<Reservation> list = new ArrayList<>();
                list.add(new Reservation(1, "John", "Doe", "123456789", "john.doe@example.com", "Workshop A"));
                list.add(new Reservation(2, "Jane", "Doe", "987654321", "jane.doe@example.com", "Workshop B"));
                return list;
            }
        }
        reservation.setReservationDAO(new TestReservationDAO()); 
        List<Reservation> actualList = reservation.getAllRecords();
        List<Reservation> expectedList = new ArrayList<>();
        expectedList.add(new Reservation(1, "Techi", "Kariska", "081298765432", "sari@gmail.com", "Menicure - [9:00 AM - 10:00 AM]"));
        expectedList.add(new Reservation(2, "Leann", "Nataly", "081234587654", "kenan@gmail.com", "Menicure - [9:00 AM - 10:00 AM]"));

        assertEquals(expectedList.size(), actualList.size());
        for (int i = 0; i < expectedList.size(); i++) {
            assertEquals(expectedList.get(i).getId(), actualList.get(i).getId());
            assertEquals(expectedList.get(i).getFirstName(), actualList.get(i).getFirstName());
            assertEquals(expectedList.get(i).getLastName(), actualList.get(i).getLastName());
            assertEquals(expectedList.get(i).getPhone(), actualList.get(i).getPhone());
            assertEquals(expectedList.get(i).getEmail(), actualList.get(i).getEmail());
            assertEquals(expectedList.get(i).getWorkshop(), actualList.get(i).getWorkshop());
        }
    }
    
    

    @Test
    public void testgetById() {
        System.out.println("Test Get By ID for Users");
        class TestReservationDAO extends ReservationDAO {
            @Override
            public List<Reservation> ShowByID(int usersId) {
                List<Reservation> list = new ArrayList<>();
                if (usersId == 1) {
                    list.add(new Reservation(1, "Alice", "Smith", "123456789", "alice@example.com", "Workshop A"));
                } else if (usersId == 2) {
                    list.add(new Reservation(2, "Bob", "Johnson", "987654321", "bob@example.com", "Workshop B"));
                }
                return list;
            }
        }
        reservation.setReservationDAO(new TestReservationDAO()); 
        reservation.getById(1); 
        assertEquals(Integer.valueOf(1), reservation.getId());
        assertEquals("Techi", reservation.getFirstName());
        assertEquals("Kariska", reservation.getLastName());
        assertEquals("081298765432", reservation.getPhone());
        assertEquals("sari@gmail.com", reservation.getEmail());
        assertEquals("Menicure - [9:00 AM - 10:00 AM]", reservation.getWorkshop());
        String resultNonExisting = reservation.getById(3); 
        assertEquals("index", resultNonExisting);
    }
    
    @Test
    public void testReservationConstructor() {
        System.out.println("Uji Konstruktor Reservation");
        Reservation reservation = new Reservation(mockReservationDAO);

        
    }
    
    @Test
    public void testGetById_NonExistentId() {
    int nonExistentId = 999; // An ID that doesn't exist
    Reservation instance = new Reservation(testReservationDAO);
    String result = instance.getById(nonExistentId);
    assertEquals("index", result); 
}
    
    @Test
    public void testGetById() {
        System.out.println("Uji Get By ID");
        class TestReservationDAO extends ReservationDAO {
     
            public List<Reservation> getbyID(Integer id) {
                List<Reservation> list = new ArrayList<>();
                if (id == 1) {
                    list.add(new Reservation(1, "Alice", "Smith", "123456789", "alice@example.com", "Workshop A"));
                } else if (id == 2) {
                    list.add(new Reservation(2, "Bob", "Johnson", "987654321", "bob@example.com", "Workshop B"));
                }
                return list;
            }

           
            public List<Reservation> ShowByID(Integer usersId) {
                List<Reservation> list = new ArrayList<>();
                if (usersId == 1) {
                    list.add(new Reservation(1, "Charlie", "Brown", "111222333", "charlie@example.com", "Workshop C"));
                } else if (usersId == 2) {
                    list.add(new Reservation(2, "Dana", "White", "444555666", "dana@example.com", "Workshop D"));
                }
                return list;
            }
        }
        reservation.setReservationDAO(new TestReservationDAO()); 
        reservation.setId(1); // Set ID ke reservasi yang valid
        String result = reservation.getById(); 
        assertEquals("reservation", result);
        assertEquals("Techi", reservation.getFirstName());
        assertEquals("Kariska", reservation.getLastName());
        assertEquals("081298765432", reservation.getPhone());
        assertEquals("sari@gmail.com", reservation.getEmail());
        assertEquals("Menicure - [9:00 AM - 10:00 AM]", reservation.getWorkshop());
        reservation.setId(3); // Set ID ke nilai yang tidak ada
        result = reservation.getById(); 
        assertEquals("index", result);
        String result2 = reservation.getById(1);
        assertEquals("edit", result2);
        assertEquals(Integer.valueOf(1), reservation.getId());
        assertEquals("Techi", reservation.getFirstName());
        assertEquals("Kariska", reservation.getLastName());
        assertEquals("081298765432", reservation.getPhone());
        assertEquals("sari@gmail.com", reservation.getEmail());
        assertEquals("Menicure - [9:00 AM - 10:00 AM]", reservation.getWorkshop());
        result2 = reservation.getById(3); 
        assertEquals("index", result2);
    }
    
    @Test
    public void testgetById_NonExistentId() {
    System.out.println("Uji getById dengan ID yang tidak ada");
    class TestReservationDAO extends ReservationDAO {
    
        public List<Reservation> getbyID(Integer id) {
            return new ArrayList<>(); 
        }
        public List<Reservation> ShowByID(Integer usersId) {
            return new ArrayList<>(); 
        }
    }
    reservation.setReservationDAO(new TestReservationDAO());
    reservation.setId(999); // Set ID ke nilai yang tidak ada
    String result = reservation.getById(); 
    assertEquals("index", result); 
    String result2 = reservation.getById(999);
    assertEquals("index", result2); // Harus navigasi ke index
}
@Test
    public void testGetReservationDAO() {
        System.out.println("Test getReservationDAO");
        ReservationDAO expectedDAO = new ReservationDAO();
        Reservation reservation = new Reservation();
        reservation.setReservationDAO(expectedDAO);
        ReservationDAO resultDAO = reservation.getReservationDAO();
        assertNotNull(resultDAO);
        assertEquals(expectedDAO, resultDAO);
    }
}
