package tools;

/**
 * Created by oleh on 07.11.2014.
 */
public class AvatarConfigs {
    /**
     * x_margin in pixels for mini copy of avatar
     */
    public int Moffestx;
    /**
     * y_margin in pixels for mini copy of avatar
     */
    public int Moffsety;
    /**
     * width in pixels for mini copy of avatar
     */
    public int Mw;
    /**
     * height in pixels for mini copy of avatar
     */
    public int Mh;
    /**
     * x_margin in pixels for normal copy of avatar
     */
    public int Noffestx;
    /**
     * y_margin in pixels for normal copy of avatar
     */
    public int Noffsety;
    /**
     * width in pixels for normal copy of avatar
     */
    public int Nw;
    /**
     * height in pixels for normal copy of avatar
     */
    public int Nh;

    public AvatarConfigs(){}

    public AvatarConfigs(int moffestx, int moffsety, int mw, int mh, int noffestx, int noffsety, int nw, int nh) {
        Moffestx = moffestx;
        Moffsety = moffsety;
        Mw = mw;
        Mh = mh;
        Noffestx = noffestx;
        Noffsety = noffsety;
        Nw = nw;
        Nh = nh;
    }


}
