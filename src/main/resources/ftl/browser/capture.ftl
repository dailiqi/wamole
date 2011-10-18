<html>
<head>
  <title>Capture the browser state!</title>
 	<script type="text/javascript" src="/resource/jquery-1.3.2.js"></script>
 	<script type="text/javascript" src="/resource/Tangram-1.3.8.js"></script>
<body>
	<div>${step}</div>
	<input type="hidden" id="step" value="20000"/>
	<div>The page will get task in <span id="second">a few</span> seconds.</div>
</body>

<!--<script type="text/javascript">
	setInterval(capture , new Number($("#step").val()));
	var lastNotice = 0;
	function capture() {
		$.ajax({
			url : location.pathname,
			type : 'put',
			success : function(result) {
				lastNotice = new Date().getTime();
				if(result) {
				
				}
			}
		})
	}
	setInterval(lastTime, 100);
	function lastTime() {
		var now = new Date().getTime();
		if(lastNotice != 0 ) {
			var step = new Number($("#step").val());
			var next = (step + lastNotice);
			var second = (next - now)/1000;
			$("#second").html(second);
			//
			//alert(document.referrer);
		}
	}
</script>-->
<script type="text/javascript" src="/resource/capture.js"></script>
</html>