package video;

import org.junit.Test;
import ws.schild.jave.AudioAttributes;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.EncoderProgressListener;
import ws.schild.jave.EncodingAttributes;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.VideoAttributes;
import ws.schild.jave.VideoFilter;
import ws.schild.jave.VideoSize;

import java.io.File;

/**
 * @Auther: csp
 * @Description:
 * @Date: Created in 2019/3/26 上午9:25
 * @Modified By:
 */
public class VideoTest {

    /*
     * @param add:   文件地址
     * @returns:     返回文件信息类型对象
     * @Description: 获得文件信息
     */
    public MultimediaInfo getMediaInfo(String add) throws EncoderException {
        File source = new File(add);
        //创建媒体对象
        MultimediaObject multimediaObject = new MultimediaObject(source);
        //创建媒体信息对象
        return multimediaObject.getInfo();
    }

    /*
     * @param add:文件地址
     * @returns: long类型的时间
     * @Description: 获得时间(ms)
     */
    public  long getTime(String add) throws EncoderException {
        MultimediaInfo info = getMediaInfo(add);
        //得到时间
        long time = info.getDuration();
        return info.getDuration();
    }


    /*
     * @param add:文件地址
     * @returns: String类型的文件格式
     * @Description: 获得文件格式
     */
    public  String getFormat(String add) throws EncoderException {
        MultimediaInfo info = getMediaInfo(add);
        return info.getFormat();
    }


    @Test
    public void getInfo() throws EncoderException {
        String inputFilePath = VideoTest.class.getResource("/").getPath() + "file/input/test2.3gp";
        MultimediaInfo info = getMediaInfo(inputFilePath);
        System.out.println(info);
    }


    @Test
    public void test() {
        ConvertProgressListener listener = new ConvertProgressListener();

        String inputFilePath = VideoTest.class.getResource("/").getPath() + "file/input/test2.3gp";
        String outputFilePath = VideoTest.class.getResource("/").getPath() + "file/output/test.mp4";

        try {
            File source = new File(inputFilePath);
            File target = new File(outputFilePath);

            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("aac");

            audio.setBitRate(new Integer(236000/2));
//            audio.setChannels(new Integer(1));
            audio.setSamplingRate(new Integer(8000));
            
            VideoAttributes video = new VideoAttributes();
            video.setCodec("mpeg4");
            video.setBitRate(new Integer(128000));
            video.setFrameRate(new Integer(15));
            video.setSize(new VideoSize(1760, 1440));
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setFormat("3gp");
            attrs.setAudioAttributes(audio);
            attrs.setVideoAttributes(video);
            Encoder encoder = new Encoder();
            try {
                encoder.encode(new MultimediaObject(source), target, attrs, listener);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println(encoder.getUnhandledMessages());
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }



    }


    @Test
    public void test2() {
        ConvertProgressListener listener = new ConvertProgressListener();

        String inputFilePath = VideoTest.class.getResource("/").getPath() + "file/input/test2.3gp";
        String outputFilePath = VideoTest.class.getResource("/").getPath() + "file/output/test.mp4";
        String ttfPath = VideoTest.class.getResource("/").getPath() + "file/input/Songti.ttc";

        try {
            File source = new File(inputFilePath);
            File target = new File(outputFilePath);

            AudioAttributes audio = new AudioAttributes();
            audio.setSamplingRate(new Integer(8000));
            audio.setVolume(512);

            VideoAttributes video = new VideoAttributes();

//            命令行: ffplay -i test2.mp4 -vf drawtext="fontfile=Songti.ttc:text='指定时间段内才能看见':x='if((gte(t\,5) * (lte(t\,10)))\,w-t*50\,NAN)':fontcolor=darkorange:fontsize=30"
//            去掉了反斜杠和drawtext后面的双引号环绕
            VideoFilter videoFilter = new VideoFilter("drawtext=fontfile=" + ttfPath + ":text='指定时间段内才能看见':x='if((gte(t,1) * (lte(t,8))),w-t*50,NAN)':fontcolor=darkorange:fontsize=50");
            video.addFilter(videoFilter);

            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setFormat("3gp");
//            attrs.setAudioAttributes(audio);
            attrs.setVideoAttributes(video);


            Encoder encoder = new Encoder();
            try {
                encoder.encode(new MultimediaObject(source), target, attrs, listener);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println(encoder.getUnhandledMessages());
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }



    }



    public class ConvertProgressListener implements EncoderProgressListener {

        public ConvertProgressListener() {
            //code
        }

        public void message(String m) {
            //code
        }

        public void progress(int p) {

            //Find %100 progress
            double progress = p / 1000.00;
            System.out.println(progress);

        }

        public void sourceInfo(MultimediaInfo m) {
            //code
        }
    }

}
