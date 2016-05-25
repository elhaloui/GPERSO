package gperso.controllers;

import gperso.controllers.PersonnelController;
import gperso.helpers.FxInitializable;
import gperso.helpers.notifications.LangProperties;
import gperso.helpers.notifications.LangSource;
import gperso.helpers.notifications.ValidatorMessages;
import gperso.controllers.stages.SecondStageController;
import gperso.models.Conge;
import gperso.services.ServiceOfConge;
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
public class NewCongeController implements FxInitializable {
    @FXML
    private TextField txtIdConge;
    @FXML
    private DatePicker txtDateDemande;
    @FXML
    private DatePicker txtDebutConge;
    @FXML
    private DatePicker txtFinConge;
    @FXML
    private TextField txtNatureConge;
    @FXML
    private TextField txtContenuConge;
    @FXML
    private TextArea txtCommentaireChef;
    @FXML
    private TextField txtEtatConge;
    
    
    
    
    @FXML
    private Button btnSave;

    private ValidationSupport validator;
    private ServiceOfConge serviceConge;
    private MessageSource messageSource;
    private ApplicationContext springContext;
    private ValidatorMessages validatorMessages;
    private LangSource lang;
    private PersonnelController PersonnelController ;
    private boolean isUpdate;
    private Conge anConge ;

    private void initValidator() {
        this.validator = new ValidationSupport();
        this.validator.registerValidator(txtDateDemande, true, Validator.createEmptyValidator(
                validatorMessages.validatorNotNull(lang.getSources(LangProperties.NAME)), Severity.ERROR));
        this.validator.registerValidator(txtDebutConge, false, Validator.createEmptyValidator(
                validatorMessages.validatorNotNull(lang.getSources(LangProperties.CONTACT_PERSON)), Severity.ERROR));
        this.validator.registerValidator(txtFinConge, false, Validator.createEmptyValidator(
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
        anConge.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        anConge.setDebutConge(Date.valueOf(txtDateDemande.getValue()));
        anConge.setDateDemande(Date.valueOf(txtDebutConge.getValue()));
        anConge.setFinConge(Date.valueOf(txtFinConge.getValue()));
        anConge.setNatureConge(txtNatureConge.getText());
        anConge.setContenuConge(txtContenuConge.getText());
        anConge.setCommentaireChef(txtCommentaireChef.getText());
        anConge.setEtatConge(txtEtatConge.getText());
        
        if(this.isUpdate)
        {
            try {
                serviceConge.update(anConge);
            } catch (Exception ex) {
                Logger.getLogger(NewCongeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else
        {
          try {
            anConge.setPersonnel(PersonnelController.getSelectedPersonnel());
            serviceConge.save(anConge);
            PersonnelController.getSelectedPersonnel().getConges().add(anConge);
            PersonnelController.showFields();
        } catch (Exception ex) {
            Logger.getLogger(NewCongeController.class.getName()).log(Level.SEVERE, null, ex);
        }  
        }
        
        
        
        doClose();
        
    }

    @Autowired
    public void setService(ServiceOfConge serviceConge) {
        this.serviceConge = serviceConge;
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
    
    
    public void exitsData(Conge anConge) {
        initValidator();
        this.anConge = anConge;
        setIsUpdate(true);
        showToFields(anConge);
       this.validator.redecorate();
    }
    
    private void showToFields(Conge anConge) {
        txtIdConge.setText(anConge.getId().toString());
        txtDateDemande.setValue(anConge.getDateDemande().toLocalDate());
        txtDateDemande.setValue(anConge.getDateDemande().toLocalDate());
        txtDateDemande.setValue(anConge.getDateDemande().toLocalDate());
        
    }
    
    public void newData() {
        initValidator();
        this.anConge = new Conge();
        isUpdate=false;
        clearFields();
        this.validator.redecorate();
    }

    private void clearFields() {
        txtIdConge.clear();
        txtDateDemande.setValue(LocalDate.now());
        txtDebutConge.setValue(LocalDate.now());
        txtFinConge.setValue(LocalDate.now());
        txtNatureConge.clear();
        txtCommentaireChef.clear();
        txtContenuConge.clear();
        txtEtatConge.clear();
    }
    
   
    
}
