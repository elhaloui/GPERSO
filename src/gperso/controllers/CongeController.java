package gperso.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
        columnIdConge.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDebutConge.setCellValueFactory(new PropertyValueFactory<>("debutConge"));
        columnFinConge.setCellValueFactory(new PropertyValueFactory<>("finConge"));
        columnDateDemande.setCellValueFactory(new PropertyValueFactory<>("dateDemande"));
        columnNatureConge.setCellValueFactory(new PropertyValueFactory<>("natureConge"));
        columnContenuConge.setCellValueFactory(new PropertyValueFactory<>("contenuConge"));
        columnCommentaireChef.setCellValueFactory(new PropertyValueFactory<>("commentaireChef"));
        columnEtatConge.setCellValueFactory(new PropertyValueFactory<>("etatConge"));
        
        
        
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
    private TableView table;

        public TableColumnCongeAction(TableView table) {
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
                Conge anConge = (Conge) table.getItems().get(getIndex());
                 if (anConge.getEtatConge().equals("APPROUVER")) {
                    setGraphic(actionColumnConge.getSingleHyperlinkTableModel("REFUSER"));
                    icon.setIcon(FontAwesomeIcon.HAND_ALT_DOWN);
                    actionColumnConge.getDeleteLink().setTextFill(Color.RED);
                    actionColumnConge.getDeleteLink().setGraphic(icon);
                    actionColumnConge.getDeleteLink().setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                anConge.setEtatConge("REFUSER");
                                serviceConge.update(anConge);
                                loadData();
                            } catch (Exception e) {
                                windows.errorLoading(lang.getSources(LangProperties.LIST_ACCOUNTS), e);
                                e.printStackTrace();
                            }
                        }
                    });
                } 
                  else  {
                    setGraphic(actionColumnConge.getSingleHyperlinkTableModel("APPROUVER"));
                    icon.setIcon(FontAwesomeIcon.HAND_ALT_UP);
                    actionColumnConge.getDeleteLink().setTextFill(Color.BLUE);
                    actionColumnConge.getDeleteLink().setGraphic(icon);
                    actionColumnConge.getDeleteLink().setOnAction((ActionEvent event) -> {
                        try {
                            anConge.setEtatConge("APPROUVER");
                            serviceConge.update(anConge);
                            loadData();
                        } catch (Exception e) {
                            windows.errorLoading(lang.getSources(LangProperties.LIST_ACCOUNTS), e);
                            e.printStackTrace();
                        }
                    });
                }
            }
        }
    }
    //**********************************************************************
   
}
