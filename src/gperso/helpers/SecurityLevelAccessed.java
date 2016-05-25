package gperso.helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

/**
 * 
 */
@Component
public class SecurityLevelAccessed {

    private ObservableList<String> list;

    public SecurityLevelAccessed() {
        list = FXCollections.observableArrayList();
        list.add("ADMIN");
        list.add("CHEF");
        list.add("PERSONNEL");
    }

    public ObservableList<String> getList() {
        return list;
    }

}
