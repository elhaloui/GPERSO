package gperso.controllers;

import gperso.controllers.PersonnelController;
import gperso.helpers.FxInitializable;
import gperso.helpers.notifications.LangProperties;
import gperso.helpers.notifications.LangSource;
import gperso.helpers.notifications.ValidatorMessages;
import gperso.controllers.stages.SecondStageController;
import gperso.models.Adresse;
import gperso.services.ServiceOfAdresse;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dimmaryanto on 06/11/15.
 */
public class NewAdresseController implements FxInitializable {
    @FXML
    private TextField txtLieu;
    @FXML
    private TextField txtVille;
    @FXML
    private Button btnSave;

    private ValidationSupport validator;
    private ServiceOfAdresse service;
    private MessageSource messageSource;
    private ApplicationContext springContext;
    private ValidatorMessages validatorMessages;
    private LangSource lang;
    private PersonnelController PersonnelController ;
    private boolean isUpdate;
    private Adresse anAdresse ;

    private void initValidator() {
        this.validator = new ValidationSupport();
        this.validator.registerValidator(txtLieu, true, Validator.createEmptyValidator(
                validatorMessages.validatorNotNull(lang.getSources(LangProperties.NAME)), Severity.ERROR));
        this.validator.registerValidator(txtVille, false, Validator.createEmptyValidator(
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
        anAdresse.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        anAdresse.setLieu(txtLieu.getText());
        anAdresse.setVille(txtVille.getText());
        if(this.isUpdate)
        {
            try {
                service.update(anAdresse);
            } catch (Exception ex) {
                Logger.getLogger(NewAdresseController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else
        {
          try {
            anAdresse.setPersonnel(PersonnelController.getSelectedPersonnel());
            service.save(anAdresse);
            PersonnelController.getSelectedPersonnel().getAdresses().add(anAdresse);
            PersonnelController.showFields();
        } catch (Exception ex) {
            Logger.getLogger(NewAdresseController.class.getName()).log(Level.SEVERE, null, ex);
        }  
        }
        
        
        
        doClose();
        
    }

    @Autowired
    public void setService(ServiceOfAdresse service) {
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
    
    
    public void exitsData(Adresse anAdresse) {
        initValidator();
        this.anAdresse = anAdresse;
        setIsUpdate(true);
        showToFields(anAdresse);
       this.validator.redecorate();
    }
    
    private void showToFields(Adresse anAdresse) {
        txtLieu.setText(anAdresse.getLieu());
        txtVille.setText(anAdresse.getVille());
    }
    
    public void newData() {
        initValidator();
        this.anAdresse = new Adresse();
        isUpdate=false;
        clearFields();
        this.validator.redecorate();
    }

    private void clearFields() {
        txtLieu.setText("");
        txtVille.setText("");
    }
    
   
    
}
