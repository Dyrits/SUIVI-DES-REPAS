package fr.eni.javaee.suividesrepas;

import java.util.ArrayList;
import java.util.List;

public class RepasException extends Exception{
    private List<Integer> codes;

    public RepasException() {
        super();
        this.codes = new ArrayList<>();
    }

    public RepasException(int code) {
        super();
        this.codes = new ArrayList<>();
        this.codes.add(code);
    }

    public RepasException(String message, RepasException exception) {
        super(message);
        this.codes = new ArrayList<>();
        this.codes.addAll(exception.getCodes());
    }

    public void addCode(int code) { if (!this.codes.contains(code)) { this.codes.add(code); } }

    public boolean hadCodes() { return this.codes.size() > 0; }

    public List<Integer> getCodes() { return this.codes; }

    @Override
    public String getMessage() {
        return "Repas Exception | " + super.getMessage();
    }
}
