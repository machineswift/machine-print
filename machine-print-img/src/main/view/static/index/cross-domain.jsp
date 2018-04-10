<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="java.util.* , java.net.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>print</title>
		<script type="text/javascript">
			<%request.setCharacterEncoding( "UTF-8" );
		String printMessage = request.getParameter("printmessage");%>
			var message =  '<%=printMessage%>';
			window.onload = function() {
				var xmlhttp = null;
				if (window.XMLHttpRequest) {
					// code for all new browsers
					xmlhttp = new XMLHttpRequest();
				} else if (window.ActiveXObject) {
					// code for IE5 and IE6
					xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
				}
				if (xmlhttp != null) {
					xmlhttp
							.open("POST", "http://localhost:8080/print/elePrintMessage",
									false);
					xmlhttp.send(message);
				} else {
					alert("Your browser does not support XMLHTTP.");
				}
			}
		</script>
	</head>
	<body>
	</body>
</html>
