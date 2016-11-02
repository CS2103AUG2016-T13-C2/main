package seedu.address.model.activity.event;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.ReadOnlyActivity;
import seedu.address.model.activity.Reminder;
import seedu.address.model.tag.UniqueTagList;

//@@author A0131813R
public class Event extends Activity implements ReadOnlyEvent {

    private StartTime startTime;
    private EndTime endTime;

    public Event(Name name, StartTime start, EndTime end, Reminder reminder, UniqueTagList tags) {
        super(name, reminder, tags);

        // assert !CollectionUtil.isAnyNull(start, end);
        this.startTime = start;
        this.endTime = end;
    }

    /**
     * Copy constructor.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getName(), source.getStartTime(), source.getEndTime(), source.getReminder(), source.getTags());
    }

    @Override
    public StartTime getStartTime() {
        return startTime;
    }

    public void setStartTime(StartTime starttime) {
        this.startTime = starttime;
    }

    @Override
    public EndTime getEndTime() {
        return endTime;
    }

    public void setEndTime(EndTime endtime) {
        this.endTime = endtime;
    }

    /**
     * Checks if this event is currently ongoing.
     * 
     * @return true if the current time is between the start and end time.
     */
    @Override
    public boolean isOngoing() {
        Date now = Calendar.getInstance().getTime();
        return now.after(startTime.getCalendarValue().getTime()) && now.before(endTime.getCalendarValue().getTime());
    }

    /**
     * Checks if this event is over.
     * 
     * @return true if the current time is after the end time.
     */
    @Override
    public boolean isOver() {
        Date now = Calendar.getInstance().getTime();
        return now.after(endTime.getCalendarValue().getTime());
    }

    @Override
    public String toStringCompletionStatus() {
        String message = "";
        if (this.isOver() && !this.startTime.recurring) {
            setisOver(true);
            message = "Event Over";
        } else if (this.isOngoing()) {
            message = "Event Ongoing";
        } else if (this.isOver() && this.startTime.recurring) {
            String[] recurfre = startTime.RecurringMessage.split(" ");
            recurringEvent();
            String cap = recurfre[0].substring(0, 1).toUpperCase() + recurfre[0].substring(1);
            message = cap + " " + recurfre[1] + "\t";
        } else if (!this.isOver() && this.startTime.recurring) {
            String[] recurfre = startTime.RecurringMessage.split(" ");
            String cap = recurfre[0].substring(0, 1).toUpperCase() + recurfre[0].substring(1);
            message = cap + " " + recurfre[1] + "\t";
        }
        if (this.reminder.recurring)
            message = message.concat("\t Remind " + this.reminder.RecurringMessage);
        return message;
    }

    @Override
    public boolean equals(Object other) {
        if (this == null || other == null) {
            return !(this == null ^ other == null);
        } else if (this.getClass() != other.getClass()) {
            return false;
        } else {
            return other == this // short circuit if same object
                    || (other instanceof ReadOnlyActivity // instanceof handles
                                                          // nulls
                            && ((Event) other).getName().equals(this.getName()) // state
                                                                                // checks
                                                                                // here
                                                                                // onwards
                            && ((Event) other).getStartTime().equals(this.getStartTime())
                            && ((Event) other).getEndTime().equals(this.getEndTime())
                            && ((Event) other).getReminder().equals(this.getReminder()));
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing
        // your own
        return Objects.hash(name, startTime, endTime, reminder, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    public void recurringEvent() {
        if (this.reminder.recurring && Calendar.getInstance().after(this.reminder.value)) {
            String[] recur;
            recur = this.reminder.RecurringMessage.split(" ", 2);
            String date = recur[1];
            try {
                this.reminder.setDate(date);
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        }
        if (this.startTime.recurring && Calendar.getInstance().after(this.startTime.value)) {
            String[] recur;
            recur = this.startTime.RecurringMessage.split(" ", 2);
            setisOver(false);
            String date = recur[1];
            try {
                this.startTime.setDate(date);
            } catch (IllegalValueException e) {
                e.printStackTrace();
            }
        }
        if (this.endTime.recurring && Calendar.getInstance().after(this.endTime.value)) {
            String[] recur;
            if (this.endTime.RecurringMessage != null) {
                recur = this.endTime.RecurringMessage.split(" ", 2);
                String date = recur[1];
                try {
                    this.endTime.setDate(date);
                } catch (IllegalValueException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
