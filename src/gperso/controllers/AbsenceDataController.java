package gperso.controllers;

import gperso.helpers.notifications.ValidatorMessages;
import gperso.helpers.notifications.LangProperties;
import gperso.helpers.notifications.DialogWindows;
import gperso.helpers.notifications.LangSource;
import gperso.helpers.notifications.DialogBalloon;
import gperso.helpers.FxInitializable;
import gperso.controllers.stages.InnerScene;
import gperso.models.Absence;
import gperso.services.ServiceOfAbsence;
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
public class AbsenceDataController implements FxInitializable {

    @FXML
    private Button btnSave;
    @FXML
    private TextField txtIdAbsence;
    @FXML
    private DatePicker txtDateAbsence;
    @FXML
    private Spinner<Integer> txtDureeAbsence;
    @FXML
    private TextArea txtMotifAbsence;
    @FXML
    private TextArea txtDecisionChef;
    
   
    

    private InnerScene innerScene;
    private ServiceOfAbsence absenceService;
    private DialogWindows windows;
    private DialogBalloon ballon;
    private ValidationSupport validationSupport;
    private Boolean isUpdate;
    private Absence anAbsence;
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
    public void setAbsenceService(ServiceOfAbsence absenceService) {
        this.absenceService = absenceService;
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
        
       validationSupport.registerValidator(txtDateAbsence, true, Validator.createEmptyValidator(
       validatorMessages.validatorNotNull(lang.getSources(LangProperties.NAME)), Severity.ERROR));
       validationSupport.registerValidator(txtDureeAbsence, true, Validator.createEmptyValidator(
       validatorMessages.validatorNotSelected(lang.getSources(LangProperties.JOB_NAME)), Severity.ERROR));
       validationSupport.registerValidator(txtMotifAbsence, true, Validator.createEmptyValidator(
       validatorMessages.validatorNotSelected(lang.getSources(LangProperties.JOB_NAME)), Severity.ERROR));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

      txtDureeAbsence.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory( 0, Integer.MAX_VALUE));
           
    }

   

    public void exitsData(Absence anAbsence) {
        initValidator();
        this.anAbsence = anAbsence;
        setUpdate(true);
        showToFields(anAbsence);
        this.validationSupport.redecorate();
    }

    public void newData() {
        initValidator();
        this.anAbsence = new Absence();
        setUpdate(false);
        clearFields();
        this.validationSupport.redecorate();
    }

    @FXML
    private void doSave() {
        
        anAbsence.setDateAbsence(Date.valueOf(txtDateAbsence.getValue()));
        anAbsence.setDecisionChef(txtDecisionChef.getText());
        anAbsence.setDureeAbsence(txtDureeAbsence.getValue());
        anAbsence.setMotifAbsence(txtMotifAbsence.getText());
        if (isUpdate) {
            try {
                absenceService.update(anAbsence);
                ballon.sucessedUpdated(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), lang.getSources(LangProperties.ID), anAbsence.getId());
                doClose();
            } catch (Exception e) {
                e.printStackTrace();
                windows.errorUpdate(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), lang.getSources(LangProperties.ID), anAbsence.getId(), e);
            }
        } else {
            try {
                absenceService.save(anAbsence);
                ballon.sucessedSave(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anAbsence.getId().toString());
                newData();
            } catch (Exception e) {
                e.printStackTrace();
                windows.errorSave(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anAbsence.getId().toString(), e);
            }
        }
    }

    @Override
    public void doClose() {
        HomeController homeAction = springContext.getBean(HomeController.class);
        homeAction.showAbsences();
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

    private void showToFields(Absence anAbsence) {
        txtIdAbsence.setText(anAbsence.getId().toString());
        txtDateAbsence.setValue(anAbsence.getDateAbsence().toLocalDate());
        txtDecisionChef.setText(anAbsence.getDecisionChef());
        txtDureeAbsence.getValueFactory().setValue(anAbsence.getDureeAbsence());
        txtMotifAbsence.setText(anAbsence.getMotifAbsence());
        
        
    }

    private void clearFields() {
        txtIdAbsence.clear();
        txtDateAbsence.setValue(LocalDate.now());
        txtDecisionChef.clear();
        txtDureeAbsence.getValueFactory().setValue(0);
        txtMotifAbsence.clear();
        
    }

    
}
