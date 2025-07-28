freemarker template test:
string test-----------${user}-----------${number}-----------${latestProduct.url}-----------${latestProduct.name}
condition test-----------
<#if user == "Big Joe">
list iterator-----------
<#list list as aa>
${aa}
</#list> 
</#if>
date test-----------${date?string("MMM/dd/yyyy")}