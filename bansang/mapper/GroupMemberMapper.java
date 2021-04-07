package org.bansang.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.bansang.dto.GroupMemberDTO;
import org.bansang.dto.MemberDTO;

public interface GroupMemberMapper {

	@Delete("delete from tbl_group_member where group_number = #{groupNumber} and member_id = #{memberDTO.memberId}")
	public void deleteMemmber(@Param("groupNumber") Long groupNumber, @Param("memberDTO") MemberDTO dto);

	@Delete("delete from tbl_group_member where group_number = #{groupNumber}")
	public void destroyGroup(Long groupNumber);

	@Delete("delete from tbl_group_member where group_number = #{groupNumber} and member_id = #{memberId}")
	public void leaveGroup(GroupMemberDTO dto);

	@Insert("insert into tbl_group_member (group_number, member_id, member_name) values (#{groupNumber}, #{memberId}, #{memberName})")
	public void addGroupMember(GroupMemberDTO dto);

	@Update("update tbl_group_member set membership = 'y' where group_number = #{groupNumber} and member_id = #{memberId}")
	public void updateMembership(GroupMemberDTO dto);

	@Update("update tbl_group_member set member_name = #{memberName} where member_id = #{memberId}")
	public void groupMemberNameUpdate(MemberDTO dto);

	@Update("update tbl_group_member set membership = 'y' where member_id = #{memberId}")
	public void signUpdateMembership(MemberDTO dto);
	
}
