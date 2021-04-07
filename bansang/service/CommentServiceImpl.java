package org.bansang.service;

import java.util.List;

import javax.inject.Inject;

import org.bansang.dto.CommentDTO;
import org.bansang.mapper.CommentMapper;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class CommentServiceImpl implements CommentService {
	
	
	
	@Inject
	private CommentMapper commentMapper;
	
	
	@Override
	public void commentRegister(CommentDTO dto) {
		log.info("service register: " + dto);
		commentMapper.insertComment(dto);
	}
	
	@Override
	public List<CommentDTO> commentList(Long recommendNumber){
//		log.info("============");
//		log.info("serviceImpl List : " + recommendNumber);
//		log.info("============");
		
		return commentMapper.list(recommendNumber);
	}
	
	
	@Override
	public void commentDelete(Long commentNumber) {
		commentMapper.deleteComment(commentNumber);		
	}
	
	@Override
	public void commentModify(CommentDTO dto) {
		commentMapper.updateComment(dto);
	}
	
	
	

}
