package nl.iqfit.core.configuration;

import java.net.URL;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IQFitConfigurationFactory {

	private static final Logger logger = LoggerFactory.getLogger(IQFitConfigurationFactory.class);

	//TODO optimize this in static initialization
	public IQFitConfig getIQFitConfig() {
		URL configLocation = ConfigurationUtils.locate("META-INF/config.xml");

		try {
			DefaultConfigurationBuilder cf = new DefaultConfigurationBuilder(configLocation);
			Configuration config = cf.getConfiguration(true);

			cf.setThrowExceptionOnMissing(true);

			logger.debug("Loaded configuration:\n{}", ConfigurationUtils.toString(config));

			return new IQFitConfig(config);
		} catch (ConfigurationException e) {
			final String message = "Error while building IQFit configuration";

			logger.error(message, e);
			throw new RuntimeException(message, e);
		}
	}
}