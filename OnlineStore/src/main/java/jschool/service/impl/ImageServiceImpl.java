package jschool.service.impl;

import jschool.service.ImageService;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.xml.ws.soap.Addressing;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

/**
 * Helper used to load images in application
 * */
@Service
public class ImageServiceImpl implements ImageService {

    private String imageDirectory;
    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
    private String ext = ".png";

    /**
     * Scalable resize image to become icon
     * @param originalImage original image object
     * @return icon image
     */
    public BufferedImage createResizedCopy(BufferedImage originalImage)
    {
        logger.info("resizing...");
        Image scaledImage = originalImage.getScaledInstance(200, -1, Image. SCALE_SMOOTH);
        BufferedImage buffereScaledImage = new BufferedImage(scaledImage.getWidth(null),
                                                            scaledImage.getHeight(null),
                                                            BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = buffereScaledImage.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(scaledImage, 0, 0, null);
        g.dispose();
        return buffereScaledImage;
    }

    /**
     * Gets file from params, processes it, resizes, saves and returns its source.
     * */
    /**
     * Uploads image
     * @param file image to save
     * @param productId id of product for loaded file
     * @return path to saved image
     */
    public String uploadFileHandler(MultipartFile file,
                                           String productId) {
        if (!(Objects.isNull(file) || file.isEmpty())) {
            try {
                String productFileName = RandomStringUtils.randomAlphanumeric(8) + "_" + productId;
                String imageName = new File(imageDirectory).getAbsolutePath() + File.separator + productFileName + ext;
                String iconName = new File(imageDirectory).getAbsolutePath() + File.separator + "icon" + productFileName + ext;

                File serverFile = new File(imageName);

                try(BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile))){
                    stream.write(file.getBytes());
                }

                BufferedImage originalImage = ImageIO.read(serverFile);

                BufferedImage resizeImageJpg = createResizedCopy(originalImage);

                ImageIO.write(resizeImageJpg, "png", new File( iconName));

                logger.info("Server File Location="+ serverFile.getAbsolutePath());
                logger.info("You've successfully uploaded file=" + serverFile.getAbsolutePath());
                return productFileName + ext;
            } catch (Exception e) {
                logger.error(e.toString());
                logger.error("You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
                return "";
            }
        } else {
            logger.error("You failed to upload the file was empty.");
            return "";
        }
    }

    @Autowired
    public void setImageDirectory(String imageDirectory) {
        this.imageDirectory = imageDirectory;
    }
}
