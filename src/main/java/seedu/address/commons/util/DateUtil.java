package seedu.address.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import seedu.address.commons.exceptions.IllegalValueException;

public class DateUtil {
    private static List<SimpleDateFormat> DATE_FORMATS;
    private static List<SimpleDateFormat> DATE_FORMATS1;
    private static List<SimpleDateFormat> DATE_FORMATS2;
    public static final String INVALID_FORMAT = "Invalid Format";

    public DateUtil() {
        DATE_FORMATS = new ArrayList<>();
        DATE_FORMATS1 = new ArrayList<>();
        DATE_FORMATS2 = new ArrayList<>();
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy h:mm a"));
        DATE_FORMATS.add(new SimpleDateFormat("d/MM/yyyy h:mm a"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy h.mm a"));
        DATE_FORMATS.add(new SimpleDateFormat("d/MM/yyyy h.mm a"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy HHmm"));
        DATE_FORMATS.add(new SimpleDateFormat("d/MM/yyyy HHmm"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy HH:mm"));
        DATE_FORMATS.add(new SimpleDateFormat("d/MM/yyyy HH:mm"));
        DATE_FORMATS.add(new SimpleDateFormat("d-MM-yyyy HH.mm"));
        DATE_FORMATS.add(new SimpleDateFormat("d/MM/yyyy HH.mm"));
        DATE_FORMATS1.add(new SimpleDateFormat("d-MM-yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d/MM/yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d-MM-yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d/MM/yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d-MM-yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d/MM/yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d-MM-yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d/MM/yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d-MM-yyyy"));
        DATE_FORMATS1.add(new SimpleDateFormat("d/MM/yyyy"));
        DATE_FORMATS2.add(new SimpleDateFormat("d-MM-yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d/MM/yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d-MM-yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d/MM/yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d-MM-yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d/MM/yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d-MM-yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d/MM/yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d-MM-yyyy HH:mm"));
        DATE_FORMATS2.add(new SimpleDateFormat("d/MM/yyyy HH:mm"));
    }

    /**
     * Validate date format with regular expression
     * 
     * @param date
     * @return true valid date format, false invalid date format
     */
    public static boolean validate(String date) {
        Date validDate;

        for (SimpleDateFormat sdf : DATE_FORMATS) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {
                    return true;
                }
            } catch (ParseException e) {
                continue;
            }
        }
        for (SimpleDateFormat sdf : DATE_FORMATS1) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {
                    return true;
                }
            } catch (ParseException e) {
                continue;
            }
        }

        return false;
    }

    /**
     * Convert valid date input into date format Optional to contain time of the
     * day in hour and mins
     * 
     * @param date
     * @return the date in valid date format
     * @throws IllegalValueException 
     */
    public static Date parseDate(String date) throws IllegalValueException {
        Date validDate;

        String dateform;
        for (SimpleDateFormat sdf : DATE_FORMATS1) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {
                    dateform = sdf.format(validDate);
                    dateform = dateform.concat(" 23:59");
                    validDate = DATE_FORMATS2.get(DATE_FORMATS1.indexOf(sdf)).parse(dateform);
                    return validDate;
                }
            } catch (ParseException e) {
                continue;
            }
        }

        for (SimpleDateFormat sdf : DATE_FORMATS) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {
                    return validDate;
                }
            } catch (ParseException e) {
                continue;
            }
        }
        throw new IllegalValueException(INVALID_FORMAT);
    }

    /**
     * Convert valid reminder date input into date format Optional to contain
     * time of the day in hour and mins
     * 
     * @param date
     * @return the date in valid date format
     * @throws IllegalValueException 
     */
    public static Date parseReminder(String date) throws IllegalValueException {
        Date validDate;
        String dateform;
        for (SimpleDateFormat sdf : DATE_FORMATS1) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {
                    dateform = sdf.format(validDate);
                    dateform = dateform.concat(" 20:00");
                    validDate = DATE_FORMATS2.get(DATE_FORMATS1.indexOf(sdf)).parse(dateform);
                    return validDate;
                }
            } catch (ParseException e) {
                continue;
            }
        }

        for (SimpleDateFormat sdf : DATE_FORMATS) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {
                    return validDate;
                }
            } catch (ParseException e) {
                continue;
            }
        }

        throw new IllegalValueException(INVALID_FORMAT);
    }

    /**
     * Convert valid date input into date format for event Must contain time of
     * the day in hour and mins
     * 
     * @param date
     * @return the date in valid date format
     * @throws IllegalValueException 
     */
    public static Date parseEvent(String date) throws IllegalValueException {
        Date validDate;

        for (SimpleDateFormat sdf : DATE_FORMATS) {
            try {
                validDate = sdf.parse(date);
                if (date.equals(sdf.format(validDate))) {
                    return validDate;
                }
            } catch (ParseException e) {
                continue;
            }
        }
        throw new IllegalValueException(INVALID_FORMAT);
    }

    public static boolean hasPassed(Date date) {
        Date today = Calendar.getInstance().getTime();

        if (date.before(today)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidDate(String test) {
        if (validate(test) || test == "")
            return true;
        else
            return false;
    }

    /**
     * Convert today's date into date format Optional to contain time of the day
     * in hour and mins
     * 
     * @param string
     *            "today"
     * @return today in valid date format
     */

    public static String DueTimeToday(String date) throws IllegalValueException {
        String[] timeparts = date.split(" ");
        String part1 = timeparts[0];
        new Date();
        Date todaydate;
        String strDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        if (timeparts.length != 1) {
            String part2 = timeparts[1];
            if (part1.contains("today"))
                return strDate.concat(" " + part2);
            else
                return strDate.concat(" " + part1);
        } else
            try {
                todaydate = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(strDate.concat(" 23:59"));
                return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(todaydate);
            } catch (ParseException e) {
                throw new IllegalValueException(INVALID_FORMAT);
            }
    }

    /**
     * Convert today's date into date format Optional to contain time of the day
     * in hour and mins
     * 
     * @param string
     *            "tomorrow"
     * @return tomorrow in valid date format
     */
    public static String DueTimeTomorrow(String date) throws IllegalValueException {
        String[] timeparts = date.split(" ");
        String part1 = timeparts[0];
        Date today = new Date();
        Date todaydate;
        new Date(today.getTime() + TimeUnit.DAYS.toMillis(1));
        String strDate = new SimpleDateFormat("dd-MM-yyyy").format(today.getTime() + TimeUnit.DAYS.toMillis(1));
        if (timeparts.length != 1) {
            String part2 = timeparts[1];
            if (part1.contains("today"))
                return strDate.concat(" " + part2);
            else
                return strDate.concat(" " + part1);
        } else
            try {
                todaydate = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(strDate.concat(" 23:59"));
                return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(todaydate);
            } catch (ParseException e) {
                throw new IllegalValueException(INVALID_FORMAT);
            }
    }

    /**
     * Convert today's date into date format Must contain time of the day in
     * hour and mins
     * 
     * @param string
     *            "today"
     * @return today in valid date format
     */
    public static String FixedTimeToday(String date) throws IllegalValueException {
        String[] timeparts = date.split(" ");
        String part1 = timeparts[0];
        String strDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        if (timeparts.length != 1) {
            String part2 = timeparts[1];
            if (part1.contains("today"))
                return strDate.concat(" " + part2);
            else
                return strDate.concat(" " + part1);
        } else
            throw new IllegalValueException(INVALID_FORMAT);
    }

    /**
     * Convert today's date into date format Must contain time of the day in
     * hour and mins
     * 
     * @param string
     *            "tomorrow"
     * @return tomorrow in valid date format
     */

    public static String FixedTimeTomorrow(String date) throws IllegalValueException {
        String[] timeparts = date.split(" ");
        String part1 = timeparts[0];
        Date today = new Date();
        new Date(today.getTime() + TimeUnit.DAYS.toMillis(1));
        String strDate = new SimpleDateFormat("dd-MM-yyyy").format(today.getTime() + TimeUnit.DAYS.toMillis(1));
        if (timeparts.length != 1) {
            String part2 = timeparts[1];
            if (part1.contains("today"))
                return strDate.concat(" " + part2);
            else
                return strDate.concat(" " + part1);
        } else
            throw new IllegalValueException(INVALID_FORMAT);
    }
    
    public static Date DueDateConvert(String date) throws IllegalValueException {
        if (date.contains("today")) { // allow user to key in "today" instead of today's date
            date = DueTimeToday(date);
        } else if (date.contains("tomorrow")) { // allow user to key in "tomorrow" instead of tomorrow's/ date
            date = DueTimeTomorrow(date);
        }
        Date taskDate = parseDate(date);
        return taskDate;
    }
    
    public static Date FixedDateConvert(String date) throws IllegalValueException {
        if (date.contains("today")) { // allow user to key in "today" instead of today's date
            date = FixedTimeToday(date);
        } else if (date.contains("tomorrow")) { // allow user to key in "tomorrow" instead of tomorrow's/ date
            date = FixedTimeTomorrow(date);
        }
        Date taskDate = parseReminder(date);
        return taskDate;
    }

    public static Date EventDateConvert(String date) throws IllegalValueException {
        if (date.contains("today")) { // allow user to key in "today" instead of today's date
            date = FixedTimeToday(date);
        } else if (date.contains("tomorrow")) { // allow user to key in "tomorrow" instead of tomorrow's/ date
            date = FixedTimeTomorrow(date);
        }
        Date taskDate = parseEvent(date);
        return taskDate;
    }
    
    public static Date EndDateTime(Date date) throws IllegalValueException {
        String strDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date.getTime() + TimeUnit.HOURS.toMillis(1));
        Date dateformat;
        try {
            dateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(strDate);
        } catch (ParseException e) {
            throw new IllegalValueException(INVALID_FORMAT);
        }
        return dateformat;

    }
}
