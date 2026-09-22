package kr.co.gnx.erp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.co.gnx.base.BaseService;
import kr.co.gnx.security.model.User;

@Service(value="ErpService")
public class ErpService extends BaseService {
	
	/**
	 * ERP 권한목록 조회
	 * @author KIMDONGUK
	 * @since 2020-11-22
	 * @param ErpVO
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getErpRoleList(ErpVO erpVO) {
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		
		resultList = getErpDAO().selectErpRoleList(erpVO);
		
		return resultList;
	}
	
	/**
	 * @Description  : 권한체크 : 해당 권한이 있는지 조회
	 * @author       : lakhyun.kim
	 * @since        : 2019. 04. 01
	 * @param        : String
	 * @return       : Map<String, String>
	 */
	public Map<String, String> selectRolesHierarchyView(String str, User user) throws Exception {
		// 권한체크 : 하위권한이 상위권한 변경할 수 없음
		Map<String, String> map = new HashMap<String, String>();
		map.put("mb_id", user.getMb_id());
		map.put("role_id", user.getRole_id());
		map.put("check_role_id", str);
		
		return getErpDAO().selectRolesHierarchyView(map);
	}
	
	/**
	 * @Description  : ERP 사용자 권한 메핑 겸직여부 조회
	 * @author       : KIMDONGUK
	 * @since        : 2020. 11. 24
	 * @param        : UserRoleVO
	 * @return       : int
	 */
	public int getErpUserRoleListCount(ErpVO erpVO){
		int resultInt = 0;
		
		resultInt = getErpDAO().selectErpUserRoleListCount(erpVO);
		
		return resultInt;
	}

	/**
	 * ERP 인사목록 조회(권한사원 변경용)
	 * @author KIMDONGUK
	 * @since 2020-11-24
	 * @param ErpVO
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getEmpSchList(ErpVO erpVO2) {
		return getErpDAO().selectEmpSchList(erpVO2);
	}
}