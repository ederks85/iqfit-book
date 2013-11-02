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
	public IQFitConfig getIQFitConfig() throws ConfigurationException {
		URL configLocation = ConfigurationUtils.locate("META-INF/config.xml");

		DefaultConfigurationBuilder cf = new DefaultConfigurationBuilder(configLocation);
		Configuration config = cf.getConfiguration(true);

		cf.setThrowExceptionOnMissing(true);

		logger.debug("Loaded configuration:\n{}", ConfigurationUtils.toString(config));

		return new IQFitConfig(config);
	}
}