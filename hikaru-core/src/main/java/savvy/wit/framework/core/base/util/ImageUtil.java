package savvy.wit.framework.core.base.util;

import savvy.wit.framework.core.base.service.log.Log;
import savvy.wit.framework.core.pattern.factory.LogFactory;
import savvy.wit.framework.core.base.callback.DrawImageCallBack;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/*******************************
 * Copyright (C),2018-2099, ZJJ
 * Title : 
 * File name : ImageUtil
 * Author : zhoujiajun
 * Date : 2018/8/30 10:05
 * Version : 1.0
 * Description : 
 ******************************/
public class ImageUtil {
    private Log log = LogFactory.getLog();

    private BufferedImage image;

    private Object [] types = {"jpg","png","gif","bmp"};

    public BufferedImage getImage() {
        return image;
    }

    public byte[] getBytes() {
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", byteArrayOutputStream);
            bytes = byteArrayOutputStream.toByteArray();
        }catch (Exception e) {
            log.error(e);
        }finally {
            try {
                byteArrayOutputStream.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    private static ImageUtil $this;

    private synchronized void setImage(BufferedImage image) {
        this.image = image;
    }

    private static class LazyInit{
        private static ImageUtil INITIALIZATION = new ImageUtil();
    }

    public static ImageUtil me() {
        $this = LazyInit.INITIALIZATION;
        return LazyInit.INITIALIZATION;
    }

    public ImageUtil draw(int width,
                      int height,
                      int imageType,
                      DrawImageCallBack callBack) {
        BufferedImage image = null;
        try {
            image = new BufferedImage(width,height,imageType);
            if (image == null) {
                throw new RuntimeException("image create error");
            }
            Graphics graphics = image.getGraphics();
            callBack.draw(graphics, image);
            if (graphics != null) {
                graphics.dispose();
            }
        } catch (Exception e) {
            log.error(e);
        }
        this.setImage(image);
        return $this;
    }

    public ImageUtil save (String... params) {
        String type = (String) ArraysUtil.me().getOneOfIt(types);
        String name = StringUtil.createCode(10, true);
        if (null == this.getImage()) {
            throw new RuntimeException("can not save, image is null.");
        }
        if (params.length > 0) {
            type = params[0];
        }
        if (params.length > 1 && params.length <= 2) {
            name = params[1] + "." + type;
        } else if (params.length > 2) {
            name = params[1] + "//" + (StringUtil.isBlank(params[2]) ? StringUtil.createCode(5, true) : params[2]) + "." + type;
        } else {
            name += "." + type;
        }
        try {
            ImageIO.write(this.getImage(), type, FileUtil.me().create(name));
        } catch (Exception e) {
            if (("file is exist").equals(e.getMessage())) {
                try {
                    ImageIO.write(this.getImage(), type, new File(name));
                } catch (Exception ioe) {
                    log.error(ioe);
                }
            } else log.error(e);
        }
        return $this;
    }

}
