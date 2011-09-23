<?xml version="1.0" encoding="UTF-8"?>
<testsuites>
<#list browsers as browser>
    <#assign failures = 0>
    <#assign total = 0>
    <#assign time = 0>
    <#list results[browser_index] as result>
    	<#if result?exists>
    	<#if result.fail!=0>
    		<#assign failures = failures + 1>
    	</#if>
        <#assign total = result.total + total>
        <#assign time = result.timeStamp + time> 
        <#else>
        <#assign failures = 1 + failures>
        <#assign total = 1 + total>
        </#if>
    </#list>
    <testsuite name="${browser}" tests="${results[browser_index]?size}" time="#{time/1000}" failures="${failures}" total="#{total}">
    <#list results[browser_index] as result>
    <#if result?exists>
        <#if (result.fail > 0)>
        <testcase name="${result.name}" time="${result.timeStamp/1000}" fail="${result.fail}" total="${result.total}">
            <failure type="junit.framework.AssertionFailedError">test error</failure>
        </testcase>
        <#else>
        <testcase name="${result.name}" time="${result.timeStamp/1000}" fail="${result.fail}" total="${result.total}" />
        </#if>
    <#else>
        <testcase name="${kisses[result_index].name}" time="0" fail="1" total="1">
   	        <failure type="junit.framework.AssertionFailedError">have no result</failure>
   		</testcase>
    </#if>
    </#list>
    </testsuite>
</#list>
</testsuites>