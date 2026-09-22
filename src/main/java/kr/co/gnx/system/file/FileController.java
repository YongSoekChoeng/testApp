package kr.co.gnx.system.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import kr.co.gnx.base.BaseController;
import kr.co.gnx.comm.util.CommUtil;
import kr.co.gnx.comm.util.ZipUtils;
import kr.co.gnx.comm.util.session.SessionUtil;
import kr.co.gnx.config.Constants.UPLOADS;
import kr.co.gnx.security.model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("FileController")
public class FileController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	/**
	 * @Description  : 파일다운로드
	 * @author       : lakhyun.kim
	 * @since        : 2018. 06. 20
	 * @return       : String
	 */
	@RequestMapping(value = "/files/getFile.do", method = RequestMethod.POST)
	public void getFile(HttpServletRequest request, HttpServletResponse response,FileVO fileVO) throws Exception{

		String file_name = fileVO.getFile_nm();
		String Path = fileVO.getFile_path();

		String retrunString = getFileService().getFile(response, Path, file_name);
		if(null != retrunString && !"".equals(retrunString) && "ok".equals(retrunString)) {
			return;
		}else {
			CommUtil.sendGenexonAlert(response, "info", "파일 다운로드", "등록된 파일이 없습니다.");
		}
	}
	
	/**
	 * @Description  : 파일다운로드
	 * @author       : jjt
	 * @since        : 2020.04.13
	 * @return       : String
	 */
	@RequestMapping(value = "/files/getFileMobile.do", method = RequestMethod.POST)
	public void getFileMobile(HttpServletRequest request, HttpServletResponse response,FileVO fileVO) throws Exception{
		
		String file_name = fileVO.getFile_nm();
		String Path = fileVO.getFile_path();
		
		String retrunString = getFileService().getFile(response, Path, file_name);
		if(null != retrunString && !"".equals(retrunString) && "ok".equals(retrunString)) {
			return;
		}else {
			CommUtil.sendMobileAlert(response, "info", "파일 다운로드", "등록된 파일이 없습니다.");
		}
	}
	
	/**
	 * @Description  : 파일 삭제(key : FILE_NO)
	 * @author       : KIMDONGUK
	 * @since        : 2018. 10. 16
	 * @return       : void
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/files/deleteFileInfo.ajax")
	public ModelAndView deleteFileInfo(HttpServletRequest request, HttpServletResponse response, FileVO fileVO) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		JSONObject paramJObj = JSONObject.fromObject(fileVO.getJson_string());
		ArrayList<FileVO> models = (ArrayList<FileVO>) JSONArray.toCollection(paramJObj.getJSONArray("models"), FileVO.class);
		getFileService().setUserSession(UserSession);
		
		for(int i = 0; i<models.size();i++) {
			FileVO fileVO1 = new FileVO();
			fileVO1.setMb_id(UserSession.getMb_id());
			fileVO1.setRef_seq(models.get(i).getRef_seq());
			fileVO1.setAttach_gbn(models.get(i).getAttach_gbn());
			getFileDAO().deleteFileInfoByRefIdx(fileVO1);
		}
		
		return mv;
	}
	
	/**
	 * 파일 압축
	 * @param request
	 * @param response
	 * @param fileVO
	 * @return
	 */
	@RequestMapping(value="/files/fileCompress.ajax")
	public void fileCompress(HttpServletRequest request, HttpServletResponse response,FileVO fileVO) throws Exception {

		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		fileVO.setMb_id(UserSession.getMb_id());
		fileVO.setIn_emp_cd(UserSession.getEmp_cd());
		fileVO.setUp_emp_cd(UserSession.getEmp_cd());
		
		try {
			List<FileVO> list = getFileService().getFileInfoList(fileVO);
			
			if(null == list || !(list.size() > 0)){
				if("2".equals(fileVO.getAttach_gbn())) {
					CommUtil.sendGenexonAlert(response, "info", "보증보험", "첨부파일이 없습니다.");
				}else if("3".equals(fileVO.getAttach_gbn())) {					
					CommUtil.sendGenexonAlert(response, "info", "기타담보", "첨부파일이 없습니다.");
				}
			}else {
				response.setHeader("Set-Cookie", "fileDownload=true; path=/");
				String output = fileVO.getFile_nm() ;
				output = output + ".zip";
				ZipUtils.zips(list, output, response);
			}
			
		}catch(Exception e) {
			logger.error(e.getMessage());
			CommUtil.sendGenexonAlert(response, "error", "기타담보", e.getMessage());
		}
	}
	
	/**
	 * 파일 목록 리스트 조회
	 * @param request
	 * @param response
	 * @param FileVO
	 * @return ModelAndView
	 */
	@RequestMapping(value="/files/getFileInfoList.ajax")
	public ModelAndView fileupload(HttpServletRequest request, HttpServletResponse response, FileVO fileVO) {
		
		ModelAndView mv = new ModelAndView("jsonView");
		
		User UserSession = SessionUtil.getSessionVO(request.getSession()).getUser();
		
		fileVO.setMb_id(UserSession.getMb_id());
		
		mv.addObject("results", getFileService().getFileInfoList(fileVO));
		
		return mv;
		
	}

}
