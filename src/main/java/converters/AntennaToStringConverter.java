package converters;

import org.springframework.core.convert.converter.Converter;
import javax.transaction.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Component;

import domain.Antenna;

@Component
@Transactional
public class AntennaToStringConverter
implements Converter<Antenna, String> {
	@Override
	public String convert(Antenna antenna)
	{
		String result;

		if (antenna == null) {
			result = null;
		} else {
			result = String.valueOf(antenna.getId());
		}

		return result;
	}
}