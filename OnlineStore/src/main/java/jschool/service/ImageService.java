package jschool.service;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;


/**
 * Helper used to load images in application
 * */
public interface ImageService {

    /**
     * Scalable resize image to become icon
     * @param originalImage original image object
     * @return icon image
     */
    BufferedImage createResizedCopy(BufferedImage originalImage);

    /**
     * Gets file from params, processes it, resizes, saves and returns its source.
     * */
    /**
     * Uploads image
     * @param file image to save
     * @param productId id of product for loaded file
     * @return path to saved image
     */
    String uploadFileHandler(MultipartFile file,
                                           String productId);

}
