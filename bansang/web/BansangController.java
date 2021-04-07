package org.bansang.web;

import org.bansang.dto.Criteria;
import org.bansang.dto.RecommendDTO;
import org.bansang.dto.RecommendImageDTO;
import org.bansang.dto.SearchCriteria;
import org.bansang.service.BansangService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gargoylesoftware.htmlunit.javascript.host.Console;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import lombok.extern.java.Log;

@Controller
@RequestMapping("/bansang/*")
@Log
public class BansangController {

	@Autowired
	BansangService bansangService;

	@GetMapping("/profile")
	public void getProfile() {
		return;
	}

	@GetMapping("/group")
	public void getGroup() {
		return;
	}

	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<byte[]> fileDown(@PathVariable("fileName") String fileName) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			in = new FileInputStream("C:\\zzz\\zdownload\\" + fileName);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.add("Content-Disposition",
					"attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}

	@GetMapping("/storeManagement")
	public void getlist(Model model, @ModelAttribute("cri") SearchCriteria cri) {
		model.addAttribute("list", bansangService.list(cri));
		return;
	}

	@GetMapping("/storeModify")
	public void GetModify(Model model, Long storeNumber, @ModelAttribute("cri") SearchCriteria cri) {
		model.addAttribute("info", bansangService.getModify(storeNumber));
		return;
	}

	@PostMapping("/remove")
	public String remove(RecommendDTO dto, RedirectAttributes rttr) {
		bansangService.delete(dto);
		rttr.addFlashAttribute("result", "delsuccess");
		return "redirect:/bansang/storeManagement";
	}

	@PostMapping("/storeModify")
	public String modify(RecommendDTO dto, RedirectAttributes rttr) {
		// log.info("===================");
		// log.info("" + dto);
		// log.info("===================");
		bansangService.modifyImage(dto);
		rttr.addAttribute("storeNumber", dto.getStoreNumber());
		rttr.addFlashAttribute("result", "modsuccess");
		return "redirect:/bansang/storeManagement";
	}

}
