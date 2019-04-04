
package com.chanxa.linayi.tools;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import java.io.File;

public class CreatFileUtils {

    public static String FILE_NAME_VIDEO = "Video";
    public static String FILE_NAME_PHOTO = "Photo";
    public static String FILE_NAME_VOICE = "Voice";
    public static String FILE_NAME = "Chookr";

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory()
                .getPath() + "/";
    }

    /**
     * 获取SD卡路径 + 应用名路径
     *
     * @return
     */
    public static String getSDCardAppPath() {
        return getSDCardPath() + FILE_NAME;
    }

    /**
     * 获取视频路径
     *
     * @return
     */
    public static String getVideoPath() {
        return getSDCardAppPath() + "/" +FILE_NAME_VIDEO + File.separator;
    }

    /**
     * 获取语音路径
     *
     * @return
     */
    public static String getVoicePath() {
        return getSDCardAppPath() + "/" + FILE_NAME_VOICE + File.separator;
    }

    /**
     * 获取获取拍照路径
     *
     * @return
     */
    public static String getPhotoPath() {
        return getSDCardAppPath() + "/" + FILE_NAME_PHOTO + File.separator;
    }

    public static void createRootFile(){
        File file = new File(getSDCardAppPath());
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static File changeFile(String filePath,
                                  String fileName) {
        File file = null;
        createRootFile();
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file;
    }

    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {

        }
    }

    public static void deleteFile(File file) {
        if (file == null){
            return;
        }
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        } else {
            Log.d("FileUtils", "文件不存在！" + "\n");
        }
    }

    public static String getSuffix(String url){

        if (TextUtils.isEmpty(url)){
            return ".jpg";
        }

        File file =new File(url);
        String fileName=file.getName();
        if (fileName.endsWith(".jpg")){
            return ".jpg";
        }else if(fileName.endsWith(".png")){
            return ".png";
        }else{
            return ".jpg";
        }
    }
}
