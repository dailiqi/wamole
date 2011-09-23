<html>
<head>
<title>Qunit List Page---   Project:${project}---</title>
<meta content="text/html; charset=UTF-8" http-equiv='Context-Type' />
    <script type="text/javascript" src="/resource/jquery-1.3.2.js"></script>
    <script type="text/javascript" src="/resource/brtest.js"></script>
    <link href="/resource/list.css" type="text/css" rel="stylesheet"/>
    <script src='/resource/Tangram-1.3.8.js' type='text/javascript'></script>
</head>
<body>
    <div class="main">
        <div id="id_caselist" class="testlist">
      <#if filter?exists>
        <#list kisses as kiss>
        <#if kiss.name?contains(filter)>
	    <a title="${kiss.name}">${kiss.name}</a>
	    </#if>
        </#list>
        <#list noKisses as kiss>
        <#if kiss.name?contains(filter)>
	    <b class="nocase" title="${kiss.name}">${kiss.name}</b><br/>
	    </#if>
        </#list>
      <#else>
        <#list kisses as kiss>
	    <a title="${kiss.name}">${kiss.name}</a>
        </#list>
        <#list noKisses as kiss>
        <b class="nocase" title="${kiss.name}">${kiss.name}</b><br/>
        </#list>
      </#if>
        </div>
        <div class="runningarea"></div>
    </div>
</body>
</html>
