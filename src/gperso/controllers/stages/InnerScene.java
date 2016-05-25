package gperso.controllers.stages;

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
    public PersonnelController personnels(@Value("/stage/scene/personnel.fxml") String fxml) throws IOException {
        setContentInitialize(fxml);
        return (PersonnelController) getInit();
    }
    @Bean
    @Scope("prototype")
    public ServiceController services(@Value("/stage/scene/service.fxml") String fxml) throws IOException {
        setContentInitialize(fxml);
        return (ServiceController) getInit();
    }
    @Bean
    @Scope("prototype")
    public CongeController conges(@Value("/stage/scene/conge.fxml") String fxml) throws IOException {
        setContentInitialize(fxml);
        return (CongeController) getInit();
    }
    @Bean
    @Scope("prototype")
    public FormationController formations(@Value("/stage/scene/formation.fxml") String fxml) throws IOException {
        setContentInitialize(fxml);
        return (FormationController) getInit();
    }
    @Bean
    @Scope("prototype")
    public PosteController postes (@Value("/stage/scene/oste.fxml") String fxml) throws IOException {
        setContentInitialize(fxml);
        return (PosteController) getInit();
    }

    
    @Bean
    @Scope("prototype")
    public PersonnelDataController personnelData(@Value("/stage/scene/personnel_data.fxml") String fxml)
            throws IOException {
        setContentInitialize(fxml);
        return (PersonnelDataController) getInit();
    }
    @Bean
    @Scope("prototype")
    public ServiceDataController serviceData(@Value("/stage/scene/service_data.fxml") String fxml)
            throws IOException {
        setContentInitialize(fxml);
        return (ServiceDataController) getInit();
    }
    @Bean
    @Scope("prototype")
    public CongeDataController congeData(@Value("/stage/scene/conge_data.fxml") String fxml)
            throws IOException {
        setContentInitialize(fxml);
        return (CongeDataController) getInit();
    }
    @Bean
    @Scope("prototype")
    public FormationDataController formationData(@Value("/stage/scene/ormation_data.fxml") String fxml)
            throws IOException {
        setContentInitialize(fxml);
        return (FormationDataController) getInit();
    }
    @Bean
    @Scope("prototype")
    public PosteDataController postenData(@Value("/stage/scene/poste_data.fxml") String fxml)
            throws IOException {
        setContentInitialize(fxml);
        return (PosteDataController) getInit();
    }

    
    
    @Bean
    @Scope("prototype")
    public AccountController accounts(@Value("/stage/scene/account.fxml") String fxml) throws IOException {
        setContentInitialize(fxml);
        return (AccountController) getInit();
    }

    @Bean
    @Scope("prototype")
    public AccountDataController accountData(@Value("/stage/scene/account_data.fxml") String fxml)
            throws IOException {
        setContentInitialize(fxml);
        return (AccountDataController) getInit();
    }

    
    
   
}
