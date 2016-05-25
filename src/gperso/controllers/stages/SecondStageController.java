package gperso.controllers.stages;

import gperso.controllers.LoginController;
import gperso.controllers.RegisterAccountController;
import gperso.controllers.NewAdresseController;
import gperso.controllers.NewCongeController;
import gperso.controllers.NewMembreController;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by dimmaryanto on 12/10/15.
 */
@Component
public class SecondStageController {

    private StageRunner loader;
    private Stage primaryStage;
    private Stage secondStage;

    @Autowired
    public SecondStageController(StageRunner loader, Stage primaryStage, Stage secondStage) {
        this.loader = loader;
        this.primaryStage = primaryStage;
        this.secondStage = secondStage;
        secondStage.initModality(Modality.APPLICATION_MODAL);
        secondStage.initOwner(this.primaryStage);
        secondStage.initStyle(StageStyle.DECORATED);
    }

    public Stage getSecondStage() {
        return secondStage;
    }

    public void showStage() {
        this.secondStage.requestFocus();
        this.secondStage.setAlwaysOnTop(false);
        this.secondStage.setResizable(false);
        this.secondStage.centerOnScreen();
        this.secondStage.show();
    }

    public void closeSecondStage() {
        this.secondStage.close();
    }

    @Bean
    @Scope("prototype")
    public LoginController showLogin(@Value("/stage/scene/login.fxml") String fxml) {
        LoginController action = (LoginController) loader.getController(getClass(), secondStage, fxml);
        secondStage.requestFocus();
        secondStage.setAlwaysOnTop(true);
        showStage();
        return action;
    }

    
    @Bean
    @Scope("prototype")
    public NewAdresseController newAdresse(
            @Value("/stage/scene/new_adresse.fxml") String fxml) {
        NewAdresseController action = (NewAdresseController) loader.getController(getClass(), secondStage, fxml);
        showStage();
        return action;
    }
    
    @Bean
    @Scope("prototype")
    public NewMembreController newMembre(
            @Value("/stage/scene/new_membre.fxml") String fxml) {
        NewMembreController action = (NewMembreController) loader.getController(getClass(), secondStage, fxml);
        showStage();
        return action;
    }
    
    @Bean
    @Scope("prototype")
    public NewCongeController newConge(
            @Value("/stage/scene/new_conge.fxml") String fxml) {
        NewCongeController action = (NewCongeController) loader.getController(getClass(), secondStage, fxml);
        showStage();
        return action;
    }

    @Bean
    @Scope("prototype")
    public RegisterAccountController registerAccount(
            @Value("/stage/scene/register_account.fxml") String fxml) {
        RegisterAccountController accountAction = (RegisterAccountController) loader.getController(getClass(),
                secondStage, fxml);
        showStage();
        return accountAction;
    }

}
