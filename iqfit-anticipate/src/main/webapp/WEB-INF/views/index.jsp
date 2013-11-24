<!DOCTYPE html>
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
					<h2 class="content-title">IQFit book anticipate</h2>
					<p class="content-block">
						Under construction... 
					</p>
				</section>
				<aside>
					<div id="newsletter">
						<h3 class="content-title">Nieuwsbrief</h3>
						<p  class="content-block">
							Ben je ge&iuml;nteresseerd om ontwikkelingen te volgen via de nieuwsbrief? Vul dan hier je emailadres in:
						</p>
						<form method="post" action="${contextPath}/email-registration">
							<label id="newsletter-email-label" for="email-input">Email:</label>
							<!-- <input id="email-input" name="emailAddress" type="email" required="required" />-->
							<input id="email-input" name="emailAddress" />
							<input type="submit" value="Submit" type="email"  />
						</form>
					</div>
					<div id="purchase" class="rotating">
						<h3 class="content-title">E-book Kopen</h3>
						<form method="post" action="/orderpage">
							<input type="submit" value="koop" />
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