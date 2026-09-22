/**
 * www.ITPSystem.net @ 2012, developed by Kim Hyun Wook
 * email:itpeople1@hotmail.com
 */
package kr.co.gnx.comm.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 파일 유틸 클래스
 * @author KIM HYUN WOOK
 */
public class FileUtility {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);

	private static String realPath;

	public static String getRealPath() {
		return realPath;
	}

	public static void setRealPath(String realPath) {
		FileUtility.realPath = realPath;
	}

	/**
	 * @param fileList FormFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return void
	 * @throws FileNotFoundException, IOException
	 */
	public static Map<String, Object> uploadFile(String path, String fileName, long fileSize, String fileContentType, InputStream stream)
	throws FileNotFoundException, IOException {
		OutputStream bos = null;
		Map<String, Object>  map = null;
		fileName = getFileNameWithoutPath(fileName);

		try {
			mkdir(path);
			// 파일 정보
			String fileNo = Long.toString(System.nanoTime());	// 파일 번호
			int token = fileName.lastIndexOf(".");
			String fileExt = fileName.substring(token, fileName.length()).toLowerCase();
			String saveFileName = fileNo + fileExt;     //서버에 저장된 파일명

			if (logger.isDebugEnabled()) {
				logger.debug("uploadFile(path = " + path
						+ ") - File Upload Info : fileNo = " + fileNo
						+ ", fileName = " + fileName
						+ ", saveFileName = " + saveFileName
						+ ", fileSize = " + fileSize
						+ ", fileContentType = " + fileContentType);
			}

			// 파일을 업로드할 절대 경로를 지정해야 한다.
			bos = new FileOutputStream(path +File.separator+ saveFileName);

			int bytesRead = 0;
			byte[] buffer = new byte[1024*10];	//10K
			while ((bytesRead = stream.read(buffer, 0, buffer.length)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			if (logger.isInfoEnabled()) {
				logger.info("uploadFile() - upload complete");
			}
			// 파일정보 전달...
			map = new HashMap<String, Object> ();
			map.put("file_no", fileNo);
			map.put("file_nm", fileName);
			map.put("save_file_nm", saveFileName);
			map.put("file_size", new Long(fileSize));
			map.put("file_content_type", fileContentType);
		} catch (FileNotFoundException e) {
			//throw e;
			e.printStackTrace();
		} catch (IOException e) {
			//throw e;
			e.printStackTrace();
		} finally {
			try {
				if(bos!=null) bos.flush();
				if(bos!=null) bos.close();
				if(stream!=null) stream.close();
			} catch (IOException e) {
				//throw e;
				e.printStackTrace();
			}
		}
		return map;
	}


	/**
	 * @param fileList FormFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return void
	 * @throws FileNotFoundException, IOException
	 */
	public static void uploadRealFileOverwrite(String path, String fileName, long fileSize, String fileContentType, InputStream stream)
	throws FileNotFoundException, IOException {
		OutputStream bos = null;
		fileName = getFileNameWithoutPath(fileName);

		try {
			mkdir(path);
			// 파일을 업로드할 절대 경로를 지정해야 한다.
			bos = new FileOutputStream(path +File.separator+ fileName);

			int bytesRead = 0;
			byte[] buffer = new byte[1024*10];	//10K
			while ((bytesRead = stream.read(buffer, 0, buffer.length)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			if (logger.isInfoEnabled()) {
				logger.info("uploadFile() - upload complete");
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if(bos!=null) bos.flush();
				if(bos!=null) bos.close();
				if(stream!=null) stream.close();
			} catch (IOException e) {
				throw e;
			}
		}
	}

	/**
	 * @param path String
	 * @param fileno String
	 * @param fileName String
	 * @param fileContentType String
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return void
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void downloadFile(String path, String encoding, String fileNo,
			String fileName, String saveFileName, String fileContentType, HttpServletRequest request,
			HttpServletResponse response)
	throws FileNotFoundException, IOException {

		//File file = new File(path, saveFileName);
		File file = new File(path);
		int fileSize = (int)file.length();
		String client = request.getHeader("User-Agent");
		//
		if(client.indexOf("MSIE 5.5") != -1) {
			response.setHeader("Content-Disposition","filename="+URLEncoder.encode(fileName, encoding)+";");
		} else {
			response.setHeader("Content-Disposition","attachment;filename="+URLEncoder.encode(fileName, encoding)+";");
		}
		response.setContentType(fileContentType);
		response.setContentLength(fileSize);

		if (logger.isDebugEnabled()) {
			logger.debug("downloadFile(path = " + path
					+ ") - File Upload Info : fileNo = " + fileNo
					+ ", fileName = " + fileName
					+ ", saveFileName = " + saveFileName
					+ ", fileSize = " + fileSize
					+ ", fileContentType = " + fileContentType);
		}
		byte b[] = new byte[1024];
		if (file.isFile()) {
			BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
			BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());
			int read = 0;
			try {
				while ((read = fin.read(b)) != -1){
					outs.write(b,0,read);
				}
				if (logger.isDebugEnabled()) {
					logger.debug("downloadFile() - download complete");
				}
			} catch (FileNotFoundException e) {
				throw e;
			} catch (IOException e) {
				throw e;
			} finally {
				try {
					if(outs!=null) outs.flush();
					if(outs!=null) outs.close();
					if(fin!=null) fin.close();
				} catch (IOException e1) {
					throw e1;
				}
			}
		}
	}

	public static void copyFile(String srcPath, String srcSaveFileName, String targetPath, String targetSaveFileName)
	throws FileNotFoundException, IOException {

		File file = new File(srcPath);
		File targetFile = new File(targetPath+targetSaveFileName);

		byte b[] = new byte[1024];
		if (file.isFile()) {
			BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
			BufferedOutputStream outs = new BufferedOutputStream(new FileOutputStream(targetFile));
			int read = 0;
			try {
				while ((read = fin.read(b)) != -1){
					outs.write(b,0,read);
				}
				if (logger.isDebugEnabled()) {
					logger.debug("copyFile() - copyFile complete");
				}
			} catch (FileNotFoundException e) {
				throw e;
			} catch (IOException e) {
				throw e;
			} finally {
				try {
					if(outs!=null) outs.flush();
					if(outs!=null) outs.close();
					if(fin!=null) fin.close();
				} catch (IOException e1) {
					throw e1;
				}
			}
		}
	}

	/**
	 * @param path String
	 * @param filename String
	 * @return boolean
	 */
	public static boolean deleteFile(String path, String filename) {
		File file = new File(path, filename);

		if (logger.isInfoEnabled()) {
			logger.info("deleteFile(path = " + path + ", filename = " + filename + ")");
		}

		boolean fileDelete = false;

		if(file.isFile()){
			fileDelete = file.delete();
		}
		return fileDelete;
	}

	public static boolean deleteFile(String fullpath) {
		File file = new File(fullpath);

		if (logger.isInfoEnabled()) {
			logger.info("deleteFile(fullpath = " + fullpath + ")");
		}

		boolean fileDelete = false;

		if(file.isFile()){
			fileDelete = file.delete();
		}
		return fileDelete;
	}


	/**
	 * @param path String
	 * @param filename String
	 * @return boolean
	 */
	public static void mkdir(String path) {
		if (path != null && !path.equals("")) {
			File file = new File(path);
			if (!file.isDirectory()) {
				file.mkdirs();
				if (logger.isInfoEnabled()) {
					logger.info("create directory(path = " + path + ")");
				}
			}
		}
	}

	public static String[] fileList(String path){

		File file = new File(path);

		return file.list();
	}

	public static String getFileNameWithoutPath(String fullName) {
		int lastIndex = 0;
		lastIndex = fullName.lastIndexOf(File.separator);
		if(lastIndex == -1) return fullName;
		return fullName.substring(lastIndex+1, fullName.length());
	}

	public static File view(String absolutePath, String inputFileName, HttpServletRequest request,
			HttpServletResponse response ) throws UnsupportedEncodingException,
			FileNotFoundException, IOException{

		String returnFileName = inputFileName;

		File inputFile = new File(absolutePath, returnFileName);
		byte b [] = new byte[1024];

		String strClient = request.getHeader("user-agent");

		response.setHeader("Content-Length", String.valueOf(inputFile.length()) );
		response.setHeader("Content-Disposition", "inline;filename=" + returnFileName);
		response.setHeader("Content-Transfer-Encoding", "binary;");

		if(inputFile.isFile()){
			BufferedInputStream fin = new BufferedInputStream(new FileInputStream(inputFile));
			BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

			int read = 0;

			try{
				while((read = fin.read(b, 0, 1024)) != -1){
					outs.write(b, 0, read);
				}
				outs.close();
				fin.close();

			}catch(IOException e){
				throw e;
			}finally{
				if(outs != null){
					outs.close();
					outs = null;
				}
				if(fin != null ){
					fin.close();
					fin = null;
				}
			}
		}

		return inputFile;
	}

	/**
	 * 디렉터리를 삭제 한다.
	 * @param path String
	 * @param filename String
	 * @return boolean
	 */
	public static void rmdir(String path) {
		if (path != null && !path.equals("")) {
			File file = new File(path);
			if (file.isDirectory()) {
				file.delete();
				if (logger.isInfoEnabled()) {
					logger.info("delete directory(path = " + path + ")");
				}
			}
		}
	}
}