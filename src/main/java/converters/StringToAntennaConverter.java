package converters;

import javax.transaction.Transactional;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import domain.Antenna;
import repositories.AntennaRepository;

@Component
@Transactional
public class StringToAntennaConverter
implements Converter<String, Antenna> {
	@Autowired
	private AntennaRepository repository;

	@Override
	public Antenna convert(String text)
	{
		Antenna result;

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