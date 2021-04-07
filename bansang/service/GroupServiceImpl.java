package org.bansang.service;

import java.util.List;

import org.bansang.dto.GroupDTO;
import org.bansang.dto.GroupMemberDTO;
import org.bansang.dto.MemberDTO;
import org.bansang.mapper.GroupMapper;
import org.bansang.mapper.GroupMemberMapper;
import org.bansang.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {
	
	@Autowired
	GroupMapper groupMapper;

	@Autowired
	GroupMemberMapper groupMemberMapper;
	
	@Autowired
	MemberMapper memberMapper;
	
	@Override
	public void upload(GroupDTO dto) {
		groupMapper.groupRegister(dto);
		groupMapper.groupMemberRegister(dto.getList());
		groupMapper.groupMembershipUpdate();
	}

	@Override
	public List<GroupDTO> appGroupList(String memberId) {
		return groupMapper.appGroupList(memberId);
	}

	public List<GroupDTO> groupList(MemberDTO dto) {
		return groupMapper.groupList(dto);
	}

	@Override
	public List<GroupMemberDTO> groupMemberList(Long groupNumber) {
		return groupMapper.groupMemberList(groupNumber);
	}

	@Override
	public void deleteMemmber(Long groupNumber, MemberDTO dto) {
		groupMemberMapper.deleteMemmber(groupNumber, dto);
		groupMapper.downCount(groupNumber);
		return;
	}

	@Override
	public void destroyGroup(Long groupNumber) {
		groupMemberMapper.destroyGroup(groupNumber);
		groupMapper.destroyGroup(groupNumber);
		return;
	}

	@Override
	public void modifyName(GroupDTO dto) {
		groupMapper.modifyName(dto);
	}

	@Override
	public void leaveGroup(GroupMemberDTO dto) {
		groupMemberMapper.leaveGroup(dto);
		groupMapper.downCount(dto.getGroupNumber());
		
	}

	@Override
	public void addGroupMember(GroupMemberDTO dto) {
		groupMemberMapper.addGroupMember(dto);
		MemberDTO isUser = memberMapper.isUser(dto);
		if(isUser != null) { // ���� ����ڶ��...
			groupMemberMapper.updateMembership(dto);
		}
		groupMapper.upCount(dto.getGroupNumber());
		
	}
}
