package org.bansang.web;

import java.util.List;
import org.apache.poi.ss.usermodel.Header;
import org.bansang.dto.CommentDTO;
import org.bansang.dto.RecommendDTO;
import org.bansang.mapper.CommentMapper;
import org.bansang.service.CommentService;
import org.bansang.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

@Log
@CrossOrigin(origins="*", allowedHeaders="*")
@RestController
@RequestMapping("/review/*")
public class CommentController {
	
	@Autowired
	private CommentService commentService;

	@Autowired
	private RecommendService recommendService;
	
	//댓글 추가
	@PostMapping("/add")
	public void addComment(@RequestBody CommentDTO dto) {
		log.info("===================");
		log.info("Comment Controller: " + dto.getCommentContents());		
		log.info("===================");
		
		commentService.commentRegister(dto);
	}
	

	@GetMapping("/commentList/{recommendNumber}")
	public List<CommentDTO> commentList(@PathVariable("recommendNumber") Long recommendNumber){
		return commentService.commentList(recommendNumber);
	}
	
	
	
	@DeleteMapping("/delete/{commentNumber}")
	public ResponseEntity<String> deleteComment(@PathVariable("commentNumber") Long commentNumber){
		log.info("del recommendNumber: "+ commentNumber);
		
		commentService.commentDelete(commentNumber);
		
		return new ResponseEntity<String>("delete", HttpStatus.OK);
		
	}
	
	
	@PutMapping("/modify/{commentNumber}")
	public ResponseEntity<String> updateComment(@PathVariable("commentNumber") Long commentNumber, @RequestBody CommentDTO dto){
		
		dto.setCommentNumber(commentNumber);
		commentService.commentModify(dto);
		
		return new ResponseEntity<String>("modify", HttpStatus.OK);
	}
	
	
	@GetMapping("/recommend/{recommendNumber}")
	public RecommendDTO getInfo(@PathVariable("recommendNumber") Long recommendNumber) {
		
		return recommendService.getInfo(recommendNumber);
	}

}
