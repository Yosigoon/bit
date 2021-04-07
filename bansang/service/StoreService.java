package org.bansang.service;

import java.util.List;

import org.bansang.dto.RecommendDTO;


public interface StoreService {


	public List<RecommendDTO> list(RecommendDTO dto);
	
	public List<RecommendDTO> listMap(RecommendDTO dto);
	
	public RecommendDTO view(Long storeNumber);

	public void register(RecommendDTO dto);

	public RecommendDTO getInfo(Long storeNum);

	public List<String> getImageList(Long storeNumber);

	public List<RecommendDTO> specificList(RecommendDTO dto);

	public List<RecommendDTO> specificListMap(RecommendDTO dto);

	
}
