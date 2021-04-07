package org.bansang.web;

import java.util.List;

import org.bansang.dto.MemberAreaDTO;
import org.bansang.dto.MemberDTO;
import org.bansang.dto.RecommendDTO;
import org.bansang.service.MemberService;
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
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@CrossOrigin(origins="*", allowedHeaders="*")
@RequestMapping("/member/*")
@RestController
@Log
public class MemberController {

	@Autowired
	MemberService memberService;
	
	@PostMapping("/new")
	public void join(@RequestBody MemberDTO dto) {
		memberService.register(dto);
	}
	
	@PostMapping("/modify")
	public void modify(@RequestBody MemberDTO dto) {
		memberService.modify(dto);
	}
	
	
	@GetMapping("/profile")
	public MemberDTO getProfileInfo(MemberDTO dto) {
		return memberService.getInfo(dto);
	}
	
	@GetMapping("/recommendList")
	public List<RecommendDTO> recommendList(MemberDTO dto) {
		return memberService.recommendList(dto);
	}
	
	@GetMapping("/profileArea")
	public List<MemberAreaDTO> getProfileArea(MemberDTO dto) {
		
		return memberService.getArea(dto);
	}
	
	@PostMapping("/login")
	public MemberDTO checkLoginInfo(@RequestBody MemberDTO dto) {
		return memberService.getLoginInfo(dto);
	}
	
	@PutMapping("/deleteToken")
	public ResponseEntity<String> deleteToken(@RequestBody MemberDTO dto){
		memberService.deleteToken(dto);
		return new ResponseEntity<String>("delete", HttpStatus.OK);
	}
}
