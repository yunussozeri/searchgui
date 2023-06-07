/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pm2.zugmv;

/**
 * Rows of CSV Data is stored in this class.
 *
 * @author yunussozeri
 */
public class Row {
    private final String unternehmen, strasse, plz, ort;
    private final boolean personenverkehr, gueterverkehr;
    private static final String JA = "Ja";

    public Row(String unternehmen, String strasse, String plz, String ort, String personenverkehr, String gueterverkehr) {
        this.unternehmen = unternehmen;
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
        this.personenverkehr = personenverkehr.equals(JA);
        this.gueterverkehr = gueterverkehr.equals(JA);
    }

    public String getUnternehmen() {
        return unternehmen;
    }

    public String getStrasse() {
        return strasse;
    }

    public String getPlz() {
        return plz;
    }

    public String getOrt() {
        return ort;
    }

    public String isPersonenverkehr() {
        return personenverkehr? "Ja":"Nein";
    }

    public String isGueterverkehr() {
        return gueterverkehr? "Ja":"Nein";
    }
    
    @Override
    public String toString() {
        return "Row{" + "unternehmen=" + unternehmen + 
                ", strasse=" + strasse +
                ", ort=" + ort + 
                ", PLZ=" + plz + 
                ", gueterverkehr=" + gueterverkehr + 
                ", persoenenverkehr=" + personenverkehr + '}';
    }
    
}
