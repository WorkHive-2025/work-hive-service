package workhive.app.member.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import workhive.app.exception.GeneralException;
import workhive.app.exception.enums.ErrorCode;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class MapToStringConverter implements AttributeConverter<Map<String, Object>, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(Map<String, Object> attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            log.error("MapToStringConverter - Error converting map to string: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INVALID_PARAMETER_ERROR, "입력된 데이터 형식이 올바르지 않습니다.");
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, new TypeReference<>() {});
        } catch (Exception e) {
            log.error("MapToStringConverter - Error converting string to map: {}", e.getMessage());
            throw new GeneralException(ErrorCode.INVALID_PARAMETER_ERROR, "입력된 데이터 형식이 올바르지 않습니다.");
        }
    }
}
