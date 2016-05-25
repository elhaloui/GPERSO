package gperso.controllers;

import gperso.helpers.FxInitializable;
import gperso.helpers.TableViewColumnAction;
import gperso.helpers.notifications.DialogBalloon;
import gperso.helpers.notifications.DialogWindows;
import gperso.helpers.notifications.LangProperties;
import gperso.helpers.notifications.LangSource;
import gperso.models.Conge;
import gperso.services.ServiceOfConge;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

/**
 * Created by dimmaryanto on 10/5/15.
 */
public class CongeController implements FxInitializable {

    @FXML
    private TableView<Conge> tableConge;
    @FXML
    private TableColumn<Conge, String> columnIdConge;
    @FXML
    private TableColumn<Conge, Date> columnDebutConge;
    @FXML
    private TableColumn<Conge, Date> columnFinConge;
    @FXML
    private TableColumn<Conge, Date> columnDateDemande;
    @FXML
    private TableColumn<Conge, String> columnNatureConge;
    @FXML
    private TableColumn<Conge, String> columnContenuConge;
    @FXML
    private TableColumn<Conge, String> columnCommentaireChef;
    @FXML
    private TableColumn<Conge, String> columnEtatConge;
    
    @FXML
    private TableColumn columnActionConge;

    private HomeController homeAction;
    private DialogWindows windows;
    private DialogBalloon ballon;
    private ServiceOfConge serviceConge;
    private TableViewColumnAction actionColumnConge;
    private ApplicationContext springContext;
    private MessageSource messageSource;
    private LangSource lang;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnIdConge.setCellValueFactory(new PropertyValueFactory<Conge, String>("id"));
        columnDebutConge.setCellValueFactory(new PropertyValueFactory<Conge, Date>("debutConge"));
        columnFinConge.setCellValueFactory(new PropertyValueFactory<Conge, Date>("finConge"));
        columnDateDemande.setCellValueFactory(new PropertyValueFactory<Conge, Date>("dateDemande"));
        columnNatureConge.setCellValueFactory(new PropertyValueFactory<Conge, String>("natureConge"));
        columnContenuConge.setCellValueFactory(new PropertyValueFactory<Conge, String>("contenuConge"));
        columnCommentaireChef.setCellValueFactory(new PropertyValueFactory<Conge, String>("commentaireChef"));
        columnEtatConge.setCellValueFactory(new PropertyValueFactory<Conge, String>("etatConge"));
        
        
        
        columnActionConge.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableColumnCongeAction(tableConge);
            }
        });
    }

    @FXML
    public void loadData() {
        try {
            tableConge.getItems().clear();
            windows.loading(tableConge.getItems(), serviceConge.findAll(), lang.getSources(LangProperties.LIST_OF_DEPARTMENTS));
            tableConge.requestFocus();
        } catch (Exception e) {
            windows.errorLoading(lang.getSources(LangProperties.LIST_OF_DEPARTMENTS), e);
            e.printStackTrace();
        }

    }

    @FXML
    private void newConge() {
        CongeDataController action = springContext.getBean(CongeDataController.class);
        homeAction.updateContent();
        action.newData();
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

    @Autowired
    public void setLang(LangSource lang) {
        this.lang = lang;
    }

    @Autowired
    public void setActionColumn(TableViewColumnAction actionColumnConge) {
        this.actionColumnConge = actionColumnConge;
    }

    @Autowired
    public void setConge(ServiceOfConge serviceConge) {
        this.serviceConge = serviceConge;
    }

    @Autowired
    public void setBallon(DialogBalloon ballon) {
        this.ballon = ballon;
    }

    @Autowired
    public void setWindows(DialogWindows windows) {
        this.windows = windows;
    }

    @Autowired
    public void setHomeAction(HomeController homeAction) {
        this.homeAction = homeAction;
    }

    @FXML
    public void tableViewClearSelection() {
        tableConge.getSelectionModel().clearSelection();
    }

    private class TableColumnCongeAction extends TableCell<Conge, String> {
        TableView table;

        public TableColumnCongeAction(TableView table) {
            this.table = table;
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                Conge conge = (Conge) table.getItems().get(getIndex());
                setGraphic(actionColumnConge.getDefautlTableModel());
                actionColumnConge.getUpdateLink().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        CongeDataController action = springContext.getBean(CongeDataController.class);
                        homeAction.updateContent();
                        action.ExitsData(conge);
                    }
                });
                actionColumnConge.getDeleteLink().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (windows.confirmDelete(
                                lang.getSources(LangProperties.DATA_A_DEPARTMENT), conge.getId(), lang.getSources(LangProperties.ID), conge.getId()
                        ).get() == ButtonType.OK) {
                            try {
                                serviceConge.delete(conge);
                                loadData();
                                ballon.sucessedRemoved(lang.getSources(LangProperties.DATA_A_DEPARTMENT), conge.getId());
                            } catch (Exception e) {
                                windows.errorRemoved(lang.getSources(LangProperties.DATA_A_DEPARTMENT), lang.getSources(LangProperties.ID), conge.getId(), e);
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }
    }
}
