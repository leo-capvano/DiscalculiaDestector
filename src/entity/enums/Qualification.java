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
public enum Qualification implements Convertible {

    PRIMARY_SCHOOL_DIPLOMA("Licenza elementare"),
    MIDDLE_SCHOOL_DIPLOMA("Licenza media"),
    HIGH_SCHOOL_DIPLOMA("Diploma di istruzione secondaria superiore"),
    UNIVERSITY_DEGREE("Laurea"),
    OTHER("Altro"),
    UNKNOWN("Sconosciuto");

    private String value;
        
    private Qualification(String value) {
        this.value = value;
    }    
    
    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Convertible[] getValues() {
        return Qualification.values();
    }
    
}
