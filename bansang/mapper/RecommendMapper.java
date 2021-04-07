package org.bansang.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.bansang.dto.MemberDTO;
import org.bansang.dto.RecommendDTO;

public interface RecommendMapper {
	

	@Insert("insert into tbl_recommend (store_number, recommend_contents, member_id) values (LAST_INSERT_ID(), #{recommendContents}, #{memberId} )")
    public void firstRegister(RecommendDTO dto);

	@Insert("insert into tbl_recommend (store_number, recommend_contents, member_id) values (#{storeNumber}, #{recommendContents}, #{memberId} )")
	public void plusRegister(RecommendDTO recommendDto);

	@Select("select * from tbl_recommend where store_number = #{storeNumber}")
	public List<RecommendDTO> selectList(Long storeNumber);

	public void fileUpload(String[] images);

	@Delete("delete from tbl_recommend where recommend_number = #{recommendNumber}")
	public void deleteRecommend(Long recommendNumber);

	public RecommendDTO getRecommendInfo(Long recommendNumber);

	@Update("update tbl_recommend set recommend_contents= #{recommendContents} where recommend_number= #{recommendNumber}")
	public void recommendModify(RecommendDTO dto);

	@Select("select * from tbl_recommend where recommend_number = #{recommendNumber}")
	public RecommendDTO selectInfo(Long recommendNumber);

	public void updateFirstImage();

	public List<RecommendDTO> recommendList(MemberDTO dto);

	@Delete("select * from tbl_recommend_image where recommend_number = #{recommendNumber}")
	public void deleteImages(RecommendDTO dto);

	
}