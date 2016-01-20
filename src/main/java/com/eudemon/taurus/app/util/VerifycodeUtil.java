package com.eudemon.taurus.app.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import com.asprise.ocr.Ocr;

public class VerifycodeUtil {
	public static String getCode(InputStream in, Color codeColor) {
		String code = "";
		try {
			FileUtils.copyInputStreamToFile(in, new File("d:/1.png"));

			BufferedImage image = ImageIO.read(new File("d:/1.png"));
			int border = 0;
			for (int y = image.getMinY() + border; y < image.getHeight() - border; y++) {
				for (int x = image.getMinX() + border; x < image.getWidth() - border; x++) {
					if (image.getRGB(x, y) != codeColor.getRGB()) {
						image.setRGB(x, y, Color.WHITE.getRGB());
					}
				}
			}
			ImageIO.write(image, "png", new File("d:/2.png"));

			BufferedImage image1 = ImageIO.read(new File("d:/2.png"));
			for (int y = image1.getMinY(); y < image1.getHeight(); y++) {
				for (int x = image1.getMinX(); x < image1.getWidth(); x++) {
					if (image1.getRGB(x, y) != Color.WHITE.getRGB()) {
						image1.setRGB(x, y, Color.BLACK.getRGB());
					}
				}
			}
			ImageIO.write(image1, "png", new File("d:/3.png"));

			Ocr.setUp(); // one time setup
			Ocr ocr = new Ocr(); // create a new OCR engine
			ocr.startEngine("eng", Ocr.SPEED_FAST); // English
			code = ocr.recognize(new File[] { new File("d:/3.png") }, Ocr.RECOGNIZE_TYPE_ALL,
					Ocr.OUTPUT_FORMAT_PLAINTEXT); // PLAINTEXT | XML | PDF | RTF

			code = code.substring(0, 4);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return code;
	}
}
