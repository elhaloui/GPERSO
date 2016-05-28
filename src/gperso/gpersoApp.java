package gperso;
import gperso.controllers.HomeController;
import gperso.config.ApplicationConfigurer;
import gperso.helpers.AutheticationLevel;
import gperso.models.Account;
import gperso.models.Level;
import gperso.services.ServiceOfAccount;
import gperso.utile.FxDialogs;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 *.
 */
public class gpersoApp extends Application {

    private static ApplicationContext context;
   public static void main(String[] args) {
        launch(gpersoApp.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        Task<Object> worker = getTask();
        worker.setOnFailed(event -> {
           FxDialogs.showException("L'application a recontrer un erreur", "Veuillez Contactez l'administtrateur", (Exception) worker.getException());
            System.exit(0);
        });

        worker.setOnSucceeded(event -> {
            
            HomeController home = context.getBean(HomeController.class);
            home.doLogout();
            try {
                ServiceOfAccount serviceAccount = context.getBean(ServiceOfAccount.class);
                Boolean adminAvailabel = false;
                Iterator<Account> values = serviceAccount.findAll().iterator();
                while (values.hasNext()) {
                    Account account = values.next();
                    if (account.getLevel().equals(AutheticationLevel.getValue(Level.ADMIM))&& account.getUsername().equals("admin")) {
                        adminAvailabel = true;
                     }
                }
                if (!adminAvailabel) {
                    Account anAccount = new Account();
                    anAccount.setUsername("admin");
                    anAccount.setPasswd("admin");
                    anAccount.setActive(true);
                    anAccount.setFullname("Administrator");
                    anAccount.setLevel(Level.ADMIM.getValue());
                    anAccount.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
                    serviceAccount.save(anAccount);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

       
   //         Locale.setDefault(Locale.getDefault());
            worker.run();
        


    }

    private Task<Object> getTask() {
        return new Task<Object>() {
            Integer workDone;
            Integer workMax;

            public void setWorkDone(Integer workDone) {
                this.workDone = workDone;
            }

            public void setWorkMax(Integer workMax) {
                this.workMax = workMax;
            }

            @Override
            protected void setException(Throwable t) {
                super.setException(t);
            }

            @Override
            protected Object call() throws Exception {
                context = new AnnotationConfigApplicationContext(ApplicationConfigurer.class);
                String[] names = context.getBeanDefinitionNames();
                setWorkMax(context.getBeanDefinitionCount());
                for (int i = 0; i < workMax; i++) {
                    setWorkDone(i);
                    updateProgress(workDone, workMax - 1);
                    updateMessage("Configuartion done : " + names[workDone]);
                    System.out.println(getMessage());
                    Thread.sleep(1);
                }
                succeeded();
                return null;
            }

        };
    }
}
