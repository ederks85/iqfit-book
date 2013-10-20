package nl.iqfit.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PaymentReportServlet
 */
@WebServlet("/idealpaymentreport")
public class IDealPaymentReportServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// parse payment report

		// call backend to update database if necessary

		// call backend to retrieve payment details

		// call backend to update order with retrieved payment details
	}
}