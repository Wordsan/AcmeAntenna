package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

import domain.PlatformSubscription;
import repositories.PlatformSubscriptionRepository;

@Component
@Transactional
public class StringToPlatformSubscriptionConverter
        implements Converter<String, PlatformSubscription>
{
    @Autowired
    private PlatformSubscriptionRepository repository;

    @Override
    public PlatformSubscription convert(String text)
    {
        PlatformSubscription result;

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