package com.l.demo.util;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by luocheng on 2019/4/9.
 */
public class HttpClientUtils {

    public static void upload(String url, String localFile, Map<String, String> param) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();

// 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost(url);

            File file = new File(localFile);

// 相当于<input type="file" name="file"/>
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", new FileInputStream(file), ContentType.DEFAULT_BINARY, file.getName());
            for (Map.Entry<String, String> entry : param.entrySet()) {
                String key = entry.getKey();
// 相当于<input type="text" name="userName" value=userName>
                StringBody value = new StringBody(entry.getValue(), ContentType.create("text/plain", Consts.UTF_8));
                builder.addPart(key, value);
            }
            HttpEntity reqEntity = builder.build();

            httpPost.setEntity(reqEntity);

// 发起请求 并返回请求的响应
            response = httpClient.execute(httpPost);

            System.out.println("The response value of token:" + response.getFirstHeader("token"));

// 获取响应对象
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
// 打印响应长度
                System.out.println("Response content length: " + resEntity.getContentLength());
                System.out.println(EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
            }

// 销毁
            EntityUtils.consume(resEntity);
        } catch (Exception e) {
            System.out.println("出错啦...." + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
