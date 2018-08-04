package httpClientdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huishen on 17/6/22.
 * 测试所有的demo
 */
public class HttpClientMainTest {

    public static void main(String[] args) {

        execGet("http://www.baidu.com");
        // execPost("http://123.206.202.131:8088/v1/users/login");
        // execGetSSL( "https://api.searchads.apple.com/api/v1/acls");

    }

    private static void execGet(String url) {
        //httpGet
        HttpGet httpGet = new HttpGet(url);
        // httpGet.setHeader("Accept", "text/html, */*; q=0.01");
        // httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        // httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        // httpGet.setHeader("Referer", url);
        // httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:46.0) Gecko/20100101 Firefox/46.0");

        HttpClientUtil.execute(httpGet);
    }

    private static void execPost2(String url) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("username", "vip"));
        nvps.add(new BasicNameValuePair("password", "secret"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        HttpClientUtil.execute(httpPost);
    }

    private static void execPost(String url) {
        //httpPost
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(getHttpEntity());

        HttpClientUtil.execute(httpPost);
    }

    private static void execGetSSL(String url) {
        //httpGet
        HttpGet httpGet = new HttpGet(url);

        HttpClientUtil.execute(httpGet);
    }

    // @SuppressWarnings("Duplicates")
    // private CloseableHttpResponse execPostSSL(CloseableHttpClient httpClient, String url) {
    //
    //     HttpPost httpPost = new HttpPost();
    //
    // }

    private static HttpEntity getHttpEntity() {
        //一个对象
        Map<String, String> map = new HashMap<>();
        map.put("mobile_phone", "13888888888");
        map.put("pwd", "123456");

        //序列化
        String s = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            s = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new StringEntity(s, "utf-8");
    }

    @Test
    public void test() {
        // String url = "http://www.baidu.com";
        // String url = "https://www.google.com";
        // String url = "https://www.facebook.com";
        String url = "https://twitter.com/";
        // String url = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=ethereum&count=2";
        // String url = "https://api.binance.com/api/v1/klines?symbol=BTCUSDT&interval=15m";

        // binance
        // String url = "https://api.binance.com/api/v1/ticker/24hr";

        // bitfinex
        // String url = "https://api.bitfinex.com/v2/tickers?symbols=tMITHUSD%2CtXVGUSD%2CtBCCUSD%2CtLTCBTC%2CtBATETH%2CtMTNBTC%2CtXVGJPY%2CtDAIUSD%2CtSNTETH%2CtELFETH%2CtBCHUSD%2CtTRXBTC%2CtEOSETH%2CtBATUSD%2CtXLMUSD%2CtXLMGBP%2CtETHGBP%2CtMTNETH%2CtMIOTAUSD%2CtQASHBTC%2CtDRKUSD%2CtXVGGBP%2CtAIONUSD%2CtTRXETH%2CtQTMBTC%2CtANTUSD%2CtXVGETH%2CtBT2%2CtETPBTC%2CtTRXUSD%2CtREQETH%2CtBCUUSD%2CtETPETH%2CtAIDETH%2CtSNGLSETH%2CtAIONETH%2CtSNGLSBTC%2CtNEOBTC%2CtMIOTAETH%2CtSANUSD%2CtREPETH%2CtDATAETH%2CtSANETH%2CtDASHUSD%2CtBT2BTC%2CtXVGBTC%2CtQTUMETH%2CtLTCUSD%2CtDRKBTC%2CtRLCETH%2CtZRXETH%2CtBCCBTC%2CtMTNUSD%2CtRLCBTC%2CtSTORJBTC%2CtDAIETH%2CtXLMJPY%2CtEDOBTC%2CtBCHETH%2CtXLMEUR%2CtETHJPY%2CtMITHETH%2CtEOSUSD%2CtRCNBTC%2CtZRXUSD%2CtDTHETH%2CtETCUSD%2CtSANBTC%2CtXRPUSD%2CtDATAUSD%2CtBT1BTC%2CtFUNBTC%2CtXVGEUR%2CtELFBTC%2CtBTGUSD%2CtXRPBTC%2CtMIOTAEUR%2CtDAIBTC%2CtIOTBTC%2CtBCUBTC%2CtMITHBTC%2CtQTUMBTC%2CtQASHETH%2CtBCHBTC%2CtETCBTC%2CtRCNUSD%2CtSTORJETH%2CtSNTBTC%2CtEDOUSD%2CtANTETH%2CtREPUSD%2CtLRCETH%2CtXLMETH%2CtMIOTABTC%2CtELFUSD%2CtDATABTC%2CtZRXBTC%2CtDTHBTC%2CtLRCBTC%2CtXMRUSD%2CtXLMBTC%2CtREQBTC%2CtAVTUSD%2CtBFXBTC%2CtEOSJPY%2CtIOTETH%2CtETHBTC%2CtBATBTC%2CtRCNETH%2CtRRTBTC%2CtOMGUSD%2CtFUNETH%2CtEDOETH%2CtRDNETH%2CtETPUSD%2CtAIDBTC%2CtRRTUSD%2CtNEOETH%2CtBTCUSD%2CtOMGBTC%2CtAVTBTC%2CtETHUSD%2CtGNTETH%2CtNEOUSD%2CtTNBBTC%2CtCFIBTC%2CtSTORJUSD%2CtMIOTAGBP%2CtNEOEUR%2CtQTUMUSD%2CtTNBETH%2CtDASHBTC%2CtBTCGBP%2CtNEOGBP%2CtEOSEUR%2CtBTCEUR%2CtEOSBTC%2CtSNTUSD%2CtREPBTC%2CtETHEUR%2CtAIDUSD%2CtBTCJPY%2CtZECUSD%2CtSNGLSUSD%2CtAVTETH%2CtMIOTAJPY%2CtTH1BTC%2CtRDNBTC%2CtZECBTC%2CtGNTBTC%2CtIOTUSD%2CtDTHUSD%2CtFUNUSD%2CtEOSGBP%2CtSNGETH%2CtCFIETH%2CtBTGBTC%2CtOMGETH%2CtNEOJPY%2CtQASHUSD%2CtBFXUSD%2CtAIONBTC%2CtBT1USD%2CtANTBTC%2CtXMRBTC";

        // bittrex
        // String url = "https://bittrex.com/api/v1.1/public/getmarketsummaries";

        // gateio
        // String url = "http://data.gate.io/api2/1/tickers";

        // hitbtc
        // String url ="https://api.hitbtc.com/api/2/public/ticker";

        // huobi放弃

        // liqui
        // String url = "https://api.liqui.io/api/3/ticker/knc_usdt-snm_eth-oax_usdt-omg_usdt-cfi_usdt-cvc_btc-pro_eth-eth_usdt-qtum_btc-ant_usdt-hmq_btc-dnt_usdt-mln_eth-hmq_eth-ptoy_eth-storj_eth-ae_eth-ast_btc-bcap_btc-dash_usdt-snt_btc-ae_btc-ant_eth-wings_btc-1st_usdt-snt_usdt-snm_btc-lun_eth-vsl_btc-enj_btc-plu_usdt-oax_eth-bmc_eth-eos_usdt-zrx_btc-xid_eth-wings_eth-ldc_btc-gno_btc-bat_eth-neu_btc-lun_usdt-bcap_usdt-eos_eth-adx_eth-vsl_eth-dnt_btc-req_btc-tnt_usdt-cvc_usdt-pay_eth-bmc_usdt-bcap_eth-mana_usdt-trx_eth-golos_btc-rlc_usdt-ae_usdt-gup_usdt-plu_eth-dgd_btc-mana_btc-knc_eth-stx_eth-req_eth-cvc_eth-eng_usdt-bnt_eth-cln_eth-aion_eth-sngls_btc-waves_eth-snm_usdt-eth_btc-bat_btc-qtum_eth-ven_usdt-zrx_eth-gno_eth-incnt_eth-storj_btc-salt_btc-1st_btc-icn_btc-adx_usdt-myst_usdt-taas_eth-srn_btc-san_btc-mln_usdt-eng_eth-ven_eth-ptoy_usdt-net_eth-srn_eth-omg_btc-gnt_eth-wpr_eth-qrl_usdt-ven_btc-cfi_btc-ptoy_btc-time_eth-icn_eth-tnt_eth-incnt_usdt-sbd_btc-steem_btc-1st_eth-dash_eth-time_usdt-waves_usdt-dash_btc-edg_eth-gnt_usdt-adx_btc-trst_usdt-xid_btc-lun_btc-gup_eth-zrx_usdt-round_eth-req_usdt-eng_btc-eos_btc-rep_btc-time_btc-bnt_usdt-plu_btc-bnt_btc-myst_btc-xid_usdt-trx_usdt-edg_usdt-qrl_eth-incnt_btc-ast_eth-salt_usdt-mgo_eth-mana_eth-san_usdt-gnt_btc-tnt_btc-taas_btc-mco_eth-gbg_btc-ltc_btc-gno_usdt-snt_eth-mco_btc-dct_btc-pay_usdt-ast_usdt-btc_usdt-trst_eth-trx_btc-waves_btc-taas_usdt-round_usdt-net_btc-knc_btc-bcc_eth-sngls_usdt-bcc_usdt-tkn_eth-round_btc-gup_btc-qrl_btc-trst_btc-rep_usdt-stx_usdt-bat_usdt-sngls_eth-xzc_btc-tkn_btc-cfi_eth-rep_eth-hmq_usdt-cln_btc-mln_btc-pro_btc-san_eth-myst_eth-mgo_usdt-dgd_usdt-bmc_btc-dnt_eth-rlc_eth-bcc_btc-omg_eth-mco_usdt-rlc_btc-mgo_btc-icn_usdt-oax_btc-aion_btc-qtum_usdt-wings_usdt-wpr_btc-dgd_eth-tkn_usdt-storj_usdt-edg_btc-ant_btc-ltc_usdt-ltc_eth-salt_eth-ans_btc-pay_btc-stx_btc-vsl_usdt";

        // okex
        // String url = "https://www.okex.com/api/v1/ticker.do?symbol=aeternity_BTC";

        // poloniex
        // String url = "https://poloniex.com/public?command=returnTicker";

        // String url = "https://www.okcoin.com/api/v1/ticker.do?symbol=btc_usd";

        HttpGet httpGet = new HttpGet(url);
        HttpClientUtil.execute(httpGet);
    }


}
