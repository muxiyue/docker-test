package download;

/**
 * Created by amosli on 14-7-2.
 */
public class DownUtilTest {

    public static void main(String args[]) throws Exception {
        final DownUtil downUtil = new DownUtil("http://mirrors.cnnic.cn/apache/tomcat/tomcat-7/v7.0.54/bin/apache-tomcat-7.0.54.zip", "tomcat-7.0.54.zip", 3);

        downUtil.download();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(downUtil.getCompleteRate()<1){
                    System.out.println("已完成:"+downUtil.getCompleteRate());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

}