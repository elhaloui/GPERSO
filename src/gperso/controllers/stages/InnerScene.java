package gperso.controllers.stages;

import gperso.controllers.AbsenceController;
import gperso.controllers.AbsenceDataController;
import gperso.controllers.CongeController;
import gperso.controllers.CongeDataController;
import gperso.controllers.FormationController;
import gperso.controllers.FormationDataController;
import gperso.controllers.PersonnelController;
import gperso.controllers.PersonnelDataController;
import gperso.controllers.PosteController;
import gperso.controllers.PosteDataController;
import gperso.controllers.ServiceController;
import gperso.controllers.ServiceDataController;
import gperso.controllers.AccountController;
import gperso.controllers.AccountDataController;
import gperso.controllers.PunitionController;
import gperso.controllers.PunitionDataController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by dimmaryanto on 10/1/15.
 */
@Component
public class InnerScene {

    private Initializable init;

    private Node node;

    private ResourceBundle resourceBundle;

    private Stage primaryStage;

    @Autowired
    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @Autowired
    @Qualifier("primaryStage")
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Initializable getInit() {
        return init;
    }

    private void setInit(Initializable init) {
        this.init = init;
    }

    public Node getNode() {
        return node;
    }

    private void setNode(Node node) {
        this.node = node;
    }

    private void setContentInitialize(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.setResources(resourceBundle);
        this.setNode(loader.load());
        setInit(loader.getController());
    }

    
    @Bean
    @Scope("prototype")
    public PersonnelController personnelController(@Qualifier("PersonnelController")  @Value("/stage/scene/personnel.fxml") String fxml) throws IOException {
        setContentInitialize(fxml);
        return (PersonnelController) getInit();
    }
    @Bean
    @Scope("prototype")
    public ServiceController serviceController(@Qualifier("ServiceController")  @Value("/stage/scene/service.fxml") String fxml) throws IOException {
        setContentInitialize(fxml);
        return (ServiceController) getInit();
    }
    @Bean
    @Scope("prototype")
    public CongeController congeController(@Qualifier("CongeController")  @Value("/stage/scene/conge.fxml") String fxml) throws IOException {
        setContentInitialize(fxml);
        return (CongeController) getInit();
    }
    @Bean
    @Scope("prototype")
    public FormationController formationController(@Qualifier("FormationController")  @Value("/stage/scene/formation.fxml") String fxml) throws IOException {
        setContentInitialize(fxml);
        return (FormationController) getInit();
    }
    @Bean
    @Scope("prototype")
    public PosteController posteController ( @Qualifier("PosteController")  @Value("/stage/scene/poste.fxml") String fxml) throws IOException {
        setContentInitialize(fxml);
        return (PosteController) getInit();
    }

    
    @Bean
    @Scope("prototype")
    public PersonnelDataController personnelDataController(@Qualifier("PersonnelDataController")  @Value("/stage/scene/personnel_data.fxml") String fxml)
            throws IOException {
        setContentInitialize(fxml);
        return (PersonnelDataController) getInit();
    }
    @Bean
    @Scope("prototype")
    public ServiceDataController serviceData(@Qualifier("ServiceDataController")  @Value("/stage/scene/service_data.fxml") String fxml)
            throws IOException {
        setContentInitialize(fxml);
        return (ServiceDataController) getInit();
    }
    @Bean
    @Scope("prototype")
    public CongeDataController congeData(@Qualifier("CongeDataController")  @Value("/stage/scene/conge_data.fxml") String fxml)
            throws IOException {
        setContentInitialize(fxml);
        return (CongeDataController) getInit();
    }
    @Bean
    @Scope("prototype")
    public FormationDataController formationData(@Qualifier("FormationDataController")  @Value("/stage/scene/formation_data.fxml") String fxml)
            throws IOException {
        setContentInitialize(fxml);
        return (FormationDataController) getInit();
    }
    @Bean
    @Scope("prototype")
    public PosteDataController postenData(@Qualifier("PosteDataController")  @Value("/stage/scene/poste_data.fxml") String fxml)
            throws IOException {
        setContentInitialize(fxml);
        return (PosteDataController) getInit();
    }

    
    
    @Bean
    @Scope("prototype")
    public AccountController accounts(@Qualifier("AccountController")  @Value("/stage/scene/account.fxml") String fxml) throws IOException {
        setContentInitialize(fxml);
        return (AccountController) getInit();
    }

    @Bean
    @Scope("prototype")
    public AccountDataController accountData(@Qualifier("AccountDataController")  @Value("/stage/scene/account_data.fxml") String fxml)
            throws IOException {
        setContentInitialize(fxml);
        return (AccountDataController) getInit();
    }

    @Bean
    @Scope("prototype")
    public AbsenceController absenceController(@Qualifier("AbsenceController")  @Value("/stage/scene/absence.fxml") String fxml) throws IOException {
        setContentInitialize(fxml);
        return (AbsenceController) getInit();
    }

    @Bean
    @Scope("prototype")
    public AbsenceDataController absenceDataController(@Qualifier("AbsenceDataController")  @Value("/stage/scene/absence_data.fxml") String fxml)
            throws IOException {
        setContentInitialize(fxml);
        return (AbsenceDataController) getInit();
    }
    
     @Bean
    @Scope("prototype")
    public PunitionController punitionController(@Qualifier("PunitionController")  @Value("/stage/scene/punition.fxml") String fxml) throws IOException {
        setContentInitialize(fxml);
        return (PunitionController) getInit();
    }

    @Bean
    @Scope("prototype")
    public PunitionDataController punitionDataController(@Qualifier("PunitionDataController")  @Value("/stage/scene/punition_data.fxml") String fxml)
            throws IOException {
        setContentInitialize(fxml);
        return (PunitionDataController) getInit();
    }
    
    
   
}
