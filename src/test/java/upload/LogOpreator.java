package upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//日志读写操作：LogOpreator
public class LogOpreator {

    private DownloadLog d;
    private ObjectInputStream obi;
    private ObjectOutputStream obo;
    private File file;


    public LogOpreator(File file) {
        this.file = file;
    }

    //写入对象到文件
    public void write(long point,boolean isFinish){
        d=new DownloadLog(point,isFinish);
        try {
            obo=new ObjectOutputStream(new FileOutputStream(file));
            obo.writeObject(d);
            obo.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //取出point
    public long readPoint(){
        try {
            obi=new ObjectInputStream(new FileInputStream(file));
            Object logOb = obi.readObject();
            DownloadLog log=(DownloadLog)logOb;
            obi.close();
            return log.getPoint();
        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;        
    }
    public boolean readIsFinish(){
        try {
            obi=new ObjectInputStream(new FileInputStream(file));
            Object logOb = obi.readObject();
            DownloadLog log=(DownloadLog)logOb;
            obi.close();
            return log.isFinish();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;    
    }

}