package us.linkpl.linkplus.commom;



public class Consts {

    public static String FILE_ROOT = getRootPath();

    private static String getRootPath(){
            return System.getProperty("user.dir");
    }
}
