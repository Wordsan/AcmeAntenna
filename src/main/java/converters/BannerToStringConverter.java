
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Banner;

@Component
@Transactional
public class BannerToStringConverter implements Converter<Banner, String> {

	@Override
	public String convert(final Banner b) {
		String result;

		if (b == null)
			result = null;
		else
			result = String.valueOf(b.getId());

		return result;
	}
}
