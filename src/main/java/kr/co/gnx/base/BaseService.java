package kr.co.gnx.base;

import org.springframework.beans.factory.annotation.Autowired;

import kr.co.gnx.comm.CommDAO;
import kr.co.gnx.config.Constants;
import kr.co.gnx.erp.ErpDAO;
import kr.co.gnx.erp.ErpService;
import kr.co.gnx.logs.LogsDAO;
import kr.co.gnx.security.UserDAO;
import kr.co.gnx.security.model.User;
import kr.co.gnx.system.SystemDAO;
import kr.co.gnx.system.commoncode.CommonCodeDAO;
import kr.co.gnx.system.file.FileDAO;
import kr.co.gnx.system.file.FileService;
import kr.co.gnx.system.login.LoginDAO;
import kr.co.gnx.system.member.MemberDAO;

public class BaseService {

	private User UserSession;

	private Constants constants;

	@Autowired
	private CommDAO commDao;

	@Autowired
	private LogsDAO logsDAO;

	@Autowired
	private SystemDAO systemDao;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private CommonCodeDAO commoncodeDAO;

	@Autowired
	private MemberDAO memberDAO;

	@Autowired
	private FileDAO fileDAO;

	@Autowired
	private LoginDAO loginDAO;

	@Autowired
	private FileService fileService;

	@Autowired
	private ErpDAO erpDAO;

	@Autowired
	private ErpService erpService;

	public CommDAO getCommDao() {
		return commDao;
	}

	public void setCommDao(CommDAO commDao) {
		this.commDao = commDao;
	}

	public LogsDAO getLogsDAO() {
		return logsDAO;
	}

	public void setLogsDAO(LogsDAO logsDAO) {
		this.logsDAO = logsDAO;
	}

	public SystemDAO getSystemDao() {
		return systemDao;
	}

	public void setSystemDao(SystemDAO systemDao) {
		this.systemDao = systemDao;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public CommonCodeDAO getCommoncodeDAO() {
		return commoncodeDAO;
	}

	public void setCommoncodeDAO(CommonCodeDAO commoncodeDAO) {
		this.commoncodeDAO = commoncodeDAO;
	}

	public User getUserSession() {
		return UserSession;
	}

	public void setUserSession(User userSession) {
		UserSession = userSession;
	}

	public Constants getConstants() {
		return constants;
	}

	public void setConstants(Constants constants) {
		this.constants = constants;
	}

	public MemberDAO getMemberDAO() {
		return memberDAO;
	}

	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	public FileDAO getFileDAO() {
		return fileDAO;
	}

	public void setFileDAO(FileDAO fileDAO) {
		this.fileDAO = fileDAO;
	}

	public LoginDAO getLoginDAO() {
		return loginDAO;
	}

	public void setLoginDAO(LoginDAO loginDAO) {
		this.loginDAO = loginDAO;
	}

	public FileService getFileService() {
		return fileService;
	}

	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	public ErpDAO getErpDAO() {
		return erpDAO;
	}

	public void setErpDAO(ErpDAO erpDAO) {
		this.erpDAO = erpDAO;
	}

	public ErpService getErpService() {
		return erpService;
	}

	public void setErpService(ErpService erpService) {
		this.erpService = erpService;
	}

}
