package upload;

import java.io.Serializable;

//实体存储对象：DownloadLog（其中point用于存储文件指针，isFinish用于存储文件是否拷贝完）
public class DownloadLog implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private long point;
    private boolean isFinish=false;
    
    
    
    public DownloadLog() {

    }

    public DownloadLog(long point,boolean isFinish) {
        this.point = point;
        this.isFinish=isFinish;
    }

    public long getPoint() {
        return point;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "DownloadLog [point=" + point + "]";
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean isFinsh) {
        this.isFinish = isFinsh;
    }
    
    
}
