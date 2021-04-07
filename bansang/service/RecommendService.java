package org.bansang.service;

import java.util.List;

import org.bansang.dto.RecommendDTO;

public interface RecommendService {

	public List<RecommendDTO> getList(Long storeNumber);


	public void recommendDelete(Long recommendNumber);

	public RecommendDTO getRecommendInfo(Long recommendNumber);

	public List<String> getImageList(Long recommendNumber);

	public void recommendModify(RecommendDTO dto);

	public RecommendDTO getInfo(Long recommendNumber);


}
