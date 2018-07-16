package converters;

import javax.transaction.Transactional;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import domain.Tutorial;
import repositories.TutorialRepository;

@Component
@Transactional
public class StringToTutorialConverter
implements Converter<String, Tutorial> {
	@Autowired
	private TutorialRepository repository;

	@Override
	public Tutorial convert(String text)
	{
		Tutorial result;

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