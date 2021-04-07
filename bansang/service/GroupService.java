package org.bansang.service;

import java.util.List;

import org.bansang.dto.GroupDTO;
import org.bansang.dto.GroupMemberDTO;
import org.bansang.dto.MemberDTO;

public interface GroupService {

	public void upload(GroupDTO dto);

	public List<GroupDTO> appGroupList(String MemberId);

	public List<GroupDTO> groupList(MemberDTO dto);
	
	public List<GroupMemberDTO> groupMemberList(Long groupNumber);

	public void deleteMemmber(Long groupNumber, MemberDTO dto);

	public void destroyGroup(Long groupNumber);

	public void modifyName(GroupDTO dto);

	public void leaveGroup(GroupMemberDTO dto);

	public void addGroupMember(GroupMemberDTO dto);
}
