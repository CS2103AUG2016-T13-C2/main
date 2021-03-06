package seedu.lifekeeper.commons.events.ui;

import seedu.lifekeeper.commons.events.BaseEvent;
import seedu.lifekeeper.logic.commands.Command;

/**
 * Indicates an attempt to execute an incorrect command
 */
public class IncorrectCommandAttemptedEvent extends BaseEvent {

    public IncorrectCommandAttemptedEvent(Command command) {}

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
