package kr.co.gnx.erp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.gnx.system.commoncode.CommonCodeVO;
import org.springframework.stereotype.Repository;

import kr.co.gnx.base.BaseDAO;

@Repository(value="ErpDAO")
public class ErpDAO extends BaseDAO {
	/****************************************************************************** ANYBIZ 데이터 가져오는 부분 ******************************************************************************/
	/** ERP 데이터 가져오는 부분 **/
	//공통코드 그룹
	public List<Map<String, String>> selectErpMemberList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpMemberList", erpVO);
	}
	
	//공통코드
	public List<Map<String, String>> selectErpCommonCodeGroupList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpCommonCodeGroupList", erpVO);
	}
	
	public List<Map<String, String>> selectErpInscoList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpInscoList", erpVO);
	}
	
	public List<Map<String, String>> selectErpCommonCodeList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpCommonCodeList", erpVO);
	}
	
	public List<Map<String, String>> selectErpOrgList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpOrgList", erpVO);
	}
	
	public List<Map<String, String>> selectErpMonthOrgList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpMonthOrgList", erpVO);
	}
	
	public List<Map<String, String>> selectErpEmpList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpEmpList", erpVO);
	}
	
	public List<Map<String, String>> selectErpMonthEmpList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpMonthEmpList", erpVO);
	}
	
	public List<Map<String, String>> selectErpLoginList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpLoginList", erpVO);
	}
	
	public List<Map<String, String>> selectErpEmpcodeList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpEmpcodeList", erpVO);
	}
	
	public List<Map<String, String>> selectErpEmpGuaranteeList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpEmpGuaranteeList", erpVO); 
	}
	
	public List<Map<String, String>> selectErpContractList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpContractList", erpVO);
	}
	
	public List<Map<String, String>> selectErpContractShareList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpContractShareList", erpVO);
	}
	
	//월마감 계약 쉐어리스트
	public List<Map<String, String>> selectErpMonthContractShareList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpMonthContractShareList", erpVO);
	}

	//인별 유지율
	public List<Map<String, String>> selectErpEmpUsiList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpEmpUsiList", erpVO);
	}

	//조직별 유지율
	public List<Map<String, String>> selectErpOrgUsiList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpOrgUsiList", erpVO);
	}
	
	//인별수금율 리스트
	public List<Map<String, String>> selectErpEmpSugumList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpEmpSugumList", erpVO);
	}

	//조직별 수금율 리스트
	public List<Map<String, String>> selectErpOrgSugumList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpOrgSugumList", erpVO);
	}
	
	public List<Map<String, String>>  selectErpOrgSettleList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpOrgSettleList", erpVO);
	}
	
	public List<Map<String, String>>  selectErpOrgSettleGSList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpOrgSettleGSList", erpVO);
	}
	
	public List<Map<String, String>>  selectErpIncompletesalesList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpIncompletesalesList", erpVO);
	}
	
	//수금이관 이력 데이터 조회
	public List<Map<String, String>> selectErpContractShareHistList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpContractShareHistList", erpVO);
	}
	
	
	/**
	 * 회사정보 단건 조회
	 * @since 2020-11-22
	 * @author KIMDONGUK
	 * @param ErpVO
	 * @return Map
	 */
	public Map<String, String> selectErpMemberView(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectOne(getErpmapper() + "selectErpMemberView", erpVO);
	}

	/**
	 * ERP 권한목록 조회
	 * @author KIMDONGUK
	 * @since 2020-11-22
	 * @param ErpVO
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> selectErpRoleList(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpRoleList", erpVO);
	}
	
	/**
	 * @Description  : ERP 권한체크 : 해당 권한이 있는지 조회
	 * @author       : KIMDONGUK
	 * @since        : 2020. 11. 22
	 * @param        : Map<String, String>
	 * @return       : Map<String, String>
	 */
	public Map<String, String> selectRolesHierarchyView(Map<String, String> map){
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap = getSqlSessionAnybiz().selectOne(getErpmapper() + "selectErpRolesHierarchyView", map);
		return resultMap;
	}
	
	/**
	 * @Description  : ERP 사용자 권한 메핑 겸직여부 조회
	 * @author       : KIMDONGUK
	 * @since        : 2020. 11. 24
	 * @param        : UserRoleVO
	 * @return       : int
	 */
	public int selectErpUserRoleListCount(ErpVO erpVO) {
		return getSqlSessionAnybiz().selectOne(getErpmapper() + "selectErpUserRoleListCount", erpVO);
	}

	/**
	 * ERP 인사목록 조회(권한사원 변경용)
	 * @author KIMDONGUK
	 * @since 2020-11-24
	 * @param ErpVO
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> selectEmpSchList(ErpVO erpVO2) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectEmpSchList", erpVO2);
	}
	
	/**
	 * ERP 인사목록 조회(권한사원 변경용)
	 * @author KIMDONGUK
	 * @since 2020-11-24
	 * @param ErpVO
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> selectErpEmpEduTrainingMonthList(ErpVO erpVO2) {
		return getSqlSessionAnybiz().selectList(getErpmapper() + "selectErpEmpEduTrainingMonthList", erpVO2);
	}

	/**
	 * @Description  : 상품구분2 조회 (dropDownList)
	 * @author       : lakhyun.kim
	 * @since        : 2019. 03. 11
	 * @return       : List<ProductGroupVO>
	 */
	public List<CommonCodeVO> selectProductgroupKind2(CommonCodeVO commonCodeVO){
		List<CommonCodeVO> reulstList = new ArrayList<>();
		reulstList = getSqlSessionAnybiz().selectList(getErpmapper() + "selectProductgroupKind2", commonCodeVO);
		return reulstList;
	}
}
