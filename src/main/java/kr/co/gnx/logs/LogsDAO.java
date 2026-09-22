package kr.co.gnx.logs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import kr.co.gnx.base.BaseDAO;

@Repository(value = "LogsDAO")
public class LogsDAO extends BaseDAO{
	private static final Logger logger = LoggerFactory.getLogger(LogsDAO.class);

	/**
	 * @desc 에러 로그 저장
	 * @author KIMDONGUK
	 * @since 2019-04-19
	 * @param ErrorLogVO
	 * @return int
	 */
	public int insertErrorLog(ErrorLogVO errorLogVO) {
		logger.debug("LogsDAO.insertAccessLog");
		return getSqlSession().insert(getLogsmapper() + "insertErrorLog", errorLogVO);
	}

	/**
	 * @desc 액션 로그 저장
	 * @author KIMDONGUK
	 * @since 2019-04-22
	 * @param ActionLogVO
	 * @return int
	 */
	public int insertActionLog(ActionLogVO actionLogVO) {
		return getSqlSession().insert(getLogsmapper() + "insertActionLog", actionLogVO);
	}

	/**
	 * 로그인 기록 리스트 조회
	 * @author KIMDONGUK
	 * @since 2019-04-25
	 * @param LoginHistVO
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> selectLoginHistList(LoginHistVO loginHistVO) {
		return getSqlSession().selectList(getLogsmapper() + "selectLoginHistList", loginHistVO);
	}
	
	/**
	 * 로그인 기록 조회
	 * @author TAEYOONKIM
	 * @since 2020-03-18
	 * @param LoginHistVO
	 * @return List<Map<String, String>>
	 */
	public HashMap<String, Object> selectLoginHistDtm(LoginHistVO loginHistVO) {
		return getSqlSession().selectOne(getLogsmapper() + "selectLoginHistDtm", loginHistVO);
	}
	
	/**
	 * 회원가입 인증 발송 로그
	 * @author JJT
	 * @since 2020-03-30
	 * @param LoginHistVO
	 * @return List<Map<String, String>>
	 */
	public int insertJoinSendLog(LoginHistVO loginHistVO) throws Exception{
		return getSqlSession().insert(getLogsmapper() + "insertJoinSendLog", loginHistVO);
	}	
	

	/**
	 * 액션 로그 리스트 조회
	 * @author KIMDONGUK
	 * @since 2019-04-25
	 * @param LoginHistVO
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> selectActionLogList(ActionLogVO actionLogVO) {
		return getSqlSession().selectList(getLogsmapper() + "selectActionLogList", actionLogVO);
	}
	
	/**
	 * 에러 로그 리스트 조회
	 * @author KIMDONGUK
	 * @since 2019-04-25
	 * @param LoginHistVO
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> selectErrorLogList(ErrorLogVO errorLogVO) {
		return getSqlSession().selectList(getLogsmapper() + "selectErrorLogList", errorLogVO);
	}
}
