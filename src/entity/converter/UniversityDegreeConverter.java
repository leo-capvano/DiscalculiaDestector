/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.converter;

import entity.enums.UniversityDegree;
import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
@Converter(autoApply = true)
public class UniversityDegreeConverter implements AttributeConverter<UniversityDegree, String> {

    @Override
    public String convertToDatabaseColumn(UniversityDegree attribute) {
        if(attribute == null)
            return null;
        
        return attribute.getValue();
    }

    @Override
    public UniversityDegree convertToEntityAttribute(String dbData) {
        if(dbData == null)
            return null;
        
        return Stream.of(UniversityDegree.values())
                .filter(g -> g.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
    
}
