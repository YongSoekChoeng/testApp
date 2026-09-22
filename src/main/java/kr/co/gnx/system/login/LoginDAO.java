package kr.co.gnx.system.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import kr.co.gnx.base.BaseDAO;
import kr.co.gnx.comm.excel.excelHandler2;
import kr.co.gnx.comm.util.DecryptMap;

@Repository(value = "LoginDAO")
public class LoginDAO extends BaseDAO{
	private static final Logger logger = LoggerFactory.getLogger(LoginDAO.class);

	/**
	 * @Description  : 로그인 정보 조회(사용자계정관리)
	 * @author       : lakhyun.kim
	 * @since        : 2018. 06. 04
	 * @param        : LoginVO
	 * @return       : List<Map<String, String>>
	 */
	public List<Map<String, String>> selectLoginList(LoginVO loginVO){
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		resultList = getSqlSession().selectList(getLoginmapper() + "selectLoginList", loginVO);
		return resultList;
	}

	/**
	 * @Description  : 로그인 정보 저장
	 * @author       : lakhyun.kim
	 * @since        : 2018. 06. 04
	 * @return       : void
	 */
	public void insertLogin(LoginVO loginVO){
		getSqlSession().insert(getLoginmapper() + "insertLogin", loginVO);
	}

	/**
	 * @Description  : 로그인 정보 수정
	 * @author       : lakhyun.kim
	 * @since        : 2018. 06. 04
	 * @return       : void
	 */
	public void updateLogin(LoginVO loginVO){
		getSqlSession().update(getLoginmapper() + "updateLogin", loginVO);
	}
	
	/**
	 * @Description  : 로그인 정보 삭제
	 * @author       : lakhyun.kim
	 * @since        : 2018. 06. 04
	 * @return       : void
	 */
	public void deleteLogin(LoginVO loginVO){
		getSqlSession().delete(getLoginmapper() + "deleteLogin", loginVO);
	}

	/**
	 * @Description  : 사용자계정관리 엑셀다운로드
	 * @author       : lakhyun.kim
	 * @since        : 2019. 03. 05
	 * @param        : LoginVO
	 * @return       : excelHandler2
	 */
	public void selectLoginListExcel(LoginVO loginVO, excelHandler2 eh){
		getSqlSession().select(getLoginmapper() + "selectLoginList", loginVO, eh);
	}

	/**
	 * @Description  : 로그인 이력 저장
	 * @author       : lakhyun.kim
	 * @since        : 2019. 04. 16
	 * @param        : LoginVO
	 * @return       : int
	 */
	public int insertLoginHist(LoginVO loginVO) {
		int resultInt = 0;
		resultInt = getSqlSession().insert(getLoginmapper() + "insertLoginHist", loginVO);
		return resultInt;
	}
	
	/**
	 * 로그인 정보 입력 전 삭제
	 * @param loginVO
	 * @return
	 */
	public void deleteLoginInfo(LoginVO loginVO) {
		getSqlSession().delete(getLoginmapper() + "deleteLoginInfo", loginVO);
	}
	
	/**
	 * 로그인 정보(사번) 조회
	 * @param loginVO
	 * @return
	 */
	public String getEmpcd(LoginVO loginVO) {
		return getSqlSession().selectOne(getLoginmapper() + "getEmpcd", loginVO);
	}
	
	/**
	 * 로그인 정보 조회
	 * @param loginVO
	 * @return
	 */
	public List<DecryptMap> selectLoginInfoList(LoginVO loginVO) {
		return getSqlSession().selectList(getLoginmapper() + "selectLoginInfoList", loginVO);
	}

	/**
	 * 로그인 이력 횟수 조회
	 * @param loginVO
	 * @return
	 */
	public String selectLoginHistCount(LoginVO loginVO) {
		return getSqlSession().selectOne(getLoginmapper() + "selectLoginHistCount", loginVO);
	}
	
	/**
	 * @Description  : 비밀번호 초기화 대상 조회
	 * @author       : JANGCHAEHOON
	 * @since        : 2021. 03. 05
	 * @param        : void
	 */
	public List<Map<String, String>> selectClearPassList(){
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		resultList = getSqlSession().selectList(getLoginmapper() + "selectClearPassList");
		return resultList;
	}
	
	
	/**
	 * @Description  : 로그인 정보 수정
	 * @author       : lakhyun.kim
	 * @since        : 2018. 06. 04
	 * @return       : void
	 */
	public void updatePwdErrNbtm(LoginVO loginVO){
		getSqlSession().update(getLoginmapper() + "updatePwdErrNbtm", loginVO);
	}	
}
