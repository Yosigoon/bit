package org.bansang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.bansang.dto.CommentDTO;

public interface CommentMapper {
	
	@Insert("insert into tbl_comment (comment_contents, recommend_number, member_id) values (#{commentContents}, #{recommendNumber}, #{memberId})")
	public void insertComment(CommentDTO dto);
	
	@Select("select tbl_member.member_id, comment_number, comment_contents, recommend_number, image_name, temp.register_date, member_name, member_image from\r\n" + 
			"(select * from tbl_comment where recommend_number = #{recommendNumber}) temp\r\n" + 
			"inner join tbl_member\r\n" + 
			"on tbl_member.member_id = temp.member_id")
	public List<CommentDTO> list(Long recommendNumber);
	
	@Delete("delete from tbl_comment where comment_number = #{commentNumber}")
	public void deleteComment(Long commentNumber);
	
	@Update("update tbl_comment set comment_contents = #{commentContents} where comment_number = #{commentNumber}")
	public void updateComment(CommentDTO dto);

}
