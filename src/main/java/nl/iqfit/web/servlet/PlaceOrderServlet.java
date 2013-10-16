package nl.iqfit.web.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.iqfit.logic.facade.OrderFacade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Edwin
 */
@WebServlet("/placeorder")
public class PlaceOrderServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(PlaceOrderServlet.class);

	@Inject OrderFacade orderFacade;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Placing new order");

		request.getRequestDispatcher("/WEB-INF/views/payment/startpayment.jsp").forward(request, response);
	}
}