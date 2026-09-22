package kr.co.gnx.base;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;

public class BaseDAO {
	
	@Resource(name="sqlSession")
	private SqlSessionTemplate sqlSession;
	
	@Resource(name="sqlSessionAnybiz")
	private SqlSessionTemplate sqlSessionAnybiz;
	
	//@Resource(name="sqlSessionSms")
	//private SqlSessionTemplate sqlSessionSms;
	
	private static final String springsecurityMapper = "SpringSecurity.";//springsecurity-mapper.xml
	private static final String commMapper = "Comm.";//comm-mapper.xml
	private static final String memberMapper = "Member.";//member-mapper.xml
	private static final String menuMapper = "Menu.";//menu-mapper.xml
	private static final String commcodeMapper = "CommonCode.";//commoncode-mapper.xml
	private static final String fileMapper = "File.";//file-mapper.xml
	private static final String loginMapper = "Login.";//login-mapper.xml
	private static final String userroleMapper = "UserRole.";//userrole-mapper.xml
	private static final String roleMapper = "Role.";//role-mapper.xml
	private static final String inscoMapper = "Insco.";//insco-mapper.xml
	private static final String authenticationSwaggerMapper = "AuthenticationSwagger.";//authenticationSwagger-mapper.xml
	private static final String insaMapper = "Insa.";//insa-mapper.xml
	private static final String orgMapper = "Org.";//org-mapper.xml
	private static final String uploadexcelMapper = "UploadExcel.";//uploadexcel-mapper.xml
	private static final String logsMapper = "logs.";
	private static final String productmapper = "Product.";//product-mapper.xml
	//private static final String smsMapper = "Sms."; //sms-mapper.xml
	private static final String closemonthMapper = "CloseMonth.";//closemonth-mapper.xml
	private static final String insabasisMapper = "InsaBasis.";	//insabasis-mapper.xml
	private static final String contractbasisMapper = "ContractBasis.";	//contractbasis-mapper.xml
	private static final String targetempMapper = "TargetEmp.";			//targetemp-mapper.xml
	private static final String jigubconfigMapper = "JigubConfig.";		//jigubconfig-mapper.xml
	private static final String otherfeesMapper = "OtherFees.";			//otherfees-mapper.xml
	private static final String rulemngMapper = "RuleMng.";				//rulemng-mapper.xml
	private static final String inscomconfigMapper = "InscomConfig.";	//inscomconfig-mapper.xml
	private static final String suipcommMapper = "SuipComm.";			//suipcomm-mapper.xml
	private static final String inscomcalcMapper = "InscomCalc.";		//inscomcalc-mapper.xml
	private static final String parentcompanyMapper = "ParentCompany.";		//inscomcalc-mapper.xml
	private static final String evaluationMapper = "Evaluation.";		//evaluation-mapper.xml
	private static final String contractmstMapper = "ContractMst.";		//contractmst-mapper.xml
	private static final String jigubcommconfigMapper = "JigubCommConfig.";		//jigubcommconfig-mapper.xml
	private static final String jigubcommMapper = "JigubComm.";			//jigubcomm-mapper.xml
	private static final String inscomconfirmMapper = "InscomConfirm.";	//inscomconfirm-mapper.xml
	private static final String payrollMapper = "Payroll.";				//payroll-mapper.xml
	private static final String erpMapper = "Erp.";						//erp-mapper.xml
	private static final String jigubstatisticsMapper = "JigubStatistics."; //jigubstatistics-mapper.xml
	private static final String sugistatisticsMapper = "SugiStatistics.";  //sugistatistics-mapper.xml
	private static final String jisasugistatisticsMapper = "JisaSugiStatistics."; //JisaSugiStatistics-mapper.xml
	private static final String incompleteSalesMapper = "IncompleteSales."; //incompleteSales-mapper.xml
	private static final String jigubSettleMapper = "JigubSettle."; //jigubSettle-mapper.xml
	private static final String transferMapper = "Transfer."; //Transfer-mapper.xml
	
	
	public static String getParentcompanymapper() {
		return parentcompanyMapper;
	}

	public static String getJisasugistatisticsmapper() {
		return jisasugistatisticsMapper;
	}

	public SqlSessionTemplate getSqlSession() {
		return sqlSession;
	}

	public SqlSessionTemplate getSqlSessionAnybiz() {
		return sqlSessionAnybiz;
	}
	
	/*
	public SqlSessionTemplate getSqlSessionSms() {
		return sqlSessionSms;
	}
	*/

	public static String getSpringsecuritymapper() {
		return springsecurityMapper;
	}

	public static String getCommmapper() {
		return commMapper;
	}

	public static String getMembermapper() {
		return memberMapper;
	}

	public static String getMenumapper() {
		return menuMapper;
	}

	public static String getCommcodemapper() {
		return commcodeMapper;
	}

	public static String getAuthenticationswaggermapper() {
		return authenticationSwaggerMapper;
	}

	public static String getFilemapper() {
		return fileMapper;
	}

	public static String getLoginmapper() {
		return loginMapper;
	}

	public static String getInsamapper() {
		return insaMapper;
	}

	public static String getUserrolemapper() {
		return userroleMapper;
	}

	public static String getRolemapper() {
		return roleMapper;
	}

	public static String getInscomapper() {
		return inscoMapper;
	}

	public static String getOrgmapper() {
		return orgMapper;
	}

	public static String getUploadexcelmapper() {
		return uploadexcelMapper;
	}

	public static String getLogsmapper() {
		return logsMapper;
	}

	public static String getProductmapper() {
		return productmapper;
	}
	
	/*
	public static String getSmsmapper() {
		return smsMapper;
	}
	*/
	
	public static String getClosemonthmapper() {
		return closemonthMapper;
	}

	public static String getInsabasismapper() {
		return insabasisMapper;
	}

	public static String getContractbasismapper() {
		return contractbasisMapper;
	}

	public static String getTargetempmapper() {
		return targetempMapper;
	}

	public static String getJigubconfigmapper() {
		return jigubconfigMapper;
	}

	public static String getOtherfeesmapper() {
		return otherfeesMapper;
	}

	public static String getRulemngmapper() {
		return rulemngMapper;
	}

	public static String getInscomconfigmapper() {
		return inscomconfigMapper;
	}

	public static String getSuipcommmapper() {
		return suipcommMapper;
	}

	public static String getInscomcalcmapper() {
		return inscomcalcMapper;
	}

	public static String getEvaluationmapper() {
		return evaluationMapper;
	}

	public static String getContractmstmapper() {
		return contractmstMapper;
	}

	public static String getJigubcommconfigmapper() {
		return jigubcommconfigMapper;
	}

	public static String getJigubcommmapper() {
		return jigubcommMapper;
	}

	public static String getInscomconfirmmapper() {
		return inscomconfirmMapper;
	}

	public static String getPayrollmapper() {
		return payrollMapper;
	}

	public static String getErpmapper() {
		return erpMapper;
	}

	public static String getJigubstatisticsmapper() {
		return jigubstatisticsMapper;
	}

	public static String getSugistatisticsmapper() {
		return sugistatisticsMapper;
	}

	public static String getIncompletesalesmapper() {
		return incompleteSalesMapper;
	}

	public static String getJigubsettlemapper() {
		return jigubSettleMapper;
	}
	
	public static String getTransfermapper() {
		return transferMapper;
	}
	
	
}
