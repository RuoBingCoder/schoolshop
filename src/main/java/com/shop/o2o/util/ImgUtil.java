package com.shop.o2o.util;

import com.shop.o2o.dto.ImgHolder;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author : 石建雷
 * @date :2019/4/8
 * 图片工具包
 */
@Slf4j
public class ImgUtil {
    /**
     * 当前项目路径
     */
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random RANDOM = new Random();

    /**
     * 添加缩略图
     *
     * @param imgHolder
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(ImgHolder imgHolder, String targetAddr) {

        String realFileName = getRandomFileName();
        String extension = getFileExtension(imgHolder.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(imgHolder.getInputStream()).size(600, 400)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/waterMark.jpg")), 0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            e.fillInStackTrace();

        }
        return relativeAddr;
    }

    /**
     * 创建目标路径所在目录
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File file = new File(realFileParentPath);
        if (!file.exists()) {
            file.mkdirs();

        }

    }

    /**
     * 获取文件流的扩展名
     *
     * @return
     */
    private static String getFileExtension(String cFile) {

        return cFile.substring(cFile.lastIndexOf("."));
    }

    /**
     * 生成随机文件名，当前年月日时分秒+随机五位数
     *
     * @return
     */
    public static String getRandomFileName() {
        int rannum = RANDOM.nextInt(8999) + 10000;
        String nowTimeStr = simpleDateFormat.format(new Date());

        return nowTimeStr + rannum;


    }


    /**
     * storePath是文件路径还是目录路径
     * 如果storePath是文件路径则删除该文件
     * 如果storePath是目录路径则进行删除该路径下所有文件
     * 删除图片
     *
     * @param storePath
     */

    public static void deleteImgPath(String storePath) {
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File file[] = fileOrPath.listFiles();
                for (int i = 0; i < file.length; i++) {
                    file[i].delete();

                }

            }
            fileOrPath.delete();
        }

    }

    /**
     * 添加水印
     */

    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("H:/images/IMG_10691.jpg")).size(600, 500)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/waterMark.jpg")), 0.25f)
                .outputQuality(0.8f).toFile("H:/images/IMG_106912.jpg");
    }
}
