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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import pojo.NailartUtil;
import pojo.Schedule;

public class ScheduleDAOTest {
    private ScheduleDAO scheduleDAO;
    private Session session;
    private Query query;
    private Transaction transaction; 
    
    public ScheduleDAOTest() {
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
        // Mock Session dan Query
        scheduleDAO = new ScheduleDAO();
        session = mock(Session.class);
        query = mock(Query.class);
        transaction = mock(Transaction.class); 
        
        SessionFactory sessionFactory = mock(SessionFactory.class);
        when(sessionFactory.openSession()).thenReturn(session);
        // Inisialisasi ScheduleDAO dengan session yang dimock

        NailartUtil.setSessionFactory(mock(SessionFactory.class));
        when(NailartUtil.getSessionFactory().openSession()).thenReturn(session);

        when(session.beginTransaction()).thenReturn(transaction);
        when(query.list()).thenReturn(new ArrayList<Schedule>());
        when(session.createQuery(anyString())).thenReturn(query);
    }
    
    @After
    public void tearDown() {
        System.out.println("\n");
    }
    
    @Test
    public void testRetrieveTblSchedules() {
        List<Schedule> expectedSchedules = new ArrayList<>();
        Schedule schedule1 = new Schedule();
        schedule1.setId(1);
        schedule1.setTime("Morning Shift");
        expectedSchedules.add(schedule1);

        Schedule schedule2 = new Schedule();
        schedule2.setId(2);
        schedule2.setTime("Afternoon Shift");
        expectedSchedules.add(schedule2);

        when(query.list()).thenReturn(expectedSchedules);
        List<Schedule> actualSchedules = scheduleDAO.retrieveTblSchedules();
        verify(session).createQuery("from Schedule");
        assertEquals(expectedSchedules, actualSchedules);
    }

    @Test
    public void testRetrieveTblSchedulesEmptyList() {
        List<Schedule> emptyList = new ArrayList<>();
        when(query.list()).thenReturn(emptyList);
        List<Schedule> actualSchedules = scheduleDAO.retrieveTblSchedules();
        verify(session).createQuery("from Schedule");
        verify(query).list();
        assertTrue(actualSchedules.isEmpty());
    }

    @Test
    public void testRetrieveTblSchedulesException() {
        when(session.createQuery(anyString())).thenThrow(new RuntimeException("Database error"));
        List<Schedule> actualSchedules = scheduleDAO.retrieveTblSchedules();
        verify(session).createQuery("from Schedule");
        assertTrue(actualSchedules.isEmpty());
    }

    
    @Test
    public void testGetByID() {
        int scheduleId = 1;
        List<Schedule> expectedSchedules = new ArrayList<>();
        Schedule schedule1 = new Schedule();
        schedule1.setId(scheduleId);
        schedule1.setTime("09:00 - 10:00");
        expectedSchedules.add(schedule1);
        when(query.setParameter("id", scheduleId)).thenReturn(query);
        when(query.list()).thenReturn(expectedSchedules);
        List<Schedule> actualSchedules = scheduleDAO.getByID(scheduleId);
        verify(session).createQuery("from Schedule where id = :id");
        verify(query).setParameter("id", scheduleId);
        verify(query).list();
        assertEquals(expectedSchedules, actualSchedules);
    }

    @Test
    public void testGetByIDEmptyList() {
        int scheduleId = 999; 
        List<Schedule> emptyList = new ArrayList<>();

        when(query.setParameter("id", scheduleId)).thenReturn(query);
        when(query.list()).thenReturn(emptyList);

        List<Schedule> actualSchedules = scheduleDAO.getByID(scheduleId);

        verify(session).createQuery("from Schedule where id = :id");
        verify(query).setParameter("id", scheduleId);
        verify(query).list();
        assertTrue(actualSchedules.isEmpty());
    }

    @Test
    public void testGetByIDException() {
        int scheduleId = 1;

        when(session.createQuery(anyString())).thenThrow(new RuntimeException("Database error"));

        List<Schedule> actualSchedules = scheduleDAO.getByID(scheduleId);

        verify(session).createQuery("from Schedule where id = :id");
        assertTrue(actualSchedules.isEmpty());
    }
    
        @Test
    public void testAddSchedule() {
        Schedule schedule = new Schedule();
        schedule.setId(1);
        schedule.setTime("Morning Shift");
        scheduleDAO.addSchedule(schedule);
        verify(session).save(schedule);
        verify(transaction).commit();
    }

    @Test
    public void testAddScheduleException() {
        Schedule schedule = new Schedule();
        schedule.setId(1);
        schedule.setTime("Morning Shift");
        doThrow(new RuntimeException("Database error")).when(session).save(schedule);
        scheduleDAO.addSchedule(schedule);

        verify(transaction, never()).commit();
    }
    
    @Test
    public void testEditSchedule() {
        Schedule schedule = new Schedule();
        schedule.setId(1);
        schedule.setTime("Morning Shift");
        scheduleDAO.editSchedule(schedule);
        verify(session).update(schedule);
        verify(transaction).commit();
    }

    @Test
    public void testEditScheduleException() {
        Schedule schedule = new Schedule();
        schedule.setId(1);
        schedule.setTime("Morning Shift");
        doThrow(new RuntimeException("Database error")).when(session).update(schedule);
        scheduleDAO.editSchedule(schedule);
        verify(transaction, never()).commit();
    }
    
    
    @Test
    public void testDeleteSchedule() {
        int scheduleId = 1;
        Schedule schedule = mock(Schedule.class);
        when(session.load(Schedule.class, scheduleId)).thenReturn(schedule);
        scheduleDAO.deleteSchedule(scheduleId);
        verify(session).delete(schedule);
        verify(transaction).commit();
    }

    @Test
    public void testDeleteScheduleException() {
        int scheduleId = 1;
        when(session.load(Schedule.class, scheduleId)).thenThrow(new RuntimeException("Database error"));
        scheduleDAO.deleteSchedule(scheduleId);
        verify(transaction, never()).commit();
    }

    @Test
    public void testSearchScheduleWithAllParameters() {
        String id = "1";
        String time = "12:00";
        String services = "Nail Service";
        Query query = mock(Query.class);
        List<Schedule> expectedSchedules = new ArrayList<>();
        expectedSchedules.add(new Schedule());
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter("id", Integer.parseInt(id))).thenReturn(query);
        when(query.setParameter("time", time)).thenReturn(query);
        when(query.setParameter("services", services)).thenReturn(query);
        when(query.list()).thenReturn(expectedSchedules);
        List<Schedule> result = scheduleDAO.searchSchedule(id, time, services);

        assertEquals(1, result.size());
        verify(session).createQuery(anyString());
        verify(query).setParameter("id", Integer.parseInt(id));
        verify(query).setParameter("time", time);
        verify(query).setParameter("services", services);
        verify(query).list();
    }

    @Test
    public void testSearchScheduleWithNoParameters() {
        String id = null;
        String time = null;
        String services = null;
        Query query = mock(Query.class);
        List<Schedule> expectedSchedules = new ArrayList<>();
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.list()).thenReturn(expectedSchedules);
        List<Schedule> result = scheduleDAO.searchSchedule(id, time, services);
        assertEquals(0, result.size());
        verify(session).createQuery(anyString());
        verify(query, never()).setParameter(anyString(), any());
        verify(query).list();
    }

    @Test
    public void testSearchScheduleWithSomeParameters() {
        // Parameter untuk pencarian
        String id = "1";
        String time = null;
        String services = "Nail Service";

        // Siapkan mock query dan daftar hasil
        Query query = mock(Query.class);
        List<Schedule> expectedSchedules = new ArrayList<>();
        expectedSchedules.add(new Schedule());

        // Mengonfigurasi perilaku query
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter("id", Integer.parseInt(id))).thenReturn(query);
        when(query.setParameter("services", services)).thenReturn(query);
        when(query.list()).thenReturn(expectedSchedules);

        // Panggil metode searchSchedule
        List<Schedule> result = scheduleDAO.searchSchedule(id, time, services);

        // Verifikasi hasil
        assertEquals(1, result.size());
        verify(session).createQuery(anyString());
        verify(query).setParameter("id", Integer.parseInt(id));
        verify(query).setParameter("services", services);
        verify(query).list();
    }

} 