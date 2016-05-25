package gperso.controllers;

import gperso.helpers.notifications.LangSource;
import gperso.helpers.notifications.DialogBalloon;
import gperso.helpers.notifications.ValidatorMessages;
import gperso.helpers.notifications.DialogWindows;
import gperso.helpers.notifications.LangProperties;
import gperso.controllers.HomeController;
import gperso.helpers.FxInitializable;
import gperso.controllers.stages.InnerScene;
import gperso.models.Poste;
import gperso.services.ServiceOfPoste;
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
import java.util.ResourceBundle;

/**
 * Created by dimmaryanto on 07/10/15.
 */
public class PosteDataController implements FxInitializable {

    @FXML
    private Button btnSave;
    @FXML
    private TextField txtIdPoste;
    @FXML
    private TextField txtNomPoste;
    @FXML
    private TextField txtFonctionPoste;
    
    @FXML
    private Spinner<Integer> txtCoutEnfant;
    
    @FXML
    private Spinner<Integer> txtCoutMarie;
    
    @FXML
    private Spinner<Integer> txtCoutCelebataire;
    
    @FXML
    private Spinner<Integer> txtCoutNote;
    
    @FXML
    private Spinner<Integer> txtCoutAnciennete;
    
    @FXML
    private Spinner<Integer> txtCoutFormation;
    
    @FXML
    private Spinner<Integer> txtCoutAbsence;
    
   
    

    private InnerScene innerScene;
    private ServiceOfPoste posteService;
    private DialogWindows windows;
    private DialogBalloon ballon;
    private ValidationSupport validationSupport;
    private Boolean isUpdate;
    private Poste anPoste;
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
    public void setPosteService(ServiceOfPoste posteService) {
        this.posteService = posteService;
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
   validationSupport.registerValidator(txtCoutAbsence, true, Validator.createEmptyValidator(
        validatorMessages.validatorNotNull(lang.getSources(LangProperties.NAME)), Severity.ERROR));
        
        validationSupport.registerValidator(txtCoutAnciennete, true, Validator.createEmptyValidator(
       validatorMessages.validatorNotSelected(lang.getSources(LangProperties.JOB_NAME)), Severity.ERROR));
       validationSupport.registerValidator(txtCoutFormation, true, Validator.createEmptyValidator(
       validatorMessages.validatorNotSelected(lang.getSources(LangProperties.JOB_NAME)), Severity.ERROR));      
       
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtCoutEnfant.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        txtCoutMarie.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        txtCoutCelebataire.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        txtCoutNote.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        txtCoutAnciennete.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        txtCoutFormation.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        txtCoutAbsence.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(Integer.MIN_VALUE, 0));
        
    }

   

    public void exitsData(Poste anPoste) {
        initValidator();
        this.anPoste = anPoste;
        setUpdate(true);
        showToFields(anPoste);
        this.validationSupport.redecorate();
    }

    public void newData() {
        initValidator();
        this.anPoste = new Poste();
        setUpdate(false);
        clearFields();
        this.validationSupport.redecorate();
    }

    @FXML
    private void doSave() {
        
        anPoste.setNomPoste(txtNomPoste.getText());
        anPoste.setFonctionPoste(txtFonctionPoste.getText());
        anPoste.setCoutEnfant(txtCoutEnfant.getValue());
        anPoste.setCoutMarie(txtCoutMarie.getValue());
        anPoste.setCoutCelibataire(txtCoutCelebataire.getValue());
        anPoste.setCoutNote(txtCoutNote.getValue());
        anPoste.setCoutFormation(txtCoutFormation.getValue());
        anPoste.setCoutAbsence(txtCoutAbsence.getValue());
        anPoste.setCoutAnciennete(txtCoutAnciennete.getValue());
        if (isUpdate) {
            try {
                posteService.update(anPoste);
                ballon.sucessedUpdated("Poste modifier", "Poste modifier", anPoste.getId());
                doClose();
            } catch (Exception e) {
                e.printStackTrace();
                windows.errorUpdate("", "", anPoste.getId(), e);
            }
        } else {
            try {
                posteService.save(anPoste);
                ballon.sucessSave("Poste ajouter", anPoste.getNomPoste());
                newData();
            } catch (Exception e) {
                e.printStackTrace();
                windows.errorSave("", anPoste.getNomPoste(), e);
            }
        }
    }

    @Override
    public void doClose() {
        HomeController homeAction = springContext.getBean(HomeController.class);
        homeAction.showPostes();
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

    private void showToFields(Poste anPoste) {
        txtIdPoste.setText(anPoste.getId().toString());
        txtNomPoste.setText(anPoste.getNomPoste());
        txtFonctionPoste.setText(anPoste.getFonctionPoste());
        txtCoutEnfant.getValueFactory().setValue(anPoste.getCoutEnfant());
        txtCoutAbsence.getValueFactory().setValue(anPoste.getCoutAbsence());
        txtCoutAnciennete.getValueFactory().setValue(anPoste.getCoutAnciennete());
        txtCoutCelebataire.getValueFactory().setValue(anPoste.getCoutCelibataire());
        txtCoutFormation.getValueFactory().setValue(anPoste.getCoutFormation());
        txtCoutMarie.getValueFactory().setValue(anPoste.getCoutMarie());
        txtCoutNote.getValueFactory().setValue(anPoste.getCoutNote());
        
        
    }

    private void clearFields() {
        txtIdPoste.clear();
        txtNomPoste.clear();
        txtFonctionPoste.clear();
        txtCoutEnfant.getValueFactory().setValue(0);
        txtCoutAbsence.getValueFactory().setValue(0);
        txtCoutAnciennete.getValueFactory().setValue(0);
        txtCoutCelebataire.getValueFactory().setValue(0);
        txtCoutFormation.getValueFactory().setValue(0);
        txtCoutMarie.getValueFactory().setValue(0);
        txtCoutNote.getValueFactory().setValue(0);
        
    }

    
}
