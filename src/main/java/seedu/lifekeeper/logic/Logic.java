package seedu.lifekeeper.logic;

import javafx.collections.ObservableList;
import seedu.lifekeeper.logic.commands.CommandResult;
import seedu.lifekeeper.model.ReadOnlyLifeKeeper;
import seedu.lifekeeper.model.activity.ReadOnlyActivity;

/**
 * API of the Logic component
 */
public interface Logic {
    
    /**
     * Resets the data stored in the model to the new specified data.
     * @param newData the new Lifekeeper data
     */
    void resetData(ReadOnlyLifeKeeper newData);
    
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     */
    CommandResult execute(String commandText);

    /** Returns the filtered list of activities */
    ObservableList<ReadOnlyActivity> getFilteredActivityList();
    
    /** Returns the filtered list of overdue tasks */
    ObservableList<ReadOnlyActivity> getFilteredOverdueTaskList();

}
