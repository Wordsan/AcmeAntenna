
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.MaintenanceRequestRepository;
import domain.MaintenanceRequest;

@Component
@Transactional
public class StringToMaintenanceRequestConverter implements Converter<String, MaintenanceRequest> {

	@Autowired
	private MaintenanceRequestRepository	repository;


	@Override
	public MaintenanceRequest convert(final String text) {
		MaintenanceRequest result;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				final int id = Integer.valueOf(text);
				result = this.repository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
