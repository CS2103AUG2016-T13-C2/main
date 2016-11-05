package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.task.ReadOnlyTask;

/**
 * Panel containing the list of tasks that are already Overdue.
 */
//@@author A0125284H
public class OverdueTaskListPanel extends ListPanel {
	/**
	 * Panel containing the list of persons.
	 */
	private final Logger logger = LogsCenter.getLogger(OverdueTaskListPanel.class);
	private static final String FXML = "OverdueListPanel.fxml";

/*
	@FXML
	private static ListView<ReadOnlyActivity> overdueActivityListView;
*/
	public OverdueTaskListPanel() {
	        super();
	    }

	// Functions specific to OverdueListPanel ---------------------------------------------
	
    private void setEventHandlerForSelectionChangeEvent() {
        this.activityListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                raise(new PersonPanelSelectionChangedEvent(newValue));
            }
        });
    }
    
    /**
     * 
     * @param primaryStage
     * @param personListPlaceholder
     * @param taskList - the function calling should make sure that an ActivityList consisting of only tasks are passed.
     * @return
     */

	public static OverdueTaskListPanel load(Stage primaryStage, AnchorPane personListPlaceholder,
		                            	ObservableList<ReadOnlyActivity> taskList) {
		OverdueTaskListPanel overdueActivitiesListPanel = 
				UiPartLoader.loadUiPart(primaryStage, personListPlaceholder, new OverdueTaskListPanel());
		overdueActivitiesListPanel.configure(taskList);
		return overdueActivitiesListPanel;
	}
	
	/**
	 * To configure listView in activityListView to accept overdue Tasks
	 * 
	 * @preconditions: personList only contains (overdue) Tasks.
	 * 
	 */
	
	//@@author A0125284H
    protected void setConnections(ObservableList<ReadOnlyActivity> personList) {
        activityListView.setItems(personList);
        activityListView.setCellFactory(listView -> new DashboardListViewCell());
    }
	
	@Override
	public String getFxmlPath() {
		return FXML;
	}
	
	@Override
	public void setPlaceholder(AnchorPane pane) {
		this.placeHolderPane = pane;
	}
}