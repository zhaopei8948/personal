package online.zhaopei.personal.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import online.zhaopei.personal.configuration.PersonalProp;
import online.zhaopei.personal.jaxb.SfhcData;
import online.zhaopei.personal.jaxb.SfhcPackage;
import online.zhaopei.personal.jaxb.SfhcPackageHead;
import online.zhaopei.personal.transformer.CommonTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.http.Consts;
import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhaopei on 18/5/10.
 */
public final class HttpUtil {

    private static final Log LOGGER = LogFactory.getLog(HttpUtil.class);

    private static String getSign(String sid, String rid, String rtime, String secret) throws Exception {
        Assert.notNull(sid, "sid must not be null");
        Assert.notNull(rid, "rid must not be null");
        Assert.notNull(rtime, "rtime must not be null");
        Assert.notNull(secret, "secret must not be null");
        LOGGER.info("sid:[" + sid + "]\nrid:[" + rid + "]\nrtime:[" + rtime + "]\nsecret:[" + secret + "]");
        return AESUtil.sign(sid + rid + rtime, secret);
    }

    private static String buildGetKeyRequest(String sid, String rid, String rtime, String secret) throws Exception {
        JSONObject request = new JSONObject();
        request.put("gjgxjhpt_sid", sid);
        request.put("gjgxjhpt_rid", rid);
        request.put("gjgxjhpt_rtime", rtime);
        request.put("gjgxjhpt_sign", getSign(sid, rid, rtime, secret));
        return request.toJSONString();
    }

    private static String buildSfhcRequest(PersonalProp personalProp, String rtime, String secret, String requestXml) throws Exception {
        StringBuffer requestData = new StringBuffer();
        requestData.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.tongtech.com/\">");
        requestData.append("<soapenv:Header><tongtechheader>");
        requestData.append("<gjgxjhpt_rid>" + personalProp.getRid() + "</gjgxjhpt_rid>");
        requestData.append("<gjgxjhpt_sid>" + personalProp.getSid() + "</gjgxjhpt_sid>");
        requestData.append("<gjgxjhpt_rtime>" + rtime + "</gjgxjhpt_rtime>");
        requestData.append("<gjgxjhpt_sign>" + getSign(personalProp.getSid(), personalProp.getRid(), rtime, secret) + "</gjgxjhpt_sign>");
        requestData.append("</tongtechheader></soapenv:Header><soapenv:Body>");
        requestData.append("<web:GK_GXFW_SFHC><web:in0>");
        requestData.append("<![CDATA[");
        requestData.append(requestXml);
//        requestData.append("<![CDATA[<PACKAGE><PACKAGEHEAD><FWQQBH>4660c10a-6aef-4a75-8b19-90b3b1b34e2f</FWQQBH>");
//        requestData.append("<QQBWDM>GJXXZX</QQBWDM><QQBWMC>国家信息中心</QQBWMC><SJLYXTDM>22123456</SJLYXTDM>");
//        requestData.append("<SJLYXTMC>河南省数据共享交换平台</SJLYXTMC><JLS>1</JLS><FSSJ>20180403162308</FSSJ>");
//        requestData.append("<QQRGMSFHM>622301198910012198</QQRGMSFHM>");
//        requestData.append("<QQRXM>孙伯伟</QQRXM><QQRDWDM>1141000000518438XA</QQRDWDM><QQRDWMC>河南省工商行政管理局</QQRDWMC>");
//        requestData.append("<QQYWLXDM>00100103601</QQYWLXDM><QQYWLXMC>企业登记</QQYWLXMC><QQYWXTDM>00100103601001</QQYWXTDM>");
//        requestData.append("<QQYWXTMC>河南省企业登记全程电子化</QQYWXTMC></PACKAGEHEAD><DATA><RECORD no=\"1\">");
//        requestData.append("<GMSFHM>654201199108150813</GMSFHM><XM>吴子琦</XM></RECORD></DATA></PACKAGE>]]>");
        requestData.append("]]></web:in0><web:in1>" + personalProp.getSfhcUserName() + "</web:in1><web:in2>" + personalProp.getSfhcPassword() + "</web:in2>");
        requestData.append("</web:GK_GXFW_SFHC></soapenv:Body></soapenv:Envelope>");

        return requestData.toString();
    }

    public static JSONObject requestSfhc(PersonalProp personalProp, CommonTransformer commonTransformer, SfhcPackage sfhcPackage) {
        Assert.notNull(personalProp, "PersonalProp must not be null");
        Assert.notNull(commonTransformer, "CommonTransformer must not be null");
        Assert.notNull(sfhcPackage, "SfhcPackage must not be null");
        String secretPath = personalProp.getSecretFile();
        String requestXml = null;
        String response = null;
        JSONObject result = new JSONObject();
        String url = personalProp.getServiceHost() + personalProp.getSfhcUrl();
        String getKeyUrl = personalProp.getServiceHost() + personalProp.getGetKeyUrl();
        String rtime = System.currentTimeMillis() + "";
        String secret = null;
        boolean requestSecret = true;
        File secretFile = null;
        try {
            secretFile = new File(secretPath);
            if (secretFile.exists()) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(secretFile)));
                String newSecret = bufferedReader.readLine().trim();
                String endTime = bufferedReader.readLine().trim();
                LOGGER.info("endTime = [" + endTime + "] rtime = [" + rtime + "] rtime + 60000 = [" + (Long.valueOf(rtime) + 60000) + "]");
                if (Long.valueOf(rtime) + 60000 < Long.valueOf(endTime)) {
                    secret = newSecret;
                    requestSecret = false;
                }
            }

            if (requestSecret) {
                secret = requestSecret(getKeyUrl, personalProp.getSid(), personalProp.getRid(), rtime, personalProp.getSecret(), secretPath);
            }
            sfhcPackage.getSfhcPackageHead().setFssj(Calendar.getInstance().getTime());
            requestXml = commonTransformer.ObjectToString(sfhcPackage, false);
            response = httpPost(url, buildSfhcRequest(personalProp, rtime, secret, requestXml), ContentType.create("text/xml", Consts.UTF_8));
            if (-1 != response.indexOf("<soap:Fault>")) {
                result.put("success", false);
                int startIdx = response.indexOf("<faultcode>") + 11;
                int endIdx = response.indexOf("</faultcode>", startIdx);
                LOGGER.info(" fault code startIdx=[" + startIdx + "] endIdx=[" + endIdx + "]");
                result.put("faultCode", response.substring(startIdx, endIdx));
                startIdx = response.indexOf("<faultstring>") + 13;
                endIdx = response.indexOf("</faultstring>");
                LOGGER.info(" fault cause startIdx=[" + startIdx + "] endIdx=[" + endIdx + "]");
                result.put("faultCause", response.substring(startIdx, endIdx));
            } else {
                int startIdx = response.indexOf("<ns1:GK_GXFW_SFHCResponse");
                startIdx = response.indexOf("<ns1:out>", startIdx) + 9;
                int endIdx = response.indexOf("</ns1:out>", startIdx);
                LOGGER.info("success startIdx=[" + startIdx + "] endIdx=[" + endIdx + "]");
                String receiptStr = response.substring(startIdx, endIdx);
                String receiptUnescapeStr = StringEscapeUtils.unescapeXml(receiptStr);
                LOGGER.info("receiptStr=[" + receiptStr + "]");
                LOGGER.info("receiptUnescapeStr=" + receiptUnescapeStr + "]");
                SfhcPackage responsePackage = commonTransformer.StringToObject(receiptUnescapeStr, SfhcPackage.class);
                SfhcPackageHead header = responsePackage.getSfhcPackageHead();
                if ("10".equals(header.getFhdm()) || "11".equals(header.getFhdm())) {
                    result.put("success", true);
                    result.put("data", responsePackage.getSfhcData());
                } else {
                    result.put("success", false);
                    result.put("fhdm", header.getFhdm());
                    result.put("fhms", header.getFhms());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("requestSfhc error", e);
        }
        return result;
    }

    private static String requestSecret(String url, String sid, String rid, String rtime, String secret, String secretPath) {
        Assert.notNull(url, "url must not be null");
        Assert.notNull(sid, "sid must not be null");
        Assert.notNull(rid, "rid must not be null");
        Assert.notNull(rtime, "rtime must not be null");
        Assert.notNull(secret, "secret must not be null");
        Assert.notNull(secretPath, "secretPath must not be null");
        String acquiredEncodeSecret = null;
        String acquireSecret = null;
        String secretEndTime = null;
        PrintWriter printWriter = null;
        try {
            String response = httpPost(url, buildGetKeyRequest(sid, rid, rtime, secret), ContentType.APPLICATION_JSON);
            if (!StringUtils.isEmpty(response)) {
                JSONObject mapjson = JSON.parseObject(response);
                if ("0".equals(mapjson.getString("code"))) {
                    acquiredEncodeSecret = mapjson.getJSONObject("data").getString("secret");
                    secretEndTime = mapjson.getJSONObject("data").getString("secretEndTime");
                    LOGGER.info("get secret is ===========[" + acquiredEncodeSecret + "]");
                    acquireSecret = AESUtil.AESDncode(secret, acquiredEncodeSecret);
                    LOGGER.info("get new secret is = [" + acquireSecret + "] endTime = [" + secretEndTime + "]");
                    printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(secretPath)));
                    printWriter.println(acquireSecret);
                    printWriter.write(secretEndTime);
                    printWriter.flush();
                    printWriter.close();
                } else {
                    throw new RuntimeException(mapjson.getString("code") + ":" + mapjson.getString("message"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("requestSecret error", e);
        }
        return acquireSecret;
    }

    public static String httpPost(String url, String data) {
        return httpPost(url, data, "");
    }

    public static String httpPost(String url, String data, String mimeType) {
        return httpPost(url, data, ContentType.create(mimeType, Consts.UTF_8));
    }

    public static String httpPost(String url, String data, ContentType contentType) {
        String result = null;
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            LOGGER.info("http request info = [" + data + "]");
            httpPost.setEntity(new StringEntity(data, contentType));
            CloseableHttpResponse response = client.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
            LOGGER.info("http request response = [" + result + "]");
            if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
                throw new HttpException(response.getStatusLine().getStatusCode() + ":" + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("httpPost error", e);
        }
        return result;
    }
}
