package pojo;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class NailArtTest {

    public NailArtTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        System.out.println("Persiapan pengujian kelas...");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("Pengujian kelas selesai.");
    }

    @Before
    public void setUp() {
        System.out.println("Persiapan untuk setiap pengujian...");
    }

    @After
    public void tearDown() {
        System.out.println("Pengujian selesai untuk satu metode.");
    }
    @Test
    public void testGetSessionFactory() {
        System.out.println("Uji getSessionFactory dengan konfigurasi default");
        SessionFactory sessionFactory = NailArt.getSessionFactory(); // Perbaiki di sini
        assertNotNull("SessionFactory harus diinisialisasi", sessionFactory);
    }
    @Test
    public void testSessionFactoryConfiguration() {
        System.out.println("Uji konfigurasi SessionFactory");
        SessionFactory sessionFactory = NailArt.getSessionFactory(); // Perbaiki di sini
        assertNotNull("SessionFactory harus diinisialisasi", sessionFactory);
        assertTrue("Harus menjadi instance dari SessionFactory", sessionFactory instanceof SessionFactory);
    }
    @Test(expected = ExceptionInInitializerError.class)
    public void testSessionFactoryInitializationFailure() {
        System.out.println("Uji kegagalan inisialisasi SessionFactory dengan konfigurasi yang salah");
        NailArt nailArtFaulty = new NailArt("hibernate-wrong.cfg.xml");
    }
    @Test
    public void testSessionFactoryWithValidConfig() {
        System.out.println("Uji inisialisasi SessionFactory dengan konfigurasi yang valid");
        NailArt nailArtValid = new NailArt("hibernate.cfg.xml");
        SessionFactory sessionFactory = NailArt.getSessionFactory(); 
        assertNotNull("SessionFactory harus berhasil diinisialisasi dengan konfigurasi valid", sessionFactory);
    }
    
    @Test
    public void testConfigureSessionFactoryWithCustomConfig() {
        System.out.println("Uji inisialisasi SessionFactory dengan konfigurasi khusus");
        String customConfig = "hibernate.cfg.xml"; 
        NailArt nailArtCustom = new NailArt(customConfig);
        SessionFactory sessionFactory = NailArt.getSessionFactory();
        assertNotNull("SessionFactory harus berhasil diinisialisasi dengan konfigurasi khusus", sessionFactory);
        assertTrue("SessionFactory harus menjadi instance yang valid", sessionFactory instanceof SessionFactory);
    }

    @Test
    public void testConfigureSessionFactoryWithDefaultConfig() {
        System.out.println("Uji inisialisasi SessionFactory dengan konfigurasi default");
        SessionFactory sessionFactory = NailArt.getSessionFactory();
        assertNotNull("SessionFactory harus berhasil diinisialisasi dengan konfigurasi default", sessionFactory);
        assertTrue("SessionFactory harus menjadi instance yang valid", sessionFactory instanceof SessionFactory);
    }
}
