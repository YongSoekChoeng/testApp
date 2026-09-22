package kr.co.gnx.system.commoncode;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import kr.co.gnx.base.BaseService;
import kr.co.gnx.system.commoncode.CommonCodeVO;

@Service
public class CommonCodeService extends BaseService{
	private static final Logger logger = LoggerFactory.getLogger(CommonCodeService.class);
	
	/**
	 * 
	 * @param vo
	 * @return
	 */
	public List<CommonCodeVO> getCommonCodeList(CommonCodeVO commonCodeVO){
		logger.debug("== CommonCodeService getCommonCodeList");
		return getCommoncodeDAO().SelectCommonCodeList(commonCodeVO);
	}
	
	/**
	 * @brief 보증보험, 기타담보 보증목적구분
	 * @param vo
	 * @return
	 */
	public List<CommonCodeVO> getAllBondGbn(CommonCodeVO commonCodeVO){
		String[] grp_cmm_cd_arr = {"BOND_GBN", "ETC_BOND_GBN"};
		commonCodeVO.setGrp_cmm_cd_arr(grp_cmm_cd_arr);
		return getCommoncodeDAO().SelectCommonCodeList(commonCodeVO);
	}
	
	/**
	 * @brief 보증보험, 기타담보 보증종류 
	 * @param vo
	 * @return
	 */
	public List<CommonCodeVO> getAllBondType(CommonCodeVO commonCodeVO){
		String[] grp_cmm_cd_arr = {"BOND_TYPE", "ETC_BOND_TYPE"};
		commonCodeVO.setGrp_cmm_cd_arr(grp_cmm_cd_arr);
		return getCommoncodeDAO().SelectCommonCodeList(commonCodeVO);
	}

	/**
	 * @brief 공통코드 그룹 리스트 조회
	 * @author KIMDONGUK
	 * @since 2019-04-02
	 * @param CommonCodeVO
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getCommonCodeGroupList(CommonCodeVO commonCodeVO) {
		commonCodeVO.setMb_id(getUserSession().getMb_id());
		commonCodeVO.setIn_emp_cd(getUserSession().getEmp_cd());
		
		return getCommoncodeDAO().selectCommonCodeGroupList(commonCodeVO);
	}

	/**
	 * @brief 공통코드그룹 입력
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param List<CommonCodeVO>
	 * @return ModelAndView
	 */
	public void insertCommonCodeGroup(List<CommonCodeVO> models) {
		for(CommonCodeVO commonCodeVO : models) {
			commonCodeVO.setMb_id(getUserSession().getMb_id());
			commonCodeVO.setIn_emp_cd(getUserSession().getEmp_cd());
			
			getCommoncodeDAO().insertCommonCodeGroup(commonCodeVO);
		}
	}
	
	/**
	 * @brief 공통코드그룹 수정
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param List<CommonCodeVO>
	 * @return ModelAndView
	 */
	public void updateCommonCodeGroup(List<CommonCodeVO> models) {
		for(CommonCodeVO commonCodeVO : models) {
			commonCodeVO.setMb_id(getUserSession().getMb_id());
			commonCodeVO.setUp_emp_cd(getUserSession().getEmp_cd());
			
			getCommoncodeDAO().updateCommonCodeGroup(commonCodeVO);
		}
	}
	
	/**
	 * @brief 공통코드그룹 삭제
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param List<CommonCodeVO>
	 * @return ModelAndView
	 */
	public void deleteCommonCodeGroup(List<CommonCodeVO> models) {
		for(CommonCodeVO commonCodeVO : models) {
			commonCodeVO.setMb_id(getUserSession().getMb_id());
			
			getCommoncodeDAO().deleteCommonCode(commonCodeVO);
			getCommoncodeDAO().deleteCommonCodeGroup(commonCodeVO);
		}
	}

	/**
	 * @brief 공통코드 입력
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param List<CommonCodeVO>
	 * @return ModelAndView
	 */
	public void insertCommonCode(List<CommonCodeVO> models) {
		for(CommonCodeVO commonCodeVO : models) {
			commonCodeVO.setMb_id(getUserSession().getMb_id());
			commonCodeVO.setIn_emp_cd(getUserSession().getEmp_cd());
			
			getCommoncodeDAO().insertCommonCode(commonCodeVO);
		}
	}
	
	/**
	 * @brief 공통코드 수정
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param List<CommonCodeVO>
	 * @return ModelAndView
	 */
	public void updateCommonCode(List<CommonCodeVO> models) {
		for(CommonCodeVO commonCodeVO : models) {
			commonCodeVO.setMb_id(getUserSession().getMb_id());
			commonCodeVO.setUp_emp_cd(getUserSession().getEmp_cd());
			
			getCommoncodeDAO().updateCommonCode(commonCodeVO);
		}
	}
	
	/**
	 * @brief 공통코드 삭제
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param List<CommonCodeVO>
	 * @return ModelAndView
	 */
	public void deleteCommonCode(List<CommonCodeVO> models) {
		for(CommonCodeVO commonCodeVO : models) {
			commonCodeVO.setMb_id(getUserSession().getMb_id());
			
			getCommoncodeDAO().deleteCommonCode(commonCodeVO);
		}
	}
	
	/**
	 * @brief 공통코드 단건 조회
	 * @author KIMDONGUK
	 * @since 2020-01-08
	 * @param CommonCodeVO
	 * @return CommonCodeVO
	 */
	public CommonCodeVO getCommonCodeView(CommonCodeVO commonCodeVO) {
		return getCommoncodeDAO().selectCommonCodeView(commonCodeVO);
	}

	/**
	 *
	 * @param vo
	 * @return
	 */
	public List<CommonCodeVO> getProductgroupKind2(CommonCodeVO commonCodeVO){
		logger.debug("== CommonCodeService getCommonCodeList");
		return getErpDAO().selectProductgroupKind2(commonCodeVO);
	}
}
