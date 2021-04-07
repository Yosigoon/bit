package org.bansang.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MemberDTO {

	private String memberId;
	private String memberPassword;
	private String memberName;
	private String memberImage;
	private String memberToken;
	private String admin;
	private String sessionKey;
	private Date sessionLimit;
	private Date registerDate;
	private String[] areas;
}
