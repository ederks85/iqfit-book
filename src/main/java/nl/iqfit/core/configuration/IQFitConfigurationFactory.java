package nl.iqfit.core.configuration;

import java.net.URL;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IQFitConfigurationFactory {

	private static final Logger logger = LoggerFactory.getLogger(IQFitConfigurationFactory.class);

	private static volatile boolean loaded = false;
	//TODO optimize this in static initialization
	public IQFitConfig getIQFitConfig() {
		URL configLocation = ConfigurationUtils.locate("META-INF/config.xml");

		try {
			DefaultConfigurationBuilder cf = new DefaultConfigurationBuilder(configLocation);
			cf.setThrowExceptionOnMissing(true);

			CombinedConfiguration config = cf.getConfiguration(true);
			Configuration interpolatedConfig = config.interpolatedConfiguration();

			if (!loaded) {
				logger.debug("Loaded interpolated configuration:\n{}", ConfigurationUtils.toString(interpolatedConfig));
				loaded = true;
			}

			return new IQFitConfig(interpolatedConfig);
		} catch (ConfigurationException e) {
			final String message = "Error while building IQFit configuration";

			logger.error(message, e);
			throw new RuntimeException(message, e);
		}
	}
}