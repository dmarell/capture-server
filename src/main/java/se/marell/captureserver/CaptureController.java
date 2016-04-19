/*
 * Created by Daniel Marell 14-03-02 12:02
 */
package se.marell.captureserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class CaptureController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static byte[] create(BufferedImage image, String imageSubType) throws IOException {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            ImageIO.write(image, imageSubType, baos);
            baos.flush();
            return baos.toByteArray();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getCameraImage(
            @RequestParam(value = "cameraNumber", defaultValue = "0") int cameraNumber)
            throws CameraGrabber.CameraGrabberException, IOException {
        CameraGrabber grabber = new CameraGrabber(cameraNumber);
        BufferedImage image = grabber.grabImage();
        logger.info("grabbed image, w: {}, h: {}", image.getWidth(), image.getHeight());
        byte[] data = create(image, "jpeg");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.parseMediaType("image/jpeg"));
        return new ResponseEntity<>(data, responseHeaders, HttpStatus.OK);
    }
}