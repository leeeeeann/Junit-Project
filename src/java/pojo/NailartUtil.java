/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Aspire 7
 */
public class NailartUtil {
    private static SessionFactory sessionFactory;

    static {
        configureSessionFactory(null);  // Gunakan file default jika tidak ada input
    }

    // Constructor tambahan untuk keperluan testing
    public NailartUtil(String configFile) {
        NailartUtil.configureSessionFactory(configFile); // Memanggil metode statis
    }

    private static void configureSessionFactory(String configFile) {
        try {
            Configuration configuration = new Configuration();
            if (configFile != null) {
                System.out.println("Menggunakan file konfigurasi khusus: " + configFile);
                configuration.configure(configFile); // Gunakan file konfigurasi yang diberikan
            } else {
                System.out.println("Menggunakan konfigurasi default");
                configuration.configure();  // Gunakan konfigurasi default
            }
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex.getMessage());
            ex.printStackTrace(); // Tambahkan stack trace untuk debugging
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static void setSessionFactory(SessionFactory sessionFactory) {
    NailartUtil.sessionFactory = sessionFactory;
}

    public static HttpSession getSession() {
    return (HttpSession)FacesContext.getCurrentInstance()
            .getExternalContext().getSession(false);
}
}
