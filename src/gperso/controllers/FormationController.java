package gperso.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import gperso.helpers.FxInitializable;
import gperso.helpers.TableViewColumnAction;
import gperso.helpers.notifications.DialogBalloon;
import gperso.helpers.notifications.DialogWindows;
import gperso.helpers.notifications.LangProperties;
import gperso.helpers.notifications.LangSource;
import gperso.models.DemandeFormation;
import gperso.models.Formation;
import gperso.models.Personnel;
import gperso.services.ServiceOfDemandeFormation;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Created by dimmaryanto on 10/1/15.
 */
@Component
public class FormationController implements FxInitializable {
    
    

    @FXML
    private TableView<Formation> tableFormation;
    @FXML
    private TableView<DemandeFormation> tableDemande;
    @FXML
    private TableColumn<Formation, String> columnIdFormation;
    @FXML
    private TableColumn<Formation, String> columnNatureFormation;
     @FXML
    private TableColumn<DemandeFormation, Personnel> columnCinPersonnel;
    @FXML
    private TableColumn<DemandeFormation, String> columnEtatDemande;
    @FXML
    private TableColumn columnActionFormation;
    @FXML
    private TableColumn columnActionDemande;
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
    
    @FXML 
    public TitledPane PaneParticipation;
    
    
    
    

    private DialogWindows windows;
    private DialogBalloon ballon;
    private HomeController homeAction;
    private MessageSource messageSource;
    private LangSource lang;
    
    private ServiceOfFormation serviceFormation;
    private ServiceOfDemandeFormation serviceDemande;
   

    
    private ApplicationContext springContext;
    private Formation selectedFormation;
    
    private TableViewColumnAction actionColumnFormation;
    private TableViewColumnAction actionColumnDemande;
    private TableViewColumnAction actionColulmnParticiper;


    public Formation getSelectedFormation() {
        return selectedFormation;
    }

    public void setSelectedFormation(Formation selectedFormation) {
        this.selectedFormation = selectedFormation;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        columnIdFormation.setCellValueFactory(new PropertyValueFactory<Formation,String>("id"));
        columnNatureFormation.setCellValueFactory(new PropertyValueFactory<Formation,String>("natureFormation"));
        columnCinPersonnel.setCellValueFactory(new PropertyValueFactory<DemandeFormation,Personnel>("personnel"));
        columnEtatDemande.setCellValueFactory(new PropertyValueFactory<DemandeFormation,String>("etatDemande"));

        
        columnActionFormation.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableColumnFormationAction(tableFormation);
            }
        });
        
        columnActionDemande.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableColumnDemandeAction(tableDemande);
            }
        });
        
       

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
    
    public void loadDemandeData() {
        try {
            tableDemande.getItems().clear();
            System.out.println(selectedFormation.getDemandes().size());
            windows.loading(tableDemande.getItems(), selectedFormation.getDemandes(), "Chargement des demandes");
            tableDemande.requestFocus();
        } catch (Exception e) {
            windows.errorLoading(lang.getSources(LangProperties.LIST_OF_EMPLOYEES), e);
            e.printStackTrace();
        }
    }

    @FXML
    public void tableViewClearSelected() {
        tableFormation.getSelectionModel().clearSelection();
    }
    
    @Autowired
    public void setLang(LangSource lang) {
        this.lang = lang;
    }

    @Autowired
    public void setActionColumnFormation(TableViewColumnAction actionColumnFormation) {
        this.actionColumnFormation = actionColumnFormation;
        
    }
@Autowired
    public void setActionColumnDemande(TableViewColumnAction actionColumnDemande) {
        this.actionColumnDemande = actionColumnDemande;
        
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
    public void setService(ServiceOfFormation serviceFormation) {
        this.serviceFormation = serviceFormation;
    }
    @Autowired
    public void setServiceDemandes(ServiceOfDemandeFormation serviceDemande) {
        this.serviceDemande = serviceDemande;
    }

    public void showFields() {
         txtIdFormation.setText(selectedFormation.getId().toString());
         txtNatureFormation.setText(selectedFormation.getNatureFormation());
         txtDebutFormation.setValue(selectedFormation.getDebutFormation().toLocalDate());
         txtFinFormation.setValue(selectedFormation.getFinFormation().toLocalDate());
         txtLieuFormation.setText(selectedFormation.getLieuFormation());
         txtDiplomeFormation.setText(selectedFormation.getDiplomeFormation());
         txtObservationFormation.setText(selectedFormation.getObservationFormation());
         
        tableDemande.getItems().clear();
         System.out.println(tableDemande);
        try {
            tableDemande.setItems( FXCollections.observableArrayList(selectedFormation.getDemandes()));
        } catch (Exception ex) {
            Logger.getLogger(FormationController.class.getName()).log(Level.SEVERE, null, ex);
        }
         loadDemandeData();

    }

    private void clearField() {
         txtIdFormation.clear();
         txtNatureFormation.clear();
         txtDebutFormation.setValue(LocalDate.now());
         txtFinFormation.setValue(LocalDate.now());
         txtLieuFormation.clear();
         txtDiplomeFormation.clear();
         txtObservationFormation.clear();
         tableDemande.getItems().clear();
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
                if(homeAction.getAccount().getLevel().equals(gperso.models.Level.ADMIM.getValue()))
                {
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
                else
                {
                    DemandeFormation demande=null;
                    try {
                        demande = serviceDemande.findOne(homeAction.getAccount(), anFormation);
                    } catch (Exception ex) {
                        Logger.getLogger(FormationController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(demande==null)
                    {
                        setGraphic(actionColumnFormation.getSingleHyperlinkTableModel("Participer"));
                        actionColumnFormation.getDeleteLink().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                      DemandeFormation  demande1 =new DemandeFormation(anFormation , new Personnel(homeAction.getAccount().getUsername()),"EN ATTEND");
                        try {
                            serviceDemande.save(demande1);
                        } catch (Exception ex) {
                            Logger.getLogger(FormationController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       loadData();
                        
                    }
                });
                        
                    }
                    else
                    {
                     setGraphic(actionColumnFormation.getSingleHyperlinkTableModel(demande.getEtatDemande()));   
                    }
                    
                }
            }

        }
    }
    
    //************************************************************************
      private class TableColumnDemandeAction extends TableCell<DemandeFormation, String> {
    private TableView table;

        public TableColumnDemandeAction(TableView table) {
            this.table = table;
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setAlignment(Pos.CENTER_LEFT);
            if (empty)
                setGraphic(null);
            else {
                FontAwesomeIconView icon = new FontAwesomeIconView();
                icon.setFont(new Font("FontAwesome", 18));
                DemandeFormation anDemande = (DemandeFormation) table.getItems().get(getIndex());
                 if (anDemande.getEtatDemande().equals("APPROUVER")) {
                    setGraphic(actionColumnDemande.getSingleHyperlinkTableModel("REFUSER"));
                    icon.setIcon(FontAwesomeIcon.HAND_ALT_DOWN);
                    actionColumnDemande.getDeleteLink().setTextFill(Color.RED);
                    actionColumnDemande.getDeleteLink().setGraphic(icon);
                    actionColumnDemande.getDeleteLink().setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                anDemande.setEtatDemande("REFUSER");
                                serviceDemande.update(anDemande);
                                loadDemandeData();
                            } catch (Exception e) {
                                windows.errorLoading(lang.getSources(LangProperties.LIST_ACCOUNTS), e);
                                e.printStackTrace();
                            }
                        }
                    });
                } 
                  else  {
                    setGraphic(actionColumnDemande.getSingleHyperlinkTableModel("APPROUVER"));
                    icon.setIcon(FontAwesomeIcon.HAND_ALT_UP);
                    actionColumnDemande.getDeleteLink().setTextFill(Color.BLUE);
                    actionColumnDemande.getDeleteLink().setGraphic(icon);
                    actionColumnDemande.getDeleteLink().setOnAction((ActionEvent event) -> {
                        try {
                            anDemande.setEtatDemande("APPROUVER");
                            serviceDemande.update(anDemande);
                            loadDemandeData();
                        } catch (Exception e) {
                            windows.errorLoading(lang.getSources(LangProperties.LIST_ACCOUNTS), e);
                            e.printStackTrace();
                        }
                    });
                }
            }
        }
    }
}