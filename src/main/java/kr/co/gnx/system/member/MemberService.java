package kr.co.gnx.system.member;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.co.gnx.base.BaseService;
import kr.co.gnx.comm.excel.excelHandler2;

@Service
public class MemberService extends BaseService{

	/**
	 * @Description  : 회원사관리 조회
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 02
	 * @param        : MemberVO
	 * @return       : List<Map<String, String>>
	 */
	public List<Map<String, String>> getMemberList(MemberVO memberVO){
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		resultList = getMemberDAO().selectMemberList(memberVO);
		return resultList;
	}
	
	/**
	 * @Description  : 인사 정보 엑셀다운로드
	 * @author       : lakhyun.kim
	 * @since        : 2018. 06. 04
	 * @return       : excelHandler2
	 */
	public excelHandler2 getMemberListExcel(MemberVO memberVO){
		excelHandler2 eh = null;
		ArrayList<String> titleList = new ArrayList<String>(Arrays.asList("고객사코드","회사명","사업자번호","상품1","상품2","상품3","상품4","상품5","사용시작일","사용종료일","사용유무"));
		
		ArrayList<String> fieldsList = new ArrayList<String>(Arrays.asList("mb_login_id","mb_nm","tax_reg_num","prod_nm1","prod_nm2","prod_nm3","prod_nm4","prod_nm5","cont_frymd","cont_toymd","use_yn_nm"));

		
		eh = new excelHandler2(memberVO.getExcelpath() ,titleList, fieldsList);
		
		getMemberDAO().selectMemberListExcel(memberVO,eh);

		//컬럼 사이즈 설정
		if(eh.getRowindex() != 0) {
			for(int i=0; i<titleList.size(); i++) {
				eh.getDataSheet().setColumnWidth(i, (eh.getDataSheet().getColumnWidth(i)) + 2048); //(int)1 : 약 0.03픽셀
			}
		}

		return eh;
	}
	
	/**
	 * @Description  : 회원사관리 URL 상세 조회
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 13
	 * @param        : MemberVO
	 * @return       : Map<String, String>
	 */
	public Map<String, String> getMemberUrlView(MemberVO memberVO){
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap = getMemberDAO().selectMemberUrlView(memberVO);
		return resultMap;
	}
	
	/**
	 * @Description  : 사업자 번호 중복 체크
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 13
	 * @param        : MemberVO
	 * @return       : int
	 */
	public int getMemberTaxCheck(MemberVO memberVO) {
		int resultInt = 0;
		resultInt = getMemberDAO().selectMemberTaxCheck(memberVO);;
		return resultInt;
	}
	
	/**
	 * @Description  : 회원사관리 등록/수정
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 02
	 * @param        : MemberVO
	 * @return       : int
	 */
	public int insertMember(MemberVO memberVO) {
		int resultInt = 0;
		/*세션정보 Start*/
		memberVO.setIn_emp_cd(getUserSession().getEmp_cd());
		memberVO.setUp_emp_cd(getUserSession().getEmp_cd());
		/*세션정보 End*/

		resultInt = getMemberDAO().selectMemberCount(memberVO);
		
		
		if(resultInt > 0){
			getMemberDAO().updateMember(memberVO);
		} else {
			getMemberDAO().insertMember(memberVO);
			// 새 회원사 입력 시 기본 로그인/권한 등을 만들어주던 Oracle 프로시저(prc_newmember) 호출은
			// 이 테스트 프로젝트 범위에서 제외되었다 - 필요한 계정은 테스트 시드 데이터로 직접 넣는다.
		}
		return resultInt;
	}

	/**
	 * @Description  : 회원사관리 삭제
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 02
	 * @param        : MemberVO
	 * @return       : int
	 */
	public int deleteMember(MemberVO memberVO){
		int resultInt = 0;
		// 회원사 삭제 프로시저 동작 -> 사용자가 실수로 제거할수 있어서 주석 처리함
		//getMemberDAO().calldeletemember(memberVO);
		resultInt = getMemberDAO().deleteMember(memberVO);
		return resultInt;
	}
	
	/**
	 * @Description  : 회원사관리 단건 조회
	 * @author       : KIMDONGUK
	 * @since        : 2019. 05. 13
	 * @param        : MemberVO
	 * @return       : Map<String, String>
	 */
	public Map<String, String> getMemberView(MemberVO memberVO) {
		return getMemberDAO().selectMemberView(memberVO);
	}
}
