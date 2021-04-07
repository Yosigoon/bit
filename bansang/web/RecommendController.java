package org.bansang.web;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bansang.dto.CommentDTO;
import org.bansang.dto.RecommendDTO;
import org.bansang.service.RecommendService;
import org.bansang.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@CrossOrigin(origins="*", allowedHeaders="*")
@RestController
@RequestMapping("/recommend/*")
@Log
public class RecommendController {
	
	@Autowired
	private RecommendService recommendService;
	
	@Autowired
	private StoreService storeService;

	@GetMapping("/store/{storeNumber}")
	public RecommendDTO getInfo(@PathVariable("storeNumber") Long storeNumber) {
		return storeService.getInfo(storeNumber);
	}
	
	@GetMapping("/recommendInfo/{recommendNumber}")
	public RecommendDTO getRecommendInfo(@PathVariable("recommendNumber") Long recommendNumber) {
		
		return recommendService.getRecommendInfo(recommendNumber);
	}
	
	@GetMapping("/list/{storeNumber}")
	public List<RecommendDTO> getList(@PathVariable("storeNumber") Long storeNumber) {
		return recommendService.getList(storeNumber);
	}
		
	@DeleteMapping("/delete/{recommendNumber}")
	public ResponseEntity<String> deleteRecommend(@PathVariable("recommendNumber") String recommendNumber){
		Long rno = Long.parseLong(recommendNumber);
		recommendService.recommendDelete(rno);
		
		return new ResponseEntity<String>("delete", HttpStatus.OK);	
	}
	
	@PutMapping("/modify/{recommendNumber}")
	public ResponseEntity<String> updateRecommend(@PathVariable("recommendNumber") Long recommendNumber, @RequestBody RecommendDTO dto){

		recommendService.recommendModify(dto);
		
		return new ResponseEntity<String>("modify", HttpStatus.OK);
	}
}
