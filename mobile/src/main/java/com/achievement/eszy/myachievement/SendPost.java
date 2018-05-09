package com.achievement.eszy.myachievement;

/**
 * 尹耀强
 * 787760669@qq.com
 * 2018.5.9
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
public class SendPost {
    public String[] fapostq(String Cookie1) throws Exception{
        //访问准备
        URL url = new URL("http://221.234.72.4/jiaowu_eszy/JWXS/cjcx/jwxs_cjcx_like.aspx");
        //post参数
        Map<String,Object> params = new LinkedHashMap<>();
        //开始访问
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("gb2312");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setRequestProperty("Cookie","ASP.NET_SessionId="+Cookie1);
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);
        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "gb2312"));

        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) >= 0;)
            sb.append((char)c);
        String response = sb.toString();
        //System.out.println(response);
        Resolve jx= new Resolve();

        return jx.jiexidata(response);
    }
}
