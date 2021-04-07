package org.bansang.service;


import java.util.Date;

import javax.inject.Inject;

import org.bansang.dto.MemberDTO;
import org.bansang.dto.RecommendDTO;
import org.bansang.mapper.GroupMemberMapper;
import org.bansang.mapper.MemberMapper;
import org.bansang.mapper.RecommendMapper;
import org.springframework.stereotype.Service;
import java.util.List;

import org.bansang.dto.MemberAreaDTO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import lombok.extern.java.Log;
@Service
@Log
@Transactional
public class MemberServiceImpl implements MemberService{

	@Inject
	private MemberMapper memberMapper;
	
	@Inject
	private GroupMemberMapper groupMemberMapper;
	
	@Inject
	private RecommendMapper recommendMapper;
	
	@Override
	public MemberDTO login(MemberDTO dto) {
		
		return memberMapper.login(dto);
	}

	@Override
	public MemberDTO autoLogin(String cookeValue) {
		
		return memberMapper.autoLogin(cookeValue);
	}

	@Override
	public void keepLogin(String memberId, String sessionKey, Date sessionLimit) {
		memberMapper.keepLogin(memberId, sessionKey, sessionLimit);
		return;
	}

	@Override
	public void updateAutoLimit(String sessionKey, Date sessionLimit) {
		memberMapper.updateAutoLimit(sessionKey, sessionLimit);
		
	}

	@Override
	public void register(MemberDTO memberDto) {

		memberMapper.insert(memberDto);
		groupMemberMapper.signUpdateMembership(memberDto);
		String[] areas = memberDto.getAreas();
		
		log.info("" + memberDto);
		
		for(int i = 0; i < areas.length; i++) {
			
			MemberAreaDTO memberAreaDto = new MemberAreaDTO();
			memberAreaDto.setMemberId(memberDto.getMemberId());
			memberAreaDto.setArea(areas[i]);
			
			memberMapper.insertArea(memberAreaDto);
		}
	}
	
	@Override
	public void modify(MemberDTO dto) {
		memberMapper.deleteArea(dto);
		
		memberMapper.updateInfo(dto);
		groupMemberMapper.groupMemberNameUpdate(dto);
		String[] areas = dto.getAreas();
		System.out.println("=======ServiceImpl==========");
		System.out.println(areas);
		System.out.println("=================");
		for(int i = 0; i < areas.length; i++) {
			MemberAreaDTO memberAreaDto = new MemberAreaDTO();
			memberAreaDto.setMemberId(dto.getMemberId());
			memberAreaDto.setArea(areas[i]);
			memberMapper.insertArea(memberAreaDto);
		}
	}	
	
	

	@Override
	public void registerImage(String memberImage, String memberId) {
		
		memberMapper.insertImage(memberImage, memberId);
	}

	@Override
	public MemberDTO getInfo(MemberDTO dto) {

		return memberMapper.selectInfo(dto);
	}

	@Override
	public List<MemberAreaDTO> getArea(MemberDTO dto) {

		return memberMapper.selectArea(dto);
	}

	@Override
	public MemberDTO getLoginInfo(MemberDTO dto) {
	
		return memberMapper.selectLoginInfo(dto);
	}

	@Override
	public void deleteToken(MemberDTO dto) {
		memberMapper.deleteToken(dto);
	}

	@Override
	public List<RecommendDTO> recommendList(MemberDTO dto) {
		
		return recommendMapper.recommendList(dto);
	}

	
}
