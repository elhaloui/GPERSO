package gperso.controllers;

import gperso.controllers.HomeController;
import gperso.controllers.ServiceDataController;
import gperso.helpers.FxInitializable;
import gperso.helpers.TableViewColumnAction;
import gperso.helpers.notifications.DialogBalloon;
import gperso.helpers.notifications.DialogWindows;
import gperso.helpers.notifications.LangProperties;
import gperso.helpers.notifications.LangSource;
import gperso.models.Service;
import gperso.services.ServiceOfService;
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
import java.util.ResourceBundle;

/**
 * Created by dimmaryanto on 10/5/15.
 */
public class ServiceController implements FxInitializable {

    @FXML
    private TableView<Service> tableService;
    @FXML
    private TableColumn<Service, String> columnIdService;
    @FXML
    private TableColumn<Service, String> columnLibelleService;
    @FXML
    private TableColumn<Service, String> columnDescriptionService;
    @FXML
    private TableColumn columnActionService;

    private HomeController homeAction;
    private DialogWindows windows;
    private DialogBalloon ballon;
    private ServiceOfService serviceService;
    private TableViewColumnAction actionColumnService;
    private ApplicationContext springContext;
    private MessageSource messageSource;
    private LangSource lang;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnIdService.setCellValueFactory(new PropertyValueFactory<Service, String>("id"));
        columnLibelleService.setCellValueFactory(new PropertyValueFactory<Service, String>("libelle"));
        columnDescriptionService.setCellValueFactory(new PropertyValueFactory<Service, String>("description"));
        columnActionService.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new TableColumnServiceAction(tableService);
            }
        });
    }

    @FXML
    public void loadData() {
        try {
            tableService.getItems().clear();
            windows.loading(tableService.getItems(), serviceService.findAll(), lang.getSources(LangProperties.LIST_OF_DEPARTMENTS));
            tableService.requestFocus();
        } catch (Exception e) {
            windows.errorLoading(lang.getSources(LangProperties.LIST_OF_DEPARTMENTS), e);
            e.printStackTrace();
        }

    }

    @FXML
    private void newService() {
        ServiceDataController action = springContext.getBean(ServiceDataController.class);
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
    public void setActionColumn(TableViewColumnAction actionColumnService) {
        this.actionColumnService = actionColumnService;
    }

    @Autowired
    public void setService(ServiceOfService serviceService) {
        this.serviceService = serviceService;
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
        tableService.getSelectionModel().clearSelection();
    }

    private class TableColumnServiceAction extends TableCell<Service, String> {
        TableView table;

        public TableColumnServiceAction(TableView table) {
            this.table = table;
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                Service service = (Service) table.getItems().get(getIndex());
                setGraphic(actionColumnService.getDefautlTableModel());
                actionColumnService.getUpdateLink().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ServiceDataController action = springContext.getBean(ServiceDataController.class);
                        homeAction.updateContent();
                        action.ExitsData(service);
                    }
                });
                actionColumnService.getDeleteLink().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (windows.confirmDelete(
                                lang.getSources(LangProperties.DATA_A_DEPARTMENT), service.getLibelle(), lang.getSources(LangProperties.ID), service.getId()
                        ).get() == ButtonType.OK) {
                            try {
                                serviceService.delete(service);
                                loadData();
                                ballon.sucessedRemoved(lang.getSources(LangProperties.DATA_A_DEPARTMENT), service.getLibelle());
                            } catch (Exception e) {
                                windows.errorRemoved(lang.getSources(LangProperties.DATA_A_DEPARTMENT), lang.getSources(LangProperties.ID), service.getId(), e);
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }
    }
}
