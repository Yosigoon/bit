package org.bansang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.bansang.dto.Criteria;
import org.bansang.dto.RecommendDTO;
import org.bansang.dto.RecommendImageDTO;
import org.bansang.dto.SearchCriteria;

public interface BansangMapper {
	
/*	@Select("select * from tbl_store order by store_number desc limit #{skip}, 10")
	public List<RecommendDTO> listPage(Criteria cri);*/
	public List<RecommendDTO> listPage(SearchCriteria cri);

	@Select("select * from tbl_store where store_number = #{storeNumber}")
	public RecommendDTO getModify(Long storeNumber);

/*	@Select("select count(store_number) from tbl_store where store_number > 0")
	public int getTotal(Criteria cri);*/
	public int getTotal(SearchCriteria cri);

	@Delete("delete from tbl_store where store_number = #{storeNumber}")
	public void delete(RecommendDTO dto);

	@Delete("delete from tbl_store_image where store_number = #{storeNumber}")
	public void deleteImg(RecommendDTO dto);
	
//	@Insert("insert into table_store_image (image_name, store_number) values (#{imageName}, LAST_INSERT_ID())")
//    public void storeModifyFileUpload(String imageName);

	
	@Insert("insert into tbl_store_image (image_name, store_number) values (#{imageName}, #{storeNumber})")
	public void updatedImg(@Param("storeNumber") Long storeNumber, @Param("imageName") String imageName );

	@Update("update tbl_store set image_name= #{imageName} where store_number= #{storeNumber}")
	public void updateMainImage(RecommendDTO dto);

	
}
