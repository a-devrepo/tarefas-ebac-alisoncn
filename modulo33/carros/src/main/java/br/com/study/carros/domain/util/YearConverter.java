package br.com.study.carros.domain.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Year;

@Converter(autoApply = true)
public class YearConverter implements AttributeConverter<Year, Short> {
    @Override
    public Short convertToDatabaseColumn(Year attribute) {
        short year = (short) attribute.getValue();
        return year;
    }

    @Override
    public Year convertToEntityAttribute(Short dbValue) {
        Year year = Year.of(dbValue);
        return year;
    }
}
