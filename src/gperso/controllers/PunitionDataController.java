package gperso.controllers;

import gperso.helpers.notifications.ValidatorMessages;
import gperso.helpers.notifications.LangProperties;
import gperso.helpers.notifications.DialogWindows;
import gperso.helpers.notifications.LangSource;
import gperso.helpers.notifications.DialogBalloon;
import gperso.helpers.FxInitializable;
import gperso.controllers.stages.InnerScene;
import gperso.models.Personnel;
import gperso.models.Punition;
import gperso.services.ServiceOfPersonnel;
import gperso.services.ServiceOfPunition;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Callback;
import org.controlsfx.control.textfield.AutoCompletionBinding;

/**
 * Created by dimmaryanto on 07/10/15.
 */
public class PunitionDataController implements FxInitializable {

    @FXML
    private Button btnSave;
    @FXML
    private TextField txtIdPunition;
    @FXML
    private DatePicker txtDebutPunition;
    @FXML
    private DatePicker txtFinPunition;
    @FXML
    private TextField txtNaturePunition;
    @FXML
    private TextArea txtMotifPunition;
    @FXML
    private TextField txtPunisseur;
    @FXML 
    private TextField txtCinPersonnel;
    
   
    

    private InnerScene innerScene;
    private ServiceOfPunition absenceService;
    private ServiceOfPersonnel personnelService;
    private DialogWindows windows;
    private DialogBalloon ballon;
    private ValidationSupport validationSupport;
    private Boolean isUpdate;
    private Punition anPunition;
    private ApplicationContext springContext;
    private MessageSource messageSource;
    private ValidatorMessages validatorMessages;
    private LangSource lang;

    @Autowired
    public void setLang(LangSource lang) {
        this.lang = lang;
    }

    @Autowired
    public void setValidatorMessages(ValidatorMessages validatorMessages) {
        this.validatorMessages = validatorMessages;
    }

    @Autowired
    public void setInnerScene(InnerScene innerScene) {
        this.innerScene = innerScene;
    }

    @Autowired
    public void setPunitionService(ServiceOfPunition absenceService) {
        this.absenceService = absenceService;
    }
    
    @Autowired
    public void setPersonnelService(ServiceOfPersonnel personnelService) {
        this.personnelService = personnelService;
    }


    

    @Autowired
    public void setWindows(DialogWindows windows) {
        this.windows = windows;
    }

    

    @Autowired
    public void setBallon(DialogBalloon ballon) {
        this.ballon = ballon;
    }

    private void initValidator() {
  this.validationSupport = new ValidationSupport();
        validationSupport.invalidProperty().addListener((observable, oldValue, newValue) -> btnSave.setDisable(newValue));
        
       validationSupport.registerValidator(txtCinPersonnel, true, Validator.createEmptyValidator(
       validatorMessages.validatorNotNull(lang.getSources(LangProperties.NAME)), Severity.ERROR));
       validationSupport.registerValidator(txtPunisseur, true, Validator.createEmptyValidator(
       validatorMessages.validatorNotSelected(lang.getSources(LangProperties.JOB_NAME)), Severity.ERROR));
       validationSupport.registerValidator(txtMotifPunition, true, Validator.createEmptyValidator(
       validatorMessages.validatorNotSelected(lang.getSources(LangProperties.JOB_NAME)), Severity.ERROR));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
new AutoCompletionTextFieldBinding(txtCinPersonnel, new Callback<AutoCompletionBinding.ISuggestionRequest, Collection>() {
    @Override
    public Collection call(AutoCompletionBinding.ISuggestionRequest param) {
        System.out.println(param.getUserText());
        try {
            System.out.println(personnelService.findByCritere(param.getUserText()).size());
            return personnelService.findByCritere(param.getUserText());
        } catch (Exception ex) {
            Logger.getLogger(PunitionDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList();
    }
});
    }

   

    public void exitsData(Punition anPunition) {
        initValidator();
        this.anPunition = anPunition;
        setUpdate(true);
        showToFields(anPunition);
        this.validationSupport.redecorate();
    }

    public void newData() {
        initValidator();
        this.anPunition = new Punition();
        setUpdate(false);
        clearFields();
        this.validationSupport.redecorate();
    }

    @FXML
    private void doSave() {
        
        anPunition.setDebutPunition(Date.valueOf(txtDebutPunition.getValue()));
        anPunition.setFinPunition(Date.valueOf(txtFinPunition.getValue()));
        anPunition.setMotifPunition(txtMotifPunition.getText());
        anPunition.setNaturePunition(txtNaturePunition.getText());
        anPunition.setPersonnel(new Personnel(txtCinPersonnel.getText()));
        anPunition.setPunisseur(txtPunisseur.getText());
        if (isUpdate) {
            try {
                absenceService.update(anPunition);
                ballon.sucessedUpdated(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), lang.getSources(LangProperties.ID), anPunition.getId());
                doClose();
            } catch (Exception e) {
                e.printStackTrace();
                windows.errorUpdate(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), lang.getSources(LangProperties.ID), anPunition.getId(), e);
            }
        } else {
            try {
                absenceService.save(anPunition);
                ballon.sucessedSave(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anPunition.getId().toString());
                newData();
            } catch (Exception e) {
                e.printStackTrace();
                windows.errorSave(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anPunition.getId().toString(), e);
            }
        }
    }

    @Override
    public void doClose() {
        HomeController homeAction = springContext.getBean(HomeController.class);
        homeAction.showPunitions();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.springContext = applicationContext;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private void setUpdate(Boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    private void showToFields(Punition anPunition) {
        txtIdPunition.setText(anPunition.getId().toString());
        txtCinPersonnel.setText(anPunition.getPersonnel()==null?anPunition.getPersonnel().getCin() : "");
        txtDebutPunition.setValue(anPunition.getDebutPunition().toLocalDate());
        txtFinPunition.setValue(anPunition.getFinPunition().toLocalDate());
        txtNaturePunition.setText(anPunition.getNaturePunition());
        txtPunisseur.setText(anPunition.getPunisseur());
        txtMotifPunition.setText(anPunition.getMotifPunition());
        
        
    }

    private void clearFields() {
        txtIdPunition.clear();
        txtCinPersonnel.clear();
        txtDebutPunition.setValue(LocalDate.now());
        txtFinPunition.setValue(LocalDate.now());
        txtNaturePunition.clear();
        txtPunisseur.clear();
        txtMotifPunition.clear();
        
    }

    
}
