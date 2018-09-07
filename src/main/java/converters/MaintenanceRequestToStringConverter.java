package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import domain.MaintenanceRequest;

@Component
@Transactional
public class MaintenanceRequestToStringConverter implements Converter<MaintenanceRequest, String> {

    @Override
    public String convert(final MaintenanceRequest request)
    {
        String result;

        if (request == null) {
            result = null;
        } else {
            result = String.valueOf(request.getId());
        }

        return result;
    }
}
