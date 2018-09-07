package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

import domain.User;
import repositories.UserRepository;

@Component
@Transactional
public class StringToUserConverter
        implements Converter<String, User>
{
    @Autowired
    private UserRepository repository;

    @Override
    public User convert(String text)
    {
        User result;

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