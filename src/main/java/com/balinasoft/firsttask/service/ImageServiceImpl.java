package com.balinasoft.firsttask.service;

import com.balinasoft.firsttask.dto.ImageDtoIn;
import com.balinasoft.firsttask.dto.ImageDtoOut;
import com.balinasoft.firsttask.system.error.ApiAssert;
import com.balinasoft.firsttask.util.StringGenerator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Calendar;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ImageServiceImpl implements ImageService {

    @Value("${project.image-folder}")
    private String IMAGE_FOLDER;

    private final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public ImageDtoOut uploadImage(ImageDtoIn imageDtoIn, long l) {
        String fileName;
        try {
            fileName = saveImage(imageDtoIn.getBase64Image());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ImageDtoOut(100, fileName);
    }

    private String saveImage(String base64Image) throws IOException {
        String fileName = generateUniqueFileName(IMAGE_FOLDER, "jpg");
        Path destinationFile = Paths.get(fileName);

        byte[] bytes = Base64.decodeBase64(base64Image);
        checkImage(bytes);

        Files.write(destinationFile, bytes, StandardOpenOption.CREATE);
        return fileName;
    }

    private void checkImage(byte[] bytes) throws IOException {
        int maxSize = 1280;
        byte[] jpegMagicNumber = new byte[]{(byte) 0xff, (byte) 0xd8, (byte) 0xff, (byte) 0xe0};
        byte[] pngMagicNumber = new byte[]{(byte) 0x89, (byte) 0x50, (byte) 0x4e, (byte) 0x47};

        byte[] magicNumber = Arrays.copyOf(bytes, 4);
        ApiAssert.badRequest(!Arrays.equals(jpegMagicNumber, magicNumber)
                && !Arrays.equals(pngMagicNumber, magicNumber), "bad-image");

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(bis);
        } catch (IIOException e) {
            //noinspection ConstantConditions
            ApiAssert.badRequest(true, "bad-image");
        }
        ApiAssert.badRequest(bufferedImage == null, "bad-image");
        ApiAssert.badRequest(bufferedImage.getWidth() > maxSize || bufferedImage.getHeight() > maxSize,
                "big-image");
    }


    private String generateUniqueFileName(String folder, String extension) throws IOException {
        String fileName;
        do {
            fileName = folder + generateRandomFileName(extension);
        }
        while (Files.exists(Paths.get(getFullPath(fileName))));

        createFolders(fileName);

        return fileName;
    }

    private String generateRandomFileName(String extension) {
        Calendar c = Calendar.getInstance();
        return "/" +
                c.get(Calendar.YEAR) +
                "/" +
                c.get(Calendar.MONTH) +
                "/" +
                c.get(Calendar.DAY_OF_MONTH) +
                "/" +
                StringGenerator.generate(32) +
                "." +
                extension;
    }

    private String getFullPath(String fileName) {
        return IMAGE_FOLDER + "/" + fileName;
    }

    private void createFolders(String fileName) throws IOException {
        String onlyFolder = fileName.substring(0, fileName.lastIndexOf('/'));
        Files.createDirectories(Paths.get(onlyFolder));
    }
}
