package org.bansang.dto;
import java.util.Date;
import lombok.Data;

@Data
public class RecommendDTO {

	private Long recommendNumber;
	private Date registerDate;
	private String recommendContents;
	private Long storeNumber;
	private String memberId;
	private String storeName;
	private String storeAddress;
	private Double latitude;
	private Double longitude;
	private String areaKeyword;
	private String[] images;
	private String imageName;
	private Double radius;
	private Long curDataCount;
}


