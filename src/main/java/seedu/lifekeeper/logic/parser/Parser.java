package seedu.lifekeeper.logic.parser;

import seedu.lifekeeper.commons.exceptions.IllegalValueException;
import seedu.lifekeeper.commons.util.StringUtil;
import seedu.lifekeeper.logic.commands.*;
import seedu.lifekeeper.model.tag.Tag;
import seedu.lifekeeper.model.tag.UniqueTagList;

import static seedu.lifekeeper.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.lifekeeper.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//@@author A0125097A
/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<task>[^/]+)"
                    + "(?<duedate>(?: d/[^/]+)?)"
                    + "(?<priority>(?: p/[^/]+)?)"
                    + "(?<start>(?: s/[^/]+)?)"
                    + "(?<end>(?: e/[^/]+)?)"
                    + "(?<recurring>(?: every/[^/]+)?)"
                    + "(?<reminder>(?: r/[^/]+)?)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    
    private static final Pattern ACTIVITY_EDIT_ARGS_FORMAT =
            Pattern.compile("(?<targetIndex>[0-9]+)" + "(?<parameters>.+)");
    
    private static final Pattern ACTIVITY_EDIT_PARAMETERS_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<task>(?: n/[^/]+)?)"
                    + "(?<duedate>(?: d/[^/]+)?)"
                    + "(?<priority>(?: p/[^/]+)?)"
                    + "(?<start>(?: s/[^/]+)?)"
                    + "(?<end>(?: e/[^/]+)?)"
                    + "(?<recurring>(?: every/[^/]+)?)"
                    + "(?<reminder>(?: r/[^/]+)?)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags
    
    public Parser() {}

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);
        
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case DoneCommand.COMMAND_WORD:
            return prepareDone(arguments);

        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return prepareList(arguments);
            
        case FindTagCommand.COMMAND_WORD:
            return prepareFindTag(arguments);
        
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
            
        case OpenCommand.COMMAND_WORD:
            return prepareOpen(arguments);
                    
        case SaveCommand.COMMAND_WORD:
            return prepareSave(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND/* + ": " + commandWord*/);
        }
    }




    private Command prepareAdd(String args){
        final Matcher matcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            return new AddCommand(
                    matcher.group("task"),
                    getElement(matcher.group("duedate")," d/"),
                    getElement(matcher.group("priority")," p/"),
                    getElement(matcher.group("start")," s/"),
                    getElement(matcher.group("end")," e/"),
                    getElement(matcher.group("reminder")," r/"),
                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags

        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
        
        return new HashSet<>(tagStrings);
    }   
    
    /**
     * Extracts the new task's element (e.g. DueDate, Priority) from the add command's argument string.
     *
     * @return task element without trailing or ending whitespaces
     */
    private static String getElement(String argument, String prefix) {
        // no priority
        if (argument.isEmpty()) {
            return "";
        }
        // replace first delimiter prefix, then return
        String priorityValue = argument.replaceFirst(prefix, "");
        // remove white spaces contained in elements entered
        return priorityValue.trim();
    }   
    
    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }
    
    private Command prepareDone(String args) {
    	 Optional<Integer> index = parseIndex(args);
         if(!index.isPresent()){
             return new IncorrectCommand(
                     String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
         }

         return new DoneCommand(index.get());
		
	}
    
    //@@author A0125680H
    /**
     * Parses arguments in the context of the edit activity command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args){
        final Matcher matcherEdit = ACTIVITY_EDIT_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if(!matcherEdit.matches()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        String indexString = matcherEdit.group("targetIndex");
        Optional<Integer> index = parseIndex(indexString);
        
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        String params = " ".concat(matcherEdit.group("parameters").trim());
        final Matcher matcherParams = ACTIVITY_EDIT_PARAMETERS_ARGS_FORMAT.matcher(params);
        
        if(!matcherParams.matches()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        
        try {
            return new EditCommand(index.get(), 
                    getElement(matcherParams.group("task"), " n/"),
                    getElement(matcherParams.group("duedate")," d/"),
                    getElement(matcherParams.group("priority")," p/"),
                    getElement(matcherParams.group("start")," s/"),
                    getElement(matcherParams.group("end")," e/"),
                    getElement(matcherParams.group("reminder")," r/"),
                    getTagsFromArgs(matcherParams.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }
    
    /**
     * Parses arguments in the context of the open command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareOpen(String args) {
        return new OpenCommand(args.trim());
    }
    
    /**
     * Parses arguments in the context of the save command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSave(String args) {
        return new SaveCommand(args.trim());
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }
    
    private Command prepareFindTag(String args) {
        final Matcher matcher = Pattern.compile(Tag.TAG_VALIDATION_REGEX).matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindTagCommand.MESSAGE_USAGE));
        }
        
        return new FindTagCommand(args);
    }

    

	/**
     * Parses arguments in the context of the list command.
     *
     * @param args full command args string
     * @return the prepared command
     */    
	private Command prepareList(String args) {

		if (args.contains("task")) {
			return new ListCommand("task");
		}

		if (args.contains("activit")) {
			return new ListCommand("activity");
		}

		if (args.contains("event")) {
			return new ListCommand("event");
		}

	      if (args.contains("done")) {
	            return new ListCommand("done");
	        }
          if (args.contains("all")) {
              return new ListCommand("all");
          }

		
		if (args.equals("")) {
			return new ListCommand("");
		} else {
			return new IncorrectCommand(ListCommand.MESSAGE_INVALID_LIST_TYPE);
		}

	}

    
}