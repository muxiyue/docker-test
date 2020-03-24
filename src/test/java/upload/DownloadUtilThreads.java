package upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

/**
 * @Auther: csp
 * @Description:
 * @Date: Created in 2019/8/13 下午1:18
 * @Modified By:
 */
//拷贝线程：DownloadUtilThreads
public class DownloadUtilThreads extends Thread {

    private long copySize;
    private long point;
    private RandomAccessFile r;
    private RandomAccessFile w;
    private File logFile;


    public DownloadUtilThreads(File sourceFile,File targetFile,long copySize,long point) throws FileNotFoundException {
        this.copySize=copySize;
        this.point=point;
        r=new RandomAccessFile(sourceFile, "r");
        w=new RandomAccessFile(targetFile, "rw");
    }


    @Override
    public void run() {
        try {
            //创建日志操作对象
            logFile=new File(Thread.currentThread().getName()+"_download.log");
            LogOpreator logOpreator = new LogOpreator(logFile);

            //首次启动下载
            if(logFile.length()==0){
                logOpreator.write(point,false);
            }
            //读取日志文件取出point,isFinish
            long startIndex = logOpreator.readPoint();
            boolean isFinish = logOpreator.readIsFinish();
            System.out.println(startIndex+"---"+isFinish);

            //判断是否已经下载完成
            if(isFinish){
                return;
            }

            //设置指针偏移
            r.seek(startIndex);
            w.seek(startIndex);

            //拷贝
            byte[] b=new byte[8192];
            int len;


            while((len=r.read(b))!=-1){
                w.write(b, 0, len);

                startIndex+=len;
                logOpreator.write(startIndex,false);

                //判断拷贝是否完成
                if(startIndex>=copySize){
                    logOpreator.write(startIndex,true);
                    System.out.println(startIndex+"---"+isFinish);
                    break;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
