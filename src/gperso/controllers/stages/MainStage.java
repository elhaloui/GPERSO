package gperso.controllers.stages;

import gperso.controllers.HomeController;
import gperso.helpers.notifications.LangProperties;
import gperso.helpers.notifications.LangSource;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 *.
 */
@Component
public class MainStage implements ApplicationContextAware, MessageSourceAware {

    private Stage primaryStage;

    private StageRunner stageRunner;
    private LangSource lang;
    private MessageSource messageSource;
    private ApplicationContext applicationContext;

    @Autowired
    public void setLang(LangSource lang) {
        this.lang = lang;
    }

    @Autowired
    public void setStageRunner(StageRunner stageRunner) {
        this.stageRunner = stageRunner;
    }

    @Autowired
    public void setPrimaryStage(@Qualifier(value = "primaryStage") Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void setTitle(String value) {
        this.primaryStage.setTitle(value);
    }

    @Bean
    public HomeController homeAction() {
        
        HomeController home = (HomeController) stageRunner.getController(getClass(), this.primaryStage, "/stage/scene/home.fxml");
        setTitle("GPERSO - System de gestion des personnels ");
        this.primaryStage.setResizable(true);
        this.primaryStage.centerOnScreen();
        this.primaryStage.show();
        return home;
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        this.applicationContext = arg0;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
