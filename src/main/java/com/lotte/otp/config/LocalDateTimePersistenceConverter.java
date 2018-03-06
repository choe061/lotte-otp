package com.lotte.otp.config;

import com.lotte.otp.util.DateUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by choi on 2018. 3. 6. AM 2:13.
 */
@Converter(autoApply = true)
public class LocalDateTimePersistenceConverter implements AttributeConverter<LocalDateTime, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDateTime attribute) {
        if (attribute == null) {
            attribute = LocalDateTime.now();
        }
        return Date.from(attribute.atZone(DateUtils.ZONE_SEOUL).withZoneSameInstant(DateUtils.ZONE_SEOUL).toInstant());
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Date dbData) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dbData.getTime()), DateUtils.ZONE_SEOUL);
    }
}
