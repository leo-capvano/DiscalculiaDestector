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
public enum Disorder implements Convertible {
    
    YES("Si"),
    NO("No"),
    UNKNOWN("Sconosciuto");

    private String value;
    
    private Disorder(String value) {
        this.value = value;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Convertible[] getValues() {
        return Disorder.values();
    }
    
}
