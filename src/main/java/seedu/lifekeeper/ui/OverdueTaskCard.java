package seedu.lifekeeper.ui;

import javafx.fxml.FXML;
import seedu.lifekeeper.model.activity.task.ReadOnlyTask;

//@@author A0125097A
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
