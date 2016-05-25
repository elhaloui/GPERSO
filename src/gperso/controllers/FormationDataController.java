package gperso.controllers;

import gperso.helpers.notifications.DialogBalloon;
import gperso.helpers.notifications.LangSource;
import gperso.helpers.notifications.DialogWindows;
import gperso.helpers.notifications.LangProperties;
import gperso.helpers.notifications.ValidatorMessages;
import gperso.controllers.HomeController;
import gperso.helpers.FxInitializable;
import gperso.controllers.stages.InnerScene;
import gperso.models.Formation;
import gperso.services.ServiceOfFormation;
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
import java.util.ResourceBundle;

/**
 * Created by dimmaryanto on 07/10/15.
 */
public class FormationDataController implements FxInitializable {

    @FXML
    private Button btnSave;
    @FXML
    private TextField txtIdFormation;
    @FXML
    private TextField txtNatureFormation;
    @FXML
    private DatePicker txtDebutFormation;
    @FXML
    private DatePicker txtFinFormation;
    @FXML
    private TextField txtLieuFormation;
    @FXML
    private TextField txtDiplomeFormation;
    @FXML
    private TextField txtObservationFormation;
   
    

    private InnerScene innerScene;
    private ServiceOfFormation formationService;
    private DialogWindows windows;
    private DialogBalloon ballon;
    private ValidationSupport validationSupport;
    private Boolean isUpdate;
    private Formation anFormation;
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
    public void setFormationService(ServiceOfFormation formationService) {
        this.formationService = formationService;
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
        
        validationSupport.registerValidator(txtNatureFormation, true, Validator.createEmptyValidator(
        validatorMessages.validatorNotNull(lang.getSources(LangProperties.NAME)), Severity.ERROR));
        
        validationSupport.registerValidator(txtDebutFormation, true, Validator.createEmptyValidator(
       validatorMessages.validatorNotSelected(lang.getSources(LangProperties.JOB_NAME)), Severity.ERROR));
       validationSupport.registerValidator(txtFinFormation, true, Validator.createEmptyValidator(
       validatorMessages.validatorNotSelected(lang.getSources(LangProperties.JOB_NAME)), Severity.ERROR));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        
    }

   

    public void exitsData(Formation anFormation) {
        initValidator();
        this.anFormation = anFormation;
        setUpdate(true);
        showToFields(anFormation);
        this.validationSupport.redecorate();
    }

    public void newData() {
        initValidator();
        this.anFormation = new Formation();
        setUpdate(false);
        clearFields();
        this.validationSupport.redecorate();
    }

    @FXML
    private void doSave() {
        
        anFormation.setNatureFormation(txtNatureFormation.getText());
        anFormation.setDebutFormation(Date.valueOf(txtDebutFormation.getValue()));
        anFormation.setFinFormation(Date.valueOf(txtFinFormation.getValue()));
        anFormation.setLieuFormation(txtLieuFormation.getText());
        anFormation.setDiplomeFormation(txtDiplomeFormation.getText());
        anFormation.setObservationFormation(txtObservationFormation.getText());
        if (isUpdate) {
            try {
                formationService.update(anFormation);
                ballon.sucessedUpdated(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), lang.getSources(LangProperties.ID), anFormation.getId());
                doClose();
            } catch (Exception e) {
                e.printStackTrace();
                windows.errorUpdate(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), lang.getSources(LangProperties.ID), anFormation.getId(), e);
            }
        } else {
            try {
                formationService.save(anFormation);
                ballon.sucessedSave(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anFormation.getNatureFormation());
                newData();
            } catch (Exception e) {
                e.printStackTrace();
                windows.errorSave(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anFormation.getNatureFormation(), e);
            }
        }
    }

    @Override
    public void doClose() {
        HomeController homeAction = springContext.getBean(HomeController.class);
        homeAction.showFormations();
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

    private void showToFields(Formation anFormation) {
        txtIdFormation.setText(anFormation.getId().toString());
        txtNatureFormation.setText(anFormation.getNatureFormation());
        txtDebutFormation.setValue(anFormation.getDebutFormation().toLocalDate());
        txtFinFormation.setValue(anFormation.getFinFormation().toLocalDate());
        txtLieuFormation.setText(anFormation.getLieuFormation());
        txtDiplomeFormation.setText(anFormation.getDiplomeFormation());
        txtObservationFormation.setText(anFormation.getObservationFormation());
        
    }

    private void clearFields() {
        txtIdFormation.clear();
        txtNatureFormation.clear();
        txtDebutFormation.setValue(LocalDate.now());
        txtFinFormation.setValue(LocalDate.now());
        txtLieuFormation.clear();
        txtDiplomeFormation.clear();
        txtObservationFormation.clear();
        
    }

    
}
