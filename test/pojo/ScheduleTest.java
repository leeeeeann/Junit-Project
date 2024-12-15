/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import DAO.ScheduleDAO;
import java.sql.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pojo.Schedule;
import static org.junit.Assert.*;
import javax.faces.event.AjaxBehaviorEvent;
import java.util.ArrayList;

public class ScheduleTest {
   
    private Schedule schedule;
    private ScheduleDAO testScheduleDAO;
    
    public ScheduleTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    
    @Before
    public void setUp() {
    schedule = new Schedule();
        
    testScheduleDAO = new ScheduleDAO() {
    public List<Schedule> getById(Integer id) {
        List<Schedule> schedules = new ArrayList<>();
        if (id == 1) {
            schedules.add(new Schedule(1, "22:00 - 23:00", "Pedicure"));
        }
        return schedules; 
    }

    @Override
    public List<Schedule> searchSchedule(String searchCriteria, String time, String services) {
        List<Schedule> schedules = new ArrayList<>();
        if ("22:00 - 23:00".equalsIgnoreCase(time)) {
            schedules.add(new Schedule(1, "22:00 - 23:00", "Menicure")); // Include id
            schedules.add(new Schedule(2, "22:00 - 23:00", "Pedicure")); // Include id
        } else if ("23:00 - 24:00".equalsIgnoreCase(time)) {
            schedules.add(new Schedule(3, "23:00 - 24:00", "Nail Art")); // Include id
        }
        return schedules;
  
            }
        };
       
        schedule = new Schedule();
    schedule.setScheduleDAO(testScheduleDAO);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testgetScheduleList() {
    System.out.println("Test Get All Record");
    Schedule instance = new Schedule();
    int expResult = 6;
    List<Schedule> result = instance.getScheduleList();
    assertEquals(expResult, result.size());
  }
    
    @Test
    public void testgetByID() {
    int testId = 2; 
    Schedule instance = new Schedule(testScheduleDAO);
    String result = instance.getById(testId); 
    assertEquals("scheduleedit", result); 
    assertEquals(testId, (int) instance.getId());
    assertEquals("10:00 - 12:00", instance.getTime()); 
    assertEquals("Nail Art 3D", instance.getServices()); 
}

    
    
    @Test
  public void testsaveSchedule() {
    System.out.println("Test Save Schedule");
    Schedule sched = new Schedule();
        sched.setId(8);
        sched.setTime("20:00 - 21:00");
        sched.setServices("Menicure");
    String expResult = "scheduledetail";
    String result = sched.saveSchedule();
    assertEquals(expResult, result);
  }

  @Test
  public void testeditSchedule() {
    System.out.println("editSchedule");
    Integer id = 5;
    Schedule sched = new Schedule();
        sched.setTime("16:00 - 18:00");
        sched.setServices("Menicure");
    String expResult = "scheduledetail.xhtml?faces-redirect=true";
    String result = sched.editSchedule();
    assertEquals(expResult, result);
  }
  
  @Test
public void testdeleteSchedule() {
    System.out.println("deleteSchedule");
    Schedule instance = new Schedule();
    instance.setId(28);
    String result = instance.deleteSchedule();
    String expResult = null;
    assertEquals(expResult, result);
}
    @Test
    public void testSetAndGetSearchCriteria() {
        Schedule schedule = new Schedule();
        String expectedCriteria = "22:00 - 23:00";
        schedule.setSearchCriteria(expectedCriteria);
        String result = schedule.getSearchCriteria();
        assertEquals(expectedCriteria, result);
    }
    
    @Test
    public void testFilterScheduleWithSearchCriteria() {
    Schedule schedule = new Schedule();
    schedule.setSearchCriteria("22:00 - 23:00");

    List<Schedule> mockScheduleList = new ArrayList<>();
    mockScheduleList.add(new Schedule(1, "22:00 - 23:00", "Menicure")); 
    schedule.setScheduleList(mockScheduleList);
    schedule.filterSchedules(null);
    assertNotNull(schedule.getScheduleList());
    }
    
    @Test
    public void testFilterScheduleWithoutSearchCriteria() {
    Schedule schedule = new Schedule();
    schedule.setSearchCriteria(""); 
    List<Schedule> mockScheduleList = new ArrayList<>();
    mockScheduleList.add(new Schedule(1, "23:00 - 24:00", "Nail Art")); // Provide an id
    mockScheduleList.add(new Schedule(2, "01:00 - 02:00", "Nail Art 3D")); 
    schedule.setScheduleList(mockScheduleList);

    schedule.filterSchedules(null); 
    assertNotNull(schedule.getScheduleList());
    assertEquals(7, schedule.getScheduleList().size());
    assertEquals("09:00 - 10:00", schedule.getScheduleList().get(0).getTime());
}
    
    @Test
    public void testLoadSchedule() {
        System.out.println("Test loadSchedule");
        schedule.setId(1);
        schedule.loadSchedule();
        assertEquals("09:00 - 10:00", schedule.getTime());
        assertEquals("Menicure", schedule.getServices());
        schedule.setId(2);
        schedule.loadSchedule();
        assertEquals("10:00 - 12:00", schedule.getTime());
        assertEquals("Nail Art 3D", schedule.getServices());
    }
    
    @Test
    public void testSearchSchedules() {
        System.out.println("Test searchSchedule");

        schedule.setTime("22:00 - 23:00");
        schedule.setServices("");
        List<Schedule> result = schedule.searchSchedule();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("22:00 - 23:00", result.get(0).getTime());
        assertEquals("Menicure", result.get(0).getServices());
        
        assertEquals("22:00 - 23:00", result.get(1).getTime());
        assertEquals("Pedicure", result.get(1).getServices());
        schedule.setTime("23:00 - 24:00");
        schedule.setServices("");
        result = schedule.searchSchedule();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("23:00 - 24:00", result.get(0).getTime());
        assertEquals("Nail Art", result.get(0).getServices());
    }
    
    @Test
    public void testInit() {
    int testId = 1; 
    Schedule instance = new Schedule();
    instance.setId(testId); 
    ScheduleDAO testScheduleDAO = new ScheduleDAO() {
        public List<Schedule> getById(Integer id) {
            List<Schedule> schedule = new ArrayList<>();
            if (id == 1) {
                schedule.add(new Schedule(1, "22:00 - 23:00", "Menicure"));
            }
            return schedule;
        }
    };
    instance.setScheduleDAO(testScheduleDAO);
    instance.init(); 
    assertEquals("09:00 - 10:00", instance.getTime()); 
    assertEquals("Menicure", instance.getServices()); 
}
    
    @Test
public void testgetByID_NonExistentId() {
    int nonExistentId = 999;
    Schedule instance = new Schedule(testScheduleDAO);
    String result = instance.getById(nonExistentId);
    assertEquals("index", result); 
}
    
}