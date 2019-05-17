package jschool.validator;

import java.util.ArrayList;
import java.util.List;

/**
 * This is aditional class, used for validation on jsp
 * */
public class Message {
    /** list of error messages we want to send* */
    private List<String> errors;
    /** list of warning messages we want to send* */
    private List<String> warnings;
    /** list of success messages we want to send* */
    private List<String> confirms;

    public Message() {
        errors = new ArrayList<>();
        warnings = new ArrayList<>();
        confirms = new ArrayList<>();
    }

    public List<String> getConfirms() {
        return confirms;
    }

    public void setConfirms(List<String> confirms) {
        this.confirms = confirms;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> w) {
        this.warnings = w;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
