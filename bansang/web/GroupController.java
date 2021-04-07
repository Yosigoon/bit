package org.bansang.web;


import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;
import org.bansang.dto.GroupDTO;
import org.bansang.dto.GroupMemberDTO;
import org.bansang.dto.MemberDTO;
import org.bansang.service.GroupService;
import org.bansang.util.ReadGroupExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.java.Log;

@CrossOrigin(origins="*", allowedHeaders="*", allowCredentials="true")
@RestController
@RequestMapping("/group/*")
@Log
public class GroupController {

	@Autowired
	GroupService groupService;

	@GetMapping("/appGroupList")
	public @ResponseBody List<GroupDTO> appGroupList(MemberDTO dto) {
		return groupService.appGroupList(dto.getMemberId());
	}
	
	@GetMapping("/groupList") 
	public @ResponseBody List<GroupDTO> groupList(MemberDTO dto) {
		return groupService.groupList(dto);
	}
	
	@GetMapping("/groupMemberList/{groupNumber}")
	public @ResponseBody List<GroupMemberDTO> groupMemberList(@PathVariable("groupNumber") Long groupNumber) {
		return groupService.groupMemberList(groupNumber);
	}


	@PostMapping("/excelUpload")
	public void uploadExcelFile(@RequestParam("file") MultipartFile file) throws Exception {
		
		UUID uuid = UUID.randomUUID();
		String uploadName = uuid.toString() + "_" + file.getOriginalFilename();
		String filePath = "C:\\zzz\\excel\\" + uploadName;
		OutputStream out = new FileOutputStream(filePath);
		FileCopyUtils.copy(file.getInputStream(), out);

		ReadGroupExcel excel = new ReadGroupExcel();
		GroupDTO dto = excel.readGroupFromExcelFile(filePath);

		groupService.upload(dto);
		
	}
	@PostMapping("/addGroupMember")
	public void addGroupMember(@RequestBody GroupMemberDTO dto){
		groupService.addGroupMember(dto);
	}
	
	@DeleteMapping("/dismissMember/{groupNumber}")
	public ResponseEntity<String> dismissMember(@PathVariable("groupNumber") Long groupNumber, @RequestBody MemberDTO dto){
		groupService.deleteMemmber(groupNumber, dto);
		return new ResponseEntity<String>("delete", HttpStatus.OK);	
	}
	@DeleteMapping("/destroyGroup/{groupNumber}")
	public ResponseEntity<String> destroyGroup(@PathVariable("groupNumber") Long groupNumber){	
		groupService.destroyGroup(groupNumber);
		return new ResponseEntity<String>("delete", HttpStatus.OK);
	}
	
	@DeleteMapping("/leaveGroup")
	public ResponseEntity<String> leaveGroup(@RequestBody GroupMemberDTO dto){
		groupService.leaveGroup(dto);
		return new ResponseEntity<String>("delete", HttpStatus.OK);	
	}
	
	@PutMapping("/groupNameMod")
	public ResponseEntity<String> groupNameMod(@RequestBody GroupDTO dto){

		groupService.modifyName(dto);
		
		return new ResponseEntity<String>("modify", HttpStatus.OK);
	}
}
