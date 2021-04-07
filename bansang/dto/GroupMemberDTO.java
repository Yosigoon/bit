package org.bansang.dto;

import lombok.Data;

@Data
public class GroupMemberDTO {

	private Long groupNumber;
	private String memberName; 
	private String memberId; 
	private String membership;
	private String imageName;
}
