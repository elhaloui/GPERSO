package gperso.controllers;

import gperso.helpers.FxInitializable;
import gperso.helpers.TableViewColumnAction;
import gperso.helpers.notifications.DialogBalloon;
import gperso.helpers.notifications.DialogWindows;
import gperso.helpers.notifications.LangProperties;
import gperso.helpers.notifications.LangSource;
import gperso.models.Formation;
import gperso.services.ServiceOfFormation;
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
public class FormationController implements FxInitializable {
    
    

    @FXML
    private TableView<Formation> tableFormation;
//    @FXML
//    private TableView<Adresse> tableAdresse;
//    @FXML
//    private TableView<Membre> tableMembre;
    @FXML
    private TableColumn<Formation, String> columnIdFormation;
    @FXML
    private TableColumn<Formation, String> columnNatureFormation;
    @FXML
    private TableColumn columnActionFormation;
    
//    @FXML 
//    private TableColumn<Adresse , String > columnLieu;
//    @FXML 
//    private TableColumn<Adresse , String > columnVille ;
//    @FXML 
//    private TableColumn<Membre , String > columnNomMembre;
//    @FXML 
//    private TableColumn<Membre , String > columnPrenomMembre;
//    @FXML 
//    private TableColumn<Membre , String > columndegreMembre;
//    @FXML 
//    private TableColumn<Membre , Date > columnNaissanceMembre;
//    @FXML 
//    private TableColumn<Membre , String > columnfonctionMembre;
    
    
    
    
//    @FXML
//    private TableColumn columnActionAdresse;
//    @FXML
//    private TableColumn columnActionMembre;
    
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
    
//    @FXML 
//    private Button btnAddAdresse;
//     @FXML 
//    private Button btnAddMembre;
    
    
    

    private DialogWindows windows;
    private DialogBalloon ballon;
    private HomeController homeAction;
    private MessageSource messageSource;
    private LangSource lang;
    
    private ServiceOfFormation serviceFormation;
//    private ServiceOfAdresse serviceAdresses;
//    private ServiceOfMembre serviceMembres;
    
    private ApplicationContext springContext;
    private Formation selectedFormation;
    
    private TableViewColumnAction actionColumnFormation;
//    private TableViewColumnAction actionColumnAdresse;
//    private TableViewColumnAction actionColumnMembre;

    public Formation getSelectedFormation() {
        return selectedFormation;
    }

    public void setSelectedFormation(Formation selectedFormation) {
        this.selectedFormation = selectedFormation;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        columnIdFormation.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNatureFormation.setCellValueFactory(new PropertyValueFactory<>("natureFormation"));
        
//        columnLieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
//        columnVille.setCellValueFactory(new PropertyValueFactory<>("ville"));
//        
//        columnNomMembre.setCellValueFactory(new PropertyValueFactory<>("nom"));
//        columnPrenomMembre.setCellValueFactory(new PropertyValueFactory<>("prenom"));
//        columndegreMembre.setCellValueFactory(new PropertyValueFactory<>("degre"));
//        columnfonctionMembre.setCellValueFactory(new PropertyValueFactory<>("fonction"));
//        columnNaissanceMembre.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        
        columnActionFormation.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableColumnFormationAction(tableFormation);
            }
        });
        
//        columnActionAdresse.setCellFactory(new Callback<TableColumn, TableCell>() {
//            @Override
//            public TableCell call(TableColumn param) {
//                return new TableColumnAdresseAction(tableAdresse);
//            }
//        });
//        
//        columnActionMembre.setCellFactory(new Callback<TableColumn, TableCell>() {
//            @Override
//            public TableCell call(TableColumn param) {
//                return new TableColumnMembreAction(tableMembre);
//            }
//        });
        
        tableFormation.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Formation> observable, Formation oldValue, Formation newValue) -> {
            if (newValue != null) {
                selectedFormation=newValue;
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
    private void newFormation() {
        FormationDataController action = springContext.getBean(FormationDataController.class);
        homeAction.updateContent();
        action.newData();
    }

    @FXML
    public void loadData() {
        try {
            tableFormation.getItems().clear();
            windows.loading(tableFormation.getItems(), serviceFormation.findAll(), lang.getSources(LangProperties.LIST_OF_EMPLOYEES));
            tableFormation.requestFocus();
        } catch (Exception e) {
            windows.errorLoading(lang.getSources(LangProperties.LIST_OF_EMPLOYEES), e);
            e.printStackTrace();
        }
    }

    @FXML
    public void tableViewClearSelected() {
        tableFormation.getSelectionModel().clearSelection();
    }
    
//    @FXML
//    private void doAddAdresse() {
//        NewAdresseAction action = springContext.getBean(NewAdresseAction.class);
//        action.setIsUpdate(false);
////        action.init(this);
//        action.newData();
//    }
//    
//    @FXML
//    private void doAddMembre() {
//        NewMembreAction action = springContext.getBean(NewMembreAction.class);
//        action.setIsUpdate(false);
//  //      action.init(this);
//        action.newData();
//    }

    @Autowired
    public void setLang(LangSource lang) {
        this.lang = lang;
    }

    @Autowired
    public void setActionColumnFormation(TableViewColumnAction actionColumnFormation) {
        this.actionColumnFormation = actionColumnFormation;
    }
//    @Autowired
//    public void setActionColumnAdresse(TableViewColumnAction actionColumnAdresse) {
//        this.actionColumnAdresse = actionColumnAdresse;
//    }
//    @Autowired
//    public void setActionColumnMembre(TableViewColumnAction actionColumnMembre) {
//        this.actionColumnMembre = actionColumnMembre;
//    }

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
    public void setService(ServiceOfFormation serviceFormation) {
        this.serviceFormation = serviceFormation;
    }
//    @Autowired
//    public void setServiceAdresses(ServiceOfAdresse serviceAdresses) {
//        this.serviceAdresses = serviceAdresses;
//    }
//    @Autowired
//    public void setServiceMembres(ServiceOfMembre serviceMembres) {
//        this.serviceMembres = serviceMembres;
//    }

    public void showFields() {
         txtIdFormation.setText(selectedFormation.getId().toString());
         txtNatureFormation.setText(selectedFormation.getNatureFormation());
         txtDebutFormation.setValue(selectedFormation.getDebutFormation().toLocalDate());
         txtFinFormation.setValue(selectedFormation.getFinFormation().toLocalDate());
         txtLieuFormation.setText(selectedFormation.getLieuFormation());
         txtDiplomeFormation.setText(selectedFormation.getDiplomeFormation());
         txtObservationFormation.setText(selectedFormation.getObservationFormation());
         
//         tableAdresse.getItems().clear();
//         tableAdresse.setItems( FXCollections.observableArrayList(selectedFormation.getAdresses()));
//         tableMembre.getItems().clear();
//         tableMembre.setItems( FXCollections.observableArrayList(selectedFormation.getMembres()));
//         btnAddAdresse.setDisable(false);
//         btnAddMembre.setDisable(false);
    }

    private void clearField() {
         txtIdFormation.clear();
         txtNatureFormation.clear();
         txtDebutFormation.setValue(LocalDate.now());
         txtFinFormation.setValue(LocalDate.now());
         txtLieuFormation.clear();
         txtDiplomeFormation.clear();
         txtObservationFormation.clear();
//         tableAdresse.getItems().clear();
//         tableMembre.getItems().clear();
//         btnAddAdresse.setDisable(true);
//         btnAddMembre.setDisable(true);
         selectedFormation=null;
    }

    private class TableColumnFormationAction extends TableCell<Formation, String> {

        private TableView<Formation> table;
        
    
        public TableColumnFormationAction(TableView tableView) {
            this.table = tableView;
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty)
                setGraphic(null);
            else {
                Formation anFormation = table.getItems().get(getIndex());
                setGraphic(actionColumnFormation.getDefautlTableModel());
                actionColumnFormation.getUpdateLink().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        FormationDataController action = springContext.getBean(FormationDataController.class);
                        homeAction.updateContent();
                        action.exitsData(anFormation);
                    }
                });
                
                actionColumnFormation.getDeleteLink().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (windows.confirmDelete(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anFormation.getId(),
                                lang.getSources(LangProperties.ID), anFormation.getId())
                                .get() == ButtonType.OK) {
                            try {
                                serviceFormation.delete(anFormation);
                                loadData();
                                ballon.sucessedRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anFormation.getId());
                            } catch (Exception e) {
                                windows.errorRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), lang.getSources(LangProperties.ID), anFormation.getId(), e);
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