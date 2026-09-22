package kr.co.gnx.system.commoncode;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import kr.co.gnx.base.BaseDAO;
import kr.co.gnx.system.commoncode.CommonCodeVO;

@Repository(value="CommonCodeDAO")
public class CommonCodeDAO extends BaseDAO{
	private static final Logger logger = LoggerFactory.getLogger(CommonCodeDAO.class);
	
	/**
	 * 
	 * @param vo
	 * @return
	 */
	public List<CommonCodeVO> SelectCommonCodeList(CommonCodeVO commonCodeVO){
		return getSqlSession().selectList( getCommcodemapper()+ "SelectCommonCodeList",commonCodeVO);
	}
	
	/**
	 * @brief 공통코드 그룹 리스트 조회
	 * @author KIMDONGUK
	 * @since 2019-04-02
	 * @param CommonCodeVO
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> selectCommonCodeGroupList(CommonCodeVO commonCodeVO) {
		return getSqlSession().selectList( getCommcodemapper()+ "selectCommonCodeGroupList", commonCodeVO);
	}

	
	/**
	 * @Description  : 연령별 기준금액 초과계약 연령별 기준금액설정 조회
	 * @author       : lakhyun.kim
	 * @since        : 2019. 04. 09
	 * @return       : Map
	 */
	public Map selectAgeAmtView(CommonCodeVO commonCodeVO) {
		return getSqlSession().selectOne(getCommcodemapper() + "selectAgeAmtView", commonCodeVO);
	}
	
	/**
	 * @Description  : 공통 코드 수정(cd_vl 수정 가능)
	 * @author       : lakhyun.kim
	 * @since        : 2019. 04. 09
	 * @return       : int
	 */
	public int updateCommonCodeValue(CommonCodeVO commonCodeVO){
		int resultInt = 0;
		resultInt = getSqlSession().update(getCommcodemapper() + "updateCommonCodeValue", commonCodeVO);
		return resultInt;
	}

	/**
	 * @brief 공통코드그룹 입력
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param CommonCodeVO
	 * @return ModelAndView
	 */
	public void insertCommonCodeGroup(CommonCodeVO commonCodeVO) {
		getSqlSession().insert(getCommcodemapper() + "insertCommonCodeGroup", commonCodeVO);
	}
	
	/**
	 * @brief 공통코드그룹 수정
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param CommonCodeVO
	 * @return ModelAndView
	 */
	public void updateCommonCodeGroup(CommonCodeVO commonCodeVO) {
		getSqlSession().update(getCommcodemapper() + "updateCommonCodeGroup", commonCodeVO);
	}
	
	/**
	 * @brief 공통코드그룹 삭제
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param CommonCodeVO
	 * @return ModelAndView
	 */
	public void deleteCommonCodeGroup(CommonCodeVO commonCodeVO) {
		getSqlSession().delete(getCommcodemapper() + "deleteCommonCodeGroup", commonCodeVO);
	}

	/**
	 * @brief 공통코드 입력
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param CommonCodeVO
	 * @return ModelAndView
	 */
	public void insertCommonCode(CommonCodeVO commonCodeVO) {
		getSqlSession().insert(getCommcodemapper() + "insertCommonCode", commonCodeVO);
	}
	
	/**
	 * @brief 공통코드 수정
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param CommonCodeVO
	 * @return ModelAndView
	 */
	public void updateCommonCode(CommonCodeVO commonCodeVO) {
		getSqlSession().update(getCommcodemapper() + "updateCommonCode", commonCodeVO);
	}
	
	/**
	 * @brief 공통코드 삭제
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param CommonCodeVO
	 * @return ModelAndView
	 */
	public void deleteCommonCode(CommonCodeVO commonCodeVO) {
		getSqlSession().delete(getCommcodemapper() + "deleteCommonCode", commonCodeVO);
	}

	/**
	 * @brief 공통코드 단건 조회
	 * @author KIMDONGUK
	 * @since 2020-01-08
	 * @param CommonCodeVO
	 * @return CommonCodeVO
	 */
	public CommonCodeVO selectCommonCodeView(CommonCodeVO commonCodeVO) {
		return getSqlSession().selectOne(getCommcodemapper() + "selectCommonCodeView", commonCodeVO);
	}
}
