package gperso.controllers;

import gperso.helpers.FxInitializable;
import gperso.controllers.stages.InnerScene;
import gperso.models.Account;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *.
 */
public class HomeController implements FxInitializable {

    private InnerScene innerScene;
    private ApplicationContext springContext;
    private MessageSource messageSource;
    private Account account;

    @FXML
    private MenuItem mniChangeAccount;
    @FXML
    private MenuItem mniAccount;
    @FXML
    private Menu menuSettings;
    @FXML
    private MenuItem mnuRegisterAccount;
    @FXML
    private TitledPane menuMasterData;
    @FXML
    private BorderPane content;
    @FXML
    private Label txtUserId;
    /////////////////////////////////////::::::::
    @FXML
    private Button btnPersonnel;
    @FXML
    private Button btnFormation;
    @FXML
    private Button btnService;
    @FXML
    private Button btnConge;
    @FXML
    private Button btnPoste;
    @FXML
    private Button btnAbsence;
     @FXML
    private Button btnPunition;
     
    /////////////////::::::::::::::::::::::::::
    
    private Hyperlink logoutAction;
    @FXML
    private Label txtName;
    @FXML
    private Label txtLevel;
    @FXML
    private MenuItem mniLogin;
    @FXML
    private MenuItem mniLogout;
    @FXML
    private Text statusLeftUser;
    @FXML
    private Text statusRightUser;
    

    @Override
    public void doClose() {
        try {
            Platform.exit();
        } catch (Exception e) {
            System.exit(0);
            e.printStackTrace();
        }
    }
     @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.springContext = applicationContext;
    }

    @Autowired
    public void setInnerScene(InnerScene innerScene) {
        this.innerScene = innerScene;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

   
    public void updateContent() {
        this.content.setCenter(innerScene.getNode());
        this.content.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        expandedMenu(false);
//        logoutAction.setVisible(false);
        statusAccount(null);
    }

    public Account getAccount() {
        return account;
    }

    @FXML
    public void doLogout() {
        statusAccount(null);
        expandedMenu(false);
        this.content.setCenter(null);
    }

    @FXML
    public void showPersonnels() {
        PersonnelController action = springContext.getBean(PersonnelController.class);
         if (account != null && account.getActive() && account.getLevel().equals("PERSONNEL")) {
           action.columnAction.setVisible(false);
           action.btnAddPersonnel.setVisible(false);
           action.btnAddAdresse.setVisible(true);
           action.btnAddConge.setVisible(true);
           action.btnAddMembre.setVisible(true);
        }
         else if (account != null && account.getActive() && account.getLevel().equals("CHEF DE CORPS")) {
           action.btnAddAdresse.setVisible(false);
           action.btnAddConge.setVisible(false);
           action.btnAddMembre.setVisible(false);
           action.columnAction.setVisible(true);
           action.btnAddPersonnel.setVisible(true);
        }
        updateContent();
        action.loadData();
    }
    
     @FXML
    public void showServices() {
        ServiceController action = springContext.getBean(ServiceController.class);
        updateContent();
        action.loadData();
    }
     @FXML
    public void showConges() {
        CongeController action = springContext.getBean(CongeController.class);
        updateContent();
        action.loadData();
    }
    
    @FXML
    public void showFormations() {
        FormationController action = springContext.getBean(FormationController.class);
        if (account != null && account.getActive() && account.getLevel().equals("CHEF DE CORPS")) {
           action.PaneParticipation.setVisible(true);
        }
        updateContent();
        action.loadData();
    }
    
    @FXML
    public void showAbsences() {
        AbsenceController action = springContext.getBean(AbsenceController.class);
        updateContent();
        action.loadData();
    }
     @FXML
    public void showPunitions() {
        PunitionController action = springContext.getBean(PunitionController.class);
        updateContent();
        action.loadData();
    }
    
    @FXML
    public void showPostes() {
        PosteController action = springContext.getBean(PosteController.class);
        updateContent();
        action.loadData();
    }

    

    @FXML
    public void showAccounts() {
        AccountController account = springContext.getBean(AccountController.class);
        updateContent();
        account.loadData();
    }

    

    
      private void statusAccount(Account anAccount) {
        txtUserId.setVisible(anAccount != null);
        txtName.setVisible(anAccount != null);
        txtLevel.setVisible(anAccount != null);

        statusLeftUser.setVisible(anAccount != null);
        statusRightUser.setVisible(anAccount != null);

    //    logoutAction.setVisible(anAccount != null);

        mniLogout.setDisable(anAccount == null);
        mniLogin.setDisable(anAccount != null);

        if (anAccount != null) {
            txtUserId.setText(anAccount.getUsername());
            txtName.setText(anAccount.getFullname());
            txtLevel.setText(anAccount.getLevel());
        } else {
            txtUserId.setText("");
            txtName.setText("");
            txtLevel.setText("");
        }
    }

    private void expandedMenu(Boolean active) {
        enableMenuSettings(active);
        menuMasterData.setExpanded(active);
        setOpacityMenu(active);
    }

    private void opacityOfMenu(Double value) {
        menuMasterData.setOpacity(value);
        
     }

    private void setOpacityMenu(Boolean active) {
        if (active) {
            opacityOfMenu(1.0);
        } else {
            opacityOfMenu(0.3);
        }
    }

    private void enableMenuSettings(Boolean active) {
        mniAccount.setDisable(!active);
    }

    public void setLogin(Account anAccount) {
        this.account = anAccount;
        statusAccount(anAccount);
        enableMenuSettings(false);
        if (anAccount != null && anAccount.getActive() && anAccount.getLevel().equals("CHEF DE CORPS")) {
            enableMenuSettings(true);
            expandedMenu(true);
            enableMenuMasterData(true);
            btnAbsence.setVisible(true);
            btnConge.setVisible(true);
            btnPoste.setVisible(true);
            btnPunition.setVisible(true);
            btnService.setVisible(true);
        } else if (anAccount != null && anAccount.getLevel().equals("CHEF DE SERVICE")) {
            expandedMenu(false);
            //enable masterdata
            menuMasterData.setOpacity(1.0);
            btnAbsence.setVisible(false);
            btnConge.setVisible(false);
            btnPoste.setVisible(false);
            btnPunition.setVisible(false);
            btnService.setVisible(false);
        } else if (anAccount != null && anAccount.getLevel().equals("PERSONNEL")) {
            expandedMenu(false);
            enableMenuMasterData(true);
            menuMasterData.setOpacity(1.0);
            btnAbsence.setVisible(false);
            btnConge.setVisible(false);
            btnPoste.setVisible(false);
            btnPunition.setVisible(false);
            btnService.setVisible(false);
            
        } 
    }

    private void enableMenuMasterData(Boolean active) {
        btnPersonnel.setDisable(!active);
        btnFormation.setDisable(!active);
        btnService.setDisable(!active);
        btnConge.setDisable(!active);
        btnPoste.setDisable(!active);
        btnAbsence.setDisable(!active);
        btnPunition.setDisable(!active);
        
       
        
    }


   

    @FXML
    public void doLogin() {
        LoginController action = springContext.getBean(LoginController.class);
        this.content.setCenter(null);
        action.initValidator();
    }


    @FXML
    public void doRegisterAccount() {
        RegisterAccountController action = springContext.getBean(RegisterAccountController.class);
        action.initValidation();
    }

    @FXML
    public void showChangedAccount() {

    }


}
