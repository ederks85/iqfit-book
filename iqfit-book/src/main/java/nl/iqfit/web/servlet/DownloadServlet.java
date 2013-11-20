package nl.iqfit.web.servlet;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.iqfit.core.configuration.IQFitConfig;
import nl.iqfit.core.configuration.IQFitConfigurationFactory;
import nl.iqfit.core.dto.OrderDataDTO;
import nl.iqfit.core.util.RequestHelper;
import nl.iqfit.logic.facade.OrderFacade;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/ebook-download")
public class DownloadServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(DownloadServlet.class);

	@Inject OrderFacade orderFacade;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//TODO: dit op alle servlets doen
		logger.info("Download servlet called with request parameters: {}", new RequestHelper().getRequestParametersToString(request));

		IQFitConfig config = new IQFitConfigurationFactory().getIQFitConfig();

		final String orderNumber = request.getParameter("orderNumber");
		if (StringUtils.isBlank(orderNumber)) {
			logger.warn("Download servlet called with INVALID order number: {}", orderNumber);
			response.sendError(404);
			return;
		}

		final OrderDataDTO order = this.orderFacade.getOrderByOrderNumber(orderNumber);
		if (order == null) {
			logger.warn("No order found for order number: {}", orderNumber);
			response.sendError(404);
			return;
		}

		// If the ebook has been downloaded before, the download is not available anymore
		if (order.isDownloaded()) {
			logger.warn("Download servlet called to download order {}, which has been marked as downloaded.", orderNumber);
			response.sendError(404);
			return;
		}

		// At this point, the download is approved.
		InputStream ebookInputStream = getServletContext().getResourceAsStream(config.getIqFitEbookPath());
		if (ebookInputStream == null) {
			logger.error("EBook file not found at path: {}", config.getIqFitEbookPath());
			response.sendError(500);
			return;
		}

		// Prepare response
		response.setContentType("application/x-download");
		response.setHeader("Content-Disposition", "attachment; filename=iqfit-ebook-test.txt"); //TODO clean this up (and maybe add version?)

		// Stream the EBook contents to client
		logger.info("Started streaming EBook contents for order: {}", orderNumber);
		int temp;
		byte[] buf = new byte[1024];
		while ((temp = ebookInputStream.read(buf)) != -1) {
			response.getOutputStream().write(buf, 0, temp);
		}

		// If the EBook has been successfully transferred, we can mark the order as downloaded
		logger.info("Successfully streamed EBook contents for order: {}", orderNumber);
		order.setDownloaded(true);
		this.orderFacade.updateOrder(order);
	}
}