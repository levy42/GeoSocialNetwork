package tools;


import java.awt.*;
import java.awt.image.BufferedImage;

public class AvatarMaker {
    public static final int NORMAL_WIDTH=500;
    public static final int NORMAL_HEIGHT=500;
    public static final int MINI_WIDTH=150;
    public static final int MINI_HEIGHT=150;

    public static BufferedImage Normal(BufferedImage image,int x,int y,int w,int h){
        return resize(image,NORMAL_WIDTH,NORMAL_HEIGHT,x,y,w,h);
    }

    public static BufferedImage Mini(BufferedImage image,int x,int y,int w,int h){
        return resize(image,MINI_WIDTH,MINI_HEIGHT,x,y,w,h);
    }

    private static BufferedImage resize(BufferedImage im,int width,int height,int x,int y,int w,int h){
        float aspect;
        BufferedImage image=im.getSubimage(x,y,w,h);
        Image i1;
        if(image.getWidth()>image.getHeight()) {
            aspect = (float)image.getWidth() / image.getHeight();
            i1=image.getScaledInstance((int)(width*aspect),height, Image.SCALE_SMOOTH);
        }
        else {
            aspect = (float)image.getHeight() / image.getWidth();
            i1=image.getScaledInstance(width,(int)(height*aspect), Image.SCALE_SMOOTH);
        }

        int width1 = i1.getWidth(null);
        int height1 = i1.getHeight(null);
        BufferedImage newImage = new BufferedImage(width1, height1,
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = newImage.getGraphics();
        g.drawImage(i1, 0, 0, null);
        g.dispose();
        return newImage;
    }
}
