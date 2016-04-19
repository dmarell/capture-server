/*
 * Created by Daniel Marell 13-01-20 12:08 PM
 */
package se.marell.captureserver;

import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;

import static org.bytedeco.javacpp.opencv_core.IplImage;

public class CameraGrabber {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private FrameGrabber grabber;
    private int cameraNumber;
    public CameraGrabber(int cameraNumber) {
        this.cameraNumber = cameraNumber;
    }

    public BufferedImage grabImage() throws CameraGrabberException {
        try {
            if (grabber == null) {
                grabber = new OpenCVFrameGrabber(cameraNumber);
                grabber.start();
            }
            IplImage grabbedImage;
            try {
                grabbedImage = grabber.grab();
            } catch (FrameGrabber.Exception e) {
                logger.error("grab exception: {}", e.getMessage());
                return null;
            }
            return grabbedImage.getBufferedImage();
        } catch (FrameGrabber.Exception e) {
            throw new CameraGrabberException("grabImage failed", e);
        }
    }

    public static class CameraGrabberException extends Exception {
        public CameraGrabberException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}