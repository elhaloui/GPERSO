package gperso.controllers;

import gperso.controllers.HomeController;
import gperso.helpers.FxInitializable;
import gperso.helpers.TableViewColumnAction;
import gperso.helpers.notifications.DialogBalloon;
import gperso.helpers.notifications.DialogWindows;
import gperso.helpers.notifications.LangProperties;
import gperso.helpers.notifications.LangSource;
import gperso.models.Personnel;
import gperso.models.Poste;
import gperso.services.ServiceOfPersonnel;
import gperso.services.ServiceOfPoste;
import gperso.utile.FxDialogs;
import gperso.utile.Utile;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by dimmaryanto on 10/1/15.
 */
@Component
public class PosteController implements FxInitializable {
    
    

    @FXML
    private TableView<Poste> tablePoste;
    @FXML 
    private TableView<Personnel> tablePersonnel;
    @FXML
    private TableColumn<Poste, String> columnIdPoste;
    @FXML
    private TableColumn<Poste, String> columnNomPoste;
    @FXML
    private TableColumn<Poste, String> columnFonctionPoste;
    
    @FXML
    private  TableColumn<Personnel , String> columnCinPersonnel;
    @FXML
    private  TableColumn<Personnel , String> columnLevelPersonnel;
    @FXML
    private  TableColumn<Personnel , String> columnPostePersonnel;
    @FXML
    private  TableColumn columnPoidsPersonnel;
    
   
    
    
    @FXML
    private TableColumn columnActionPoste;
    @FXML
    private TableColumn columnActionPersonnel;
  
    @FXML
    private TextField txtIdPoste;
    @FXML
    private TextField txtCoutEnfant;
    @FXML
    private TextField txtCoutMarie;
    @FXML
    private TextField txtCoutCelebataire;
    @FXML
    private TextField txtCoutNote;
    @FXML
    private TextField txtCoutAnciente;
    @FXML
    private TextField txtCoutFormation;
    @FXML
    private TextField txtCoutAbsence;
    

    private DialogWindows windows;
    private DialogBalloon ballon;
    private HomeController homeAction;
    private MessageSource messageSource;
    private LangSource lang;
    
    private ServiceOfPoste servicePoste;
    private ServiceOfPersonnel servicePersonnel;
    private ApplicationContext springContext;
    private Poste selectedPoste;
    
    private TableViewColumnAction actionColumnPoste;
    private TableViewColumnAction actionColumnPersonnel;
    public Poste getSelectedPoste() {
        return selectedPoste;
    }

    public void setSelectedPoste(Poste selectedPoste) {
        this.selectedPoste = selectedPoste;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        columnIdPoste.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNomPoste.setCellValueFactory(new PropertyValueFactory<>("nomPoste"));
        columnFonctionPoste.setCellValueFactory(new PropertyValueFactory<>("fonctionPoste"));
        columnCinPersonnel.setCellValueFactory(new PropertyValueFactory<>("cin"));
        columnLevelPersonnel.setCellValueFactory(new PropertyValueFactory<>("level"));
        columnPostePersonnel.setCellValueFactory(new PropertyValueFactory<>("poste.nomPoste"));
        columnPoidsPersonnel.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableCell<Personnel,String>(){
                    @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty)
                setGraphic(null);
            else {
                   Personnel anPersonnel = tablePersonnel.getItems().get(getIndex());
                      setText(PosteController.calculatePoid(anPersonnel, selectedPoste)+"");
            }
                     
//             
                }
           
        };
                }
        });
        
       
        
        
        

        columnActionPoste.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableColumnPosteAction(tablePoste);
            }
        });
        columnActionPersonnel.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableColumnPersonnelAction(tablePersonnel);
            }
        });

        tablePoste.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Poste> observable, Poste oldValue, Poste newValue) -> {
            if (newValue != null) {
                selectedPoste=newValue;
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
    private void newPoste() {
        PosteDataController action = springContext.getBean(PosteDataController.class);
        homeAction.updateContent();
        action.newData();
    }

    @FXML
    public void loadData() {
        try {
            tablePoste.getItems().clear();
            windows.loading(tablePoste.getItems(), servicePoste.findAll(), lang.getSources(LangProperties.LIST_OF_EMPLOYEES));
            tablePoste.requestFocus();
        } catch (Exception e) {
            windows.errorLoading(lang.getSources(LangProperties.LIST_OF_EMPLOYEES), e);
            e.printStackTrace();
        }
    }
    
    @FXML
    public void loadPersonnelData() {
        try {
            windows.loading(tablePersonnel.getItems(), servicePersonnel.findAll(), "Téléchargement des Personnels");
            tablePersonnel.requestFocus();
        } catch (Exception e) {
            windows.errorLoading(lang.getSources(LangProperties.LIST_OF_EMPLOYEES), e);
            e.printStackTrace();
        }
    }

    @FXML
    public void tableViewClearSelected() {
        tablePoste.getSelectionModel().clearSelection();
    }

    @Autowired
    public void setLang(LangSource lang) {
        this.lang = lang;
    }

    @Autowired
    public void setActionColumnPoste(TableViewColumnAction actionColumnPoste) {
        this.actionColumnPoste = actionColumnPoste;
    }
     @Autowired
    public void setActionColumnPersonnel(TableViewColumnAction actionColumnPersonnel) {
        this.actionColumnPersonnel = actionColumnPersonnel;
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
    public void setService(ServiceOfPoste servicePoste) {
        this.servicePoste = servicePoste;
    }
    
    @Autowired
    public void setServicePersonnel(ServiceOfPersonnel servicePersonnel) {
        this.servicePersonnel = servicePersonnel;
    }

    public void showFields() {
         txtIdPoste.setText(selectedPoste.getId().toString());
         txtCoutAbsence.setText(selectedPoste.getCoutAbsence().toString());
         txtCoutAnciente.setText(selectedPoste.getCoutAnciennete().toString());
         txtCoutCelebataire.setText(selectedPoste.getCoutCelibataire().toString());
         txtCoutEnfant.setText(selectedPoste.getCoutEnfant().toString());
         txtCoutFormation.setText(selectedPoste.getCoutFormation().toString());
         txtCoutMarie.setText(selectedPoste.getCoutMarie().toString());
         txtCoutNote.setText(selectedPoste.getCoutNote().toString());
         this.loadPersonnelData();
         
    }

    private void clearField() {
         txtIdPoste.clear();
         txtCoutAbsence.clear();
         txtCoutAnciente.clear();
         txtCoutCelebataire.clear();
         txtCoutEnfant.clear();
         txtCoutFormation.clear();
         txtCoutMarie.clear();
         txtCoutNote.clear();
         txtCoutNote.clear();
         selectedPoste=null;
         tablePersonnel.getItems().clear();
    }

    private class TableColumnPosteAction extends TableCell<Poste, String> {

        private final TableView<Poste> table;
        
    
        public TableColumnPosteAction(TableView tableView) {
            this.table = tableView;
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty)
                setGraphic(null);
            else {
                Poste anPoste = table.getItems().get(getIndex());
                setGraphic(actionColumnPoste.getDefautlTableModel());
                actionColumnPoste.getUpdateLink().setOnAction((ActionEvent event) -> {
                    PosteDataController action = springContext.getBean(PosteDataController.class);
                    homeAction.updateContent();
                    action.exitsData(anPoste);
                });
                
                actionColumnPoste.getDeleteLink().setOnAction((ActionEvent event) -> {
                    if (windows.confirmDelete(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anPoste.getId(),
                            lang.getSources(LangProperties.ID), anPoste.getId())
                            .get() == ButtonType.OK) {
                        try {
                            servicePoste.delete(anPoste);
                            loadData();
                            ballon.sucessedRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), anPoste.getId());
                        } catch (Exception e) {
                            windows.errorRemoved(lang.getSources(LangProperties.DATA_AN_EMPLOYEE), lang.getSources(LangProperties.ID), anPoste.getId(), e);
                            e.printStackTrace();
                        }
                    }
                });
            }

        }
    }
    
 


//************************************************
    private class TableColumnPersonnelAction extends TableCell<Personnel, String> {

        private final TableView<Personnel> table;
        
    
        public TableColumnPersonnelAction(TableView tableView) {
            this.table = tableView;
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty)
                setGraphic(null);
            else {
                Personnel aPersonnel = table.getItems().get(getIndex());
                setGraphic(actionColumnPersonnel.getSingleHyperlinkTableModel("affecter"));
                
                 actionColumnPersonnel.getDeleteLink().setOnAction((ActionEvent event) -> {
                
                if(aPersonnel.getPoste()!=null)
                {
                 if(FxDialogs.showConfirm("Confirmation", "Personnel deja affecter voulez vous forcer l'affectation", "oui","non").equals("oui"))
                 {
                 try {
                        aPersonnel.setPoste(selectedPoste);
                        servicePersonnel.update(aPersonnel);
                    } catch (Exception ex) {
                        Logger.getLogger(PosteController.class.getName()).log(Level.SEVERE, null, ex);
                    }    
                 }
                }
                else
                {
                    try {
                        aPersonnel.setPoste(selectedPoste);
                        servicePersonnel.update(aPersonnel);
                    } catch (Exception ex) {
                        Logger.getLogger(PosteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                 
            });    
                }
           
               
            }

        }
   
    
    private static double calculatePoid(Personnel personnel , Poste poste)
    {
     return poste.getCoutAbsence()*personnel.getAbsences().size()+
     poste.getCoutAnciennete() *Utile.getDiffYears (personnel.getDateEngagement(),Date.valueOf(LocalDate.now()))+
     poste.getCoutCelibataire() * (personnel.getSituationFamilliale().equals("Celebataire")?1:0)+
     poste.getCoutMarie() * (personnel.getSituationFamilliale().equals("Marie")?1:0)+
     poste.getCoutEnfant() * personnel.getNombreEnfants()+
     poste.getCoutFormation()* personnel.getFormations().size()+
     poste.getCoutNote()*personnel.getNote() ;
     
    }
 
}

