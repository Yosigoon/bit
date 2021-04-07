package org.bansang.dto;

import java.util.Date;
import lombok.Data;

@Data
public class CommentDTO {
	private Long commentNumber;  
	private String commentContents;
	private Long recommendNumber;
	private Date registerDate;
	private String memberId;
	private String memberName;
	private String memberImage;
}
