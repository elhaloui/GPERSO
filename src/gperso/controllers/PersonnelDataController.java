package gperso.controllers;

import gperso.helpers.notifications.DialogBalloon;
import gperso.helpers.notifications.LangSource;
import gperso.helpers.notifications.DialogWindows;
import gperso.helpers.notifications.LangProperties;
import gperso.helpers.notifications.ValidatorMessages;
import gperso.controllers.HomeController;
import gperso.helpers.FxInitializable;
import gperso.controllers.stages.InnerScene;
import gperso.models.Personnel;
import gperso.services.ServiceOfPersonnel;
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
public class PersonnelDataController implements FxInitializable {

    @FXML
    private Button btnSave;
    @FXML
    private TextField txtCin;
    @FXML
    private TextField txtMatricule;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    
    @FXML
    private ComboBox<String> txtSexe;
    @FXML
    private DatePicker txtDateNaissance;
    @FXML
    private TextField txtLieuNaissance;
    @FXML
    private ComboBox<String> txtSituationFamilliale;
    @FXML
    private TextField txtTelephone;
    @FXML
    private Spinner<Integer> txtNombreEnfants;
    @FXML
    private DatePicker txtDateEngagement;
    @FXML
    private Spinner<Double> txtNote;
    @FXML
    private Spinner<Integer> txtLevel;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtGrade;
    
    
    

    private InnerScene innerScene;
    private ServiceOfPersonnel personnelService;
    private DialogWindows windows;
    private DialogBalloon ballon;
    private ValidationSupport validationSupport;
    private Boolean isUpdate;
    private Personnel anPersonnel;
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
        
        validationSupport.registerValidator(txtCin, true, Validator.createEmptyValidator(
        validatorMessages.validatorNotNull(lang.getSources(LangProperties.NAME)), Severity.ERROR));
        
        validationSupport.registerValidator(txtMatricule, true, Validator.createEmptyValidator(
       validatorMessages.validatorNotSelected(lang.getSources(LangProperties.JOB_NAME)), Severity.ERROR));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtNombreEnfants.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory( 0, Integer.MAX_VALUE));
        txtLevel.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory( 0,2));
        txtSexe.getItems().add("H");
        txtSexe.getItems().add("F");
        txtSituationFamilliale.getItems().add("Marie");
        txtSituationFamilliale.getItems().add("Celebataire");
        
        txtNote.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0,Double.MAX_VALUE));
        

        
    }

   

    public void exitsData(Personnel anPersonnel) {
        initValidator();
        this.anPersonnel = anPersonnel;
        setUpdate(true);
        showToFields(anPersonnel);
        this.validationSupport.redecorate();
    }

    public void newData() {
        initValidator();
        this.anPersonnel = new Personnel();
        setUpdate(false);
        clearFields();
        this.validationSupport.redecorate();
    }

    @FXML
    private void doSave() {
        
        anPersonnel.setCin(txtCin.getText());
        anPersonnel.setMatricule(txtMatricule.getText());
        anPersonnel.setNom(txtNom.getText());
        anPersonnel.setPrenom(txtPrenom.getText());
        anPersonnel.setDateEngagement(Date.valueOf(txtDateEngagement.getValue()));
        anPersonnel.setDateNaissance(Date.valueOf(txtDateNaissance.getValue()));
        anPersonnel.setEmail(txtEmail.getText());
        anPersonnel.setGrade(txtGrade.getText());
        anPersonnel.setLevel(txtLevel.getValue());
        anPersonnel.setLieuNaissance(txtLieuNaissance.getText());
        anPersonnel.setNombreEnfants(txtNombreEnfants.getValue());
        anPersonnel.setNote(txtNote.getValue());
        anPersonnel.setSexe(txtSexe.getValue());
        anPersonnel.setSituationFamilliale(txtSituationFamilliale.getValue());
        anPersonnel.setTelephone(txtTelephone.getText());
        
        if (isUpdate) {
            try {
                personnelService.update(anPersonnel);
                ballon.sucessedUpdated(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), lang.getSources(LangProperties.ID), anPersonnel.getCin());
                doClose();
            } catch (Exception e) {
                e.printStackTrace();
                windows.errorUpdate(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), lang.getSources(LangProperties.ID), anPersonnel.getCin(), e);
            }
        } else {
            try {
                personnelService.save(anPersonnel);
                ballon.sucessedSave(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anPersonnel.getNom());
                newData();
            } catch (Exception e) {
                e.printStackTrace();
                windows.errorSave(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anPersonnel.getNom(), e);
            }
        }
    }

    @Override
    public void doClose() {
        HomeController homeAction = springContext.getBean(HomeController.class);
        homeAction.showPersonnels();
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

    private void showToFields(Personnel anPersonnel) {
        txtCin.setText(anPersonnel.getCin());
        txtMatricule.setText(anPersonnel.getMatricule());
        txtNom.setText(anPersonnel.getNom());
        txtPrenom.setText(anPersonnel.getPrenom());
        txtDateNaissance.setValue(anPersonnel.getDateNaissance().toLocalDate());
        txtEmail.setText(anPersonnel.getEmail());
        txtGrade.setText(anPersonnel.getGrade());
        txtLevel.getValueFactory().setValue(anPersonnel.getLevel());
        txtLieuNaissance.setText(anPersonnel.getLieuNaissance());
        txtMatricule.setText(anPersonnel.getMatricule());
        txtNombreEnfants.getValueFactory().setValue(anPersonnel.getNombreEnfants());
        txtNote.getValueFactory().setValue(anPersonnel.getNote());
        txtSexe.setValue(anPersonnel.getSexe()+"");
        txtSituationFamilliale.setValue(anPersonnel.getSituationFamilliale());
        txtTelephone.setText(anPersonnel.getTelephone());
        txtDateEngagement.setValue(anPersonnel.getDateEngagement().toLocalDate());
        
        
    }

    private void clearFields() {
        txtCin.clear();
        txtMatricule.clear();
        txtNom.clear();
        txtPrenom.clear();
        txtDateNaissance.setValue(LocalDate.now());
        txtEmail.clear();
        txtGrade.clear();
        txtLevel.getValueFactory().setValue(0);
        txtLieuNaissance.clear();
        txtMatricule.clear();
        txtNombreEnfants.getValueFactory().setValue(0);
        txtNote.getValueFactory().setValue(0.0);
        txtSexe.setValue("H");
        txtSituationFamilliale.setValue("Marie");
        txtTelephone.clear();
        txtDateEngagement.setValue(LocalDate.now());
        
    }

    
}
