package converters;

import org.springframework.core.convert.converter.Converter;
import javax.transaction.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Component;

import domain.Satellite;

@Component
@Transactional
public class SatelliteToStringConverter
implements Converter<Satellite, String> {
	@Override
	public String convert(Satellite satellite)
	{
		String result;

		if (satellite == null) {
			result = null;
		} else {
			result = String.valueOf(satellite.getId());
		}

		return result;
	}
}