<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>Qunit Run Page Case:${kiss.name}</title>
<meta content="text/html; charset=UTF-8" http-equiv='Context-Type' />
    <script src='/resource/Tangram-1.3.8.js' type='text/javascript'></script>
    <script src='/resource/jquery-1.3.2.js' type='text/javascript'></script>
    <script src='/resource/testrunner.js' type='text/javascript'></script>
    <script src='/resource/ext_qunit.js' type='text/javascript'></script>
    <link href="/resource/testsuite.css" type="text/css" rel="stylesheet"/>
    <#list resources as resource>
    <script src='${resource}' type='text/javascript'></script>
    </#list>
</head>
<body>
    <h1 id='qunit-header'></h1>
    <h2 id='qunit-banner'></h2>
    <h2 id='qunit-userAgent'></h2>
    <ol id='qunit-tests'>
    </ol>
</body>
</html>