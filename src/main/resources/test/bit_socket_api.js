/**
 * Created by huishen on 17/12/17.
 * bitfinex的websocket api
 */

function getTicker() {
    var ws = require('ws');
    var w = new ws('wss://api.bitfinex.com/ws/2');

    w.on('message', function(msg) {console.log(msg)});

    var msg = JSON.stringify({
        event: 'subscribe',
        channel: 'ticker',
        symbol: 'tBTCUSD'
    });

    w.on('open', function () {w.send(msg)} );
}