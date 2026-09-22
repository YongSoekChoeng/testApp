package kr.co.gnx.config;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 시스템 전역 상수
 * @author genexon
 *
 */
public class Constants {

	public enum UPLOADS{ROOT, UPLOAD_ROOT, COMMON_EXCEL, TEMPLATE_EXCEL, IMAGE, TEMP,BILL_SAMPLE, UPLOAD_EXCEL_SAMPLE, COMMON, UPIMAGE, CLAIM_RECEIPT, CLAIM_DOCUMENT, UBI_FILE};
	
	public enum LOGGING{LOGGING, NOLOGGING};
	
	public static LOGGING QueryLogging = LOGGING.LOGGING;
	
	private static  String WEB_ROOT_PATH = "";

	private static  String UPLOAD_ROOT_PATH = "";
	
	/** COMMON_EXCEL_FILE_PATH */
	private static String COMMON_EXCEL_FILE_PATH = "";
	
	/** TEMPLATE_EXCEL_FILE_PATH */
	private static  String TEMPLATE_EXCEL_FILE_PATH = "";
    
    /** IMAGE_MANAGE_FILE_PATH */
	private static  String IMAGE_FILE_PATH = "";
	
	/** UPIMAGE_MANAGE_FILE_PATH */
	private static  String UPIMAGE_FILE_PATH = "";

    /** TEMP_FILE_PATH */
	private static  String TEMP_FILE_PATH = "";
	
    /** IMAGE_MANAGE_FILE_PATH */
	private static  String BILL_SAMPLE_PATH = "";
	
	/** UPLOAD_EXCEL_SAMPLE_PATH **/
	private static  String UPLOAD_EXCEL_SAMPLE_PATH = "";
	
    /** TEMP_RECEIPT_FILE_PATH */
	private static  String COMMON_FILE_PATH = "";
	
	/** CLAIM_RECEIPT_FILE_PATH */
	private static  String CLAIM_RECEIPT_FILE_PATH = "";
	
	/** CLAIM_DOCUMENT_FILE_PATH */
	private static  String CLAIM_DOCUMENT_FILE_PATH = "";
	
	/** UBI_FILE - UBI 생성 파일  **/
	private static String UBI_FILE_PATH = "";
	
    
	private static final String UPLOAD_ROOT_URL = "/Upload/";
	private static final String DOWNLOAD_ROOT_URL = "/Download/";
	
	private static final String COMMON_EXCEL_FILE_URL = UPLOAD_ROOT_URL + "CommonUploadFiles/";
	private static final String TEMPLATE_EXCEL_FILE_URL = DOWNLOAD_ROOT_URL + "ExcelDownloadFiles/";
	
	private static final String IMAGE_FILE_URL =  "/images/";
	private static final String UPIMAGE_FILE_URL = UPLOAD_ROOT_URL + "UpImages/";
	private static final String TEMP_FILE_URL = UPLOAD_ROOT_URL + "TempFiles/";
	
	private static final String UPLOAD_EXCEL_SAMPLE_URL = UPLOAD_ROOT_URL + "uploadExcelSample/";
	
	private static final String BILL_SAMPLE_URL = UPLOAD_ROOT_URL + "uploadBillSample/";
	
	private static final String COMMON_FILE_URL = UPLOAD_ROOT_URL + "CommonUploadFiles/";
	
	private static final String CLAIM_RECEIPT_FILE_URL = UPLOAD_ROOT_URL + "ClaimReceiptFiles/";
	private static final String CLAIM_DOCUMENT_FILE_URL = UPLOAD_ROOT_URL + "ClaimDocumentFiles/";
	private static final String UBI_FILE_URL = UPLOAD_ROOT_URL + "ubiFile/";
	
    public static void setConstants(String WebRootPath)
    {
    	Logger logger = LoggerFactory.getLogger(Constants.class);
    	logger.debug("-----Set Constants...-----");
    	
    	Constants.WEB_ROOT_PATH = WebRootPath;
    	
    	Constants.UPLOAD_ROOT_PATH = WebRootPath+Constants.UPLOAD_ROOT_URL;
    	
    	/** COMMON_EXCEL_FILE_PATH */
    	Constants.COMMON_EXCEL_FILE_PATH = Constants.WEB_ROOT_PATH + Constants.COMMON_EXCEL_FILE_URL;
    	
    	/** TEMPLATE_EXCEL_FILE_PATH */
    	Constants.TEMPLATE_EXCEL_FILE_PATH = Constants.WEB_ROOT_PATH + Constants.TEMPLATE_EXCEL_FILE_URL;
        
    	/** IMAGE_FILE_PATH */
    	Constants.IMAGE_FILE_PATH = Constants.WEB_ROOT_PATH + Constants.IMAGE_FILE_URL;
    	
    	/** UPIMAGE_FILE_PATH */
    	Constants.UPIMAGE_FILE_PATH = Constants.WEB_ROOT_PATH + Constants.UPIMAGE_FILE_URL;
    	
    	/** TEMP_FILE_PATH */
    	Constants.TEMP_FILE_PATH = Constants.WEB_ROOT_PATH + Constants.TEMP_FILE_URL;

    	/** COMMON_FILE_PATH */
    	Constants.COMMON_FILE_PATH = Constants.WEB_ROOT_PATH + Constants.COMMON_FILE_URL;
    	
    	/** BILL_SAMPLE_PATH */
    	Constants.BILL_SAMPLE_PATH = Constants.WEB_ROOT_PATH + Constants.BILL_SAMPLE_URL;
    	
    	/** UPLOAD_MST_SAMPLE_PATH */
    	Constants.UPLOAD_EXCEL_SAMPLE_PATH = Constants.WEB_ROOT_PATH + Constants.UPLOAD_EXCEL_SAMPLE_URL;
    	
    	/** CLAIM_DOCUMENT_FILE_PATH */
    	Constants.CLAIM_RECEIPT_FILE_PATH = Constants.WEB_ROOT_PATH + Constants.CLAIM_RECEIPT_FILE_URL;
    	
    	/** CLAIM_DOCUMENT_FILE_PATH */
    	Constants.CLAIM_DOCUMENT_FILE_PATH = Constants.WEB_ROOT_PATH + Constants.CLAIM_DOCUMENT_FILE_URL;
    	
    	/** UBI_FILE_PATH */
    	Constants.UBI_FILE_PATH = Constants.WEB_ROOT_PATH + Constants.UBI_FILE_URL;
    	
    	mkdir(Constants.UPLOAD_ROOT_PATH);
    	logger.debug("-Constants.UPLOAD_ROOT_PATH={}-",Constants.UPLOAD_ROOT_PATH);
    	
    	mkdir(Constants.COMMON_EXCEL_FILE_PATH);
    	logger.debug("-Constants.COMMON_EXCEL_FILE_PATH={}-",Constants.COMMON_EXCEL_FILE_PATH);
    	
    	mkdir(Constants.TEMPLATE_EXCEL_FILE_PATH);
    	logger.debug("-Constants.TEMPLATE_EXCEL_FILE_PATH={}-",Constants.TEMPLATE_EXCEL_FILE_PATH);

    	mkdir(Constants.IMAGE_FILE_PATH);
    	logger.debug("-Constants.IMAGE_FILE_PATH={}-",Constants.IMAGE_FILE_PATH);
    	
    	mkdir(Constants.UPIMAGE_FILE_PATH);
    	logger.debug("-Constants.UPIMAGE_FILE_PATH={}-",Constants.UPIMAGE_FILE_PATH);

    	mkdir(Constants.TEMP_FILE_PATH);
    	logger.debug("-Constants.TEMP_FILE_PATH={}-",Constants.TEMP_FILE_PATH);
    	
    	mkdir(Constants.COMMON_FILE_PATH);
    	logger.debug("-Constants.COMMON_FILE_PATH={}-",Constants.COMMON_FILE_PATH);
    	
    	mkdir(Constants.BILL_SAMPLE_PATH);
    	logger.debug("-Constants.BILL_SAMPLE_PATH={}-",Constants.BILL_SAMPLE_PATH);
    	
    	mkdir(Constants.UPLOAD_EXCEL_SAMPLE_PATH);
    	logger.debug("-Constants.UPLOAD_EXCEL_SAMPLE_PATH={}-",Constants.UPLOAD_EXCEL_SAMPLE_PATH);
    	
    	mkdir(Constants.BILL_SAMPLE_PATH);
    	logger.debug("-Constants.CLAIM_RECEIPT_FILE_PATH={}-",Constants.CLAIM_RECEIPT_FILE_PATH);
    	
    	mkdir(Constants.UPLOAD_EXCEL_SAMPLE_PATH);
    	logger.debug("-Constants.CLAIM_DOCUMENT_FILE_PATH={}-",Constants.CLAIM_DOCUMENT_FILE_PATH);
    	
    	mkdir(Constants.UBI_FILE_PATH);
    	logger.debug("-Constants.UBI_FILE_PATH={}-",Constants.UBI_FILE_PATH);
    }
    
    // getURL 은 프로젝트내 상대경로 반환
    public static String getURL(UPLOADS u)
    {
    	String rtnURL = "";
    	switch(u)
    	{
    		case ROOT:
    			rtnURL = "/";
    			break;
    			
    		case UPLOAD_ROOT:
    			rtnURL = Constants.UPLOAD_ROOT_URL;
    			break;
    			
    		case COMMON_EXCEL:
    			rtnURL = Constants.COMMON_EXCEL_FILE_URL;
    			break;
    			
    		case TEMPLATE_EXCEL:
    			rtnURL = Constants.TEMPLATE_EXCEL_FILE_URL;
    			break;
    			
    		case IMAGE:
    			rtnURL = Constants.IMAGE_FILE_URL;
    			break;
    			
    		case UPIMAGE:
    			rtnURL = Constants.UPIMAGE_FILE_URL;
    			break;
    			
    		case TEMP:
    			rtnURL = Constants.TEMP_FILE_URL;
    			break;
    			
    		case COMMON:
    			rtnURL = Constants.COMMON_FILE_URL;
    			break;
    			
    		case BILL_SAMPLE:
    			rtnURL = Constants.BILL_SAMPLE_URL;
    			break;
    			
    		case UPLOAD_EXCEL_SAMPLE:
    			rtnURL = Constants.UPLOAD_EXCEL_SAMPLE_URL;
    			break;

    		case CLAIM_RECEIPT:
    			rtnURL = Constants.CLAIM_RECEIPT_FILE_URL;
    			break;
    			
    		case CLAIM_DOCUMENT:
    			rtnURL = Constants.CLAIM_DOCUMENT_FILE_URL;
    			break;
    			
    		case UBI_FILE:
    			rtnURL = Constants.UBI_FILE_URL;
    			break;
    			
    		default:
    			rtnURL = "/";
    			break;
    	}
    	return rtnURL;
    }
    
    // getPATH은 절대경로 반환
    public static String getPATH(UPLOADS u)
    {
    	String rtnPATH = "";
    	switch(u)
    	{
    		case ROOT:
    			rtnPATH = Constants.WEB_ROOT_PATH;
    			break;
    			
    		case UPLOAD_ROOT:
    			rtnPATH = Constants.UPLOAD_ROOT_PATH;
    			break;
    			
    		case COMMON_EXCEL:
    			rtnPATH = Constants.COMMON_EXCEL_FILE_PATH;
    			break;
    			
    		case TEMPLATE_EXCEL:
    			rtnPATH = Constants.TEMPLATE_EXCEL_FILE_PATH;	
    			break;
    			
    		case IMAGE:
    			rtnPATH = Constants.IMAGE_FILE_PATH;
    			break;
    			
    		case UPIMAGE:
    			rtnPATH = Constants.UPIMAGE_FILE_PATH;
    			break;
    			
    		case TEMP:
    			rtnPATH = Constants.TEMP_FILE_PATH;
    			break;
    			
    		case COMMON:
    			rtnPATH = Constants.COMMON_FILE_PATH;
    			break;
    			
    		case BILL_SAMPLE:
    			rtnPATH = Constants.BILL_SAMPLE_PATH;
    			break;
    			
    		case UPLOAD_EXCEL_SAMPLE:
    			rtnPATH = Constants.UPLOAD_EXCEL_SAMPLE_PATH;
    			break;
    			
    		case CLAIM_RECEIPT:
    			rtnPATH = Constants.CLAIM_RECEIPT_FILE_PATH;
    			break;
    			
    		case CLAIM_DOCUMENT:
    			rtnPATH = Constants.CLAIM_DOCUMENT_FILE_PATH;
    			break;
    			
    		case UBI_FILE:
    			rtnPATH = Constants.UBI_FILE_PATH;
    			break;
    			
    		default:
    			rtnPATH = Constants.WEB_ROOT_PATH;
    			break;
    	}
    	return rtnPATH;
    }
    
    private static void mkdir(String path)
    {
    	File upDir = new File(path);
    	if(!upDir.exists())//해당 디렉토리의 존재여부를 확인
    	{
    		upDir.mkdirs();//없다면 생성 
    	}
    }

    

}///~
