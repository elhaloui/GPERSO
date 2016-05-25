package gperso.controllers;

import gperso.controllers.AbsenceDataController;
import gperso.controllers.HomeController;
import gperso.helpers.FxInitializable;
import gperso.helpers.TableViewColumnAction;
import gperso.helpers.notifications.DialogBalloon;
import gperso.helpers.notifications.DialogWindows;
import gperso.helpers.notifications.LangProperties;
import gperso.helpers.notifications.LangSource;
import gperso.models.Absence;
import gperso.services.ServiceOfAbsence;
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
import java.time.LocalDate;

/**
 * Created by dimmaryanto on 10/1/15.
 */
@Component
public class AbsenceController implements FxInitializable {
    
    

    @FXML
    private TableView<Absence> tableAbsence;
    @FXML
    private TableColumn<Absence, String> columnIdAbsence;
    @FXML
    private TableColumn<Absence, String> columnCinPersonnel;
    @FXML
    private TableColumn columnActionAbsence;
    
    @FXML
    private TextField txtIdAbsence;
    @FXML
    private TextField txtDateAbsence;
    @FXML
    private TextField txtDureeAbsence;
    @FXML
    private TextArea txtMotifAbsence;
    @FXML
    private TextArea txtDecisionChef;
    

    private DialogWindows windows;
    private DialogBalloon ballon;
    private HomeController homeAction;
    private MessageSource messageSource;
    private LangSource lang;
    
    private ServiceOfAbsence serviceAbsence;

    private ApplicationContext springContext;
    private Absence selectedAbsence;
    
    private TableViewColumnAction actionColumnAbsence;
    public Absence getSelectedAbsence() {
        return selectedAbsence;
    }

    public void setSelectedAbsence(Absence selectedAbsence) {
        this.selectedAbsence = selectedAbsence;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        columnIdAbsence.setCellValueFactory(new PropertyValueFactory<>("id"));
       columnCinPersonnel.setCellValueFactory(new PropertyValueFactory<>("personnel.cin"));
       columnActionAbsence.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableColumnAbsenceAction(tableAbsence);
            }
        });

        tableAbsence.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Absence> observable, Absence oldValue, Absence newValue) -> {
            if (newValue != null) {
                selectedAbsence=newValue;
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
    private void newAbsence() {
        AbsenceDataController action = springContext.getBean(AbsenceDataController.class);
        homeAction.updateContent();
        action.newData();
    }

    @FXML
    public void loadData() {
        try {
            tableAbsence.getItems().clear();
            windows.loading(tableAbsence.getItems(), serviceAbsence.findAll(), lang.getSources(LangProperties.LIST_OF_EMPLOYEES));
            tableAbsence.requestFocus();
        } catch (Exception e) {
            windows.errorLoading(lang.getSources(LangProperties.LIST_OF_EMPLOYEES), e);
            e.printStackTrace();
        }
    }

    @FXML
    public void tableViewClearSelected() {
        tableAbsence.getSelectionModel().clearSelection();
    }

//    @Autowired
//    public void setLang(LangSource lang) {
//        this.lang = lang;
//    }

//    @Autowired
//    public void setActionColumnAbsence(TableViewColumnAction actionColumnAbsence) {
//        this.actionColumnAbsence = actionColumnAbsence;
//    }

//    @Autowired
//    public void setWindows(DialogWindows windows) {
//        this.windows = windows;
//    }
//
//    @Autowired
//    public void setBallon(DialogBalloon ballon) {
//        this.ballon = ballon;
//    }

//    @Autowired
//    public void setHomeAction(HomeController homeAction) {
//        this.homeAction = homeAction;
//    }

    @Autowired
    public void setService(ServiceOfAbsence serviceAbsence) {
        this.serviceAbsence = serviceAbsence;
    }

    public void showFields() {
         txtIdAbsence.setText(selectedAbsence.getId().toString());
         txtDateAbsence.setText(selectedAbsence.getDateAbsence().toString());
         txtDecisionChef.setText(selectedAbsence.getDecisionChef());
         txtDureeAbsence.setText(selectedAbsence.getDureeAbsence().toString());
         txtMotifAbsence.setText(selectedAbsence.getMotifAbsence());
         

    }

    private void clearField() {
         txtIdAbsence.clear();
         txtDateAbsence.clear();
         txtDecisionChef.clear();
         txtDureeAbsence.clear();
         txtMotifAbsence.clear();
         selectedAbsence=null;
    }

    private class TableColumnAbsenceAction extends TableCell<Absence, String> {

        private TableView<Absence> table;
        
    
        public TableColumnAbsenceAction(TableView tableView) {
            this.table = tableView;
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty)
                setGraphic(null);
            else {
                Absence anAbsence = table.getItems().get(getIndex());
                setGraphic(actionColumnAbsence.getDefautlTableModel());
                actionColumnAbsence.getUpdateLink().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        AbsenceDataController action = springContext.getBean(AbsenceDataController.class);
                        homeAction.updateContent();
                        action.exitsData(anAbsence);
                    }
                });
                
                actionColumnAbsence.getDeleteLink().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (windows.confirmDelete(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anAbsence.getId(),
                                lang.getSources(LangProperties.ID), anAbsence.getId())
                                .get() == ButtonType.OK) {
                            try {
                                serviceAbsence.delete(anAbsence);
                                loadData();
                                ballon.sucessedRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anAbsence.getId());
                            } catch (Exception e) {
                                windows.errorRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), lang.getSources(LangProperties.ID), anAbsence.getId(), e);
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }

        }
    }
    
    //************************************************************************
    
//    private class TableColumnAdresseAction extends TableCell<Adresse, String> {
//
//        private TableView<Adresse> table;
//        
//    
//        public TableColumnAdresseAction(TableView tableView) {
//            this.table = tableView;
//        }
//
//        @Override
//        protected void updateItem(String item, boolean empty) {
//            super.updateItem(item, empty);
//            if (empty)
//                setGraphic(null);
//            else {
//                Adresse anAdresse = table.getItems().get(getIndex());
//                setGraphic(actionColumnAdresse.getDefautlTableModel());
//                actionColumnAdresse.getUpdateLink().setOnAction(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent event) {
//                        NewAdresseAction action = springContext.getBean(NewAdresseAction.class);
//                        action.exitsData(anAdresse);
//                    }
//                });
//                
//                actionColumn.getDeleteLink().setOnAction(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent event) {
//
//                        if (windows.confirmDelete(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anAdresse.getLieu()+" "+anAdresse.getVille(),
//                                lang.getSources(LangProperties.ID), anAdresse.getLieu()+" "+anAdresse.getVille())
//                                .get() == ButtonType.OK) {
//                            try {
//                                serviceAdresses.delete(anAdresse);
//                                loadData();
//                                ballon.sucessedRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anAdresse.getLieu()+" "+anAdresse.getVille());
//                            } catch (Exception e) {
//                                windows.errorRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), lang.getSources(LangProperties.ID), anAdresse.getLieu()+" "+anAdresse.getVille(), e);
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });
//            }
//
//        }
//    }
//   
//
//
//// *****************************************************************************
//
//private class TableColumnMembreAction extends TableCell<Membre, String> {
//
//        private TableView<Membre> table;
//        
//    
//        public TableColumnMembreAction(TableView tableView) {
//            this.table = tableView;
//        }
//
//        @Override
//        protected void updateItem(String item, boolean empty) {
//            super.updateItem(item, empty);
//            if (empty)
//                setGraphic(null);
//            else {
//                Membre anMembre = table.getItems().get(getIndex());
//                setGraphic(actionColumnMembre.getDefautlTableModel());
//                actionColumnMembre.getUpdateLink().setOnAction(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent event) {
//                        NewMembreAction action = springContext.getBean(NewMembreAction.class);
//                        action.exitsData(anMembre);
//                    }
//                });
//                
//                actionColumn.getDeleteLink().setOnAction(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent event) {
//
//                        if (windows.confirmDelete(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anMembre.getNom()+" "+anMembre.getPrenom(),
//                                lang.getSources(LangProperties.ID), anMembre.getNom()+" "+anMembre.getPrenom())
//                                .get() == ButtonType.OK) {
//                            try {
//                                serviceMembres.delete(anMembre);
//                                loadData();
//                                ballon.sucessedRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anMembre.getNom()+" "+anMembre.getPrenom());
//                            } catch (Exception e) {
//                                windows.errorRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), lang.getSources(LangProperties.ID), anMembre.getNom()+" "+anMembre.getPrenom(), e);
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });
//            }
//
//        }
//    }
}