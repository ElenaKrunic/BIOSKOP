package bioskopp;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import biosko.DAO.ConnectionManager;

/**
 * Application Lifecycle Listener implementation class InitL
 *
 */
public class InitL implements ServletContextListener {

  
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

    public void contextInitialized(ServletContextEvent event)  { 
         System.out.println("...inizicalizacija......");
         //connection manager ovde 
         ConnectionManager.open();
         System.out.println("ZAVRSENO!");
    }
	
}
