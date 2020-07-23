package fr.eni.javaee.suividesrepas.bo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Repas {
    private int identifiant;
    private LocalDate date;
    private LocalTime heure;
    private List<Aliment> aliments;

    public Repas() {
        setAliments(new ArrayList<>());
    }

    public Repas(LocalDate date, LocalTime heure) {
        this();
        setDate(date);
        setHeure(heure);
    }

    public Repas(int identifiant, LocalDate date, LocalTime heure) {
        this(date, heure);
        setIdentifiant(identifiant);
    }

    public Repas(LocalDate date, LocalTime heure, List<Aliment> aliments) {
        this(date, heure);
        setAliments(aliments);
    }

    public Repas(int identifiant, LocalDate date, LocalTime heure, List<Aliment> aliments) {
        this(date, heure, aliments);
        setIdentifiant(identifiant);
    }


    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        toString
                .append("Repas ")
                .append(this.identifiant)
                .append(" effectué le ")
                .append(this.date)
                .append(" à ")
                .append(this.heure)
                .append(":");
        for (Aliment aliment : aliments) {
            toString
                    .append("\n")
                    .append("- ")
                    .append(aliment);
        }
        return toString.toString();
    }


    // GETTERS & SETTERS

    public int getIdentifiant() { return this.identifiant; }

    public void setIdentifiant(int identifiant) { this.identifiant = identifiant; }

    public LocalDate getDate() { return this.date; }

    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getHeure() { return this.heure; }

    public void setHeure(LocalTime heure) { this.heure = heure; }

    public List<Aliment> getAliments() { return this.aliments; }

    public void setAliments(List<Aliment> aliments) { this.aliments = aliments; }
}
