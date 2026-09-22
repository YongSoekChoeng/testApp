package kr.co.gnx.system.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.co.gnx.base.BaseDAO;
import kr.co.gnx.comm.excel.SampleExcelHandler;
import kr.co.gnx.comm.excel.excelHandler2;
import kr.co.gnx.comm.util.CommUtil;

@Repository(value="MemberDAO")
public class MemberDAO extends BaseDAO{

	/**
	 * @Description  : 회원사관리 조회
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 02
	 * @param        : MemberVO
	 * @return       : List<Map<String, String>>
	 */
	public List<Map<String, String>> selectMemberList(MemberVO memberVO){
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		
		
		if(CommUtil.isNotEmpty(memberVO.getPage())) {
			Integer TOTAL = getSqlSession().selectOne(getMembermapper() + "selectMemberListCount", memberVO);
			memberVO.setTotal(TOTAL);
			memberVO.setPageoffset(CommUtil.getPageOffset(memberVO.getPage(), memberVO.getPageSize()));
			
			resultList = getSqlSession().selectList(getMembermapper() + "selectMemberListPaging", memberVO);
		}else {
			resultList = getSqlSession().selectList(getMembermapper() + "selectMemberList", memberVO);
		}
		
		return resultList;
	}

	/**
	 * @Description  : 회원사관리 엑셀다운로드 조회
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 02
	 * @param        : MemberVO
	 * @return       : void
	 */
	public void selectMemberListExcel(MemberVO memberVO, excelHandler2 eh){
		getSqlSession().select(getMembermapper() + "selectMemberList", memberVO,eh);
	}
	
	/**
	 * @Description  : 회원사관리 샘플 다운로드용 데이터 조회
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 02
	 * @param        : MemberVO
	 * @return       : void
	 */
	public void selectMemberListExcelSample(MemberVO memberVO, SampleExcelHandler sampleHandler){
		getSqlSession().select(getMembermapper() + "selectMemberList", memberVO, sampleHandler);
	}
	
	/**
	 * @Description  : 회원사관리 URL 상세 조회
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 13
	 * @param        : MemberVO
	 * @return       : Map<String, String>
	 */
	public Map<String, String> selectMemberUrlView(MemberVO memberVO){
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap = getSqlSession().selectOne(getMembermapper() + "selectMemberUrlView", memberVO);
		return resultMap;
	}
	
	
	/**
	 * @Description  : 사업자 번호 중복 체크
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 13
	 * @param        : MemberVO
	 * @return       : int
	 */
	public int selectMemberTaxCheck(MemberVO memberVO) {
		int resultInt = 0;
		resultInt = getSqlSession().selectOne(getMembermapper() + "selectMemberTaxCheck", memberVO);
		return resultInt;
	}
	
	/**
	 * @Description  : 회원사 중복 조회
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 02
	 * @param        : MemberVO
	 * @return       : int
	 */
	public int selectMemberCount(MemberVO memberVO){
		int resultInt = 0;
		resultInt = getSqlSession().selectOne(getMembermapper() + "selectMemberCount", memberVO);
		return resultInt;
	}
	
	/**
	 * @Description  : 회원사관리 등록
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 02
	 * @param        : MemberVO
	 * @return       : int
	 */
	public int insertMember(MemberVO memberVO){
		int resultInt = 0;
		resultInt = getSqlSession().insert(getMembermapper() + "insertMember", memberVO);
		return resultInt;
	}

	/**
	 * @Description  : 회원사관리 수정
	 * @author       : lakhyun.kim
	 * @since        : 2019. 05. 02
	 * @param        : MemberVO
	 * @return       : int
	 */
	public int updateMember(MemberVO memberVO){
		int resultInt = 0;
		resultInt = getSqlSession().update(getMembermapper() + "updateMember", memberVO);
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
		resultInt = getSqlSession().delete(getMembermapper() + "deleteMember", memberVO);
		return resultInt;
	}
	
	/**
	 * @Description  : 회원사관리 단건 조회
	 * @author       : KIMDONGUK
	 * @since        : 2019. 05. 13
	 * @param        : MemberVO
	 * @return       : Map<String, String>
	 */
	public Map<String, String> selectMemberView(MemberVO memberVO) {
		return getSqlSession().selectOne(getMembermapper() + "selectMemberView", memberVO);
	}

	/**
	 * @Description  : 회원사관리 단건 조회(API 로그인 전용)
	 * @author       : KIMDONGUK
	 * @since        : 2019. 05. 13
	 * @param        : MemberVO
	 * @return       : Map<String, String>
	 */
	public Map<String, String> selectMemberApiView(MemberVO memberVO) {
		return getSqlSession().selectOne(getMembermapper() + "selectMemberApiView", memberVO);
	}
}
