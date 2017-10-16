<#include "base.ftl">
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
        <td id="cache-hit" class="text-center">--</td>
    </tr>
    <tr>
        <th scope="row">Cache Miss</th>
        <td id="cache-miss" class="text-center">--</td>
    </tr>
    <tr>
        <th scope="row">Cache Hit Ratio</th>
        <td id="cache-ratio" class="text-center">--</td>
    </tr>
    </tbody>
</table>
<script src="/js/ws-api.js"></script>
<script>
    let ws = new WsApi('ws://' + location.host + '/ws');

    ws.connect().then(
            function(){
                let refreshCacheStats = function() {
                    ws.callCommand('GetCacheStats', {}).then(
                            function (response) {
                                document.getElementById('cache-hit').innerText = response.hit;
                                document.getElementById('cache-miss').innerText = response.miss;
                                document.getElementById('cache-ratio').innerText = response.ratio;
                            }
                    ).catch(
                            function (errorCode, errorText) {
                                alert('Error on calling GetCacheStats: ' + errorText);
                            }
                    );
                }

                refreshCacheStats();

                setInterval(
                    refreshCacheStats
                ,3000)
            }
    ).catch(
            function(err){
                alert("Error on connecting to WebSocket " + err);
            }
    );
</script>
</#macro>
<@display_base/>
