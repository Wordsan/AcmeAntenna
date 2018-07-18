package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

import domain.Actor;
import repositories.ActorRepository;

@Component
@Transactional
public class StringToActorConverter
implements Converter<String, Actor> {
	@Autowired
	private ActorRepository repository;

	@Override
	public Actor convert(String text)
	{
		Actor result;

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