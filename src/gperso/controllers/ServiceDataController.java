package gperso.controllers;

import gperso.helpers.notifications.DialogBalloon;
import gperso.helpers.notifications.LangSource;
import gperso.helpers.notifications.DialogWindows;
import gperso.helpers.notifications.LangProperties;
import gperso.helpers.notifications.ValidatorMessages;
import gperso.controllers.HomeController;
import gperso.helpers.FxInitializable;
import gperso.controllers.stages.InnerScene;
import gperso.models.Service;
import gperso.services.ServiceOfService;
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
import java.util.ResourceBundle;

/**
 * Created by dimmaryanto on 10/5/15.
 */
public class ServiceDataController implements FxInitializable {


    @FXML
    private Button btnSave;
    @FXML
    private TextField txtIdService;
    @FXML
    private TextField txtLibelleService;
    @FXML
    private TextArea txtDescriptionService;

    private ServiceOfService serviceService;
    private DialogWindows windows;
    private DialogBalloon ballon;
    private InnerScene innerScene;
    private ValidationSupport validator;
    private ApplicationContext springContext;
    private MessageSource messageSource;
    private ValidatorMessages validatorMessages;
    private Service service;
    private boolean isUpdate;
    private LangSource lang;

    private void initValidator() {
        this.validator = new ValidationSupport();
        this.validator.invalidProperty().addListener((observable, oldValue, newValue) -> btnSave.setDisable(newValue));
       this.validator.registerValidator(txtLibelleService, true, Validator.createEmptyValidator(
                validatorMessages.validatorNotNull(lang.getSources(LangProperties.NAME)), Severity.ERROR));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void newData() {
        initValidator();
        setUpdate(false);
        this.service = new Service();
        clearFields();
        this.validator.redecorate();
    }

    public void ExitsData(Service aService) {
        initValidator();
        setUpdate(true);
        this.service = aService;
        showField(aService);
        this.validator.redecorate();
    }

    @FXML
    private void doSave() {
        
        service.setLibelle(txtLibelleService.getText());
        service.setDescription(txtDescriptionService.getText());
        if (isUpdate) {
            try {
                serviceService.update(service);
                ballon.sucessedUpdated(lang.getSources(LangProperties.DATA_A_DEPARTMENT), lang.getSources(LangProperties.ID), service.getId());
                doClose();
            } catch (Exception e) {
                windows.errorUpdate(lang.getSources(LangProperties.DATA_A_DEPARTMENT), lang.getSources(LangProperties.ID), service.getId(), e);
                e.printStackTrace();
            }
        } else {
            try {
                serviceService.save(service);
                ballon.sucessedSave(lang.getSources(LangProperties.DATA_A_DEPARTMENT), service.getLibelle());
                newData();
            } catch (Exception e) {
                windows.errorSave(lang.getSources(LangProperties.DATA_A_DEPARTMENT), service.getLibelle(), e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doClose() {
        HomeController homeAction = springContext.getBean(HomeController.class);
        homeAction.showServices();
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
    public void setService(ServiceOfService serviceService) {
        this.serviceService = serviceService;
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
        txtIdService.clear();
        txtLibelleService.clear();
        txtDescriptionService.clear();
    }

    private void showField(Service aService) {
        txtIdService.setText(aService.getId().toString());
        txtLibelleService.setText(aService.getLibelle());
        txtDescriptionService.setText(aService.getDescription());
    }
}
