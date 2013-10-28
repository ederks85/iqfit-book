package nl.iqfit.core.util;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.Validate;

/**
 * Class with utility methods to parse {@code HttpServletRequest} objects.
 * 
 * @author Edwin
 *
 */
public class RequestHelper {

	public String getRequestParametersToString(final HttpServletRequest request) {
		Validate.notNull(request, "Request is null.");

		StringBuilder sb = new StringBuilder("{");
		for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			sb.append(entry.getKey()).append(":");
			sb.append(Arrays.toString(entry.getValue())).append(",");
		}

		if (sb.length() > 1) {
			sb.setLength(sb.length() - 1);
		}
		return sb.append("}").toString();
	}
}