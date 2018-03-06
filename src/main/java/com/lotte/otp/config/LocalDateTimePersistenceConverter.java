package com.lotte.otp.config;

import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by choi on 2018. 3. 6. AM 2:13.
 */
@Converter(autoApply = true)
public class LocalDateTimePersistenceConverter implements AttributeConverter<LocalDateTime, Date> {
    private static final ZoneId ZONE_SEOUL = ZoneId.of("Asia/Seoul");

    @Override
    public Date convertToDatabaseColumn(LocalDateTime attribute) {
        if (attribute == null) {
            attribute = LocalDateTime.now();
        }
        return Date.from(attribute.atZone(ZONE_SEOUL).withZoneSameInstant(ZONE_SEOUL).toInstant());
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Date dbData) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dbData.getTime()), ZONE_SEOUL);
    }
}
