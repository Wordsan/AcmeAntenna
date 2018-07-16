package converters;

import org.springframework.core.convert.converter.Converter;
import javax.transaction.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Component;

import domain.TutorialComment;

@Component
@Transactional
public class TutorialCommentToStringConverter
implements Converter<TutorialComment, String> {
	@Override
	public String convert(TutorialComment tutorialComment)
	{
		String result;

		if (tutorialComment == null) {
			result = null;
		} else {
			result = String.valueOf(tutorialComment.getId());
		}

		return result;
	}
}