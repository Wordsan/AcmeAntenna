package converters;

import javax.transaction.Transactional;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import domain.Administrator;
import repositories.AdministratorRepository;

@Component
@Transactional
public class StringToAdministratorConverter
implements Converter<String, Administrator> {
	@Autowired
	private AdministratorRepository repository;

	@Override
	public Administrator convert(String text)
	{
		Administrator result;

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