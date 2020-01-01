package pwd.initializr.article.test.spider;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * pwd.initializr.article.test.spider@ms-web-initializr
 *
 * <h1>TODO what you want to do?</h1>
 *
 * date 2019-12-21 12:17
 *
 * @author DingPengwei[www.dingpengwei@foxmail.com]
 * @version 1.0.0
 * @since DistributionVersion
 */
public class Article {
  public static void main(String[] args) {
    // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    // 参数
    StringBuffer params = new StringBuffer();
    try {
      // 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)
      params.append("name=" + URLEncoder.encode("&", "utf-8"));
      params.append("&");
      params.append("age=24");
    } catch (UnsupportedEncodingException e1) {
      e1.printStackTrace();
    }

    // 创建Get请求
    HttpGet httpGet = new HttpGet("https://novel.zhwenpg.com/r.php?id=267440");
    // 响应模型
    CloseableHttpResponse response = null;
    try {
      // 配置信息
      RequestConfig requestConfig = RequestConfig.custom()
          // 设置连接超时时间(单位毫秒)
          .setConnectTimeout(5000)
          // 设置请求超时时间(单位毫秒)
          .setConnectionRequestTimeout(5000)
          // socket读写超时时间(单位毫秒)
          .setSocketTimeout(5000)
          // 设置是否允许重定向(默认为true)
          .setRedirectsEnabled(true).build();

      // 将上面的配置信息 运用到这个Get请求里
      httpGet.setConfig(requestConfig);

      // 由客户端执行(发送)Get请求
      response = httpClient.execute(httpGet);

      HttpEntity responseEntity = response.getEntity();
      if (response.getStatusLine().getStatusCode() == 200) {
        if (responseEntity != null) {

        }
      }
    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        // 释放资源
        if (httpClient != null) {
          httpClient.close();
        }
        if (response != null) {
          response.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
