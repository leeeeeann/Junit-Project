package DAO;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.Location;
import pojo.NailartUtil;
import org.hibernate.SessionFactory;
import DAO.locationDAO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LocationDAOTest {

    private locationDAO locationDAO; // Ganti dengan nama kelas DAO Anda
    private Session session;
    private Transaction tx;
    private Query query;

    @Before
    public void setUp() {
        locationDAO = new locationDAO(); // Ganti dengan nama kelas DAO Anda
        SessionFactory sessionFactory = mock(SessionFactory.class);
        session = mock(Session.class);
        tx = mock(Transaction.class);
        query = mock(Query.class);
        
        
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(tx);

        NailartUtil.setSessionFactory(sessionFactory); // Set session factory mock
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.list()).thenReturn(new ArrayList<Location>());
    }

    @After
    public void tearDown() {
        NailartUtil.setSessionFactory(null); // Clear session factory
    }
    
     @Test
    public void testRetrieveTblLocations() {
        List<Location> expectedLocations = new ArrayList<>();
        Location location1 = new Location();
        expectedLocations.add(location1);

        Location location2 = new Location();
        expectedLocations.add(location2);

        when(query.list()).thenReturn(expectedLocations);
        List<Location> actualLocations = locationDAO.retrieveTblLocations();
        
        verify(session).createQuery("from Location");
        assertEquals(expectedLocations, actualLocations);
    }

    @Test
    public void testRetrieveTblLocationsEmptyList() {
        List<Location> emptyList = new ArrayList<>();
        when(query.list()).thenReturn(emptyList);
        
        List<Location> actualLocations = locationDAO.retrieveTblLocations();
        
        verify(session).createQuery("from Location");
        verify(query).list();
        assertTrue(actualLocations.isEmpty());
    }

    @Test
    public void testRetrieveTblLocationsException() {
        when(session.createQuery(anyString())).thenThrow(new RuntimeException("Database error"));
        
        List<Location> actualLocations = locationDAO.retrieveTblLocations();
        
        verify(session).createQuery("from Location");
        assertTrue(actualLocations.isEmpty());
    }
    
     @Test
    public void testGetById() {
        List<Location> expectedLocations = new ArrayList<>();
        Location location = new Location();
        location.setId(1); // Pastikan Anda menyetel ID
        expectedLocations.add(location);

        when(query.list()).thenReturn(expectedLocations);
        when(query.setParameter("id", 1)).thenReturn(query); // Mocking parameter setting

        List<Location> actualLocations = locationDAO.getById(1);
        
        verify(session).createQuery("from Location where id = :id");
        verify(query).setParameter("id", 1);
        assertEquals(expectedLocations, actualLocations);
    }

    @Test
    public void testGetByIdEmptyList() {
        List<Location> emptyList = new ArrayList<>();
        when(query.list()).thenReturn(emptyList);
        when(query.setParameter("id", 1)).thenReturn(query); // Mocking parameter setting
        
        List<Location> actualLocations = locationDAO.getById(1);
        
        verify(session).createQuery("from Location where id = :id");
        verify(query).setParameter("id", 1);
        assertTrue(actualLocations.isEmpty());
    }

    @Test
    public void testGetByIdException() {
        when(session.createQuery(anyString())).thenThrow(new RuntimeException("Database error"));
        
        List<Location> actualLocations = locationDAO.getById(1);
        
        verify(session).createQuery("from Location where id = :id");
        assertTrue(actualLocations.isEmpty());
    }
    
        @Test
    public void testAddLocation() {
        Location location = new Location();
        location.setId(1); 
        locationDAO.addLocation(location);
        verify(session).save(location);
        verify(tx).commit();
    }

    @Test
    public void testAddLocationException() {
        Location location = new Location();
        location.setId(1); 
        doThrow(new RuntimeException("Database error")).when(session).save(location);
        locationDAO.addLocation(location);
        verify(session).save(location);
        verify(tx, never()).commit();
    }
    
    @Test
    public void testEditLocation() {
        Location location = new Location();
        location.setId(1); 
        locationDAO.editLocation(location);

        verify(session).update(location);
        verify(tx).commit();
    }

    @Test
    public void testEditLocationException() {
        Location location = new Location();
        location.setId(1); 
        doThrow(new RuntimeException("Database error")).when(session).update(location);
        locationDAO.editLocation(location);
        verify(session).update(location);
        verify(tx, never()).commit();
    }
    
     @Test
    public void testDeleteLocation() {
        Integer locationId = 1;
        Location mockLocation = mock(Location.class);
        when(session.load(Location.class, locationId)).thenReturn(mockLocation);
        locationDAO.deleteLocation(locationId);
        verify(session).load(Location.class, locationId);
        verify(session).delete(mockLocation);
        verify(tx).commit();
    }

    @Test
    public void testDeleteLocationException() {
        Integer locationId = 1;
        when(session.load(Location.class, locationId)).thenThrow(new RuntimeException("Database error"));
        locationDAO.deleteLocation(locationId);
        verify(session).load(Location.class, locationId);
        verify(session, never()).delete(any(Location.class));
        verify(tx, never()).commit();
    }
    

    @Test
    public void testSearchLocationById() {
        String id = "1";
        String city = null;
        String branchLocation = null;

        List<Location> expectedLocations = new ArrayList<>();
        Location location = new Location();
        location.setId(1);
        expectedLocations.add(location);

        when(query.list()).thenReturn(expectedLocations);
        
        List<Location> actualLocations = locationDAO.searchLocation(id, city, branchLocation);

        // Memverifikasi query yang dihasilkan
        verify(session).createQuery("from Location where 1=1 and id = :id");
        verify(query).setParameter("id", 1);
        assertEquals(expectedLocations, actualLocations);
    }

    @Test
    public void testSearchLocationByCity() {
        String id = null;
        String city = "Jakarta";
        String branchLocation = null;

        List<Location> expectedLocations = new ArrayList<>();
        Location location = new Location();
        location.setCity("Jakarta");
        expectedLocations.add(location);

        when(query.list()).thenReturn(expectedLocations);
        
        List<Location> actualLocations = locationDAO.searchLocation(id, city, branchLocation);
        verify(session).createQuery("from Location where 1=1 and city = :city");
        verify(query).setParameter("city", city);
        assertEquals(expectedLocations, actualLocations);
    }

    @Test
    public void testSearchLocationByBranchLocation() {
        String id = null;
        String city = null;
        String branchLocation = "Branch A";

        List<Location> expectedLocations = new ArrayList<>();
        Location location = new Location();
        location.setBranchLocation("Branch A");
        expectedLocations.add(location);

        when(query.list()).thenReturn(expectedLocations);
        
        List<Location> actualLocations = locationDAO.searchLocation(id, city, branchLocation);
        verify(session).createQuery("from Location where 1=1 and branchLocation = :branchLocation");
        verify(query).setParameter("branchLocation", branchLocation);
        assertEquals(expectedLocations, actualLocations);
    }

    @Test
    public void testSearchLocationEmptyList() {
        String id = null;
        String city = null;
        String branchLocation = null;

        List<Location> emptyList = new ArrayList<>();
        when(query.list()).thenReturn(emptyList);
        
        List<Location> actualLocations = locationDAO.searchLocation(id, city, branchLocation);
        verify(session).createQuery("from Location where 1=1");
        assertTrue(actualLocations.isEmpty());
    }

    @Test
    public void testSearchLocationException() {
        String id = "1";
        String city = null;
        String branchLocation = null;
        when(session.createQuery(anyString())).thenThrow(new RuntimeException("Database error"));

        List<Location> actualLocations = locationDAO.searchLocation(id, city, branchLocation);
        assertTrue(actualLocations.isEmpty());
    }
    
    @Test
    public void testSearchLocationWithAllParameters() {
        String id = "1";
        String city = "Jakarta";
        String branchLocation = "Central";
        Query query = mock(Query.class);
        List<Location> expectedLocations = new ArrayList<>();
        expectedLocations.add(new Location()); 
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter("id", Integer.parseInt(id))).thenReturn(query);
        when(query.setParameter("city", city)).thenReturn(query);
        when(query.setParameter("branchLocation", branchLocation)).thenReturn(query);
        when(query.list()).thenReturn(expectedLocations);
        List<Location> result = locationDAO.searchLocation(id, city, branchLocation);
        assertEquals(1, result.size());
        verify(session).createQuery(anyString());
        verify(query).setParameter("id", Integer.parseInt(id));
        verify(query).setParameter("city", city);
        verify(query).setParameter("branchLocation", branchLocation);
        verify(query).list();
    }

    @Test
    public void testSearchLocationWithNoParameters() {
        String id = null;
        String city = null;
        String branchLocation = null;
        Query query = mock(Query.class);
        List<Location> expectedLocations = new ArrayList<>();

        // Mengonfigurasi perilaku query
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.list()).thenReturn(expectedLocations);

        // Panggil metode searchLocation
        List<Location> result = locationDAO.searchLocation(id, city, branchLocation);

        // Verifikasi hasil
        assertEquals(0, result.size());
        verify(session).createQuery(anyString());
        verify(query, never()).setParameter(anyString(), any());
        verify(query).list();
    }

    @Test
    public void testSearchLocationWithSomeParameters() {

        String id = "1";
        String city = null;
        String branchLocation = "Central";

        Query query = mock(Query.class);
        List<Location> expectedLocations = new ArrayList<>();
        expectedLocations.add(new Location()); 

        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter("id", Integer.parseInt(id))).thenReturn(query);
        when(query.setParameter("branchLocation", branchLocation)).thenReturn(query);
        when(query.list()).thenReturn(expectedLocations);

        List<Location> result = locationDAO.searchLocation(id, city, branchLocation);

        assertEquals(1, result.size());
        verify(session).createQuery(anyString());
        verify(query).setParameter("id", Integer.parseInt(id));
        verify(query).setParameter("branchLocation", branchLocation);
        verify(query).list();
    }

}
