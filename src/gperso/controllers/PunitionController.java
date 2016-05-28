package gperso.controllers;

import gperso.helpers.FxInitializable;
import gperso.helpers.TableViewColumnAction;
import gperso.helpers.notifications.DialogBalloon;
import gperso.helpers.notifications.DialogWindows;
import gperso.helpers.notifications.LangProperties;
import gperso.helpers.notifications.LangSource;
import gperso.models.Punition;
import gperso.services.ServiceOfPunition;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.stereotype.Component;

/**
 * Created by dimmaryanto on 10/1/15.
 */
@Component
public class PunitionController implements FxInitializable {
    
    

    @FXML
    private TableView<Punition> tablePunition;
    @FXML
    private TableColumn<Punition, String> columnIdPunition;
    @FXML
    private TableColumn<Punition, String> columnCinPersonnel;
    @FXML
    private TableColumn columnActionPunition;
    
    @FXML
    private TextField txtIdPunition;
    @FXML
    private TextField txtDebutPunition;
    @FXML
    private TextField txtFinPunition;
    @FXML
    private TextField txtNaturePunition;
    @FXML
    private TextArea txtMotifPunition;
    @FXML
    private TextField txtPunisseur;
    @FXML 
    private TextField txtCinPersonnel;
    
    
    

    private DialogWindows windows;
    private DialogBalloon ballon;
    private HomeController homeAction;
    private MessageSource messageSource;
    private LangSource lang;
    
    private ServiceOfPunition servicePunition;

    private ApplicationContext springContext;
    private Punition selectedPunition;
    
    private TableViewColumnAction actionColumnPunition;
    public Punition getSelectedPunition() {
        return selectedPunition;
    }

    public void setSelectedPunition(Punition selectedPunition) {
        this.selectedPunition = selectedPunition;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        columnIdPunition.setCellValueFactory(new PropertyValueFactory<>("id"));
       columnCinPersonnel.setCellValueFactory(new PropertyValueFactory<>("personnel"));
       columnActionPunition.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableColumnPunitionAction(tablePunition);
            }
        });

        tablePunition.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Punition> observable, Punition oldValue, Punition newValue) -> {
            if (newValue != null) {
                selectedPunition=newValue;
                showFields();
            } else clearField();
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.springContext = applicationContext;
    }

    @Override
    public void doClose() {

    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @FXML
    private void newPunition() {
        PunitionDataController action = springContext.getBean(PunitionDataController.class);
        homeAction.updateContent();
        action.newData();
    }

    @FXML
    public void loadData() {
        try {
            tablePunition.getItems().clear();
            windows.loading(tablePunition.getItems(), servicePunition.findAll(), lang.getSources(LangProperties.LIST_OF_EMPLOYEES));
            tablePunition.requestFocus();
        } catch (Exception e) {
            windows.errorLoading(lang.getSources(LangProperties.LIST_OF_EMPLOYEES), e);
            e.printStackTrace();
        }
    }

    @FXML
    public void tableViewClearSelected() {
        tablePunition.getSelectionModel().clearSelection();
    }

    @Autowired
    public void setLang(LangSource lang) {
        this.lang = lang;
    }

    @Autowired
    public void setActionColumnPunition(TableViewColumnAction actionColumnPunition) {
        this.actionColumnPunition = actionColumnPunition;
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
    public void setHomeAction(HomeController homeAction) {
        this.homeAction = homeAction;
    }

    @Autowired
    public void setService(ServiceOfPunition servicePunition) {
        this.servicePunition = servicePunition;
    }

    public void showFields() {
         txtIdPunition.setText(selectedPunition.getId().toString());
         txtDebutPunition.setText(selectedPunition.getDebutPunition().toString());
         txtFinPunition.setText(selectedPunition.getFinPunition().toString());
         txtNaturePunition.setText(selectedPunition.getNaturePunition());
         txtMotifPunition.setText(selectedPunition.getMotifPunition());
         txtPunisseur.setText(selectedPunition.getPunisseur());
         txtCinPersonnel.setText(selectedPunition.getPersonnel()==null? selectedPunition.getPersonnel().getCin(): "" );
         
         
         

    }

    private void clearField() {
         txtIdPunition.clear();
         txtDebutPunition.clear();
         txtFinPunition.clear();
         txtNaturePunition.clear();
         txtMotifPunition.clear();
         txtPunisseur.clear();
         txtCinPersonnel.clear();
         
         selectedPunition=null;
    }

    private class TableColumnPunitionAction extends TableCell<Punition, String> {

        private TableView<Punition> table;
        
    
        public TableColumnPunitionAction(TableView tableView) {
            this.table = tableView;
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty)
                setGraphic(null);
            else {
                Punition anPunition = table.getItems().get(getIndex());
                setGraphic(actionColumnPunition.getDefautlTableModel());
                actionColumnPunition.getUpdateLink().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        PunitionDataController action = springContext.getBean(PunitionDataController.class);
                        homeAction.updateContent();
                        action.exitsData(anPunition);
                    }
                });
                
                actionColumnPunition.getDeleteLink().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (windows.confirmDelete(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anPunition.getId(),
                                lang.getSources(LangProperties.ID), anPunition.getId())
                                .get() == ButtonType.OK) {
                            try {
                                servicePunition.delete(anPunition);
                                loadData();
                                ballon.sucessedRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anPunition.getId());
                            } catch (Exception e) {
                                windows.errorRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), lang.getSources(LangProperties.ID), anPunition.getId(), e);
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }

        }
    }
    
 
}