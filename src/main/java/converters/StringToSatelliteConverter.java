package converters;

import javax.transaction.Transactional;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import domain.Satellite;
import repositories.SatelliteRepository;

@Component
@Transactional
public class StringToSatelliteConverter
implements Converter<String, Satellite> {
	@Autowired
	private SatelliteRepository repository;

	@Override
	public Satellite convert(String text)
	{
		Satellite result;

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