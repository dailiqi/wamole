<div id="browserlist">
	<#if browsers?exists>
		<#list browsers as browser>
			<img src="/resource/img/${browser.name}.sm.png" title="${browser.name}${browser.version},ip:${browser.ip},OS:${browser.os}"/>
			<#if browser.active>
			<#else>
				<div>inactive</div>
			</#if>
		</#list>
	<#else>
		无在注册的浏览器
	</#if>
</div>
<div id="time"></div>
<script type="text/javascript">
	setInterval("refreshTime()", 1000)
	function refreshTime() {
		var dateObj = new Date();
		time.innerHTML = dateObj;
	}
</script>
