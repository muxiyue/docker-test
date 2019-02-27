package image;

import com.luciad.imageio.webp.WebPWriteParam;
import docker.test.utils.FileUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SyncImageUtil {
	private final static Log log = LogFactory.getLog(SyncImageUtil.class);
	/**
	 * @param srcImageFile 原图
	 * @param result 目标图
	 * @param height 目标图高度
	 * @param width 目标图宽度
	 * @param bb 是否补白
	 * @param destFoderPath 目标图文件夹
	 * @return
	 */
	public final static boolean scale(String srcImageFile, String result, int width, int height, boolean bb, String fileType, String destFoderPath) {
		boolean bSuc = false;
		try {
			FileUtil.checkDirExists(destFoderPath);
			double ratiow = 0.0;
			double ratioh = 0.0;
			File f = new File(srcImageFile);
			BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
			if((bi.getHeight()>height)||(bi.getWidth()>width)) {
//				if(bi.getHeight() > bi.getWidth()) {
					ratioh = (new Integer(height)).doubleValue()/bi.getHeight();
//				}else {
					ratiow = (new Integer(width)).doubleValue()/bi.getWidth();
//				}
				AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratiow, ratioh), null);
				itemp = op.filter(bi, null);
			}
			if(bb) {
				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if(width == itemp.getWidth(null)) {
					g.drawImage(itemp, 0, (height-itemp.getHeight(null))/2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
				}else {
					g.drawImage(itemp, (width-itemp.getWidth(null))/2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
				}
				g.dispose();
				itemp = image;
			}
			ImageIO.write((BufferedImage)itemp, fileType, new File(result));
			bSuc = true;
		} catch (IOException e) {
			log.error(e);
		}
		return bSuc;
	}

	/**
	 * @param srcImageFile 源文件
	 * @param result	转码后的文件
	 * @param width
	 * @param height
	 * @param bb
	 * @param fileType
	 * @param destFoderPath
	 * @return
	 */
	public final static boolean scaleImage(String srcImageFile, String result, int width, int height, boolean bb, String fileType, String destFoderPath) {
		boolean bSuc = false;
		//1.检查目标文件夹是否存在，不存在则创建
		FileUtil.checkDirExists(destFoderPath);
		ImageOutputStream ios = null;
		try {
			//2.获取原图片的宽和高
			BufferedImage srcImg = ImageIO.read(new File(srcImageFile));
			int srcWidth = srcImg.getWidth(null);
			int srcHeight = srcImg.getHeight(null);
			
			float ratio = 1.0f;
			float widthRatio = (float)width/srcWidth;
			float heightRatio = (float)height/srcHeight;
			if(widthRatio<1.0 || heightRatio<1.0) {
				ratio = (widthRatio<=heightRatio) ? widthRatio : heightRatio;
			}
			int newWidth = (int) (ratio * srcWidth);
			int newHeight = (int) (ratio * srcHeight);
			if (fileType.equals("webp")){
				BufferedImage targetImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
				Graphics2D graphics = targetImg.createGraphics();
				graphics.drawImage(srcImg.getScaledInstance(newWidth, newHeight, Image.SCALE_AREA_AVERAGING), 0, 0, newWidth, newHeight, Color.white, null);
				graphics.dispose();
				
				//卷积核(权矩阵)
				float[] kernelData2 = {-0.125f, -0.125f, -0.125f, -0.125f, 2, -0.125f, -0.125f, -0.125f, -0.125f };
				Kernel kernel = new Kernel(3, 3, kernelData2);
				ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
				targetImg = cOp.filter(targetImg, null);
			
				// Obtain a WebP ImageWriter instance
				ImageWriter imageWriter = ImageIO.getImageWritersByMIMEType("image/webp").next();
				ios = ImageIO.createImageOutputStream(new FileOutputStream(result));
				imageWriter.setOutput(ios);

				// Configure encoding parameters
				WebPWriteParam writeParam = new WebPWriteParam(imageWriter.getLocale());
				writeParam.setCompressionMode(WebPWriteParam.MODE_EXPLICIT);

				// Encode
				IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(targetImg), null);
				WebPWriteParam webpParams  =  (WebPWriteParam) imageWriter.getDefaultWriteParam();
				webpParams.setCompressionMode(WebPWriteParam.MODE_EXPLICIT);
				//webpParams.setCompressionType("");
				//webpParams.setCompressionQuality(0.75f);
				
				imageWriter.write(imageMetaData, new IIOImage(targetImg, null, null), writeParam);
				imageWriter.dispose();
				//ImageIO.write(targetImg, "webp", new File(result));
			}else{
				// 1、先转换成jpg  
	            Thumbnails.of(srcImageFile).scale(1.0f).toFile(result);
	            //压缩
	            Thumbnails.of(result).size(newWidth, newHeight).outputQuality(0.75).toFile(result);
	            
//				ImageWriter imageWriter = ImageIO.getImageWritersBySuffix("jpg").next();
//				ios = ImageIO.createImageOutputStream(new FileOutputStream(result));
//				imageWriter.setOutput(ios);
//				
//				IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(targetImg), null);
//				JPEGImageWriteParam jpegParams  =  (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
//				jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
//				jpegParams.setCompressionQuality(0.75f);
//				
//				imageWriter.write(imageMetaData, new IIOImage(targetImg, null, null), null);
//				imageWriter.dispose();
			}					
			
			bSuc = true;
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(ios != null) {
				try {
					ios.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		log.info("========================新的图片转码结果[" + bSuc + "]========================");
		return bSuc;
	}
	
	/**
	 * @param srcImageFile: /usr/local/syncContent/zhengshi/4/3/3/10/display
	 * @param result:	/nas/nas_source/display/zhengshi/4/3/3/10/display/xxx_xxx.jsg
	 * @param width
	 * @param height
	 * @param fileType
	 * @param destFoderPath: /nas/nas_source/display/zhengshi/4/3/3/10/display/
	 * @return
	 */
	public final static boolean newScaleAndCut(String srcImageFile, String result, int width, int height, String fileType, String destFoderPath) {
		boolean bSuc = false;
		try {
			FileUtil.checkDirExists(destFoderPath);
			
			BufferedImage srcImg = ImageIO.read(new File(srcImageFile));
			double sw = (new Integer(srcImg.getWidth())).doubleValue();
			double sh = (new Integer(srcImg.getHeight())).doubleValue();
			if(height == 0) {
				height = (int)(width * sh / sw);
			}
			
			BufferedImage targetImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = targetImg.createGraphics();
			graphics.drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING), 0, 0, width, height, Color.white, null);
			graphics.dispose();
			
			//卷积核(权矩阵)
			float[] kernelData2 = {-0.125f, -0.125f, -0.125f, -0.125f, 2, -0.125f, -0.125f, -0.125f, -0.125f };
			Kernel kernel = new Kernel(3, 3, kernelData2);
			ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
			targetImg = cOp.filter(targetImg, null);
			
			int bw = targetImg.getWidth();
			int bh = targetImg.getHeight();
			
			if(bw > width) {//切x轴
				newSaveSubImage(targetImg, new Rectangle((bw-width)/2, 0, width, height), new File(result));
			}else {
				newSaveSubImage(targetImg, new Rectangle(0, (bh-height)/2, width, height), new File(result));
			}
			
			bSuc = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bSuc;
	}
	
	private static void newSaveSubImage(BufferedImage image, Rectangle subImageBounds, File subImageFile) throws IOException {
		if (subImageBounds.x < 0 || subImageBounds.y < 0 || subImageBounds.width - subImageBounds.x > image.getWidth()
				|| subImageBounds.height - subImageBounds.y > image.getHeight()) {
			log.info("Bad   subimage   bounds");
			return;
		}
		
		ImageOutputStream ios = null;
		try {
			
			BufferedImage subImage = image.getSubimage(subImageBounds.x, subImageBounds.y, subImageBounds.width, subImageBounds.height);
			if (subImageFile.getName().indexOf("webp")>=0){
				// Obtain a WebP ImageWriter instance
				ImageWriter imageWriter = ImageIO.getImageWritersByMIMEType("image/webp").next();
				ios = ImageIO.createImageOutputStream(new FileOutputStream(subImageFile));
				imageWriter.setOutput(ios);

				// Configure encoding parameters
				WebPWriteParam writeParam = new WebPWriteParam(imageWriter.getLocale());
				writeParam.setCompressionMode(WebPWriteParam.MODE_EXPLICIT);

				// Encode
				IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(subImage), null);
				WebPWriteParam webpParams  =  (WebPWriteParam) imageWriter.getDefaultWriteParam();
				webpParams.setCompressionMode(WebPWriteParam.MODE_EXPLICIT);
				//webpParams.setCompressionType("");
				//webpParams.setCompressionQuality(0.75f);
				
				imageWriter.write(imageMetaData, new IIOImage(subImage, null, null), null);
				imageWriter.dispose();
			}else{				
				ImageWriter imageWriter = ImageIO.getImageWritersBySuffix("jpg").next();
				ios = ImageIO.createImageOutputStream(new FileOutputStream(subImageFile));
				imageWriter.setOutput(ios);
				
				IIOMetadata imageMetaData = imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(subImage), null);
				JPEGImageWriteParam jpegParams  =  (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
				jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
				jpegParams.setCompressionQuality(0.75f);
				
				imageWriter.write(imageMetaData, new IIOImage(subImage, null, null), null);
				imageWriter.dispose();
			}
			
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(ios != null) {
				try {
					ios.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
    /** 实现缩放后的截图
    *
    * @param image
    *            缩放后的图像
    * @param subImageBounds
    *            要截取的子图的范围
    * @param subImageFile
    *            要保存的文件
    * @throws IOException */
	private static void saveSubImage(BufferedImage image, Rectangle subImageBounds, File subImageFile) throws IOException {
		if (subImageBounds.x < 0 || subImageBounds.y < 0 || subImageBounds.width - subImageBounds.x > image.getWidth()
				|| subImageBounds.height - subImageBounds.y > image.getHeight()) {
			log.info("Bad   subimage   bounds");
			return;
		}
		BufferedImage subImage = image.getSubimage(subImageBounds.x, subImageBounds.y, subImageBounds.width, subImageBounds.height);
		String fileName = subImageFile.getName();
		String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
		ImageIO.write(subImage, formatName, subImageFile);
	}
	/**
	 * 根据任意比例图片等比缩小（放大）并切割成目标尺寸
	 * @param srcImageFile
	 * @param result
	 * @param width
	 * @param height
	 * @param fileType
	 * @param destFoderPath
	 * @return
	 */
	public final static boolean scaleAndCut(String srcImageFile, String result, int width, int height, String fileType, String destFoderPath) {
		boolean bSuc = false;
		try {
			FileUtil.checkDirExists(destFoderPath);
			double ratio = 0.0;
			File f = new File(srcImageFile);
			BufferedImage bi = ImageIO.read(f);
			double sw = (new Integer(bi.getWidth())).doubleValue();
			double sh = (new Integer(bi.getHeight())).doubleValue();

			if(height == 0) {
				height = (int)(width * sh / sw);
			}
			Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
			double dw = (new Integer(width)).doubleValue();
			double dh = (new Integer(height)).doubleValue();
			if(sw/sh > dw/dh) {
				ratio = dh/sh;
			} else {
				ratio = dw/sw;
			}
			AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
			itemp = op.filter(bi, null);
			
			BufferedImage bt = (BufferedImage)itemp;
			int bw = bt.getWidth();
			int bh = bt.getHeight();
			
			if(bw > width) {//切x轴
				saveSubImage(bt, new Rectangle((bw-width)/2, 0, width, height), new File(result));
			} else {//切y轴
				saveSubImage(bt, new Rectangle(0, (bh-height)/2, width, height), new File(result));
			}
			bSuc = true;
		} catch (IOException e) {
			log.error(e);
		}
		return bSuc;
	}
	
	public static void main(String[] args) {
//		SyncImageUtil.scaleAndCut("D:/test/image/src/699018_H_HB_sc.jpg", "D:/test/image/des/699018_H_HB_1080V.jpg", 330, 440, "jpg", "D:/test/image/des/");
//		SyncImageUtil.scaleAndCut("D:/test/image/src/699018_S_HB_sc.jpg", "D:/test/image/des/699018_S_HB_1080V.jpg", 330, 440, "jpg", "D:/test/image/des/");
//		
//		SyncImageUtil.scaleAndCut("D:/test/image/src/699018_H_HB_sc.jpg", "D:/test/image/des/699018_H_HB_720DLF.jpg", 220, 220, "jpg", "D:/test/image/des/");
//		SyncImageUtil.scaleAndCut("D:/test/image/src/699018_S_HB_sc.jpg", "D:/test/image/des/699018_S_HB_720DLF.jpg", 220, 220, "jpg", "D:/test/image/des/");
//		
//		SyncImageUtil.scaleAndCut("D:/test/image/src/699018_H_HB_sc.jpg", "D:/test/image/des/699018_H_HB_720H.jpg", 336, 224, "jpg", "D:/test/image/des/");
//		SyncImageUtil.scaleAndCut("D:/test/image/src/699018_S_HB_sc.jpg", "D:/test/image/des/699018_S_HB_720H.jpg", 336, 224, "jpg", "D:/test/image/des/");
		
//		SyncImageUtil.scaleAndCut("D:/test/image/src/699018_LM_HB_sc.jpg", "D:/test/image/des/699018_LM_HB_1080V.jpg", 330, 440, "jpg", "D:/test/image/des/");
//		SyncImageUtil.scaleAndCut("D:/test/image/src/699018_LM_HB_sc.jpg", "D:/test/image/des/699018_LM_HB_720DLF.jpg", 220, 220, "jpg", "D:/test/image/des/");
//		SyncImageUtil.scaleAndCut("D:/test/image/src/699018_LM_HB_sc.jpg", "D:/test/image/des/699018_LM_HB_720H.jpg", 336, 224, "jpg", "D:/test/image/des/");
		
		//SyncImageUtil.scaleImage("D:/nas/src/kjkdmdxwpg_H32_sc.jpg", "D:/nas/src/09_HSJ720.jpg", 336, 224, true, "jpg", "D:/nas/src");
		//SyncImageUtil.scaleImage("D:/nas/src/kjkdmdxwpg_H32_sc.jpg", "D:/nas/src/071_HSJ720.jpg", 330, 220, true, "jpg", "D:/nas/src");
		//SyncImageUtil.scale("D:/nas/src/kjkdmdxwpg_H32_sc.jpg", "D:/nas/src/0720b_HSJ720.jpg", 508, 339, true, "jpg", "D:/nas/src");
		//SyncImageUtil.scaleImage("D:/nas/src/kjkdmdxwpg_H32_sc.jpg", "D:/nas/src/072f_HSJ720.jpg", 508, 339, true, "jpg", "D:/nas/src");
//		SyncImageUtil.scaleImage("D:/nas/src/ypt_H32_sc.jpg", "D:/nas/src/a_HSJ720.jpg", 508, 339, true, "jpg", "D:/nas/src");
//		SyncImageUtil.scaleImage("D:/nas/src/ypt_H32_sc.jpg", "D:/nas/src/b_HSJ720.jpg", 336, 224, true, "jpg", "D:/nas/src");
//		SyncImageUtil.scaleImage("D:/nas/src/ypt_H32_sc.jpg", "D:/nas/src/c_HSJ720.jpg", 330, 220, true, "jpg", "D:/nas/src");
		
		//SyncImageUtil.newScaleAndCut("D:/nas/src/ypt_H32_sc.jpg", "D:/nas/src/ttt_H32_sc.jpg", 336, 224, "jpg", "D:/test/image/des/");
		
		System.out.println(System.getProperty("java.library.path"));  
		SyncImageUtil.scaleImage("/Users/chenshengpeng/F_Work/examples/docker-test/src/test/resources/image/1.jpg", "/Users/chenshengpeng/F_Work/examples/docker-test/src/test/resources/image/change/1.webp", 300, 324, true, "webp", "/Users/chenshengpeng/F_Work/examples/docker-test/src/test/resources/image/change/");
	}
}
