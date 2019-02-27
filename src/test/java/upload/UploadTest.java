package upload;

import com.alibaba.fastjson.JSON;
import docker.test.controller.Info;
import docker.test.utils.StringUtil;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @Auther: csp
 * @Description:
 * @Date: Created in 2018/12/17 下午1:15
 * @Modified By:
 */
public class UploadTest {




    @Test
    public void sendFileAndParamsByHttpPost() {
        String url =  "http://127.0.0.1:8080/upload/fileUpload2";
        File file = new File("/Users/chenshengpeng/Desktop/文档/灵犀云运营分析平台（作业调度）详细设计说明书-v2.0.0.docx");
        Map<String, String> params = new HashMap<String, String>(1);
        params.put("fileName", "test2");
        sendFileAndParamsByHttpPost(file, url, params);
    }

    @Test
    public void sendFilesAndParamsByHttpPost() throws IOException {
        String url =  "http://127.0.0.1:8080/upload/springUpload2";
        File file = new File("/Users/chenshengpeng/Desktop/文档/灵犀云运营分析平台（作业调度）详细设计说明书-v2.0.0.docx");
        Map<String, String> params = new HashMap<String, String>(1);
        File file2 = new File("/Users/chenshengpeng/Desktop/文档/新亚驾校科目二考试地形.doc");
        List<File> files = new ArrayList<File>();
        files.add(file);
        files.add(file2);
        params.put("fileName", "springUpload");
        sendFilesAndParamsByHttpPost(files, url, params);
    }

    @Test
    public void sendFiles2AndParamsByHttpPost() throws IOException {
        String url =  "http://127.0.0.1:8080/upload/springUpload2";
        File file = new File("/Users/chenshengpeng/Desktop/文档/灵犀云运营分析平台（作业调度）详细设计说明书-v2.0.0.docx");
        Map<String, String> params = new HashMap<String, String>(1);
        File file2 = new File("/Users/chenshengpeng/Desktop/文档/新亚驾校科目二考试地形.doc");
        List<File> files = new ArrayList<File>();
        files.add(file);
        files.add(file2);
        params.put("fileName", "springUpload");
        sendFiles2AndParamsByHttpPost(files, url, params);
    }


    @Test
    public void sendStreamAndParamsByHttpPost() throws IOException {
        String url =  "http://127.0.0.1:8080/upload/springUpload";
        Map<String, String> params = new HashMap<String, String>(1);
        params.put("fileName", "test3.txt");
        String str = "Testing 1-2-3 \n\r"
            + " do someting";
        byte[] array = str.getBytes("UTF-8");
        InputStream inputStream = new ByteArrayInputStream(array);
        sendStreamAndParamsByHttpPost(inputStream, url, params);
    }


    /**
     * @param inputStream
     * @param url
     * @param params
     */
    public static void sendStreamAndParamsByHttpPost(InputStream inputStream, String url, Map<String, String> params)
        throws IOException {
        CloseableHttpClient client = HttpClients.custom().build();
        CloseableHttpResponse resp = null;
        try {
            HttpPost post = new HttpPost(url);
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                // multipartEntityBuilder.addTextBody不设置编码可能会中文乱码
                //				multipartEntityBuilder.addTextBody(key, value);
                multipartEntityBuilder.addTextBody(key, value, ContentType.TEXT_PLAIN.withCharset("utf-8"));
            }

            multipartEntityBuilder.addBinaryBody("file", inputStream, ContentType.MULTIPART_FORM_DATA, "write.txt");

            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            multipartEntityBuilder.setContentType(ContentType.MULTIPART_FORM_DATA);
            //            builder.addBinaryBody("file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, file.getOriginalFilename());
            HttpEntity entity = multipartEntityBuilder
                .setCharset(Charset.forName("UTF-8"))
                .build();
            post.setEntity(entity);
            resp = client.execute(post);
            int status = resp.getStatusLine().getStatusCode();
            if (status == 200) {
                Logger.getLogger("HttpClientUtil").info("上传成功");
            } else {
                Logger.getLogger("HttpClientUtil").info("上传失败");
            }

            System.out.println(StringUtil.parseResponseToStr(resp));
        } catch (ClientProtocolException e) {
            System.out.println(StringUtil.parseResponseToStr(resp));
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @param files
     * @param url
     * @param params
     */
    public static void sendFilesAndParamsByHttpPost(List<File> files, String url, Map<String, String> params)
        throws IOException {
        CloseableHttpClient client = HttpClients.custom().build();
        CloseableHttpResponse resp = null;
        try {
            HttpPost post = new HttpPost(url);
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                // multipartEntityBuilder.addTextBody不设置编码可能会中文乱码
                //				multipartEntityBuilder.addTextBody(key, value);
                multipartEntityBuilder.addTextBody(key, value, ContentType.TEXT_PLAIN.withCharset("utf-8"));
            }

            files.forEach(file -> {
                multipartEntityBuilder.addBinaryBody("file" + file.getName().hashCode() , file);
            });
            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            multipartEntityBuilder.setContentType(ContentType.MULTIPART_FORM_DATA);
            //            builder.addBinaryBody("file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, file.getOriginalFilename());
            HttpEntity entity = multipartEntityBuilder
                .setCharset(Charset.forName("UTF-8"))
                .build();
            post.setEntity(entity);
            resp = client.execute(post);
            int status = resp.getStatusLine().getStatusCode();
            if (status == 200) {
                Logger.getLogger("HttpClientUtil").info("上传成功");
            } else {
                Logger.getLogger("HttpClientUtil").info("上传失败");
            }

            System.out.println(StringUtil.parseResponseToStr(resp));
        } catch (ClientProtocolException e) {
            System.out.println(StringUtil.parseResponseToStr(resp));
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @param files
     * @param url
     * @param params
     */
    public static void sendFiles2AndParamsByHttpPost(List<File> files, String url, Map<String, String> params)
        throws IOException {
        CloseableHttpClient client = HttpClients.custom().build();
        CloseableHttpResponse resp = null;
        try {
            HttpPost post = new HttpPost(url);
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                // multipartEntityBuilder.addTextBody不设置编码可能会中文乱码
                //				multipartEntityBuilder.addTextBody(key, value);
                multipartEntityBuilder.addTextBody(key, value, ContentType.TEXT_PLAIN.withCharset("utf-8"));
            }

            multipartEntityBuilder.addTextBody("info", JSON.toJSONString(new Info("测试中文", "19999")), ContentType.APPLICATION_JSON.withCharset("utf-8"));

            files.forEach(file -> {
                multipartEntityBuilder.addBinaryBody("files" , file);
            });
            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            multipartEntityBuilder.setContentType(ContentType.create("multipart/form-data", Consts.UTF_8));
            //            builder.addBinaryBody("file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, file.getOriginalFilename());
            HttpEntity entity = multipartEntityBuilder
                .setCharset(Charset.forName("UTF-8"))
                .build();
            post.setEntity(entity);
            resp = client.execute(post);
            int status = resp.getStatusLine().getStatusCode();
            if (status == 200) {
                Logger.getLogger("HttpClientUtil").info("上传成功");
            } else {
                Logger.getLogger("HttpClientUtil").info("上传失败");
            }

            System.out.println(StringUtil.parseResponseToStr(resp));
        } catch (ClientProtocolException e) {
            System.out.println(StringUtil.parseResponseToStr(resp));
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * @param file
     * @param url
     * @param params
     */
    public static void sendFileAndParamsByHttpPost(File file, String url, Map<String, String> params) {
        CloseableHttpClient client = HttpClients.custom().build();
        try {
            HttpPost post = new HttpPost(url);
            FileBody fileBody = new FileBody(file);
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                // multipartEntityBuilder.addTextBody不设置编码可能会中文乱码
                //				multipartEntityBuilder.addTextBody(key, value);
                multipartEntityBuilder.addTextBody(key, value, ContentType.TEXT_PLAIN.withCharset("utf-8"));
            }

            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            multipartEntityBuilder.setContentType(ContentType.MULTIPART_FORM_DATA);
//            builder.addBinaryBody("file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, file.getOriginalFilename());
            HttpEntity entity = multipartEntityBuilder
                .addPart("file", fileBody)
                .setCharset(Charset.forName("UTF-8"))
                .build();
            post.setEntity(entity);
            CloseableHttpResponse resp = client.execute(post);
            int status = resp.getStatusLine().getStatusCode();
            if (status == 200) {
                Logger.getLogger("HttpClientUtil").info("上传成功");
            } else {
                Logger.getLogger("HttpClientUtil").info("上传失败");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }







}
