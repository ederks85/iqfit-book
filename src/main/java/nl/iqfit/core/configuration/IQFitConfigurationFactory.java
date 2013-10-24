package nl.iqfit.core.configuration;

import java.net.URL;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.UnionCombiner;

public class IQFitConfigurationFactory {

	//TODO optimize this in static initialization
	public IQFitConfig getIQFitConfig() throws ConfigurationException {
		//URL configLocation = ConfigurationUtils.locate("META-INF/config.xml");
		URL configLocation = ConfigurationUtils.locate("META-INF/config/default-iqfit-config.xml");
		XMLConfiguration xmlConfig = new XMLConfiguration(configLocation);
		xmlConfig.load();
		
		CombinedConfiguration combConfig = new CombinedConfiguration(new UnionCombiner());
		combConfig.addConfiguration(xmlConfig);
		combConfig.setThrowExceptionOnMissing(true);
		
		return new IQFitConfig(combConfig);
	}
}