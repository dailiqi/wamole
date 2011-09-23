<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>浏览器注册</title>
	<meta content="text/html; charset=UTF-8" http-equiv='Context-Type' />
    <script src='/resource/jquery-1.3.2.js' type='text/javascript'></script>
    <link href="/resource/testsuite.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript">
    	(function() {
    		var ua = window.navigator.userAgent;
    		//alert(ua + "jquery version:" + $.browser.version);
    		$.post(location.href, {userAgent : ua} ,
				//跳转至capture页面
    			function(result) {
    				if(result == 'false') {
    					$("#message").html("注册失败，该浏览器当前机器已有注册。");		
    				} else {
	    				location.href = "/browser/capture/" + result;
    				}
    			}
    		);
    	})();
    </script>
</head>
<body>
<div id="message"></div>

</body>
</html>