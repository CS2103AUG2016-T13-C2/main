package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.event.Event;
import seedu.address.model.activity.event.ReadOnlyEvent;
import seedu.address.model.activity.task.ReadOnlyTask;
import seedu.address.model.activity.task.Task;

public abstract class DashboardCard extends UiPart {

	@FXML
	protected HBox displayCardPanel;
	@FXML
	protected Label name;
	@FXML
	protected Label datetime;

	protected ReadOnlyActivity activity;

	public DashboardCard() {

	}

	public static DashboardCard load(ReadOnlyActivity activity) {
		DashboardCard card = null;
		String type = activity.getClass().getSimpleName().toLowerCase();
				switch(type){
				case "task": 
	                if (((Task) activity).isDueDateApproaching()) {
	                   //DashboardCard card = new UpcomingTaskCard();
	                } else if (((Task) activity).hasPassedDueDate()) {
	                	card = new OverdueTaskCard();
	                }
				case "event":
					//DashboardCard card = new UpcomingEventCard();
				}
		card.activity = activity;
		return UiPartLoader.loadUiPart(card);
	}

	@FXML
	public abstract void initialize(); 

	public HBox getLayout() {
		return displayCardPanel;
	}

	@Override
	public void setNode(Node node) {
		displayCardPanel = (HBox) node;
	}

	public abstract String getFxmlPath();
}