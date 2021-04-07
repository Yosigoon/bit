package org.bansang.service;

import java.util.List;

import org.bansang.dto.CommentDTO;

public interface CommentService {
	
	public void commentRegister(CommentDTO dto);
	
	public List<CommentDTO> commentList(Long recommendNumber);
	
	public void commentDelete(Long commentNumber);
	
	public void commentModify(CommentDTO dto);

	

}
