package gperso.controllers;

import gperso.helpers.notifications.DialogBalloon;
import gperso.helpers.notifications.LangSource;
import gperso.helpers.notifications.DialogWindows;
import gperso.helpers.notifications.LangProperties;
import gperso.helpers.notifications.ValidatorMessages;
import gperso.controllers.HomeController;
import gperso.helpers.FxInitializable;
import gperso.controllers.stages.InnerScene;
import gperso.models.Conge;
import gperso.services.ServiceOfConge;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
import javafx.scene.control.DatePicker;

/**
 * Created by dimmaryanto on 10/5/15.
 */
public class CongeDataController implements FxInitializable {


    @FXML
    private Button btnSave;
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

    private ServiceOfConge serviceConge;
    private DialogWindows windows;
    private DialogBalloon ballon;
    private InnerScene innerScene;
    private ValidationSupport validator;
    private ApplicationContext springContext;
    private MessageSource messageSource;
    private ValidatorMessages validatorMessages;
    private Conge conge;
    private boolean isUpdate;
    private LangSource lang;

    private void initValidator() {
        this.validator = new ValidationSupport();
        this.validator.invalidProperty().addListener((observable, oldValue, newValue) -> btnSave.setDisable(newValue));
       this.validator.registerValidator(txtNatureConge, true, Validator.createEmptyValidator(
                validatorMessages.validatorNotNull(lang.getSources(LangProperties.NAME)), Severity.ERROR));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void newData() {
        initValidator();
        setUpdate(false);
        this.conge = new Conge();
        clearFields();
        this.validator.redecorate();
    }

    public void ExitsData(Conge aConge) {
        initValidator();
        setUpdate(true);
        this.conge = aConge;
        showField(aConge);
        this.validator.redecorate();
    }

    @FXML
    private void doSave() {
        
        conge.setDateDemande(Date.valueOf(txtDateDemande.getValue()));
        conge.setDebutConge(Date.valueOf(txtDebutConge.getValue()));
        conge.setFinConge(Date.valueOf(txtFinConge.getValue()));
        conge.setNatureConge(txtNatureConge.getText());
        conge.setContenuConge(txtContenuConge.getText());
        conge.setCommentaireChef(txtCommentaireChef.getText());
        conge.setEtatConge(txtEtatConge.getText());
        
        if (isUpdate) {
            try {
                serviceConge.update(conge);
                ballon.sucessedUpdated(lang.getSources(LangProperties.DATA_A_DEPARTMENT), lang.getSources(LangProperties.ID), conge.getId());
                doClose();
            } catch (Exception e) {
                windows.errorUpdate(lang.getSources(LangProperties.DATA_A_DEPARTMENT), lang.getSources(LangProperties.ID), conge.getId(), e);
                e.printStackTrace();
            }
        } else {
            try {
                serviceConge.save(conge);
                ballon.sucessedSave(lang.getSources(LangProperties.DATA_A_DEPARTMENT), conge.getId().toString());
                newData();
            } catch (Exception e) {
                windows.errorSave(lang.getSources(LangProperties.DATA_A_DEPARTMENT), conge.getId().toString(), e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doClose() {
        HomeController homeAction = springContext.getBean(HomeController.class);
        homeAction.showConges();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.springContext = applicationContext;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setLang(LangSource lang) {
        this.lang = lang;
    }

    @Autowired
    public void setConge(ServiceOfConge serviceConge) {
        this.serviceConge = serviceConge;
    }

    @Autowired
    public void setWindows(DialogWindows windows) {
        this.windows = windows;
    }

    @Autowired
    public void setBallon(DialogBalloon ballon) {
        this.ballon = ballon;
    }

    @Autowired
    public void setInnerScene(InnerScene innerScene) {
        this.innerScene = innerScene;
    }

    @Autowired
    public void setValidatorMessages(ValidatorMessages validatorMessages) {
        this.validatorMessages = validatorMessages;
    }

    private void setUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    private void clearFields() {
        txtIdConge.clear();
        txtDateDemande.setValue(LocalDate.now());
        txtDebutConge.setValue(LocalDate.now());
        txtFinConge.setValue(LocalDate.now());
        txtNatureConge.clear();
        txtContenuConge.clear();
        txtCommentaireChef.clear();
        txtEtatConge.clear();
    }

    private void showField(Conge aConge) {
        txtIdConge.setText(aConge.getId().toString());
        txtDateDemande.setValue(aConge.getDateDemande().toLocalDate());
        txtDebutConge.setValue(aConge.getDebutConge().toLocalDate());
        txtFinConge.setValue(aConge.getFinConge().toLocalDate());
        txtNatureConge.setText(aConge.getNatureConge());
        txtContenuConge.setText(aConge.getContenuConge());
        txtCommentaireChef.setText(aConge.getCommentaireChef());
        txtEtatConge.setText(aConge.getEtatConge());
    }
}
