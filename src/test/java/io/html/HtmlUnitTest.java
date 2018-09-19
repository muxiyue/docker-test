package io.html;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;

import java.io.IOException;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class HtmlUnitTest {
    @Test
    public void pageClick() throws IOException {
        // 得到浏览器对象，直接New一个就能得到，现在就好比说你得到了一个浏览器了
        WebClient webclient = new WebClient();
        // 下面这2句可以写，也可以不写，设置false就是不加载css和js。访问速度更快
        webclient.getOptions().setCssEnabled(true);
        webclient.getOptions().setJavaScriptEnabled(true);
        // 做的第一件事，去拿到这个网页，只需要调用getPage这个方法即可
        HtmlPage htmlpage = webclient.getPage("http://192.168.1.101:8080/Credit-Monitor/views/reports/queryReport.action");
        System.out.println(htmlpage);

        //        htmlpage.executeJavaScript("downloadReportFile(0)");


        //        // 根据名字得到一个表单，查看上面这个网页的源代码可以发现表单的名字叫“f”
//        final HtmlForm form = htmlpage.getFormByName("f");
//        // 同样道理，获取”百度一下“这个按钮
//        final HtmlSubmitInput button = form.getInputByValue("百度一下");
//        // 得到搜索框
//        final HtmlTextInput textField = form.getInputByName("wd");
//        //设置搜索框的value
//        textField.setValueAttribute("战狼2");
//        // 设置好之后，模拟点击按钮行为。
//        final HtmlPage nextPage = button.click();
//        // 把结果转成String
//        String result = nextPage.asXml();
//        //得到的是点击后的网页
//        System.out.println(result);
    }
}
