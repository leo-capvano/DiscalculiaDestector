/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.converter;

import entity.enums.Operation;
import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Francesco Capriglione
 * @version 1.0
 */
@Converter(autoApply = true)
public class OperationConverter implements AttributeConverter<Operation, String> {

    @Override
    public String convertToDatabaseColumn(Operation attribute) {
        if(attribute == null)
            return null;
        
        return attribute.getValue();
    }

    @Override
    public Operation convertToEntityAttribute(String dbData) {
        if(dbData == null)
            return null;
        
        return Stream.of(Operation.values())
                .filter(g -> g.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
