package kr.co.gnx.system.file;
/*
 * Copyright GENEXON (c) 2014.
 */

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import kr.co.gnx.base.BaseDAO;


/**
 * 첨부파일정보 DAO
 */
@Repository(value = "FileDAO")
public class FileDAO extends BaseDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(FileDAO.class);

	/**
	 * @Description  : 첨부파일정보목록 조회
	 * @author       : lakhyun.kim
	 * @since        : 2018. 07. 10
	 * @return       : List<FileVO>
	 */
	public List<FileVO> selectFileInfoList(FileVO fileVO){
		List<FileVO> resultList = new ArrayList<FileVO>();
		resultList = getSqlSession().selectList(getFilemapper() + "selectFileInfoList", fileVO);
		return resultList;
	}

	/**
	 * @Description  : 첨부파일정보목록 상세 조회
	 * @author       : lakhyun.kim
	 * @since        : 2019. 03. 12
	 * @return       : FileVO
	 */
	public FileVO selectFileInfoView(FileVO fileVO){
		FileVO resultObject = new FileVO();
		resultObject = getSqlSession().selectOne(getFilemapper() + "selectFileInfoView", fileVO);
		return resultObject;
	}
	
	/**
	 * @brief 첨부파일정보 중복조회
	 * @param FileVO
	 * @return int
	 */
	public int selectFileInfoCount(FileVO fileVO){
		int resultInt = 0;
		resultInt = getSqlSession().selectOne(getFilemapper() + "selectFileInfoCount", fileVO);
		return resultInt;
	}
	
	/**
	 * @brief 첨부파일정보 입력
	 * @param FileVO
	 * @return int
	 */
	public int insertFileInfo(FileVO fileVO){
		int resultInt = 0;
		resultInt = getSqlSession().insert(getFilemapper() + "insertFileInfo", fileVO);
		return resultInt;
	}
	
	/**
	 * @brief 첨부파일정보 수정
	 * @param FileVO
	 * @return int
	 */
	public int updateFileInfo(FileVO fileVO){
		int resultInt = 0;
		resultInt = getSqlSession().update(getFilemapper() + "updateFileInfo", fileVO);
		return resultInt;
	}
	
	/**
	 * @brief 첨부파일정보 삭제
	 * @param FileVO
	 * @return int
	 */
	public int deleteFileInfo(FileVO fileVO){
		int resultInt = 0;
		resultInt = getSqlSession().update(getFilemapper() + "deleteFileInfo", fileVO);
		return resultInt;
	}
	
	/**
	 * @brief 첨부파일정보 삭제
	 * @param FileVO
	 * @return int
	 */
	public int deleteFileInfoByRefIdx(FileVO fileVO){
		int resultInt = 0;
		resultInt = getSqlSession().update(getFilemapper() + "deleteFileInfoByRefIdx", fileVO);
		return resultInt;
	}
	
	/**
	 * @brief 게시판 첨부파일정보 복사
	 * @param FileVO
	 * @return int
	 */
	public int insertFileInfoCopy(FileVO fileVO){
		int resultInt = 0;
		resultInt = getSqlSession().insert(getFilemapper() + "insertFileInfoCopy", fileVO);
		return resultInt;
	}

	/**
	 * @brief 게시물이동시 파일도 이동
	 * @param FileVO
	 * @return int
	 */
	public int updateBoardFileInfo(FileVO fileVO){
		int resultInt = 0;
		resultInt = getSqlSession().update(getFilemapper() + "updateBoardFileInfo", fileVO);
		return resultInt;
	}

	/**
	 * @Description  : 임시파일정보 조회
	 * @author       : lakhyun.kim
	 * @since        : 2019. 09. 04
	 * @return       : int
	 */
	public List<FileVO> selectTempFileInfoList(FileVO fileVO){
		List<FileVO> resultList = new ArrayList<FileVO>();
		resultList = getSqlSession().selectList(getFilemapper() + "selectTempFileInfoList", fileVO);
		return resultList;
	}

	/**
	 * @Description  : 임시파일정보 삭제
	 * @author       : lakhyun.kim
	 * @since        : 2019. 09. 04
	 * @return       : int
	 */
	public int deleteTempFileInfo(FileVO fileVO){
		int resultInt = 0;
		resultInt = getSqlSession().delete(getFilemapper() + "deleteTempFileInfo", fileVO);
		return resultInt;
	}
	
	/**
	 * @Description  : 임시파일정보 수정
	 * @author       : lakhyun.kim
	 * @since        : 2019. 09. 04
	 * @return       : int
	 */
	public int updateTempFileInfo(FileVO fileVO){
		int resultInt = 0;
		resultInt = getSqlSession().update(getFilemapper() + "updateTempFileInfo", fileVO);
		return resultInt;
	}
	
}
