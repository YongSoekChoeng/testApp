package kr.co.gnx.logs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import kr.co.gnx.base.BaseService;

@Service(value = "LogsService")
public class LogsService extends BaseService {
	private static final Logger logger = LoggerFactory.getLogger(LogsService.class);
	
	/**
	 * @desc 에러 로그 저장
	 * @author KIMDONGUK
	 * @since 2019-04-19
	 * @param ErrorLogVO
	 * @return int
	 */
	public int insertErrorLog(ErrorLogVO errorLogVO) {
		int resultInt = 0;
		
		resultInt = getLogsDAO().insertErrorLog(errorLogVO);
		
		return resultInt;
	}

	/**
	 * @desc 액션 로그 저장
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param ActionLogVO
	 * @return int
	 */
	public int insertActionLog(ActionLogVO actionLogVO) {
		int resultInt = 0;
		
		resultInt = getLogsDAO().insertActionLog(actionLogVO);
		
		return resultInt;
	}

	/**
	 * 로그인 기록 리스트 조회
	 * @author KIMDONGUK
	 * @since 2019-04-25
	 * @param LoginHistVO
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getLoginHistList(LoginHistVO loginHistVO) {
		loginHistVO.setMb_id(getUserSession().getMb_id());
		
		return getLogsDAO().selectLoginHistList(loginHistVO);
	}
	
	/**
	 * 로그인 시간 조회
	 * @author TAEYOONKIM
	 * @since 2020-03-18
	 * @param LoginHistVO
	 * @return Map<String, String>
	 */
	public HashMap<String, Object> getLoginHistDtm(LoginHistVO loginHistVO) {
		return getLogsDAO().selectLoginHistDtm(loginHistVO);
	}
	
	/**
	 * 회원가입 인증 발송 로그
	 * @author JJT
	 * @since 2020-03-30
	 * @param LoginHistVO
	 * @return List<Map<String, String>>
	 */
	public int insertJoinSendLog(LoginHistVO loginHistVO) {
		try {
			getLogsDAO().insertJoinSendLog(loginHistVO);
		} catch (Exception e) {
			logger.error(e.getMessage());	// 로그의 에러가 프로세스를 간섭하지 않게 한다
		}
		return 1;
	}	

	/**
	 * 액션 로그 리스트 조회
	 * @author KIMDONGUK
	 * @since 2019-04-25
	 * @param LoginHistVO
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getActionLogList(ActionLogVO actionLogVO) {
		actionLogVO.setMb_id(getUserSession().getMb_id());
		
		return getLogsDAO().selectActionLogList(actionLogVO);
	}

	/**
	 * 에러 로그 리스트 조회
	 * @author KIMDONGUK
	 * @since 2019-04-25
	 * @param LoginHistVO
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getErrorLogList(ErrorLogVO errorLogVO) {
		errorLogVO.setMb_id(getUserSession().getMb_id());
		
		return getLogsDAO().selectErrorLogList(errorLogVO);
	}
	
}
