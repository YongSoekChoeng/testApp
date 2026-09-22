package kr.co.gnx.comm.excel;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.gnx.comm.util.CommUtil;
import kr.co.gnx.config.Constants;
import kr.co.gnx.config.Constants.UPLOADS;


@SuppressWarnings("rawtypes")
public class SampleExcelHandler implements ResultHandler {

	private static final Logger logger = LoggerFactory.getLogger(SampleExcelHandler.class);
	
	private ArrayList<String> TITLE = null;
	private ArrayList<String> FIELDS = null;
	
	private Workbook wb = null;
	private XSSFWorkbook temp_wb = null;
	private Sheet dataSheet = null;
	private int rowindex = 0;
	
	private int hidden = 0;
	private int BLANK_ROW = 0;
	private String TILTE_YN  = "Y";
	
	private DataFormat dataFormat = null;
	private CellStyle numStyle = null;
	private CellStyle rateStyle = null;
	
	private Font monthFont = null;
	private CellStyle  style = null;
	
    public int getRowindex() {
		return rowindex;
	}


	/**
     * 
     * Description  : 샘플로 사용할 엑셀 파일 세팅
     * 최초 생성일  : 2020. 03. 17.
     * 페키지       : genexon.comm.excel;
     * @param File
     */
	public SampleExcelHandler(String filePath) {
		
		try  {
			temp_wb = new XSSFWorkbook(new FileInputStream(filePath));
			wb =  new SXSSFWorkbook(temp_wb, 1000);
						
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

    @Override
	public void handleResult(ResultContext context) {
       Sheet sh = dataSheet;
		
       try {
			Object obj = context.getResultObject();
			
			
			//맵형태(mybatis alias > lhmap, hmap)
			if(obj instanceof java.util.LinkedHashMap || obj instanceof java.util.HashMap) {
				
				hidden = 1;
				  
				@SuppressWarnings("unchecked")
				Map<String, Object> resultMap = (Map<String, Object>) obj;
				
				Iterator<String> itvalue = resultMap.keySet().iterator();
				Iterator<String> itvaluevalue = resultMap.keySet().iterator();
				
				Object keyvalue = null;
				Row headerRow = sh.createRow(rowindex);
				
				if (rowindex == 0) {
					//빈로우 생성
				    for(int j =0  ; j < BLANK_ROW; j++){
				        rowindex++;
						headerRow = sh.createRow(rowindex);
				    }
				    
				    //타이틀 작성여부
				    if(TILTE_YN.equals("Y")){
				        //타이틀 파라메터가 있는경우
				        if(TITLE != null){
				        
				        	for(int  i= 0 ; i < this.TITLE.size(); i++ ){
		
								  Cell cell = headerRow.createCell(i);
								  cell.setCellValue(TITLE.get(i));
								  cell.setCellStyle(style);
								  
							}
				        //타이틀 파라메터가 없는경우map keyvlaue 를 타이틀로 	
				        }else{
				        	int i = 0;
							while(itvalue.hasNext())
							{	
							  keyvalue = itvalue.next();
							  String keystr = keyvalue.toString();
							  
							  Cell cell = headerRow.createCell(i);
							  cell.setCellValue(keystr);
							  cell.setCellStyle(style);
							  
							  i++;
							}
				        }
				        rowindex++;
						headerRow = sh.createRow(rowindex);
				    }
				}
				
				//FIELDS 값 정의 했을 때 > FIELDS에 정의해 놓은 결과값 필드명으로 맵에있는 데이터 가져와서 셀 데이터 생성
				if(FIELDS != null) {
					String cellValue = "";
					
					for(int i=0; i < FIELDS.size(); i++) {
						Cell cell = headerRow.createCell(i);
						
						cellValue = String.valueOf(resultMap.get(FIELDS.get(i)));

						if(cellTypeStringField(FIELDS.get(i))) {
								cell.setCellType(CellType.STRING);
								cell.setCellValue(cellValue.replace("null", ""));
						}else{
							if(CommUtil.isStringDouble(cellValue)) {
								//금액, 성적 컬럼 셀은 #,### 형식으로 나오도록
								if(cellTypeNumberField(FIELDS.get(i)) && !cellTypeRateField(FIELDS.get(i))) {
									cell.setCellType(CellType.NUMERIC);
									cell.setCellStyle(numStyle);
									cell.setCellValue(Double.parseDouble(cellValue.replace("null", "")));
								}else if(cellTypeRateField(FIELDS.get(i)) && !cellTypeNumberField(FIELDS.get(i))) {
									cell.setCellType(CellType.NUMERIC);
									cell.setCellStyle(rateStyle);
									cell.setCellValue(Double.parseDouble(cellValue.replace("null", "")) / 100 );
								}else {
									cell.setCellValue(Double.parseDouble(cellValue.replace("null", "")));
								}
			                }else {
			                    cell.setCellValue(cellValue.replace("null", ""));
			                }
						}
					}
				}else {	//FIELDS 값 정의 안했을 때 > 맵의 데이터 순차적으로 insert
					int i = 0;
					
					while(itvaluevalue.hasNext()) {
						keyvalue = itvaluevalue.next();
						String keystr = keyvalue.toString();
						String value = String.valueOf(resultMap.get(keystr));
						
						Cell cell = headerRow.createCell(i);
						  
						  if (resultMap.get(keystr) instanceof String) {
							  cell.setCellValue( value.replace("null", ""));
						  } else {
							  if(CommUtil.isStringDouble(value)){
								  cell.setCellValue(Double.parseDouble(value.replace("null", "")));
							  }else{
								  cell.setCellValue( value.replace("null", ""));
							  }
						  }
					 
					  
					  i++;
					}
				}

				rowindex++;
			
			//VO 형태	
			}
			else
			{
				Field[] fields = obj.getClass().getDeclaredFields();
				
				if (TITLE != null && FIELDS !=null ) {

					hidden = 1;
					Row headerRow = sh.createRow(rowindex);
					
					if (rowindex == 0) {
				        //타이틀 파라메터가 있는경우
			        	for(int  i= 0 ; i < TITLE.size(); i++ ){
			        		  Cell cell = headerRow.createCell(i);
			        		  
			        		  if(i==0) {
			        			  cell.setCellValue("serialVersionUID");
			        		  }
							  cell.setCellValue(TITLE.get(i));				        		  					 
							  cell.setCellStyle(style);
							  
						}
				        
				        rowindex++;
						headerRow = sh.createRow(rowindex);
					}
					
					Map<String, Object> map = new LinkedHashMap<String, Object>();
						
					for (int i = 0; i <= fields.length - 1; i++) {
						if (fields[i] != null) {
							fields[i].setAccessible(true);
							map.put(fields[i].getName().toString(), String.valueOf(fields[i].get(obj)));
						}
					}
					
					if(map != null) {
						for (int i = 0; i < FIELDS.size(); i++) {                                                                            
							
							Cell cell = headerRow.createCell(i);
							String cellValue = "";
							
							if(FIELDS.get(i) != null ) {
								cellValue = String.valueOf(map.get(FIELDS.get(i)));
								
								if(cellTypeStringField(FIELDS.get(i))) {
									cell.setCellType(CellType.STRING);
									cell.setCellValue(cellValue.replace("null", ""));
								}else{
									if(CommUtil.isStringDouble(cellValue)) {
										//금액, 성적 컬럼 셀은 #,### 형식으로 나오도록
										if(cellTypeNumberField(FIELDS.get(i)) && !cellTypeRateField(FIELDS.get(i))) {
											cell.setCellType(CellType.NUMERIC);
											cell.setCellStyle(numStyle);
											
											cell.setCellValue(Double.parseDouble(cellValue.replace("null", "")));
										}else if(cellTypeRateField(FIELDS.get(i)) && !cellTypeNumberField(FIELDS.get(i))) {
											cell.setCellType(CellType.NUMERIC);
											cell.setCellStyle(rateStyle);
											
											cell.setCellValue(Double.parseDouble(cellValue.replace("null", "")) / 100 );
										}else {
											cell.setCellValue(Double.parseDouble(cellValue.replace("null", "")));
										}
					                }else {
					                    cell.setCellValue(cellValue.replace("null", ""));
					                }
								} 
							}			                
						}
					}
					
					
				} else {
//					Field[] fields = obj.getClass().getDeclaredFields();
					hidden = 0;
					Row headerRow = sh.createRow(rowindex);
					
					if (rowindex == 0) 
					{
						
						for (int i = 0; i <= fields.length - 1; i++)
						{
							fields[i].setAccessible(true);
							Cell cell = headerRow.createCell(i);
							cell.setCellValue(fields[i].getName().toString());
						}
						rowindex++;
						headerRow = sh.createRow(rowindex);
						
					}
	
					for (int i = 0; i <= fields.length - 1; i++)
					{
						
						Cell cell = headerRow.createCell(i);
						String cellvalue = "";
						
						if(fields[i] != null )
						{
							fields[i].setAccessible(true);
							cellvalue = String.valueOf(fields[i].get(obj));
						}
						
		                if(CommUtil.isStringDouble(cellvalue))
		                {
		                    cell.setCellValue(Double.parseDouble(cellvalue.replace("null", "")));
		                }
		                else
		                {
		                    cell.setCellValue(cellvalue.replace("null", ""));
		                }
		                
					}
				}
				rowindex++;
			}
			
		}
		catch (Exception e)
        {
			logger.error(e.getMessage());
        }
	}

	/**
	 * 
	 * Description  : 생성된 엑셀 파일 전송 
	 * 최초 생성일  : 2014. 5. 20. : 오전 10:18:54
	 * file         : excelHandler.java 
	 * 페키지       : genexon.comm.excel
	 * RETURN       : void 
	 * @param response
	 * @param filename
	 * @throws Exception

	 */
	public void sendResponse(HttpServletResponse response, String filename){

		OutputStream out = null;
		
		try {
			out = response.getOutputStream();
			
			filename = URLEncoder.encode(filename, "UTF-8");
			filename = filename.replace("+", "%20");	//파일명에 공백이 있으면 +가 되고 그 +를 공백으로 다시 변환
			
		    response.setHeader("Content-Disposition", "attachment;filename=" + filename + ";");
		    response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			response.setDateHeader("Last-Modified", new Date().getTime());
		    
			wb.write(out);

		} catch (Exception e) {
			logger.error(e.getMessage());
			//throw new ServletException(e);
		} finally {
			try {
				if(null != out && !(wb instanceof SXSSFWorkbook)){
					out.close();
				}
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
		}
	}
	
	/**
	 * 
	 * Description  : 엑셀파일 암호 적용하여 생성된 파일 전송
	 * 최초 생성일  : 2020. 02. 07.
	 * file         : excelHandler2.java 
	 * RETURN       : void
	 * @param HttpServletResponse response, String filename, String excelPwd
	 */
	public void sendEncryptExcelResponse(HttpServletResponse response, String filename, String excelPwd) {

		OutputStream out = null;
		OutputStream encDataStream = null;
		POIFSFileSystem fs = null;
		
		try {
			fs = new POIFSFileSystem();
			EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);

			Encryptor enc = info.getEncryptor();
			enc.confirmPassword(excelPwd);
			encDataStream = enc.getDataStream(fs);
			
			if(hidden == 0) {
				wb.setSheetOrder("Data", 1);
				wb.setSheetHidden(1, true);
	
			}else{
				wb.setSheetOrder("Data", 0);
				wb.setSheetHidden(0, false);
			}
			
			wb.write(encDataStream);
			wb.close();
			encDataStream.close();
			
			filename = URLEncoder.encode(filename, "UTF-8");
			filename = filename.replace("+", "%20");	//파일명에 공백이 있으면 +가 되고 그 +를 공백으로 다시 변환
			
		    response.setHeader("Content-Disposition", "attachment;filename=" + filename + ";");
		    response.setHeader("Set-Cookie", "fileDownload=true; path=/");
			response.setDateHeader("Last-Modified", new Date().getTime());
						
			out = response.getOutputStream();
			fs.writeFilesystem(out);

		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if(null != out && !(wb instanceof SXSSFWorkbook)){
					out.close();
				}
				
				if(fs != null) {
					fs.close();
				}
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
		}
	}

	
	/**
	 * 
	 * Description  : 파일삭제 
	 * 최초 생성일  : 2014. 5. 20. : 오전 10:30:55
	 * file         : excelHandler.java 
	 * 페키지       : genexon.comm.excel
	 * RETURN       : void 
	 * @throws Exception

	 */
	public void closeExcel(){
		try {
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * 
	 * Description  : 워크북 리턴  
	 *
	 * 최초 생성일  : 2014. 5. 20. : 오전 10:30:58
	 * file         : excelHandler.java 
	 * 페키지       : genexon.comm.excel
	 * RETURN       : XSSFWorkbook 
	 * @return

	 */
	public Workbook getWb() {
	    return wb;
	}

	
	public boolean isNumeric(String str) {  
		try {
		    @SuppressWarnings("unused")
		    double d = Double.parseDouble(str);  
	    
	    }catch(NumberFormatException nfe) {
	    	return false;  
	    }
		
	    return true;
	}
	
	/**
	 * @desc 시트 추가 삭제
	 * @author KIMDONGUK
	 * @since 2019-03-28
	 * @param HttpServletRequest, HttpServletResponse, UploadExcelVO, FileVO
	 * @return ModelAndView
	 */
	public void addWorkSheet(String sheetName, ArrayList<String> titleList, ArrayList<String> fieldList) throws IllegalArgumentException, Exception {
		TITLE = titleList;
		FIELDS = fieldList;
		TILTE_YN = "Y";
		rowindex = 0;

		dataSheet = wb.createSheet(sheetName);
		
		monthFont = wb.createFont();
		monthFont.setFontHeightInPoints((short)12);
	    monthFont.setColor(IndexedColors.WHITE.getIndex());
		
	    style = wb.createCellStyle();
	    style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    style.setFont(monthFont);
	    
	    dataFormat = wb.createDataFormat();

		numStyle = wb.createCellStyle();
		numStyle.setDataFormat(dataFormat.getFormat("#,##0"));
		
		rateStyle = wb.createCellStyle();
		rateStyle.setDataFormat(dataFormat.getFormat("0.00%"));
	}
	
	/**
	 * @desc 데이터가 숫자일 수도 있으나 셀 타입이 String 타입이어야 하는 필드
	 * @param field
	 * @return
	 */
	public boolean cellTypeStringField(String field) {
		boolean yn = "pscd".equals(field) || "scd".equals(field) || field.indexOf("inspol_no") != -1 || field.indexOf("emp_cd") != -1 
					|| field.indexOf("hpno") != -1 || field.indexOf("telno") != -1 || "insco_emp_cd".equals(field)
					|| field.indexOf("bk_id") != -1 || field.indexOf("maid") != -1 || field.indexOf("carcode") != -1
					|| field.indexOf("mo_cd") != -1;
		return yn;
	}
	
	/**
	 * @desc 숫자형 데이터 중 #,###으로 나와야 하는 컬럼
	 * @param field
	 * @return
	 */
	public boolean cellTypeNumberField(String field) {
		boolean yn = (field.indexOf("amt") != -1 || field.indexOf("money") != -1 || field.indexOf("hwan") != -1 ||
					  field.indexOf("life_prod_kind2") != -1 || field.indexOf("mojibgo") != -1 || field.indexOf("usigo") != -1 ||
					  field.indexOf("sugum_resource") != -1 || field.indexOf("sugum_result") != -1
					 );
		
		return yn;
	}
	
	/**
	 * @desc 백분율(#.##%)로 나와야 하는 컬럼
	 * @param field
	 * @return
	 */
	public boolean cellTypeRateField(String field) {
		boolean yn = (field.indexOf("usi_rate") != -1 || field.indexOf("sugum_rate") != -1 || field.endsWith("rate")
					 );
		
		return yn;
	}
	
}
