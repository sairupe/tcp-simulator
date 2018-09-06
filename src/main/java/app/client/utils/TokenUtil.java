package app.client.utils;

import app.client.data.AppDataHolder;
import app.client.data.RobotDataHolder;
import app.client.data.StatisticHolder;
import app.client.net.task.TaskManager;
import app.client.net.test.Netty4AppClient;
import app.client.net.test.Netty4XbClient;
import app.client.vo.RobotVo;
import app.client.vo.UserVo;
import com.gowild.core.util.HttpUtil;
import com.gowild.core.util.LogUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zh on 2018/9/6.
 */
public class TokenUtil {

    private static CloseableHttpClient httpclient = HttpClientBuilder.create().build();

    private static CloseableHttpAsyncClient syncHttpclient = HttpAsyncClients.createDefault();

    public static String getRobotToken(String mac) {

        String token = "";
        try {
            String tokenUrl = Netty4XbClient.TOKEN_URL;
            HttpPost request = new HttpPost(tokenUrl);

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            NameValuePair macParam = new BasicNameValuePair("mac", mac);
            NameValuePair snParam = new BasicNameValuePair("serial_no", mac);
            NameValuePair clientIdParam = new BasicNameValuePair("client_id", "e3383afbf2d444cdba823c1a8f25ec12");
            list.add(macParam);
            list.add(snParam);
            list.add(clientIdParam);

            CloseableHttpResponse response = null;
            request.setEntity(new UrlEncodedFormEntity(list));
//            long start = System.currentTimeMillis();
            response = httpclient.execute(request);
//            System.out.println("=====>>> time " + (System.currentTimeMillis() - start));

            token = EntityUtils.toString(response.getEntity(), "utf8");
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.error(e);
        }

        JSONObject result = new JSONObject(token);
        int code = result.getInt("code");
        if (10100100 == code) {
            JSONObject data = result.getJSONObject("data");
            token = data.getString("access_token");
            StatisticHolder.incRobotGetToken();
            return token;
        }
        return null;
    }

    public static String getAppToken(String account) {
        Map<String, String> header = new HashMap<>();
        Map<String, String> param = new HashMap<>();
        param.put("mobile", account);
        param.put("captcha", "8888");
        param.put("client_id", "4403d3900fe74a3cb3df660d249ead0f");
        String repsonse2 = null;
        String url = Netty4AppClient.TOKEN_URL;
        try {
            repsonse2 = HttpUtil.sendWebRequestByForm(null, url, header, param, null);
            JSONObject result = new JSONObject(repsonse2);
            int code = result.getInt("code");
            if (10100100 == code) {
                JSONObject data = result.getJSONObject("data");
                String loginToken = data.get("access_token").toString();
                StatisticHolder.incAppGetToken();
                return loginToken;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void syncGetRobotToken(RobotVo robotVo) {

        String token = "";
        String mac = robotVo.getMac();
        try {
            String tokenUrl = Netty4XbClient.TOKEN_URL;
            HttpPost request = new HttpPost(tokenUrl);

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            NameValuePair macParam = new BasicNameValuePair("mac", mac);
            NameValuePair snParam = new BasicNameValuePair("serial_no", mac);
            NameValuePair clientIdParam = new BasicNameValuePair("client_id", "e3383afbf2d444cdba823c1a8f25ec12");
            list.add(macParam);
            list.add(snParam);
            list.add(clientIdParam);

            CloseableHttpResponse response = null;
            request.setEntity(new UrlEncodedFormEntity(list));
//                long start = System.currentTimeMillis();
            syncHttpclient.execute(request, new FutureCallback<HttpResponse>() {

                public void completed(final HttpResponse response) {
                    System.out.println(" callback thread id is : " + Thread.currentThread().getId());
                    System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
                    try {
                        String token = EntityUtils.toString(response.getEntity(), "UTF-8");
                        JSONObject result = new JSONObject(token);
                        int code = result.getInt("code");
                        if (10100100 == code) {
                            JSONObject data = result.getJSONObject("data");
                            token = data.getString("access_token");
                            robotVo.setToken(token);
                            StatisticHolder.incRobotGetToken();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                public void failed(final Exception ex) {
                    System.out.println(request.getRequestLine() + "->" + ex);
                    System.out.println(" callback thread id is : " + Thread.currentThread().getId());
                }

                public void cancelled() {
                    System.out.println(request.getRequestLine() + " cancelled");
                    System.out.println(" callback thread id is : " + Thread.currentThread().getId());
                }

            });
//                System.out.println("=====>>> time " + (System.currentTimeMillis() - start));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String syncGetAppToken(UserVo userVo) {
        String account = userVo.getUserName();
        Map<String, String> header = new HashMap<>();
        Map<String, String> param = new HashMap<>();
        param.put("mobile", account);
        param.put("captcha", "8888");
        param.put("client_id", "4403d3900fe74a3cb3df660d249ead0f");
        String repsonse2 = null;
        String url = Netty4AppClient.TOKEN_URL;
        try {
            repsonse2 = HttpUtil.sendWebRequestByForm(null, url, header, param, null);
            JSONObject result = new JSONObject(repsonse2);
            int code = result.getInt("code");
            if (10100100 == code) {
                JSONObject data = result.getJSONObject("data");
                String loginToken = data.get("access_token").toString();
                StatisticHolder.incAppGetToken();
                return loginToken;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void initialAllRobotToken() {
        Map<Integer, RobotVo> id2RotbotVoMap = RobotDataHolder.getId2RotbotVoMap();
        for (Map.Entry<Integer, RobotVo> entry : id2RotbotVoMap.entrySet()) {
//            // 同步
//            String mac = entry.getValue().getMac();
//            String token = getRobotToken(mac);
//            entry.getValue().setToken(token);
            // 异步
            syncGetRobotToken(entry.getValue());
        }
    }

    public static void initialAllAppToken() {
        Map<Integer, UserVo> id2UserVoMap = AppDataHolder.getId2UserVoMap();
        for (Map.Entry<Integer, UserVo> entry : id2UserVoMap.entrySet()) {
            String userName = entry.getValue().getUserName();
            String token = getAppToken(userName);
            entry.getValue().setToken(token);
        }
    }
}
