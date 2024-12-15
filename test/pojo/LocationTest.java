/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import DAO.locationDAO;
import java.sql.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pojo.Location;
import static org.junit.Assert.*;
import javax.faces.event.AjaxBehaviorEvent;
import java.util.ArrayList;

public class LocationTest {
    
    private Location location;
    private locationDAO testLocationDAO;
    
    public LocationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        location = new Location();
        
    testLocationDAO = new locationDAO() {
    @Override
    public List<Location> getById(Integer id) {
        List<Location> locations = new ArrayList<>();
        if (id == 1) {
            locations.add(new Location(1, "Jakarta", "Central Jakarta"));
        }
        return locations; 
    }

    @Override
    public List<Location> searchLocation(String searchCriteria, String city, String branchLocation) {
        List<Location> locations = new ArrayList<>();
        if ("Jakarta".equalsIgnoreCase(city)) {
            locations.add(new Location(1, "Jakarta", "Central Jakarta")); 
            locations.add(new Location(2, "Jakarta", "North Jakarta")); 
        } else if ("Bandung".equalsIgnoreCase(city)) {
            locations.add(new Location(3, "Bandung", "West Bandung")); 
        }
        return locations;
  
            }
        };
       
        location = new Location();
    location.setLocationDAO(testLocationDAO);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testgetLocationList() {
    System.out.println("Test Get All Record");
    Location instance = new Location();
    int expResult = 6; 
    List<Location> result = instance.getLocationList();
    assertEquals(expResult, result.size());
}

  
    @Test
    public void testGetById() {
    int testId = 1; 
    Location instance = new Location(testLocationDAO);
    String result = instance.getById(testId);
    assertEquals("adminlocation", result);
    assertEquals(testId, (int) instance.getId());
    assertEquals("Jakarta", instance.getCity()); 
    assertEquals("Central Jakarta", instance.getBranchLocation()); 
}
  @Test
  public void testsaveLocation() {
    System.out.println("Test Save Product");
    Location order = new Location();
        order.setId(8);
        order.setCity("Depok");
        order.setBranchLocation("Lechy Licious Margonda");
    String expResult = "adminlocation";
    String result = order.saveLocation();
    assertEquals(expResult, result);
  }

  @Test
  public void testeditLocation() {
    System.out.println("editLocation");
    Integer id = 5;
    Location order = new Location();
        order.setCity("Jogja");
        order.setBranchLocation("Lechy Licious Kaliurang");
    String expResult = "adminlocation.xhtml?faces-redirect=true";
    String result = order.editLocation();
    assertEquals(expResult, result);
  }
  
  @Test
  public void testdeleteLocation() {
    System.out.println("deleteLocation");
    Location instance = new Location();
    instance.setId(7);
    String expResult = "adminlocation";
    String result = instance.deleteLocation();
    assertEquals(expResult, result);
  }
  
   @Test
    public void testSetAndGetSearchCriteria() {
        Location location = new Location();
        String expectedCriteria = "Jakarta";
        location.setSearchCriteria(expectedCriteria);
        String result = location.getSearchCriteria();
        assertEquals(expectedCriteria, result);
    }
    
    @Test
    public void testFilterLocationWithSearchCriteria() {
    Location location = new Location();
    location.setSearchCriteria("Jakarta");

    List<Location> mockLocationList = new ArrayList<>();
    mockLocationList.add(new Location(1, "Jakarta", "Central Jakarta")); 
    location.setLocationList(mockLocationList);
    location.filterLocation(null);
    assertNotNull(location.getLocationList());
}


    @Test
public void testFilterLocationWithoutSearchCriteria() {
    Location location = new Location();
    location.setSearchCriteria("");
    List<Location> mockLocationList = new ArrayList<>();
    mockLocationList.add(new Location(1, "Bandung", "West Bandung")); // Provide an id
    mockLocationList.add(new Location(2, "Surabaya", "East Surabaya")); 
    location.setLocationList(mockLocationList);
    location.filterLocation(null); 
    assertNotNull(location.getLocationList());
    assertEquals(7, location.getLocationList().size());
    assertEquals("Jakarta", location.getLocationList().get(0).getCity());
}  
    @Test
    public void testLoadLocation() {
        System.out.println("Test loadLocation");
        location.setId(1);
        location.loadLocation();
        assertEquals("Jakarta", location.getCity());
        assertEquals("Central Jakarta", location.getBranchLocation());
        location.setId(2);
        location.loadLocation();
        assertEquals("Jakarta", location.getCity());
        assertEquals("Central Jakarta", location.getBranchLocation());
    }
    
    @Test
    public void testSearchLocations() {
        System.out.println("Test searchLocations");

        location.setCity("Jakarta");
        location.setBranchLocation("");
        List<Location> result = location.searchLocations();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Jakarta", result.get(0).getCity());
        assertEquals("Central Jakarta", result.get(0).getBranchLocation());
        assertEquals("Jakarta", result.get(1).getCity());
        assertEquals("North Jakarta", result.get(1).getBranchLocation());
        location.setCity("Bandung");
        location.setBranchLocation(""); 
        result = location.searchLocations();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Bandung", result.get(0).getCity());
        assertEquals("West Bandung", result.get(0).getBranchLocation());
    }
    
    
@Test
public void testInit() {
    int testId = 1;
    Location instance = new Location();
    instance.setId(testId); 
    locationDAO testLocationDAO = new locationDAO() {
        @Override
        public List<Location> getById(Integer id) {
            List<Location> locations = new ArrayList<>();
            if (id == 1) {
                locations.add(new Location(1, "Jakarta", "Central Jakarta"));
            }
            return locations;
        }
    };
    instance.setLocationDAO(testLocationDAO);
    instance.init(); 
    assertEquals("Jakarta", instance.getCity()); 
    assertEquals("Central Jakarta", instance.getBranchLocation()); 
}

    @Test
    public void testGetById_NonExistentId() {
    int nonExistentId = 999; 
    Location instance = new Location(testLocationDAO);
    String result = instance.getById(nonExistentId);
    assertEquals("index", result); 
}



}
    



