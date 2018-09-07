package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import domain.Platform;

@Component
@Transactional
public class PlatformToStringConverter
        implements Converter<Platform, String>
{
    @Override
    public String convert(Platform platform)
    {
        String result;

        if (platform == null) {
            result = null;
        } else {
            result = String.valueOf(platform.getId());
        }

        return result;
    }
}