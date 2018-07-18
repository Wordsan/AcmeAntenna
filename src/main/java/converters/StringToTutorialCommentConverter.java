package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

import domain.TutorialComment;
import repositories.TutorialCommentRepository;

@Component
@Transactional
public class StringToTutorialCommentConverter
implements Converter<String, TutorialComment> {
	@Autowired
	private TutorialCommentRepository repository;

	@Override
	public TutorialComment convert(String text)
	{
		TutorialComment result;

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