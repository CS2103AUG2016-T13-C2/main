package seedu.address.ui;

import javafx.fxml.FXML;
import seedu.address.model.activity.event.ReadOnlyEvent;
import seedu.address.model.activity.task.ReadOnlyTask;

public class OverdueTaskCard extends DashboardCard{

	 private static final String FXML = "overdueTaskCard.fxml";
	 
	 public OverdueTaskCard() {
	 }
 
		@FXML
		public void initialize() {

			name.setText(activity.getName().fullName);
			datetime.setText(((ReadOnlyTask) activity).getDueDate().forDashboardDisplay());

		}
	 
	    public String getFxmlPath() {
	        return FXML;
	    }
	
}
