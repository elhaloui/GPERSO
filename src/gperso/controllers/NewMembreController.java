package gperso.controllers;

import gperso.controllers.PersonnelController;
import gperso.helpers.FxInitializable;
import gperso.helpers.notifications.LangProperties;
import gperso.helpers.notifications.LangSource;
import gperso.helpers.notifications.ValidatorMessages;
import gperso.controllers.stages.SecondStageController;
import gperso.models.Membre;
import gperso.services.ServiceOfMembre;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dimmaryanto on 06/11/15.
 */
public class NewMembreController implements FxInitializable {
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML 
    private TextField txtDegre;
    @FXML
    private DatePicker dateNaissance;
    
    @FXML TextField txtFonction;
    
    
    @FXML
    private Button btnSave;

    private ValidationSupport validator;
    private ServiceOfMembre service;
    private MessageSource messageSource;
    private ApplicationContext springContext;
    private ValidatorMessages validatorMessages;
    private LangSource lang;
    private PersonnelController PersonnelController ;
    private boolean isUpdate;
    private Membre anMembre ;

    private void initValidator() {
        this.validator = new ValidationSupport();
        this.validator.registerValidator(txtNom, true, Validator.createEmptyValidator(
                validatorMessages.validatorNotNull(lang.getSources(LangProperties.NAME)), Severity.ERROR));
        this.validator.registerValidator(txtPrenom, false, Validator.createEmptyValidator(
                validatorMessages.validatorNotNull(lang.getSources(LangProperties.CONTACT_PERSON)), Severity.ERROR));
        
    }

    @FXML
    @Override
    public void doClose() {
        SecondStageController controller = springContext.getBean(SecondStageController.class);
        controller.closeSecondStage();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.springContext = applicationContext;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    

    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @FXML
    public void doSave() {
        anMembre.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        anMembre.setNom(txtNom.getText());
        anMembre.setPrenom(txtPrenom.getText());
        anMembre.setDateNaissance(Date.valueOf(dateNaissance.getValue()));
        anMembre.setDegreParente(txtDegre.getText());
        anMembre.setFonction(txtFonction.getText());
        if(this.isUpdate)
        {
            try {
                service.update(anMembre);
            } catch (Exception ex) {
                Logger.getLogger(NewMembreController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else
        {
          try {
            anMembre.setPersonnel(PersonnelController.getSelectedPersonnel());
            service.save(anMembre);
            PersonnelController.getSelectedPersonnel().getMembres().add(anMembre);
            PersonnelController.showFields();
        } catch (Exception ex) {
            Logger.getLogger(NewMembreController.class.getName()).log(Level.SEVERE, null, ex);
        }  
        }
        
        
        
        doClose();
        
    }

    @Autowired
    public void setService(ServiceOfMembre service) {
        this.service = service;
    }

    @Autowired
    public void setValidatorMessages(ValidatorMessages validatorMessages) {
        this.validatorMessages = validatorMessages;
    }

    @Autowired
    public void setLang(LangSource lang) {
        this.lang = lang;
    }

    public void init( PersonnelController aPersonnelController)
    {
    initValidator();
    this.validator.redecorate(); 
    PersonnelController=aPersonnelController;
    }

    public boolean isIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }
    
    
    public void exitsData(Membre anMembre) {
        initValidator();
        this.anMembre = anMembre;
        setIsUpdate(true);
        showToFields(anMembre);
       this.validator.redecorate();
    }
    
    private void showToFields(Membre anMembre) {
        txtNom.setText(anMembre.getNom());
        txtPrenom.setText(anMembre.getPrenom());
        txtDegre.setText(anMembre.getDegreParente());
        txtFonction.setText(anMembre.getFonction());
        dateNaissance.setValue(anMembre.getDateNaissance().toLocalDate());
    }
    
    public void newData() {
        initValidator();
        this.anMembre = new Membre();
        isUpdate=false;
        clearFields();
        this.validator.redecorate();
    }

    private void clearFields() {
        txtDegre.setText("");
        txtFonction.setText("");
        txtNom.setText("");
        txtPrenom.setText("");
        dateNaissance.setValue(LocalDate.now());
    }
    
   
    
}
