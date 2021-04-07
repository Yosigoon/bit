package org.bansang.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.bansang.dto.GroupMemberDTO;
import org.bansang.dto.MemberAreaDTO;
import org.bansang.dto.MemberDTO;
import org.springframework.web.bind.annotation.PutMapping;

public interface MemberMapper {

	@Select("select * from tbl_member where member_id = #{memberId} and member_password = #{memberPassword} ")
	public MemberDTO login(MemberDTO dto);

	@Select("select * from tbl_member where session_key = #{cookeValue} and session_limit > now()")
	public MemberDTO autoLogin(String cookeValue);

	@Select("select * from tbl_member where member_id = #{memberId}")
	public MemberDTO isUser(GroupMemberDTO dto);

	@Update("update tbl_member set session_key = #{sessionKey}, session_limit = #{sessionLimit} where member_id = #{memberId}")
	public void keepLogin(@Param("memberId") String memberId, @Param("sessionKey") String sessionKey, @Param("sessionLimit") Date sessionLimit);

	@Update("update tbl_member set session_limit = #{sessionLimit} where session_key = #{sessionKey}")
	public void updateAutoLimit(@Param("sessionKey") String sessionKey,@Param("sessionLimit") Date sessionLimit);
	
	@Insert("insert into tbl_member (member_id, member_password, member_name, member_token) values (#{memberId}, #{memberPassword}, #{memberName}, #{memberToken})")
	public void insert(MemberDTO dto);
	
	@Insert("insert into tbl_area values (#{memberId}, #{area})")
	public void insertArea(MemberAreaDTO dto);
	
	@Update("update tbl_member set member_image = #{memberImage} where member_id = #{memberId}")
	public void insertImage(@Param("memberImage")String memberImage, @Param("memberId")String memberId);

	@Select("select * from tbl_member where member_id = #{memberId}")
	public MemberDTO selectInfo(MemberDTO dto);

	@Select("select area from tbl_area where member_id = #{memberId}")
	public List<MemberAreaDTO> selectArea(MemberDTO dto);
	
	@Select("select * from tbl_member where member_id = #{memberId} and member_password = #{memberPassword}")
	public MemberDTO selectLoginInfo(MemberDTO dto);

	@Delete("delete from tbl_area where member_id = #{memberId}")
	public void deleteArea(MemberDTO dto);

	@Update("update tbl_member set member_name = #{memberName}, member_password = #{memberPassword}  where member_id = #{memberId}")
	public void updateInfo(MemberDTO dto);

	@Update("update tbl_member set member_token = null where member_id = #{memberId}")
	public void deleteToken(MemberDTO dto);

	

}
