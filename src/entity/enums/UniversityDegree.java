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
public enum UniversityDegree implements Convertible {
    HUMANISTIC_SOCIAL("Umanistica-Sociale"),
    SCIENTIFIC_TECNOLOGICAL("Scientifica-Tecnologica"),
    SANITARY("Sanitaria");
    
    private String value;
    
    private UniversityDegree(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Convertible[] getValues() {
        return UniversityDegree.values();
    }
}
