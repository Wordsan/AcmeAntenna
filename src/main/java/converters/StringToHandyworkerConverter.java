package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

import domain.Handyworker;
import repositories.HandyworkerRepository;

@Component
@Transactional
public class StringToHandyworkerConverter implements Converter<String, Handyworker> {

    @Autowired
    private HandyworkerRepository repository;


    @Override
    public Handyworker convert(final String text)
    {
        Handyworker result;

        try {
            if (StringUtils.isEmpty(text)) {
                result = null;
            } else {
                final int id = Integer.valueOf(text);
                result = this.repository.findOne(id);
            }
        } catch (final Throwable oops) {
            throw new IllegalArgumentException(oops);
        }

        return result;
    }
}
