package gperso.controllers;

import gperso.controllers.HomeController;
import gperso.controllers.PersonnelDataController;
import gperso.helpers.FxInitializable;
import gperso.helpers.TableViewColumnAction;
import gperso.helpers.notifications.DialogBalloon;
import gperso.helpers.notifications.DialogWindows;
import gperso.helpers.notifications.LangProperties;
import gperso.helpers.notifications.LangSource;
import gperso.models.Adresse;
import gperso.models.Conge;
import gperso.models.Membre;
import gperso.models.Personnel;
import gperso.services.ServiceOfAdresse;
import gperso.services.ServiceOfConge;
import gperso.services.ServiceOfMembre;
import gperso.services.ServiceOfPersonnel;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import org.controlsfx.control.table.TableFilter;
import org.springframework.stereotype.Component;

/**
 * Created by dimmaryanto on 10/1/15.
 */
@Component
public class PersonnelController implements FxInitializable {
    
    

    @FXML
    private TableView<Personnel> tableView;
    @FXML
    private TableView<Adresse> tableAdresse;
    @FXML
    private TableView<Membre> tableMembre;
    @FXML
    private TableView<Conge> tableConge;
    @FXML
    private TableColumn<Personnel, String> columnCin;
    @FXML 
    private TableColumn<Adresse , String > columnLieu;
    @FXML 
    private TableColumn<Adresse , String > columnVille ;
    @FXML 
    private TableColumn<Membre , String > columnNomMembre;
    @FXML 
    private TableColumn<Membre , String > columnPrenomMembre;
    @FXML 
    private TableColumn<Membre , String > columndegreMembre;
    @FXML 
    private TableColumn<Membre , Date > columnNaissanceMembre;
    @FXML 
    private TableColumn<Membre , String > columnfonctionMembre;
    
    @FXML
    private TableColumn<Conge, Integer> columnIdConge;
    @FXML 
    private TableColumn<Conge , String > columnNatureConge;
    @FXML 
    private TableColumn<Conge , Date > columnDateDemande ;
    @FXML 
    private TableColumn<Conge , Date > columnDebutConge;
    @FXML 
    private TableColumn<Conge , Date > columnFinConge;
    @FXML 
    private TableColumn<Conge , String > columnContenuConge;
    @FXML 
    private TableColumn<Conge , String > columnCommentaireChef;
    @FXML 
    private TableColumn<Conge , String > columnEtatConge;
    
    
    
    @FXML
    private TableColumn columnAction;
    @FXML
    private TableColumn columnActionAdresse;
    @FXML
    private TableColumn columnActionMembre;
    @FXML
    private TableColumn columnActionConge;
    
    @FXML
    private TextField txtCin;
    @FXML
    private TextField txtMatricule;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtSexe;
    @FXML
    private TextField txtDateNaissance;
    @FXML
    private TextField txtLieuNaissance;
    @FXML
    private TextField txtSituationFamilliale;
    @FXML
    private TextField txtTelephone;
    @FXML
    private TextField txtNombreEnfants;
    @FXML
    private TextField txtdateEngagement;
    @FXML
    private TextField txtNote;
    @FXML
    private TextField txtLevel;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtGrade;
    
    @FXML 
    private Button btnAddAdresse;
     @FXML 
    private Button btnAddMembre;
    @FXML 
    private Button btnAddConge;
    
    

    private DialogWindows windows;
    private DialogBalloon ballon;
    private HomeController homeAction;
    private MessageSource messageSource;
    private LangSource lang;
    
    private ServiceOfPersonnel service;
    private ServiceOfAdresse serviceAdresses;
    private ServiceOfMembre serviceMembres;
    private ServiceOfConge serviceConges;
    
    private ApplicationContext springContext;
    private Personnel selectedPersonnel;
    
    private TableViewColumnAction actionColumn;
    private TableViewColumnAction actionColumnAdresse;
    private TableViewColumnAction actionColumnMembre;
    private TableViewColumnAction actionColumnConge;
    

    public Personnel getSelectedPersonnel() {
        return selectedPersonnel;
    }

    public void setSelectedPersonnel(Personnel selectedPersonnel) {
        this.selectedPersonnel = selectedPersonnel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        columnCin.setCellValueFactory(new PropertyValueFactory<>("cin"));
        
        columnLieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        columnVille.setCellValueFactory(new PropertyValueFactory<>("ville"));
        
        columnNomMembre.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnPrenomMembre.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        columndegreMembre.setCellValueFactory(new PropertyValueFactory<>("degre"));
        columnfonctionMembre.setCellValueFactory(new PropertyValueFactory<>("fonction"));
        columnNaissanceMembre.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        
        columnIdConge.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNatureConge.setCellValueFactory(new PropertyValueFactory<>("natureConge"));
        columnDateDemande.setCellValueFactory(new PropertyValueFactory<>("dateDemande"));
        columnDebutConge.setCellValueFactory(new PropertyValueFactory<>("debutConge"));
        columnFinConge.setCellValueFactory(new PropertyValueFactory<>("finConge"));
        columnContenuConge.setCellValueFactory(new PropertyValueFactory<>("contenuConge"));
        columnCommentaireChef.setCellValueFactory(new PropertyValueFactory<>("commentaireChef"));
        columnEtatConge.setCellValueFactory(new PropertyValueFactory<>("etatConge"));
        
        columnAction.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableColumnPersonnelAction(tableView);
            }
        });
        
        columnActionAdresse.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableColumnAdresseAction(tableAdresse);
            }
        });
        
        columnActionMembre.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableColumnMembreAction(tableMembre);
            }
        });
        columnActionConge.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableColumnCongeAction(tableConge);
            }
        });
        
        tableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Personnel> observable, Personnel oldValue, Personnel newValue) -> {
            if (newValue != null) {
                selectedPersonnel=newValue;
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
    private void newPersonnel() {
        PersonnelDataController action = springContext.getBean(PersonnelDataController.class);
        homeAction.updateContent();
        action.newData();
    }

    @FXML
    public void loadData() {
        try {
            tableView.getItems().clear();
            windows.loading(tableView.getItems(), service.findAll(), lang.getSources(LangProperties.LIST_OF_EMPLOYEES));
            tableView.requestFocus();
        } catch (Exception e) {
            windows.errorLoading(lang.getSources(LangProperties.LIST_OF_EMPLOYEES), e);
            e.printStackTrace();
        }
    }

    @FXML
    public void tableViewClearSelected() {
        tableView.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void doAddAdresse() {
        NewAdresseController action = springContext.getBean(NewAdresseController.class);
        action.setIsUpdate(false);
        action.init(this);
        action.newData();
    }
    
    @FXML
    private void doAddMembre() {
        NewMembreController action = springContext.getBean(NewMembreController.class);
        action.setIsUpdate(false);
        action.init(this);
        action.newData();
    }
    
    @FXML
    private void doAddConge() {
        NewCongeController action = springContext.getBean(NewCongeController.class);
        action.setIsUpdate(false);
        action.init(this);
        action.newData();
    }

    @Autowired
    public void setLang(LangSource lang) {
        this.lang = lang;
    }

    @Autowired
    public void setActionColumn(TableViewColumnAction actionColumn) {
        this.actionColumn = actionColumn;
    }
    @Autowired
    public void setActionColumnAdresse(TableViewColumnAction actionColumnAdresse) {
        this.actionColumnAdresse = actionColumnAdresse;
    }
    @Autowired
    public void setActionColumnMembre(TableViewColumnAction actionColumnMembre) {
        this.actionColumnMembre = actionColumnMembre;
    }
    
    @Autowired
    public void setActionColumnConge(TableViewColumnAction actionColumnConge) {
        this.actionColumnConge = actionColumnConge;
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
    public void setService(ServiceOfPersonnel service) {
        this.service = service;
    }
    @Autowired
    public void setServiceAdresses(ServiceOfAdresse serviceAdresses) {
        this.serviceAdresses = serviceAdresses;
    }
    @Autowired
    public void setServiceMembres(ServiceOfMembre serviceMembres) {
        this.serviceMembres = serviceMembres;
    }
    @Autowired
    public void setServiceConges(ServiceOfConge serviceConges) {
        this.serviceConges = serviceConges;
    }

    public void showFields() {
         txtCin.setText(selectedPersonnel.getCin());
         txtMatricule.setText(selectedPersonnel.getMatricule());
         txtNom.setText(selectedPersonnel.getNom());
         txtPrenom.setText(selectedPersonnel.getPrenom());
        txtDateNaissance.setText(selectedPersonnel.getDateNaissance().toLocalDate().toString());
        txtEmail.setText(selectedPersonnel.getEmail());
        txtGrade.setText(selectedPersonnel.getGrade());
        txtLevel.setText(selectedPersonnel.getLevel().toString());
        txtLieuNaissance.setText(selectedPersonnel.getLieuNaissance());
        txtMatricule.setText(selectedPersonnel.getMatricule());
        txtNombreEnfants.setText(selectedPersonnel.getNombreEnfants().toString());
        txtNote.setText(selectedPersonnel.getNote().toString());
        txtSexe.setText(selectedPersonnel.getSexe()+"");
        txtSituationFamilliale.setText(selectedPersonnel.getSituationFamilliale());
        txtTelephone.setText(selectedPersonnel.getTelephone());
        txtdateEngagement.setText(selectedPersonnel.getDateEngagement().toLocalDate().toString());
         tableAdresse.getItems().clear();
         tableAdresse.setItems( FXCollections.observableArrayList(selectedPersonnel.getAdresses()));
         tableMembre.getItems().clear();
         tableMembre.setItems( FXCollections.observableArrayList(selectedPersonnel.getMembres()));
         tableConge.getItems().clear();
         tableConge.setItems( FXCollections.observableArrayList(selectedPersonnel.getConges()));
         btnAddAdresse.setDisable(false);
         btnAddMembre.setDisable(false);
         btnAddConge.setDisable(false);
    }

    private void clearField() {
         txtCin.clear();
         txtMatricule.clear();
         txtNom.clear();
        txtPrenom.clear();
        txtDateNaissance.clear();
        txtEmail.clear();
        txtGrade.clear();
        txtLevel.clear();
        txtLieuNaissance.clear();
        txtMatricule.clear();
        txtNombreEnfants.clear();
        txtNote.clear();
        txtSexe.clear();
        txtSituationFamilliale.clear();
        txtTelephone.clear();
        txtdateEngagement.clear();
        tableAdresse.getItems().clear();
        tableMembre.getItems().clear();
         btnAddAdresse.setDisable(true);
         btnAddMembre.setDisable(true);
         btnAddConge.setDisable(true);
         selectedPersonnel=null;
    }

    private class TableColumnPersonnelAction extends TableCell<Personnel, String> {

        private TableView<Personnel> table;
        
    
        public TableColumnPersonnelAction(TableView tableView) {
            this.table = tableView;
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty)
                setGraphic(null);
            else {
                Personnel anPersonnel = table.getItems().get(getIndex());
                setGraphic(actionColumn.getDefautlTableModel());
                actionColumn.getUpdateLink().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        PersonnelDataController action = springContext.getBean(PersonnelDataController.class);
                        homeAction.updateContent();
                        action.exitsData(anPersonnel);
                    }
                });
                
                actionColumn.getDeleteLink().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (windows.confirmDelete(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anPersonnel.getCin(),
                                lang.getSources(LangProperties.ID), anPersonnel.getCin())
                                .get() == ButtonType.OK) {
                            try {
                                service.delete(anPersonnel);
                                loadData();
                                ballon.sucessedRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anPersonnel.getCin());
                            } catch (Exception e) {
                                windows.errorRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), lang.getSources(LangProperties.ID), anPersonnel.getCin(), e);
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }

        }
    }
    
    //************************************************************************
    
    private class TableColumnAdresseAction extends TableCell<Adresse, String> {

        private TableView<Adresse> table;
        
    
        public TableColumnAdresseAction(TableView tableView) {
            this.table = tableView;
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty)
                setGraphic(null);
            else {
                Adresse anAdresse = table.getItems().get(getIndex());
                setGraphic(actionColumnAdresse.getDefautlTableModel());
                actionColumnAdresse.getUpdateLink().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        NewAdresseController action = springContext.getBean(NewAdresseController.class);
                        action.exitsData(anAdresse);
                    }
                });
                
                actionColumn.getDeleteLink().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (windows.confirmDelete(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anAdresse.getLieu()+" "+anAdresse.getVille(),
                                lang.getSources(LangProperties.ID), anAdresse.getLieu()+" "+anAdresse.getVille())
                                .get() == ButtonType.OK) {
                            try {
                                serviceAdresses.delete(anAdresse);
                                loadData();
                                ballon.sucessedRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anAdresse.getLieu()+" "+anAdresse.getVille());
                            } catch (Exception e) {
                                windows.errorRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), lang.getSources(LangProperties.ID), anAdresse.getLieu()+" "+anAdresse.getVille(), e);
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }

        }
    }
   


// *****************************************************************************

private class TableColumnMembreAction extends TableCell<Membre, String> {

        private TableView<Membre> table;
        
    
        public TableColumnMembreAction(TableView tableView) {
            this.table = tableView;
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty)
                setGraphic(null);
            else {
                Membre anMembre = table.getItems().get(getIndex());
                setGraphic(actionColumnMembre.getDefautlTableModel());
                actionColumnMembre.getUpdateLink().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        NewMembreController action = springContext.getBean(NewMembreController.class);
                        action.exitsData(anMembre);
                    }
                });
                
                actionColumn.getDeleteLink().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (windows.confirmDelete(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anMembre.getNom()+" "+anMembre.getPrenom(),
                                lang.getSources(LangProperties.ID), anMembre.getNom()+" "+anMembre.getPrenom())
                                .get() == ButtonType.OK) {
                            try {
                                serviceMembres.delete(anMembre);
                                loadData();
                                ballon.sucessedRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anMembre.getNom()+" "+anMembre.getPrenom());
                            } catch (Exception e) {
                                windows.errorRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), lang.getSources(LangProperties.ID), anMembre.getNom()+" "+anMembre.getPrenom(), e);
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }

        }
    }

//********************************************************************************************
private class TableColumnCongeAction extends TableCell<Conge, String> {

        private TableView<Conge> table;
        
    
        public TableColumnCongeAction(TableView tableView) {
            this.table = tableView;
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty)
                setGraphic(null);
            else {
                Conge anConge = table.getItems().get(getIndex());
                setGraphic(actionColumnConge.getDefautlTableModel());
                actionColumnConge.getUpdateLink().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        NewCongeController action = springContext.getBean(NewCongeController.class);
                        action.exitsData(anConge);
                    }
                });
                
                actionColumn.getDeleteLink().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (windows.confirmDelete(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anConge.getId()+" "+anConge.getNatureConge(),
                                lang.getSources(LangProperties.ID), anConge.getId()+" "+anConge.getNatureConge())
                                .get() == ButtonType.OK) {
                            try {
                                serviceConges.delete(anConge);
                                loadData();
                                ballon.sucessedRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anConge.getId()+" "+anConge.getNatureConge());
                            } catch (Exception e) {
                                windows.errorRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), lang.getSources(LangProperties.ID), anConge.getId()+" "+anConge.getNatureConge(), e);
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }

        }
    }
}
