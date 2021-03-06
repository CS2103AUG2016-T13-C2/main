package seedu.lifekeeper.testutil;

import seedu.lifekeeper.commons.exceptions.IllegalValueException;
import seedu.lifekeeper.model.activity.Name;
import seedu.lifekeeper.model.activity.Reminder;
import seedu.lifekeeper.model.activity.task.DueDate;
import seedu.lifekeeper.model.activity.task.Priority;
import seedu.lifekeeper.model.tag.Tag;

/**
 * A mutable Task object. For testing only.
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() throws IllegalValueException {
        this.task = new TestTask();
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }
    
    public TaskBuilder withReminder(String address) throws IllegalValueException {
        this.task.setReminder(new Reminder(address));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.addTags(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withDueDate(String dueDate) throws IllegalValueException {
    	this.task.setDueDate(new DueDate(dueDate));
    	return this;
    }
    
    public TaskBuilder withPriority(String priority) throws IllegalValueException {
    	this.task.setPriority(new Priority(priority));
    	return this;
    }

    public TestTask build() {
        return this.task;
    }
}
