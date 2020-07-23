package fr.eni.javaee.suividesrepas.bo;

public class Aliment {
    private int identifiant;
    private String nom;

    public Aliment(String nom) {
        setNom(nom);
    }

    public Aliment(int identifiant, String nom) {
        this(nom);
        setIdentifiant(identifiant);
    }

    @Override
    public String toString() {
        return this.nom + "(" + identifiant + ")";
    }


    // GETTERS & SETTERS

    public int getIdentifiant() { return this.identifiant; }

    public void setIdentifiant(int identifiant) { this.identifiant = identifiant; }

    public String getNom() { return this.nom; }

    public void setNom(String nom) { this.nom = nom; }
}
