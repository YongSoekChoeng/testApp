package kr.co.gnx.comm;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import kr.co.gnx.base.BaseDAO;
import kr.co.gnx.system.login.LoginVO;

@Repository(value="CommDAO")
public class CommDAO extends BaseDAO{
	private static final Logger logger = LoggerFactory.getLogger(CommDAO.class);

	/**
	 * @Description  : SMS 인증번호 체크
	 * @author       : lakhyun.kim
	 * @since        : 2019. 04. 19
	 * @param        : LoginVO
	 * @return       : Map<String, String>
	 */
	public Map<String, String> selectAuthNumCheck(LoginVO loginVO) {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap = getSqlSessionAnybiz().selectOne(getCommmapper() + "selectAuthNumCheck", loginVO);
		return resultMap;
	}

	/**
	 * @Description  : SMS 인증번호 저장
	 * @author       : lakhyun.kim
	 * @since        : 2019. 04. 19
	 * @return       : ModelAndView
	 */
	public int insertAuthNum(LoginVO loginVO){
		int resultInt = 0;
		resultInt = getSqlSessionAnybiz().insert(getCommmapper() + "insertAuthNum", loginVO);
		return resultInt;
	}

	/**
	 * @Description  : SMS 인증번호 삭제
	 * @author       : lakhyun.kim
	 * @since        : 2019. 04. 19
	 * @return       : ModelAndView
	 */
	public int deleteAuthNum(LoginVO loginVO){
		int resultInt = 0;
		resultInt = getSqlSessionAnybiz().delete(getCommmapper() + "deleteAuthNum", loginVO);
		return resultInt;
	}

	// CMS -> 정보계(Relay) DB 이관 배치 기능은 이 테스트 프로젝트 범위에서 제외되었다(원본: cms-mapper.xml/relay-mapper.xml, comm.scheduler.CloseDayScheduler).

}
