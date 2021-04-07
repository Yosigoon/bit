package org.bansang.service;

import javax.inject.Inject;
import javax.swing.plaf.synth.SynthSplitPaneUI;

import org.bansang.dto.RecommendDTO;
import org.bansang.mapper.RecommendMapper;
import org.bansang.mapper.StoreMapper;
import org.bansang.util.Crawling;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


@Service
@Log
public class StoreServiceImpl implements StoreService {

	@Autowired
	StoreMapper storeMapper;
	
	@Inject
	private RecommendMapper recommendMapper;
	
	@Override
	public List<RecommendDTO> list(RecommendDTO dto) {
		dto.setRadius(dto.getRadius() / 1000); // 100m => 0.1m
		return storeMapper.listPage(dto);
	}
	
	@Override
	public List<RecommendDTO> listMap(RecommendDTO dto) {
		dto.setRadius(dto.getRadius() / 1000); // 100m => 0.1m
		return storeMapper.listMap(dto);
	}
	
	@Override
	public List<RecommendDTO> specificList(RecommendDTO dto) {
		return storeMapper.specificList(dto);
	}
	
	@Override
	public List<RecommendDTO> specificListMap(RecommendDTO dto) {

		return storeMapper.specificListMap(dto);
	}
	

	@Override
	public RecommendDTO view(Long storeNumber) {
		return storeMapper.view(storeNumber);
	}

	
	@Override
	public void register(RecommendDTO dto) {

		int count = 10;
		RecommendDTO obj = storeMapper.exist(dto);
		if(obj == null) {
			
			Long storeNumber = storeMapper.register(dto);
			List<String> storeImageName = new ArrayList<String>();
			for (int i = 0; i < count; i++) {
				try {	
					Crawling crawling = new Crawling((i+1), dto.getStoreName(), storeMapper, storeNumber);
					crawling.start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			recommendMapper.firstRegister(dto);
			
		}else {  
			dto.setStoreNumber(obj.getStoreNumber());
			recommendMapper.plusRegister(dto);
		}
		if(dto.getImages().length !=0) { 
			recommendMapper.fileUpload(dto.getImages());
			recommendMapper.updateFirstImage();
		}
	}

	@Override
	public RecommendDTO getInfo(Long storeNum) {
		return storeMapper.selectInfo(storeNum);
	}

	@Override
	public List<String> getImageList(Long storeNumber) {
		
		return storeMapper.getImageList(storeNumber);
	}







}
