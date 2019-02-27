package image;

import com.luciad.imageio.webp.WebPReadParam;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DecodeTest {
    public static void main(String args[]) throws IOException {
        String inputWebpPath = "/Users/chenshengpeng/F_Work/examples/docker-test/src/test/resources/image/1.webp";
        String outputJpgPath = "/Users/chenshengpeng/F_Work/examples/docker-test/src/test/resources/image/1_.jpg";
        String outputJpegPath = "/Users/chenshengpeng/F_Work/examples/docker-test/src/test/resources/image/1_.jpeg";
        String outputPngPath = "/Users/chenshengpeng/F_Work/examples/docker-test/src/test/resources/image/1_.png";

        // Obtain a WebP ImageReader instance
        ImageReader reader = ImageIO.getImageReadersByMIMEType("image/webp").next();

        // Configure decoding parameters
        WebPReadParam readParam = new WebPReadParam();
        readParam.setBypassFiltering(true);

        // Configure the input on the ImageReader
        reader.setInput(new FileImageInputStream(new File(inputWebpPath)));

        // Decode the image
        BufferedImage image = reader.read(0, readParam);

        ImageIO.write(image, "png", new File(outputPngPath));
        ImageIO.write(image, "jpg", new File(outputJpgPath));
        ImageIO.write(image, "jpeg", new File(outputJpegPath));

    }
}
