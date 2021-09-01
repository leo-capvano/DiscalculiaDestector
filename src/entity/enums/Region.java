/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.enums;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
public enum Region implements Convertible {
    ABRUZZO("Abruzzo"),
    BASILICATA("Basilicata"),
    CALABRIA("Calabria"),
    CAMPANIA("Campania"),
    EMILIA_ROMAGNA("Emilia-Romagna"),
    FRIULI_VENEZIA_GIULIA("Friuli-Venezia Giulia"),
    LAZIO("Lazio"),
    LIGURIA("Liguria"),
    LOMBARDIA("Lombardia"),
    MARCHE("Marche"),
    MOLISE("Molise"),
    PIEMONTE("Piemonte"),
    PUGLIA("Puglia"),
    SARDEGNA("Sardegna"),
    SICILIA("Sicilia"),
    TOSCANA("Toscana"),
    TRENTINO_ALTO_ADIGE("Trentino Alto Adige"),
    UMBRIA("Umbria"),
    VALLE_D_AOSTA("Valle D'Aosta"),
    VENETO("Veneto");
    
    private String value;
    
    private Region(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Convertible[] getValues() {
        return Region.values();
    }
}
