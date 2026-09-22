package kr.co.gnx.system.login;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import kr.co.gnx.base.BaseService;
import kr.co.gnx.comm.excel.excelHandler2;
import kr.co.gnx.comm.util.CommUtil;
import kr.co.gnx.comm.util.DecryptMap;
import kr.co.gnx.comm.util.crypto.CryptoUtil;

@Service("LoginService")
public class LoginService extends BaseService{
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
	

	/**
	 * @Description  : 로그인 정보 조회(사용자계정관리)
	 * @author       : lakhyun.kim
	 * @since        : 2019. 03. 05
	 * @param        : LoginVO
	 * @return       : List<Map<String, String>>
	 */
	public List<Map<String, String>> getLoginList(LoginVO loginVO){
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		resultList = getLoginDAO().selectLoginList(loginVO);
		return resultList;
	}

	/**
	 * @Description  : 로그인 정보 수정(비밀번호 초기화, 계정잠금/해제, 2factor인증/해제)
	 * @author       : lakhyun.kim
	 * @since        : 2019. 03. 05
	 * @param        : ArrayList<LoginVO>
	 * @return       : void
	 */
	public void updateLogin(ArrayList<LoginVO> models){
		for(int i=0; i < models.size(); i++) {
			LoginVO loginVO = models.get(i);
			loginVO.setMb_id(getUserSession().getMb_id());
			loginVO.setIn_emp_cd(getUserSession().getEmp_cd());
			loginVO.setUp_emp_cd(getUserSession().getEmp_cd());
			
			// 비밀번호 초기화
			if(loginVO.getPwd_intzn_type() != null && !loginVO.getPwd_intzn_type().equals("")) {
				
				// 임시비밀번호 생성 : 10자리 영어, 숫자, 특수문자를 포함한 문자(특수문자 1자리, 영어/숫자 랜덤)
				//String user_pwd = CommUtil.temporaryPassword(10);
				// 엑셀 요청사항 : 숫자 8자리 난수
				String user_pwd = "";
				SecureRandom random = new SecureRandom();
				random.setSeed(new Date().getTime());
				for (int j = 0; j < 8; j++) {
					user_pwd += random.nextInt(10);
				}
				
				try {
					loginVO.setUser_pwd(CryptoUtil.encryptlogin(user_pwd));
				} catch (Exception e) {
					logger.info(e.getMessage());
				}
				loginVO.setAcct_lock_type("N");
				loginVO.setPwd_err_nbtm("0");

				// 비밀번호 초기화 알림(SMS) 발송은 이 테스트 프로젝트 범위에서 제외 - 새 비밀번호는 DB에만 저장된다.
			}
			
			getLoginDAO().updateLogin(loginVO);
		}
	}
	
	/**
	 * @Description  : 사용자계정관리 엑셀다운로드
	 * @author       : lakhyun.kim
	 * @since        : 2019. 03. 05
	 * @param        : LoginVO
	 * @return       : excelHandler2
	 */
	public excelHandler2 getLoginListExcel(LoginVO loginVO){
		excelHandler2 eh = null;
		
		String[] tiltestemp = {"사원번호", "사원명", "재직구분", "비밀번호 초기화여부", "계정 잠김여부", "2factor인증 사용여부", "최근접속현황"};
		
		String[] fieldstemp = {"emp_cd", "emp_nm", "empsta", "pwd_intzn_type", "acct_lock_type", "login_autr_type_nm", "login_dtm"};
		ArrayList<String> titleList = new ArrayList<String>();
		ArrayList<String> fieldsList = new ArrayList<String>();
		
		Collections.addAll(titleList, tiltestemp);
		Collections.addAll(fieldsList,fieldstemp);
		
		eh = new excelHandler2(loginVO.getExcelpath(), titleList, fieldsList);
		getLoginDAO().selectLoginListExcel(loginVO, eh);

		//컬럼 사이즈 설정
		if(eh.getRowindex() != 0) {
			for(int i=0; i<tiltestemp.length; i++) {
				eh.getDataSheet().setColumnWidth(i, (eh.getDataSheet().getColumnWidth(i)) + 2048); //(int)1 : 약 0.03픽셀
			}
		}
		return eh;
	}

	/**
	 * @Description  : 로그인 이력 저장
	 * @author       : lakhyun.kim
	 * @since        : 2019. 04. 16
	 * @param        : LoginVO
	 * @return       : int
	 */
	public int insertLoginHist(LoginVO loginVO){
		int resultInt = 0;
		resultInt = getLoginDAO().insertLoginHist(loginVO);
		return resultInt;
	}

	/**
	 * @Description  : 비밀번호변경
	 * @author       : KIMDONGUK
	 * @since        : 2019. 06. 02
	 * @param        : LoginVO
	 */
	public void passwordChange(LoginVO loginVO) throws Exception {
		loginVO.setMb_id(getUserSession().getMb_id());
		loginVO.setUser_id(getUserSession().getUser_id());
		loginVO.setEmp_cd(getUserSession().getEmp_cd());
		loginVO.setAcct_lock_type("N");
		loginVO.setPwd_err_nbtm("0");
		loginVO.setPwd_chg_dt(CommUtil.getDateFormat("yyyyMMdd"));
		
		String user_pwd = CryptoUtil.encryptlogin(loginVO.getUser_pwd());
		loginVO.setUser_pwd(user_pwd);
		
		getLoginDAO().updateLogin(loginVO);
	}
	
	/**
	 * @Description  : 비밀번호 유지
	 * @author       : JANGCHAEHOON
	 * @since        : 2021. 09. 15
	 * @param        : void
	 */
	public void passwordUsi(LoginVO loginVO) throws Exception {
		loginVO.setMb_id(getUserSession().getMb_id());
		loginVO.setUser_id(getUserSession().getUser_id());
		loginVO.setEmp_cd(getUserSession().getEmp_cd());
		loginVO.setPwd_chg_dt(CommUtil.getDateFormat("yyyyMMdd"));
		
		getLoginDAO().updateLogin(loginVO);
		
	}	
	
	/**
	 * 로그인 이력 횟수 조회
	 * @param loginVO
	 * @return
	 */
	public String selectLoginHistCount(LoginVO loginVO) {
		return getLoginDAO().selectLoginHistCount(loginVO);
	}
	
	/**
	 * 로그인 이력 횟수 조회
	 * @param loginVO
	 * @return
	 */
	public void updatePwdErrNbtm(LoginVO loginVO) {
		getLoginDAO().updatePwdErrNbtm(loginVO);
	}	
	
	/**
	 * @Description  : 로그인 이력 저장
	 * @author       : lakhyun.kim
	 * @since        : 2019. 04. 16
	 * @param        : LoginVO
	 * @return       : int
	 */
	public DecryptMap getLoginInfoView(LoginVO loginVO){
		
		DecryptMap result = null;
		
		try {
			
			List<DecryptMap> list = getLoginDAO().selectLoginInfoList(loginVO);
			
			if(CommUtil.isNotEmpty(loginVO.getTelno() ) ) {
				/* 휴대폰인증 */
				for(int i=0; i<list.size(); i++) {
					
					DecryptMap login = list.get(i);
					
					if(CommUtil.isEquals(loginVO.getTelno(), login.get("telno"))) {
						result = login;
					}
					
				}
				
			} else if(CommUtil.isNotEmpty(loginVO.getEmail() ) ) {
				/* 이메일 인증 */
				for(int i=0; i<list.size(); i++) {
					
					DecryptMap login = list.get(i);
					
					if(CommUtil.isEquals(loginVO.getEmail(), login.get("email"))) {
						result = login;
					}
					
				}
				
			} else {
				
				if(list.size() != 0) {
					result = list.get(0);
				}
				
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
		
		return result;
		
	}

}
