package org.bansang.service;

import java.util.List;

import javax.inject.Inject;

import org.bansang.dto.RecommendDTO;
import org.bansang.mapper.RecommendImageMapper;
import org.bansang.mapper.RecommendMapper;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

@Service
@Log
public class RecommendServiceImpl implements RecommendService {

	@Inject
    private RecommendMapper recommendMapper;
	
	@Inject
	private RecommendImageMapper recommendImageMapper;

	@Override
	public List<RecommendDTO> getList(Long storeNumber) {
		return recommendMapper.selectList(storeNumber);
	}

	@Override
	public void recommendDelete(Long recommendNumber) {
		recommendImageMapper.deleteImageFiles(recommendNumber);
		recommendMapper.deleteRecommend(recommendNumber);
		return;
	}

	@Override
	public RecommendDTO getRecommendInfo(Long recommendNumber) {
		return recommendMapper.getRecommendInfo(recommendNumber);
	}

	@Override
	public List<String> getImageList(Long recommendNumber) {
		
		return recommendImageMapper.getImageList(recommendNumber);
	}

	@Override
	public void recommendModify(RecommendDTO dto) {
		recommendMapper.recommendModify(dto);
		recommendMapper.deleteImages(dto);
		recommendMapper.fileUpload(dto.getImages());
		return;
	}	
	
	@Override
	public RecommendDTO getInfo(Long recommendNumber) {
		
		return recommendMapper.selectInfo(recommendNumber);
	}
}



