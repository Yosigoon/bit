package org.bansang.service;


import java.util.Date;
import java.util.List;
import org.bansang.dto.MemberDTO;
import org.bansang.dto.RecommendDTO;
import org.springframework.http.ResponseEntity;

import org.bansang.dto.MemberAreaDTO;

public interface MemberService {

	public MemberDTO login(MemberDTO dto);

	public MemberDTO autoLogin(String cookeValue);

	public void keepLogin(String memberId, String sessionKey, Date sessionLimit);

	public void updateAutoLimit(String sessionKey, Date sessionLimit);

	public void register(MemberDTO dto);

	public void registerImage(String memberImage, String memberId);

	public MemberDTO getInfo(MemberDTO dto);

	public List<MemberAreaDTO> getArea(MemberDTO dto);
	
	public MemberDTO getLoginInfo(MemberDTO dto);

	public void modify(MemberDTO dto);

	public void deleteToken(MemberDTO dto);

	public List<RecommendDTO> recommendList(MemberDTO dto);

}
