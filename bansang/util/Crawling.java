package org.bansang.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileLockInterruptionException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.bansang.mapper.StoreMapper;
import org.imgscalr.Scalr;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.gargoylesoftware.htmlunit.javascript.host.html.Option;
import lombok.extern.java.Log;

import lombok.extern.java.Log;

@Log
public class Crawling extends Thread {

	public static WebDriver driver;
	int index;
	String storeName;
	StoreMapper storeMapper;
	Long storeNumber;

	public Crawling(int index, String storeName, StoreMapper storeMapper, Long storeNumber) {
		this.index = index;
		this.storeName = storeName;
		this.storeMapper = storeMapper;
		this.storeNumber = storeNumber;
	}

	public void run() {
		try {
			System.setProperty("webdriver.chrome.driver", "C:\\zzz\\chromedriver\\chromedriver.exe"); // ũ�ҵ���̹� ����

			ChromeOptions options = new ChromeOptions();
			
			options.addArguments("headless");
		  
			String url = "https://search.naver.com/search.naver?where=image&sm=tab_jum&query="+storeName; 
	
			driver = new ChromeDriver(options);
			driver.get(url);

			String imgUrl = driver.findElement(By.cssSelector(
					"#_sau_imageTab > div.photowall._photoGridWrapper > div.photo_grid._box > div:nth-child(" + index
							+ ") > a.thumb._thumb > img"))
					.getAttribute("src");

			URL image = new URL(imgUrl);
			BufferedImage img = ImageIO.read(image);
			UUID uuid = UUID.randomUUID();
			String uploadName = uuid.toString();
			String filePath = "C:\\zzz\\crawling\\" + uploadName + ".jpg";
			// ImageIO.write(img, "png", new File(filePath));

			ImageIO.write(img, "jpg", new File(filePath));
			
			// crop image---------------------
			File file = new File("C:\\zzz\\crawling\\"+ uploadName + ".jpg");

			byte[] imageInByte = FileUtils.readFileToByteArray(file);
			
			InputStream in = new ByteArrayInputStream(imageInByte);
			BufferedImage origin = ImageIO.read(in);
			
			log.info("origin==========================" + origin);

			int height = origin.getHeight();
			int width = origin.getWidth();
			
			int imgsize = height >= width ? width : height;
			
			BufferedImage croppedImage = Scalr.crop(origin, (width-imgsize)/2, (height-imgsize)/2+50, imgsize, imgsize*3/4);

			BufferedImage resizedImage = Scalr.resize(croppedImage, 600, 450);
			
			String thumbnailName = "s_" + uploadName;
			
			ImageIO.write(resizedImage, "jpg", new File("C:\\zzz\\crawling\\" + thumbnailName + ".jpg"));
			// -------------------------------	
			
			storeMapper.uploadStoreImage(uploadName, storeNumber);

		} catch (Exception e) {
			e.printStackTrace();
		}

		driver.quit();

	}

}
