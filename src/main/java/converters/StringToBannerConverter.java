package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

import domain.Banner;
import repositories.BannerRepository;

@Component
@Transactional
public class StringToBannerConverter implements Converter<String, Banner> {

    @Autowired
    private BannerRepository repository;


    @Override
    public Banner convert(final String text)
    {
        Banner result;

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
