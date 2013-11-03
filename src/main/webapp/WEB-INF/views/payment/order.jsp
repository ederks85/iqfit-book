<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>IQFit - Het slimme sporten</title>
		<link rel="stylesheet" href="/css/style.css" />
	</head>
	<body>
		<div id="wrapper">
			<header>
				<div id="header-logo"></div>
				<h1>Het slimme sporten</h1>
			</header>
			<div id="content">
				<section>
					<h2 class="content-title">Betalen</h2>
					<p>
						Order overzicht hier!
					</p>
					<p class="content-block">
						Kies hieronder a.u.b. je gewenste betaalmethode om het e-book te kopen. 
					</p>
					<form id="selectBankForm" name="selectBankForm" method="post" action="/placeorder">
						<br class="clearBoth" />
						<label for="firstName">Voornaam:</label>
						<input class="formField" type="text" id="firstName" name="firstName" />
						<br class="clearBoth" />

						<label for="middleName">Tussenvoegsel:</label>
						<input class="formField" type="text" id="middleName" name="middleName" />
						<br class="clearBoth" />

						<label for="surName">Achternaam:</label>
						<input class="formField" type="text" id="surName" name="surName" />
						<br class="clearBoth" />

						<label for="emailAddress">Email adres:</label>
						<input class="formField" type="email" id="emailAddress" name="emailAddress" />
						<br class="clearBoth" />

						<label for="bankList">Kies uw bank:</label>
						<select id="bankList" name="bankList">
							<c:forEach var="bank" items="${bankList}">
								<option value="${bank.bankId}">${bank.bankName}</option>
							</c:forEach>
						</select>
						<br class="clearBoth" />

						<input type="submit" value="Verzenden" />
					</form>
				</section>
				<aside>
					<div id="newsletter">
						<h3 class="content-title">Nieuwsbrief</h3>
						<p class="content-block">
							Ben je geïnteresseerd om ontwikkelingen te volgen via de nieuwsbrief? Vul dan hier je emailadres in:
						</p>
						<form>
							<label id="newsletter-email-label" for="newsletter-email">Email:<input id="newsletter-email" name="newsletter-email" type="email" /></label>
						</form>
					</div>
				</aside>
			</div>
			<hr />
			<footer>
				<div id="contact-info">
					www.iqfit.nl<br />
					Teststraat 12<br />
					3333 AA Tilburg, Nederland<br />
					<br />
					Tel: 0699999999<br />
					Email: info@iqfit.nl
				</div>
				<div id="copyright">
					Copyright 2013
				</div>
			</footer>
		</div>
	</body>
</html>