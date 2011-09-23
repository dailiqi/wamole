<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>Wamole Index</title>
	<meta content="text/html; charset=UTF-8" http-equiv='Context-Type' />
    <script src='/resource/jquery-1.3.2.js' type='text/javascript'></script>
    <link href="/resource/testsuite.css" type="text/css" rel="stylesheet"/>
</head>
<body>
   	<#include "project/list.ftl">
	<div>
		<span>任务列表：</span>
		<ol>
		</ol>
	</div>
	<a href="/newProject">添加</a>
	
	<div id="bm">
		<#include "browser/list.ftl">
	</div>
</body>
<script type="text/javascript">
	setInterval("refreshBrowserList()" , 5000)
	function refreshBrowserList() {
		$.get("/browser" , new Date() ,function(result) {
			$("#bm").html(result);
		});
	}
</script>
</html>