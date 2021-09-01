/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.converter;

import entity.enums.Qualification;
import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
@Converter(autoApply = true)
public class QualificationConverter implements AttributeConverter<Qualification, String>{

    @Override
    public String convertToDatabaseColumn(Qualification attribute) {
        if(attribute == null)
            return null;
        
        return attribute.getValue();
    }

    @Override
    public Qualification convertToEntityAttribute(String dbData) {
        if(dbData == null)
            return null;
        
        return Stream.of(Qualification.values())
                .filter(g -> g.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
    
}
