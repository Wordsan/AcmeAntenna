package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

import domain.Platform;
import repositories.PlatformRepository;

@Component
@Transactional
public class StringToPlatformConverter
        implements Converter<String, Platform>
{
    @Autowired
    private PlatformRepository repository;

    @Override
    public Platform convert(String text)
    {
        Platform result;

        try {
            if (StringUtils.isEmpty(text)) {
                result = null;
            } else {
                int id = Integer.valueOf(text);
                result = repository.findOne(id);
            }
        } catch (Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }
}