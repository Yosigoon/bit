package org.bansang.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class GroupDTO {

	private Long groupNumber;
	private String groupName;
	private String groupLeader;
	private String groupLeaderName;
	private String memberId;
	private Long groupMemberCount;
	private Date registerDate;
	private List<GroupMemberDTO> list;
}
