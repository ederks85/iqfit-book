<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>IQFit - Het slimme sporten</title>
		<link rel="stylesheet" href="/css/style.css" />
		<script type="text/javascript" src="${contextPath}/js/jquery-1.10.2.min.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				setTimeout('submit()', ${paymentReturnTimeOutCheckDelay});
			});

			function submit() {
				$('#cache').val(new Date().getTime());
				$('#paymentStatusCheckForm').attr('action', '${contextPath}/paymentreturn');
				$('#paymentStatusCheckForm').attr('method', 'get');
				$('#paymentStatusCheckForm').submit();
				$('#paymentStatusCheckForm').unbind('submit');
			}
		</script>
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
					<p class="content-block">
						Payment pending
						<form id="paymentStatusCheckForm">
							<input type="hidden" name="transaction_id" value="${transactionId}" />
							<input type="hidden" id="cache" name="cache" />
						</form>
					</p>
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