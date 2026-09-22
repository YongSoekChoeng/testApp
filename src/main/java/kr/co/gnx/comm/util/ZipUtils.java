package kr.co.gnx.comm.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.gnx.system.file.FileVO;

public class ZipUtils {

	private static final int COMPRESSION_LEVEL = 8;

	private static final int BUFFER_SIZE = 1024 * 2;

	private static final Logger logger = LoggerFactory.getLogger(ZipUtils.class);

	/**
	 * 지정된 폴더를 Zip 파일로 압축한다.
	 * 
	 * @param sourcePath - 압축 대상 디렉토리
	 * @param output     - 저장 zip 파일 이름
	 * @throws Exception
	 */
	public static void zip(String sourcePath, String output) throws Exception {

		// 압축 대상(sourcePath)이 디렉토리나 파일이 아니면 리턴한다.
		File sourceFile = new File(sourcePath);
		if (!sourceFile.isFile() && !sourceFile.isDirectory()) {
			throw new Exception("압축 대상의 파일을 찾을 수가 없습니다.");
		}

		// output 의 확장자가 zip이 아니면 리턴한다.
		if (!(StringUtils.substringAfterLast(output, ".")).equalsIgnoreCase("zip")) {
			throw new Exception("압축 후 저장 파일명의 확장자를 확인하세요");
		}

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ZipOutputStream zos = null;

		try {
			fos = new FileOutputStream(output); // FileOutputStream
			bos = new BufferedOutputStream(fos); // BufferedStream
			zos = new ZipOutputStream(bos); // ZipOutputStream
			zos.setLevel(COMPRESSION_LEVEL); // 압축 레벨 - 최대 압축률은 9, 디폴트 8
			zipEntry(sourceFile, sourcePath, zos); // Zip 파일 생성
			zos.finish(); // ZipOutputStream finish
		} finally {
			if (zos != null) {
				zos.close();
			}
			if (bos != null) {
				bos.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}

	 public static void zips(List<FileVO> sourcePathlist, String output, HttpServletResponse response) throws Exception {
	    	// Buffer 사이즈를 설정한다.
	    	byte[] buffer = new byte[4096];
	    	 
	    	 String fileName = output;
	         ZipOutputStream out = null;
	         OutputStream out1 = null;
	         File file = null;
	         
	         // ZIP 파일 다운로드 설정을 한다.
	         fileName = URLEncoder.encode(fileName, "UTF-8");
	         fileName = fileName.replace("+", "%20");	//파일명에 공백이 있으면 +가 되고 그 +를 공백으로 다시 변환
	         
	         response.setContentType("application/x-zip-compressed; charset=utf-8");
	         response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";");
	         
	         FileInputStream fis = null;
	         
	    	try {
	            out1 = response.getOutputStream();
	            out = new ZipOutputStream(out1);
	    	
	            for(int i=0; i<sourcePathlist.size();i++){
	            	
	            	try {
	            		file = new File(sourcePathlist.get(i).getFile_path());
					    fis = new FileInputStream(file);
					    out.putNextEntry(new ZipEntry(file.getName()));
					    //out.putNextEntry(new ZipEntry(sourcePathlist.get(i).getFile_no()));
					    int len = 0;
					    while((len = fis.read(buffer)) > 0) {
				    		out.write(buffer, 0, len);
					    }
					    
					    out.closeEntry();
	            	}catch(Exception e) {
	            		logger.error(e.getMessage());
	            		continue;
	            	}
				    //fis.close();
	            }
	    	 
	    	}catch (FileNotFoundException e) {
	    		logger.error(e.getMessage());
	    	} catch (Exception e) {
	    		logger.error(e.getMessage());
	    	}finally{
	    		try {
		    		 if (out != null) {
		    			 out.close();
		              }
		    		 if (fis != null) {
		    			 fis.close();
		    		 }
				/*
				 * if (out1 != null) { out1.flush(); out1.close(); }
				 */
	    		}catch (Exception e) {
	    			logger.error(e.getMessage());
				}
	        }
	   	}

	/**
	 * 압축
	 * 
	 * @param sourceFile
	 * @param sourcePath
	 * @param zos
	 * @throws Exception
	 */
	private static void zipEntry(File sourceFile, String sourcePath, ZipOutputStream zos) throws Exception {
		// sourceFile 이 디렉토리인 경우 하위 파일 리스트 가져와 재귀호출
		if (sourceFile.isDirectory()) {
			if (sourceFile.getName().equalsIgnoreCase(".metadata")) { // .metadata 디렉토리 return
				return;
			}
			File[] fileArray = sourceFile.listFiles(); // sourceFile 의 하위 파일 리스트
			for (int i = 0; i < fileArray.length; i++) {
				zipEntry(fileArray[i], sourcePath, zos); // 재귀 호출
			}
		} else { // sourcehFile 이 디렉토리가 아닌 경우
			BufferedInputStream bis = null;
			try {
				String sFilePath = sourceFile.getPath();
				String zipEntryName = sFilePath.substring(sourcePath.length(), sFilePath.length());

				bis = new BufferedInputStream(new FileInputStream(sourceFile));
				ZipEntry zentry = new ZipEntry(zipEntryName);
				zentry.setTime(sourceFile.lastModified());
				zos.putNextEntry(zentry);

				byte[] buffer = new byte[BUFFER_SIZE];
				int cnt = 0;
				while ((cnt = bis.read(buffer, 0, BUFFER_SIZE)) != -1) {
					zos.write(buffer, 0, cnt);
				}
				zos.closeEntry();
			} finally {
				if (bis != null) {
					bis.close();
				}
			}
		}
	}

	/**
	 * Zip 파일의 압축을 푼다.
	 *
	 * @param zipFile             - 압축 풀 Zip 파일
	 * @param targetDir           - 압축 푼 파일이 들어간 디렉토리
	 * @param fileNameToLowerCase - 파일명을 소문자로 바꿀지 여부
	 * @throws Exception
	 */
	public static void unzip(File zipFile, File targetDir, boolean fileNameToLowerCase) throws Exception {
		FileInputStream fis = null;
		ZipInputStream zis = null;
		ZipEntry zentry = null;

		try {
			fis = new FileInputStream(zipFile); // FileInputStream
			zis = new ZipInputStream(fis); // ZipInputStream

			while ((zentry = zis.getNextEntry()) != null) {
				String fileNameToUnzip = zentry.getName();
				if (fileNameToLowerCase) { // fileName toLowerCase
					fileNameToUnzip = fileNameToUnzip.toLowerCase();
				}

				File targetFile = new File(targetDir, fileNameToUnzip);

				if (zentry.isDirectory()) {// Directory 인 경우
					FileUtils.forceMkdir(targetFile);
				} else { // File 인 경우
					// parent Directory 생성
					FileUtils.forceMkdir(targetFile.getParentFile());
					unzipEntry(zis, targetFile);
				}
			}
		} finally {
			if (zis != null) {
				zis.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
	}

	/**
	 * Zip 파일의 한 개 엔트리의 압축을 푼다.
	 *
	 * @param zis      - Zip Input Stream
	 * @param filePath - 압축 풀린 파일의 경로
	 * @return
	 * @throws Exception
	 */
	protected static File unzipEntry(ZipInputStream zis, File targetFile) throws FileNotFoundException, IOException, Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(targetFile);

			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = zis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
		} finally {
			if (fos != null) fos.close();
		}
		
		return targetFile;
	}
}
