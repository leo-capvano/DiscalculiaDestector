/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.converter;

import entity.enums.Disorder;
import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
@Converter(autoApply = true)
public class DisorderConverter implements AttributeConverter<Disorder, String> {

    @Override
    public String convertToDatabaseColumn(Disorder attribute) {
        if(attribute == null)
            return null;
        
        return attribute.getValue();
    }

    @Override
    public Disorder convertToEntityAttribute(String dbData) {
        if(dbData == null)
            return null;
        
        return Stream.of(Disorder.values())
                .filter(g -> g.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
    
}
