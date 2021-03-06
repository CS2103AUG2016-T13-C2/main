package seedu.lifekeeper.model.activity.event;

import seedu.lifekeeper.model.activity.ReadOnlyActivity;
//@@author A0125680H
public interface ReadOnlyEvent extends ReadOnlyActivity {
    StartTime getStartTime();
    EndTime getEndTime();
    
    boolean isOngoing();
    boolean isOver();

    String toStringCompletionStatus();
    
    String displayTiming();
    
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndTime().equals(this.getEndTime())
                && other.getReminder().equals(this.getReminder()));
    }
    
    /**
     * Formats the activity as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Start Time: ")
                .append(getStartTime())
                .append(" End Time: ")
                .append(getEndTime())
                .append(" Reminder: ")
                .append(getReminder())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
