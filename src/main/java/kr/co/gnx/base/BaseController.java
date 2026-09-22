package kr.co.gnx.base;

import org.springframework.beans.factory.annotation.Autowired;

import kr.co.gnx.comm.CommService;
import kr.co.gnx.erp.ErpService;
import kr.co.gnx.logs.LogsService;
import kr.co.gnx.security.UserService;
import kr.co.gnx.system.SystemService;
import kr.co.gnx.system.commoncode.CommonCodeService;
import kr.co.gnx.system.file.FileDAO;
import kr.co.gnx.system.file.FileService;
import kr.co.gnx.system.login.LoginService;
import kr.co.gnx.system.member.MemberService;

public class BaseController {

	@Autowired
	private CommService commService;

	@Autowired
	private LogsService logsService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private SystemService systemService;

	@Autowired
	private UserService userService;

	@Autowired
	private CommonCodeService commonCodeService;

	@Autowired
	private FileService fileService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private FileDAO fileDAO;

	@Autowired
	private ErpService erpService;

	public CommService getCommService() {
		return commService;
	}

	public void setCommService(CommService commService) {
		this.commService = commService;
	}

	public LogsService getLogsService() {
		return logsService;
	}

	public void setLogsService(LogsService logsService) {
		this.logsService = logsService;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public CommonCodeService getCommonCodeService() {
		return commonCodeService;
	}

	public void setCommonCodeService(CommonCodeService commonCodeService) {
		this.commonCodeService = commonCodeService;
	}

	public FileService getFileService() {
		return fileService;
	}

	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public FileDAO getFileDAO() {
		return fileDAO;
	}

	public void setFileDAO(FileDAO fileDAO) {
		this.fileDAO = fileDAO;
	}

	public ErpService getErpService() {
		return erpService;
	}

	public void setErpService(ErpService erpService) {
		this.erpService = erpService;
	}

}
