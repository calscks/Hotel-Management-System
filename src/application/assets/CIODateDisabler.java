package application.assets;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
/*
How to use:
new CIODateDisabler(1, 2)
1: Your check in date picker
2: Your check out date picker

or (for facility cuz got one date picker only)
new CIODateDisabler(your datePicker)
 */
public class CIODateDisabler {
    private DatePicker ciDatePicker;
    private DatePicker coDatePicker;
    private Long duration;

    public CIODateDisabler(DatePicker datePicker){
        ciDatePicker = datePicker;
        disableCiDate();
    }

    public CIODateDisabler(DatePicker ciDatePicker, DatePicker coDatePicker){
        this.ciDatePicker = ciDatePicker;
        this.coDatePicker = coDatePicker;

        disableCiDate();
        disableCoDate();

        //convert to an sqlite-acceptable date format
        final String datePattern = "yyyy-MM-dd";
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern(datePattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };

        this.ciDatePicker.setConverter(converter);
        this.coDatePicker.setConverter(converter);

    }

    private void disableCiDate(){
        //for check in datePicker
        ciDatePicker.setValue(LocalDate.now());
        //obtained from oracle doc on how to disable date on datePicker, customised for my needs
        final Callback<DatePicker, DateCell> disableCiDate = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #c46067;");
                        }
                    }
                };
            }
        };
        //set the above dayCellFactory into date_ci datePicker
        ciDatePicker.setDayCellFactory(disableCiDate);
    }

    private void disableCoDate(){
        //for check out datePicker, disable dates before check in date
        final Callback<DatePicker, DateCell> disableCoDate = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore( ciDatePicker.getValue().plusDays(1) )) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ff9b83;");
                        }
                        duration = ChronoUnit.DAYS.between(ciDatePicker.getValue(), item);
                        setTooltip(new Tooltip("Staying for " + duration + " days"));
                    }
                };
            }
        };
        coDatePicker.setDayCellFactory(disableCoDate);
    }
}
