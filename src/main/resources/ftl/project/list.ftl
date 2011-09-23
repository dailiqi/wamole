<div>
	<span>项目列表:</span>
	<ol>
		<#list projects as project>
			<li><a href="/project/${project.name}/">${project.name}</a></li>
		</#list>
	</ol>
</div>