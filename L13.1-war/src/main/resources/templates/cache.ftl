<#include "base.ftl">
<#macro header>
    <meta http-equiv="refresh" content="5" >
</#macro>
<#macro content>
<h1>Cache metrics: </h1>
<table class="table">
    <thead class="thead-inverse">
    <tr>
        <th>Metric</th>
        <th class="text-center">Value</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <th scope="row">Cache Hit</th>
        <td class="text-center">${cacheHitCount}</td>
    </tr>
    <tr>
        <th scope="row">Cache Miss</th>
        <td class="text-center">${cacheMissCount}</td>
    </tr>
    <tr>
        <th scope="row">Cache Hit Ratio</th>
        <td class="text-center">${cacheHitRatio}%</td>
    </tr>
    </tbody>
</table>
</#macro>
<@display_base/>
