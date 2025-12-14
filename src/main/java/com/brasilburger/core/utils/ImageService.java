package com.brasilburger.core.utils;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.net.URI;
import java.util.Map;

// Optional Cloudinary import commented out
// import com.cloudinary.Cloudinary;
// import com.cloudinary.utils.ObjectUtils;

public class ImageService {

    private static final String CLOUD_NAME = System.getenv("CLOUDINARY_CLOUD_NAME");
    private static final String CLOUD_BASE_URL = "https://res.cloudinary.com/" + (CLOUD_NAME == null ? "{cloud_name}" : CLOUD_NAME) + "/image/upload/";

    private ImageService() {}

    public static BufferedImage readLocal(File file) throws IOException {
        return ImageIO.read(file);
    }

    public static BufferedImage readFromUrl(String url) throws IOException {
        return ImageIO.read(URI.create(url).toURL());
    }

    public static Map<String, Object> uploadToCloudinary(File file, String publicId) {
        throw new UnsupportedOperationException("Upload Cloudinary désactivé. Ajoute la dépendance Cloudinary et implémente cette méthode si nécessaire.");
    }

    public static String buildCloudinaryUrl(String publicId, String format) {
        if (CLOUD_NAME == null) {
            return CLOUD_BASE_URL + publicId + (format != null ? ("." + format) : "");
        } else {
            return "https://res.cloudinary.com/" + CLOUD_NAME + "/image/upload/" + publicId + (format != null ? ("." + format) : "");
        }
    }
}
