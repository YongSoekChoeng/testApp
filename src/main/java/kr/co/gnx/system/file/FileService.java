package kr.co.gnx.system.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gnx.base.BaseService;
import kr.co.gnx.comm.util.CommUtil;
import kr.co.gnx.comm.util.ImageUtil;
import kr.co.gnx.config.Constants;
import kr.co.gnx.config.Constants.UPLOADS;

/**
 * 첨부파일정보 관리 service
 */
@Service(value = "FileService")
public class FileService extends BaseService{

	private static final Logger logger = LoggerFactory.getLogger(FileService.class);

	/**
	 * @brief 첨부파일정보 조회
	 * @param FileVO
	 * @return List<FileVO>
	 */
	public List<FileVO> getFileInfoList(FileVO fileVO){
		List<FileVO> resultList = new ArrayList<FileVO>();
		resultList = getFileDAO().selectFileInfoList(fileVO);
		return resultList;
	}
	
	/**
	 * @desc 첨부파일 단일건 조회
	 * @author KIMDONGUK
	 * @since 2019. 03. 28
	 * @param FileVO
	 * @return FileVO
	 */
	public FileVO getFileInfoView(FileVO fileVO) {
//		fileVO.setMb_id(getUserSession().getMb_id());
//		fileVO.setIn_emp_cd(getUserSession().getEmp_cd());
//		fileVO.setUp_emp_cd(getUserSession().getEmp_cd());
		
		return getFileDAO().selectFileInfoView(fileVO);
	}
	
	/**
	 * @Description  : 파일 업로드 및 DB 저장
	 * @author       : lakhyun.kim
	 * @since        : 2018. 06. 04
	 * @return       : FileVO
	 */
	public FileVO fileSave(MultipartFile multipartFile, UPLOADS u){
		
		FileVO filevo =  new FileVO();
		filevo.setMb_id(getUserSession().getMb_id());
		filevo.setIn_emp_cd(getUserSession().getEmp_cd());
		filevo.setUp_emp_cd(getUserSession().getEmp_cd());
		
		String fileName = multipartFile.getOriginalFilename();// 원본파일명
		String fileId = CommUtil.getUUID();// 저장파일명
		String fileFormat = fileName.substring(fileName.lastIndexOf(".") + 1);// 확장자
		String retunfilename = fileId + "." + fileFormat;// 저장파일명.확장자
		
		getConstants();
		String filePath = Constants.getPATH(u) + getUserSession().getMb_id() + "/"; // upload폴더
		getConstants();
		String fileUrl =  Constants.getURL(u) + getUserSession().getMb_id() + "/" + retunfilename; // upload폴더/저장파일명.확장자
		
		String fileSize = Long.toString(multipartFile.getSize()); // 파일 사이즈
		String mine = multipartFile.getContentType();
		String fullPath =  filePath + retunfilename ; // upload폴더명/저장파일명.확장자
		
		logger.debug("#####################################");
		logger.debug("UUID 파일명: " + fileId);
		logger.debug("원본 파일명: " + fileName);
		logger.debug("확장자: " + fileFormat);
		logger.debug("파일 PATH : " + fileUrl);
		logger.debug("파일 REAL PATH : " + fullPath);
		logger.debug("파일 사이즈 : " + fileSize);
		logger.debug("파일 mime : " + mine);
		logger.debug("#####################################");	
		logger.debug("");
		logger.debug("");
		
		filevo.setFile_no(fileId);//FILE_NO
		filevo.setFile_nm(fileName);//FILE_NM
		filevo.setFile_format(fileFormat);//FILE_FORMAT
		filevo.setFile_path(fullPath); //절대경로
		filevo.setFile_url(fileUrl); //저장경로
		filevo.setFile_size(Long.toString(multipartFile.getSize()));//파일사이즈
		filevo.setFile_mime(mine);//MIME
		
		/* 파일정보 저장 */
		getFileDAO().insertFileInfo(filevo);
		
		
		File file = new File(fullPath);
	
		if(!file.exists()){
			file.mkdirs();
		}
		logger.debug(file.getPath());
		try {
			multipartFile.transferTo(file);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return filevo;
	}
	
	/**
	 * @Description  : 파일 업로드 및 DB 저장(fileSave 오버로딩 메소드), 위의 메소드로는 attachGbn, ref_seq 등을 입력할 수 없고
	 * 				   insertOrUpdateDocument 메소드는 FileVO를 리턴하지 않아 새로 생성
	 * @author       : KIMDONGUK
	 * @since        : 2019. 10. 04
	 * @return       : FileVO
	 */
	public FileVO fileSave(MultipartFile multipartFile, UPLOADS u, FileVO filevo) {
		filevo.setMb_id(getUserSession().getMb_id());
		filevo.setIn_emp_cd(getUserSession().getEmp_cd());
		filevo.setUp_emp_cd(getUserSession().getEmp_cd());
		
		String fileName = multipartFile.getOriginalFilename();// 원본파일명
		String fileId = CommUtil.getUUID();// 저장파일명
		String fileFormat = fileName.substring(fileName.lastIndexOf(".") + 1);// 확장자
		String retunfilename = fileId + "." + fileFormat;// 저장파일명.확장자
		
		String filePath = Constants.getPATH(u) + getUserSession().getMb_id() + "/"; // upload폴더
		String fileUrl =  Constants.getURL(u) + getUserSession().getMb_id() + "/" + retunfilename; // upload폴더/저장파일명.확장자
		
		String fileSize = Long.toString(multipartFile.getSize()); // 파일 사이즈
		String mine = multipartFile.getContentType();
		String fullPath =  filePath + retunfilename ; // upload폴더명/저장파일명.확장자
		
		logger.debug("#####################################");
		logger.debug("UUID 파일명: " + fileId);
		logger.debug("원본 파일명: " + fileName);
		logger.debug("확장자: " + fileFormat);
		logger.debug("파일 PATH : " + fileUrl);
		logger.debug("파일 REAL PATH : " + fullPath);
		logger.debug("파일 사이즈 : " + fileSize);
		logger.debug("파일 mime : " + mine);
		logger.debug("#####################################");	
		logger.debug("");
		logger.debug("");
		
		filevo.setFile_no(fileId);//FILE_NO
		filevo.setFile_nm(fileName);//FILE_NM
		filevo.setFile_format(fileFormat);//FILE_FORMAT
		filevo.setFile_path(fullPath); //절대경로
		filevo.setFile_url(fileUrl); //저장경로
		filevo.setFile_size(Long.toString(multipartFile.getSize()));//파일사이즈
		filevo.setFile_mime(mine);//MIME
		
		/* 파일정보 저장 */
		getFileDAO().insertFileInfo(filevo);
		
		
		File file = new File(fullPath);
	
		if(!file.exists()){
			file.mkdirs();
		}
		logger.debug(file.getPath());
		try {
			multipartFile.transferTo(file);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		
		return filevo;
	}
	
	/**
	 * @Description  : 파일 업로드 및 DB 저장(fileSave 오버로딩 메소드), 위의 메소드로는 attachGbn, ref_seq 등을 입력할 수 없고
	 * 				   insertOrUpdateDocument 메소드는 FileVO를 리턴하지 않아 새로 생성
	 * @author       : KIMDONGUK
	 * @since        : 2019. 10. 04
	 * @return       : FileVO
	 */
	public FileVO CamerafileSave(MultipartFile multipartFile, UPLOADS u, FileVO filevo) {
		filevo.setMb_id(getUserSession().getMb_id());
		filevo.setIn_emp_cd(getUserSession().getEmp_cd());
		filevo.setUp_emp_cd(getUserSession().getEmp_cd());
		
		//String fileName = multipartFile.getOriginalFilename();// 원본파일명
		String fileName = filevo.getFile_nm();	// 원본파일명
		String fileId = CommUtil.getUUID();// 저장파일명
		String fileFormat = fileName.substring(fileName.lastIndexOf(".") + 1);// 확장자
		String retunfilename = fileId + "." + fileFormat;// 저장파일명.확장자
		
		String filePath = Constants.getPATH(u) + getUserSession().getMb_id() + "/" ; // upload폴더
		String fileUrl =  Constants.getURL(u) + getUserSession().getMb_id() + "/"  + retunfilename; // upload폴더/저장파일명.확장자
		
		String fileSize = Long.toString(multipartFile.getSize()); // 파일 사이즈
		String mine = multipartFile.getContentType();
		String fullPath =  filePath + retunfilename ; // upload폴더명/저장파일명.확장자
		
		logger.debug("#####################################");
		logger.debug("UUID 파일명: " + fileId);
		logger.debug("원본 파일명!: " + fileName);
		logger.debug("원본 파일명?: " + filevo.getFile_nm());
		logger.debug("확장자: " + fileFormat);
		logger.debug("파일 PATH : " + fileUrl);
		logger.debug("파일 REAL PATH : " + fullPath);
		logger.debug("파일 사이즈 : " + fileSize);
		logger.debug("파일 mime : " + mine);
		logger.debug("#####################################");	
		logger.debug("");
		logger.debug("");
		
		filevo.setFile_no(fileId);//FILE_NO
		filevo.setFile_nm(fileName);//FILE_NM
		filevo.setFile_format(fileFormat);//FILE_FORMAT
		filevo.setFile_path(fullPath); //절대경로
		filevo.setFile_url(fileUrl); //저장경로
		filevo.setFile_size(Long.toString(multipartFile.getSize()));//파일사이즈
		filevo.setFile_mime(mine);//MIME
		
		/* 파일정보 저장 */
		getFileDAO().insertFileInfo(filevo);
		
		
		File file = new File(fullPath);
	
		if(!file.exists()){
			file.mkdirs();
		}
		logger.debug(file.getPath());
		try {
			multipartFile.transferTo(file);
			//ImageUtil iu = new ImageUtil();
			//iu.CameraImageChamge(filevo);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		
		return filevo;
	}
	
	/**
	 * @Description  : 첨부서류 입력/수정
	 * @author       : lakhyun.kim
	 * @since        : 2018. 07. 10
	 * @return       : int
	 */
	public int insertOrUpdateDocument(MultipartFile multipartFile, UPLOADS u, FileVO filevo){
			int resultInt = 0;
			logger.info("start service insertOrUpdateEmpDocument");
			String fileName = multipartFile.getOriginalFilename();//원본파일명
			String fileId = CommUtil.getUUID();//저장파일명(자동생성됨)
			String fileFormat = fileName.substring(fileName.lastIndexOf(".")+1);//확장자
			String returnfilename =  fileId+"."+fileFormat;//저장파일명.확장자
			
			getConstants();
			String filePath = "";
			String fileUrl = "";
			
			if(filevo.getAttach_gbn() != null && !filevo.getAttach_gbn().equals("")) {
				if(filevo.getAttach_gbn().equals("9")) {
					// 게시판은 mb_id 별로 구분
					filePath =  Constants.getPATH(u)+filevo.getMb_id()+"/";//upload폴더
					fileUrl =  Constants.getURL(u)+filevo.getMb_id()+"/"+returnfilename;//upload폴더/저장파일명.확장자
				} else {
					filePath =  Constants.getPATH(u);//upload폴더
					fileUrl =  Constants.getURL(u)+returnfilename;//upload폴더/저장파일명.확장자
				}
			}
			getConstants();
			
			String fileSize = Long.toString(multipartFile.getSize());//파일 사이즈
			String mine = multipartFile.getContentType();
			String fullPath =  filePath + returnfilename ;//upload폴더명/저장파일명.확장자
	
			
			logger.debug("#####################################");
			logger.debug("UUID 파일명: " + fileId);
			logger.debug("원본 파일명: " + fileName);
			logger.debug("확장자: " + fileFormat);
			logger.debug("파일 PATH : " + fileUrl);
			logger.debug("파일 REAL PATH : " + fullPath);
			logger.debug("파일 사이즈 : " + fileSize);
			logger.debug("파일 mime : " + mine);
			logger.debug("#####################################");	
			logger.debug("");
			
			File file = new File(fullPath);
			
			if(!file.exists()){
				file.mkdirs();
			}
			logger.debug(file.getPath());
			try {
				multipartFile.transferTo(file);				
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
			
			filevo.setFile_nm(fileName);//FILE_NM
			filevo.setFile_format(fileFormat);//FILE_FORMAT
			filevo.setFile_path(fullPath);//절대경로
			filevo.setFile_url(fileUrl);//저장경로
			filevo.setFile_size(Long.toString(multipartFile.getSize()));//파일사이즈
			filevo.setFile_mime(mine);//MIME
			
			int fileinfocount = getFileDAO().selectFileInfoCount(filevo);
			if(fileinfocount > 0){
				logger.info("update Document");
				resultInt = getFileDAO().updateFileInfo(filevo);
			} else {
				logger.info("insert Document");
				filevo.setFile_no(fileId);//FILE_NO
				resultInt = getFileDAO().insertFileInfo(filevo);
			}
		
		return resultInt;
	}
	
	/**
	 * @brief 첨부파일정보 삭제
	 * @param List<FileVO>
	 * @return integer
	 */
	public int deleteFileInfo(ArrayList<FileVO> models){
		int resultInt = 0;
		for (int i = 0; i < models.size(); i++) {
			FileVO fileVO = models.get(i);
			fileVO.setMb_id(getUserSession().getMb_id());
			fileVO.setIn_emp_cd(getUserSession().getEmp_cd());
			fileVO.setUp_emp_cd(getUserSession().getEmp_cd());
			resultInt += getFileDAO().deleteFileInfo(fileVO);
		}
		return resultInt;
	}
	/**
     * @description 물리파일 및 DataBase 파일정보 삭제
     * @param FileVO
     * @return int 
     */
	public int deleteFile(FileVO fileVO){
		int resultInt = 0;
		
		fileVO.setMb_id(getUserSession().getMb_id());
		FileVO resultObject = getFileDAO().selectFileInfoView(fileVO);

		if (CommUtil.isNotEmpty(resultObject) && CommUtil.isNotEmpty(resultObject.getFile_path())) {
			logger.debug("파일삭제:{}", resultObject.getFile_path());
			File file = new File(resultObject.getFile_path());
			file.delete();
			resultInt = getFileDAO().deleteFileInfo(resultObject);
		}
		
		return resultInt;
	}
	/**
	 * @Description  : 업로드 폴더에 파일다운로드
	 * @author       : lakhyun.kim
	 * @since        : 2018. 06. 20
	 * @return       : String
	 */
	public String getFile(HttpServletResponse response ,String path,String filename){
		String fd_file_name ="";
		OutputStream out = null;
		FileInputStream fis = null;
		String returnString = "file";
		try {
			fd_file_name = filename;
			String filePath =  path;
			logger.info("FILE_NM = " + fd_file_name);
			logger.info("FILE_PATH = " + filePath);
			
		    String file_org = new String(fd_file_name.getBytes("EUC-KR"),"8859_1").replaceAll("\\+", "%20");  
		    File file = new File(filePath);
		    
		    if(file.isFile()){
		    	response.setContentType("application/octet-stream");
				response.setContentLength((int)file.length());
				response.setHeader("Content-Transfer-Encoding", "binary");
				response.setHeader("Content-Disposition", "attachment;fileName=\""+ file_org +"\";");
				out = response.getOutputStream();
				fis = new FileInputStream(file);
				FileCopyUtils.copy(fis,out);
			
				returnString = "ok";
				
			}else{
				returnString = "file";
			}
	    } catch (Exception e) {
	    	logger.info(e.getMessage());
	    }finally{
	    	try {
	    		if(fis != null){
	    			fis.close();
	    		}
	    		if(out != null) {
	    			out.flush();
	    		}
			} catch (Exception e2) {
				logger.info(e2.getMessage());
			}
	    }
		
		return returnString;
	}
	
	/**
	 * @Description  : 이미지 파일 업로드및 DB 저장  
	 * @author       : lakhyun.kim
	 * @since        : 2019. 03. 11
	 * @return       : void
	 */
	public FileVO imageSave(MultipartFile multipartFile, UPLOADS u){
		FileVO filevo = null;
	
		String fileName = multipartFile.getOriginalFilename();  //원본파일명
		String fileId = CommUtil.getUUID();  					//저장파일명
		String fileFormat = fileName.substring(fileName.lastIndexOf(".")+1); //확장자
		String retunfilename =  getUserSession().getMb_id().toUpperCase() + "/" + fileId+"."+fileFormat;          //저장파일명.확장자
		
		getConstants();
		String filePath =  Constants.getPATH(u); // upload폴더
		getConstants();
		String fileUrl =  Constants.getURL(u) + retunfilename; // upload폴더/저장파일명.확장자
		
		String fileSize = Long.toString(multipartFile.getSize()); //파일 사이즈
		String mine = multipartFile.getContentType();
		String fullPath =  filePath + retunfilename ; // upload폴더명/저장파일명.확장자

		
		logger.debug("#####################################");
		logger.debug("UUID 파일명: " + fileId);
		logger.debug("원본 파일명: " + fileName);
		logger.debug("확장자: " + fileFormat);
		logger.debug("파일 PATH : " + fileUrl);
		logger.debug("파일 REAL PATH : " + fullPath);
		logger.debug("파일 사이즈 : " + fileSize);
		logger.debug("파일 mime : " + mine);
		logger.debug("#####################################");	
		logger.debug("");
		logger.debug("");
		
		filevo =  new FileVO();
		
		filevo.setMb_id(getUserSession().getMb_id());
		filevo.setIn_emp_cd(getUserSession().getEmp_cd());
		filevo.setUp_emp_cd(getUserSession().getEmp_cd());
		
		filevo.setFile_no(fileId);
		filevo.setFile_nm(fileName);
		filevo.setFile_format(fileFormat);
		filevo.setFile_path(fullPath);
		filevo.setFile_url(fileUrl);
		filevo.setFile_size(Long.toString(multipartFile.getSize()));
		filevo.setFile_mime(mine);
		
		File file = new File(fullPath);
		/*파일정보 저장 */
		
		getFileDAO().insertFileInfo(filevo);
		logger.debug(file.getPath());
		try {
			multipartFile.transferTo(file);			
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		
		if(!file.exists()){
			file.mkdirs();
		}
	
		return filevo;
	}

	/**
	 * @Description  : 임시 파일 삭제 및 디비 삭제
	 * @author       : lakhyun.kim
	 * @since        : 2019. 09. 04
	 * @return       : int
	 */
	public int fileTempDelete(FileVO fileVO) throws Exception {
		
		List<FileVO> fileVO2 = getFileDAO().selectTempFileInfoList(fileVO);
		
		if(fileVO2.size() > 0){
			for (int i = 0; i < fileVO2.size(); i++) {
				File file = new File(fileVO2.get(i).getFile_path());
				file.delete();
			}
			return getFileDAO().deleteTempFileInfo(fileVO);
		}
		return 0;
	}
	
}
