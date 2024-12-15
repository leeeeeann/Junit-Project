package DAO;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import pojo.NailartUtil;
import pojo.Reservation;

public class ReservationDAOTest {
    private ReservationDAO reservationDAO;
    private Session session;
    private Query query;
    private Transaction transaction;

    public ReservationDAOTest() {
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
        reservationDAO = new ReservationDAO();
        session = mock(Session.class);
        query = mock(Query.class);
        transaction = mock(Transaction.class);

        SessionFactory sessionFactory = mock(SessionFactory.class);
        when(sessionFactory.openSession()).thenReturn(session);

        NailartUtil.setSessionFactory(sessionFactory);
        when(NailartUtil.getSessionFactory().openSession()).thenReturn(session);

        when(session.beginTransaction()).thenReturn(transaction);
        when(session.createQuery(anyString())).thenReturn(query);
    }

    @After
    public void tearDown() {
        System.out.println("\n");
    }

    @Test
    public void testRetrieveTblReservation() {
        List<Reservation> expectedReservations = new ArrayList<>();
        Reservation reservation1 = new Reservation();
        reservation1.setId(1);
        expectedReservations.add(reservation1);

        Reservation reservation2 = new Reservation();
        reservation2.setId(2);
        expectedReservations.add(reservation2);

        when(query.list()).thenReturn(expectedReservations);
        List<Reservation> actualReservations = reservationDAO.retrieveTblReservation();
        
        verify(session).createQuery("from Reservation");
        assertEquals(expectedReservations, actualReservations);
    }

    @Test
    public void testRetrieveTblReservationEmptyList() {
        List<Reservation> emptyList = new ArrayList<>();
        when(query.list()).thenReturn(emptyList);
        
        List<Reservation> actualReservations = reservationDAO.retrieveTblReservation();
        
        verify(session).createQuery("from Reservation");
        verify(query).list();
        assertTrue(actualReservations.isEmpty());
    }

    @Test
    public void testRetrieveTblReservationException() {
        when(session.createQuery(anyString())).thenThrow(new RuntimeException("Database error"));
        
        List<Reservation> actualReservations = reservationDAO.retrieveTblReservation();
        
        verify(session).createQuery("from Reservation");
        assertTrue(actualReservations.isEmpty());
    }
    
      @Test
    public void testGetByIDFound() {
        Reservation expectedReservation = new Reservation();
        expectedReservation.setId(1);
        when(query.setParameter(anyString(), anyInt())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(expectedReservation);

        List<Reservation> actualReservations = reservationDAO.getbyID(1);
        
        verify(session).createQuery("from Reservation where id = :id");
        verify(query).setParameter("id", 1);
        assertEquals(1, actualReservations.size());
        assertEquals(expectedReservation, actualReservations.get(0));
    }

    @Test
    public void testGetByIDNotFound() {
        when(query.setParameter(anyString(), anyInt())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(null);

        List<Reservation> actualReservations = reservationDAO.getbyID(999); // ID yang tidak ada
        
        verify(session).createQuery("from Reservation where id = :id");
        verify(query).setParameter("id", 999);
        assertTrue(actualReservations.isEmpty());
    }

    @Test
    public void testGetByIDException() {
        when(session.createQuery(anyString())).thenThrow(new RuntimeException("Database error"));

        List<Reservation> actualReservations = reservationDAO.getbyID(1);
        
        verify(session).createQuery("from Reservation where id = :id");
        assertTrue(actualReservations.isEmpty());
    }
    
    @Test
    public void testShowByIDFound() {
        Reservation expectedReservation = new Reservation();
        expectedReservation.setId(1);
        List<Reservation> expectedList = new ArrayList<>();
        expectedList.add(expectedReservation);
        
        when(query.setInteger(anyString(), anyInt())).thenReturn(query);
        when(query.list()).thenReturn(expectedList);

        List<Reservation> actualReservations = reservationDAO.ShowByID(1);
        
        verify(session).createQuery("from Reservation where id=:id");
        verify(query).setInteger("id", 1);
        assertEquals(expectedList, actualReservations);
    }

    @Test
    public void testShowByIDNotFound() {
        when(query.setInteger(anyString(), anyInt())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList<Reservation>()); // Daftar kosong jika tidak ditemukan

        List<Reservation> actualReservations = reservationDAO.ShowByID(999); // ID yang tidak ada
        
        verify(session).createQuery("from Reservation where id=:id");
        verify(query).setInteger("id", 999);
        assertTrue(actualReservations.isEmpty());
    }

    @Test
    public void testShowByIDException() {
        when(session.createQuery(anyString())).thenThrow(new RuntimeException("Database error"));

        List<Reservation> actualReservations = reservationDAO.ShowByID(1);
        
        verify(session).createQuery("from Reservation where id=:id");
        assertTrue(actualReservations.isEmpty());
    }
    
      @Test
    public void testAddReservation() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setFirstName("lean");
        reservationDAO.addReservation(reservation);
        verify(session).save(reservation);
        verify(transaction).commit();
    }

    @Test
    public void testAddReservationException() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setFirstName("lean");

        doThrow(new RuntimeException("Database error")).when(session).save(reservation);
        reservationDAO.addReservation(reservation);
        verify(session).save(reservation);
        verify(transaction, never()).commit();
    }
    
    @Test
    public void testEditUser() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setFirstName("lean");

        reservationDAO.editUser(reservation);
        verify(session).update(reservation);
        verify(transaction).commit();
    }

    @Test
    public void testEditUserException() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setFirstName("lean");

        doThrow(new RuntimeException("Database error")).when(session).update(reservation);
        reservationDAO.editUser(reservation);
        verify(session).update(reservation);
        verify(transaction, never()).commit();
    }
    
    
     @Test
    public void testDeleteUser() {
        Integer reservationId = 1;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        when(session.load(Reservation.class, reservationId)).thenReturn(reservation);
        reservationDAO.deleteUser(reservationId);
        verify(session).delete(reservation);
        verify(transaction).commit();
    }

    @Test
    public void testDeleteUserException() {
        Integer reservationId = 1;
        when(session.load(Reservation.class, reservationId)).thenThrow(new RuntimeException("Database error"));
        reservationDAO.deleteUser(reservationId);
        verify(session, never()).delete(any(Reservation.class));
        verify(transaction, never()).commit();
    }
}
