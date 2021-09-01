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
public enum School implements Convertible {
    
    SCIENTIFIC_HIGH_SCHOOL("Liceo scientifico"),
    LINGUISTIC_HIGH_SCHOOL("Liceo linguistico"),
    CLASSICAL_HIGH_SCHOOL("Liceo classico"),
    ARTISTIC_HIGH_SCHOOL("Liceo artistico"),
    TECHNICAL_INSTITUTE("Istituto tecnico"),
    ECONOMIC_INSTITUTE("Istituto economico"),
    PROFESSIONAL_INSTITUTE("Istituto professionale"),
    MIDDLE_SCHOOL_DIPLOMA("Licenza media"),
    PRIMARY_SCHOOL_DIPLOMA("Licenza elementare");
    
    private String value;
    
    private School(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
    
    @Override
    public Convertible[] getValues() {
        return School.values();
    }
}
