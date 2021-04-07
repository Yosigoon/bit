package org.bansang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface RecommendImageMapper {

	@Insert("insert into table_recommend_image (imageName, recommendNumber) values (#{imageName}, LAST_INSERT_ID())")
	public void insert(String imageName);
	
	@Select("select image_name imageName from tbl_recommend_image where recommend_number = #{recommendNumber}")
	public List<String> getImageList(Long recommendNumber);

	@Delete("delete from tbl_recommend_image where recommend_number = #{recommendNumber}")
	public void deleteImageFiles(Long recommendNumber);
}
