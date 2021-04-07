package org.bansang.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.bansang.dto.MemberDTO;
import org.bansang.service.MemberService;
import org.bansang.service.RecommendService;
import org.bansang.service.StoreService;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gargoylesoftware.htmlunit.javascript.host.Console;

import lombok.extern.java.Log;

@CrossOrigin
@RequestMapping("/upload/*")
@RestController
@Log
public class ImageUploadController {

	@Autowired
	private RecommendService recommendService;

	@Autowired
	private StoreService storeService;
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/register")
	public @ResponseBody Map<String, String> uploadRecommendImagePost(@RequestParam("file") MultipartFile file)
			throws IOException {

		String original = file.getOriginalFilename();
		UUID uuid = UUID.randomUUID();
		String uploadName = uuid.toString() + "_" + original;

		String filePath = "C:\\zzz\\zupload\\" + uploadName;
		OutputStream out = new FileOutputStream(filePath);
		FileCopyUtils.copy(file.getInputStream(), out);
		
		
		// crop image---------------------
        BufferedImage origin = ImageIO.read(file.getInputStream());

		int height = origin.getHeight();
		int width = origin.getWidth();
		
		int imgsize = height >= width ? width : height;
		
		BufferedImage croppedImage = Scalr.crop(origin, (width-imgsize)/2, (height-imgsize)/2+50, imgsize, imgsize*3/4);

		BufferedImage resizedImage = Scalr.resize(croppedImage, 600, 450);
		
		String thumbnailName = "s_" + uploadName;
		
		ImageIO.write(resizedImage, "jpg", new File("C:\\zzz\\zupload\\" + thumbnailName + ".jpg"));
		// -------------------------------	
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("original", file.getOriginalFilename());
		map.put("uploadName", uploadName);
		return map;
	}
	
	
	@PostMapping("/profile")
	public @ResponseBody Map<String, String> uploadProfileImagePost(@RequestParam("file") MultipartFile file, MemberDTO dto)
			throws IOException {

		String original = file.getOriginalFilename();
		
		log.info(dto.getMemberId());
				
		original = original.substring(0, original.lastIndexOf(".") + 4);
				
		UUID uuid = UUID.randomUUID();
		String uploadName = uuid.toString() + "_" + original;
				
		String filePath = "C:\\zzz\\zupload\\" + uploadName;
		OutputStream out = new FileOutputStream(filePath);
		FileCopyUtils.copy(file.getInputStream(), out);
		
		// crop image---------------------
		BufferedImage origin = ImageIO.read(file.getInputStream());

		int height = origin.getHeight();
		int width = origin.getWidth();
		
		int imgsize = height >= width ? width : height;
		
		BufferedImage croppedImage = Scalr.crop(origin, (width-imgsize)/2, (height-imgsize)/2+10, imgsize, imgsize);

		BufferedImage resizedImage = Scalr.resize(croppedImage, 400, 400);
		
		String thumbnailName = "s_" + uploadName;
		
		ImageIO.write(resizedImage, "jpg", new File("C:\\zzz\\zupload\\" + thumbnailName));
		// -------------------------------	
			
		memberService.registerImage(uploadName, dto.getMemberId());
		

		Map<String, String> map = new HashMap<String, String>();
		map.put("imageName", uploadName);
		
		log.info("" + map);
		
		return map;
	}

	@GetMapping("/show/{uploadName:.+}")
	public @ResponseBody byte[] display(@PathVariable("uploadName") String uploadName) throws Exception {
		log.info("" + uploadName);
		File file = new File("C:\\zzz\\zupload\\" + uploadName);
		return FileUtils.readFileToByteArray(file);
	}
	

	@GetMapping("/recommendImages/{recommendNumber}")
	public @ResponseBody List<String> list(@PathVariable("recommendNumber") Long recommendNumber) {
		return recommendService.getImageList(recommendNumber);
	}

	
	
	@GetMapping("/thumbImages/{imageName:.+}")
    public @ResponseBody byte[] thumb(@PathVariable("imageName") String imageName) throws Exception {
        File file = new File("C:\\zzz\\crawling\\" + imageName + ".jpg");

        return FileUtils.readFileToByteArray(file);
    }
	

	@GetMapping("/zuploadThumbNoExtension/{imageName:.+}")
	public @ResponseBody byte[] zuploadThumbNoExtension(@PathVariable("imageName") String imageName) throws Exception {

		File file = new File("C:\\zzz\\zupload\\" + imageName);

		return FileUtils.readFileToByteArray(file);
	}
	
	// storeModify crawling 
	@GetMapping("/storeImages/{storeNumber}")
	public @ResponseBody List<String> storeImages(@PathVariable("storeNumber") Long storeNumber) {
		return storeService.getImageList(storeNumber);
	}
	
	
	// storeModify ImageUpload
	@PostMapping("/storeRegister")
	public @ResponseBody Map<String, String> storeRegister(@RequestParam("file") MultipartFile file)
			throws IOException {
		
		String original = file.getOriginalFilename();
		UUID uuid = UUID.randomUUID();
		String uploadName = uuid.toString() + "_" + original;

		String filePath = "C:\\zzz\\crawling\\" + uploadName;
		
		
		OutputStream out = new FileOutputStream(filePath);
		FileCopyUtils.copy(file.getInputStream(), out);
		
		
		
        BufferedImage origin = ImageIO.read(file.getInputStream());

		int height = origin.getHeight();
		int width = origin.getWidth();
		
		int imgsize = height >= width ? width : height;
		
		BufferedImage croppedImage = Scalr.crop(origin, (width-imgsize)/2, (height-imgsize)/2+50, imgsize, imgsize*3/4);

		BufferedImage resizedImage = Scalr.resize(croppedImage, 600, 450);
		
		String[] nameArr = uploadName.split("\\.");
		
		uploadName = nameArr[0];
		
		String thumbnailName = "s_" + uploadName;
		ImageIO.write(resizedImage, "jpg", new File("C:\\zzz\\crawling\\" + thumbnailName + ".jpg"));
			

		Map<String, String> map = new HashMap<String, String>();
		map.put("original", file.getOriginalFilename());
		map.put("thumbnailName", thumbnailName);
		map.put("uploadName", uploadName);
		return map;
	}
	
	@GetMapping("/storeShow/{imageName:.+}")
	public @ResponseBody byte[] storeShow(@PathVariable("imageName") String imageName) throws Exception {
/*		log.info("=====================");
		log.info("" + imageName);
		log.info("=====================");*/
		File file = new File("C:\\zzz\\crawling\\" + imageName + ".jpg");
		return FileUtils.readFileToByteArray(file);
	}


	@GetMapping("/profileImage/{imageName:.+}")
	public @ResponseBody byte[] profile(@PathVariable("imageName") String imageName) throws Exception {

		File file = new File("C:\\zzz\\profile\\" + imageName);

		return FileUtils.readFileToByteArray(file);
	}

}
