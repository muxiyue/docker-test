package image;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @Auther: csp
 * @Description:
 * @Date: Created in 2018/12/14 下午5:55
 * @Modified By:
 */
public class CompressImage {

    @Test
    public void compressImage() throws IOException {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();

        System.out.println(path + "/image/compress/11.jpg");

        Thumbnails.of(path + "/image/1.jpg")
            .scale(0.4f)      // 图片尺寸
            .outputQuality(1f)  // 图片质量
            .toFile("/Users/chenshengpeng/F_Work/examples/docker-test/src/test/resources/image/compress/33.jpg");
    }


    /**
     * 缩放图像（按高度和宽度缩放）
     * @param srcImageFile 源图像文件地址
     * @param result 缩放后的图像地址
     * @param height 缩放后的高度
     * @param width 缩放后的宽度
     * @throws IOException
     * @isPHP_PC php的pc端调用
     * @isPHP_MOBILE php的移动无线端调用
     */
    public  void scale(String srcImageFile, String newImageFile, int width,int height, boolean isPHP_PC,boolean isPHP_MOBILE) throws IOException {
        Image img = ImageIO.read(new File(srcImageFile));
        int newWidth = img.getWidth(null),newHeight = img.getHeight(null);

        // 计算比例
        if(isPHP_MOBILE && !isPHP_PC && img.getWidth(null) <= width){
            //无线调用时，宽度没有压缩宽大，则返回原图
        }else if ((img.getHeight(null) > height) || (img.getWidth(null) > width)) {

            if(isPHP_PC && !isPHP_MOBILE){//PHP的微游记PC端缩略图以定宽或定高(以较长的一边)为基准
                if(img.getWidth(null) > img.getHeight(null)){
                    newWidth = width;
                    newHeight = (int)(new Integer(width).doubleValue()/img.getWidth(null)*img.getHeight(null));
                }else{
                    newHeight = height;
                    newWidth = (int)(new Integer(height).doubleValue()/img.getHeight(null)*img.getWidth(null));
                }

            }else if(isPHP_MOBILE && !isPHP_PC){//PHP的微游记(移动)端缩略图以定宽为基准
                newWidth = width;
                newHeight = (int)(new Integer(width).doubleValue()/img.getWidth(null)*img.getHeight(null));

            }else {//vst端：原图长/压缩长 > 原图宽/压缩图宽   ? 原图长/压缩长:原图宽/压缩图宽,以比例较大的为基准压缩
                // 压缩比判断方法二
                double ratio_height = (new Integer(height)).doubleValue()/ img.getHeight(null);
                double ratio_width = (new Integer(width)).doubleValue() / img.getWidth(null);

                if(ratio_height>ratio_width){
                    newHeight = height;
                    newWidth = (int)((new Integer(height)).doubleValue()/ img.getHeight(null) * img.getWidth(null));
                }else{
                    newWidth = width;
                    newHeight = (int)((new Integer(width)).doubleValue() / img.getWidth(null) * img.getHeight(null));
                }
            }
        }

        Thumbnails.of(srcImageFile)
            .size(newWidth, newHeight)
            .toFile(newImageFile);

    }

    /**
     * 添加图片水印
     *
     * @param targetImg
     *            目标图片路径，如：C:\\myPictrue\\1.jpg
     * @param waterImg
     *            水印图片路径，如：C:\\myPictrue\\logo.png
     * @param positions 水印图片所在位置
     *
     * @param alpha
     *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明) * @param quality 压缩清晰度
     *            <b>建议为1.0</b>
     * @throws IOException
     */
    public void pressImage(String targetImg, File waterImg, Positions positions, float alpha) throws IOException {
        Thumbnails.of(targetImg)
            .watermark(positions, ImageIO.read(waterImg), alpha)
            .scale(1)//缩放比例
            .toFile(targetImg);
    }

    /**
     * 把图片印刷到图片上
     *
     * @param pressImg
     *            -- 水印文件
     * @param targetImg
     *            -- 目标文件
     * @param alpha
     *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明) * @param quality 压缩清晰度
     *            <b>建议为1.0</b>
     *
     * @throws IOException
     */
    public void pressImage(String targetImg, String pressImg,float alpha) throws IOException {
        Thumbnails.of(targetImg)
            .watermark(Positions.TOP_LEFT, ImageIO.read(new File(pressImg)), alpha)
            .outputQuality(1)//生成质量100%
            .scale(1)//缩放比例
            .toFile(targetImg);
    }

    /**
     * 把图片印刷到图片上
     *
     * @param pressImg
     *            -- 水印文件
     * @param targetImg
     *            -- 目标文件
     * @param position
     *
     @param alpha
      *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明) * @param quality 压缩清晰度
      *            <b>建议为1.0</b>
      *
      * @throws IOException
     */
    public void pressImage(String targetImg, String pressImg, Positions position,float alpha) throws IOException {
        Thumbnails.of(targetImg)
            .watermark(position, ImageIO.read(new File(pressImg)), alpha)
            .outputQuality(1)//生成质量100%
            .scale(1)//缩放比例
            .toFile(targetImg);
    }

    /**
     * 裁剪图片
     * @throws IOException
     */
    public void region(String sourceImg,String newImgPath,int x,int y,int width,int height) throws IOException{
        //指定坐标
        Thumbnails.of(sourceImg)
            .sourceRegion(x, y, width, height)//x轴、y轴，裁剪宽、裁剪高
            .size(width, height)//裁剪后的图片生成的尺寸
            //设置是否按比例  false,图片可能会走形 默认true,必须在设置尺寸后设置
            .keepAspectRatio(false)
            .toFile(newImgPath);
    }

    /**
     * 裁剪图片
     * @throws IOException
     */
    public void region2(String sourceImg,String newImgPath,int x,int y,int width,int height) throws IOException{
        //指定坐标
        Thumbnails.of(sourceImg)
            .sourceRegion(x, y, width, height)//x轴、y轴，裁剪宽、裁剪高
            .size(width, height)//裁剪后的图片生成的尺寸
            //设置是否按比例  false,图片可能会走形 默认true,必须在设置尺寸后设置
            .keepAspectRatio(true)
            .toFile(newImgPath);
    }

    /**
     * 旋转图片
     * @param sourceImage 原图片
     * @param newImage 生成的新图片
     * @param degrees 旋转度数
     * @throws IOException
     */
    public void rorate(String sourceImage,String newImage,double degrees) throws IOException{
        Thumbnails.of(sourceImage)
            .rotate(degrees)//旋转度数
            .scale(1)//缩放比例
            .toFile(newImage);
    }

    /**
     * 转换图片格式
     * @param sourceImg 原图
     * @param newImg    转换后的新图
     * @param format	格式
     * @throws IOException
     */
    public void transferImageFormat(String sourceImg,String newImg,String format) throws IOException{
        Thumbnails.of(sourceImg)
            .outputFormat(format)
            .scale(1)
            .toFile(newImg);
    }

    @Test
    public  void test() {
        try {
//            region("/Users/chenshengpeng/F_Work/examples/docker-test/src/test/resources/image/1.jpg",
//                "/Users/chenshengpeng/F_Work/examples/docker-test/src/test/resources/image/cut/2.jpg",
//                1000, 500, 300, 300);

            region2("/Users/chenshengpeng/F_Work/examples/docker-test/src/test/resources/image/1.jpg",
                "/Users/chenshengpeng/F_Work/examples/docker-test/src/test/resources/image/cut/3.jpg", 1000, 500, 300,
                300);


//            pressImage("/Users/chenshengpeng/F_Work/examples/docker-test/src/test/resources/image/change/11.png",  "/Users/chenshengpeng/F_Work/examples/docker-test/src/test/resources/image/cut/2.jpg",
//                Positions.BOTTOM_LEFT, 0.3f);

            //ImageUtil2.scale("C:\\Users\\chengjiangbo\\Desktop\\images\\c743d228-3ecf-4711-9187-725f252d14b1.jpg", "C:\\Users\\chengjiangbo\\Desktop\\images\\480_300.jpg", 480, 300, true, false);
            //ImageUtil2.pressImage("C:\\Users\\chengjiangbo\\Desktop\\images\\IMG_waterImage.jpg", "C:\\Users\\chengjiangbo\\Desktop\\images\\QRCode.png",Positions.BOTTOM_CENTER, 1);
            //ImageUtil2.region("C:\\Users\\chengjiangbo\\Desktop\\images\\IMG_waterImage.jpg", "C:\\Users\\chengjiangbo\\Desktop\\images\\IMG_waterImage123.jpg",0,0,100,100);
            //ImageUtil2.rorate("C:\\Users\\chengjiangbo\\Desktop\\images\\IMG_waterImage.jpg", "C:\\Users\\chengjiangbo\\Desktop\\images\\IMG_waterImage111.jpg", 90);
//            transferImageFormat("/Users/chenshengpeng/F_Work/examples/docker-test/src/test/resources/image/1.jpg", "/Users/chenshengpeng/F_Work/examples/docker-test/src/test/resources/image/change/11.png", "png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
