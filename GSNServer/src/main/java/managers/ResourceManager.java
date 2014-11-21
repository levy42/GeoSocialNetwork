package managers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by oleh on 06.11.2014.
 */
public class ResourceManager {
    public static void writeToFile(InputStream uploadedInputStream,String uploadedFileLocation) {
        try {
            OutputStream out;
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static String saveImage(BufferedImage image,String name){
        try {
            File outputfile = new File(BaseURLs.ImagesPath+name+".jpg");
            ImageIO.write(image, "jpg", outputfile);
        } catch (IOException e) {
            return  null;
        }
        return BaseURLs.ImagesURL+name+".jpg";
    }
}
