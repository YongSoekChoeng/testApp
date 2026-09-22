/**
 * www.ITPSystem.net @ 2012, developed by Kim Hyun Wook
 * email:itpeople1@hotmail.com
 */
package kr.co.gnx.comm.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageVO {

	//------ PageVO Info
	/**
	 * 전체 레코드 건수
	 */
	private int totalCnt;
	/**
	 * 현재 페이지
	 */
	private int pageNo;
	/**
	 * 한 화면에 표시될 데이터의 줄 수
	 */
	private int lineCnt = 0;
	/**
	 * 한 화면에 표시될 링크 페이지 수
	 */
	private int pageCnt = 0;
	/**
	 * 화면에 보여질 시작 블록 번호
	 */
	private int preBlock;
	/**
	 * 화면에 보여질 마지막 블록 번호
	 */
	private int nextBlock;
	/**
	 * 처음 블록번호
	 */
	private int firstPage;
	/**
	 * 마지막 블록번호
	 */
	private int lastPage;
	/**
	 * 현재 블럭 번호
	 */
	private int nowBlock;
	/**
	 * 총 블럭 수
	 */
	private int totalBlock;
	/**
	 * 읽지 않은 게시물 수
	 */
	private int notReadCnt;

	// -----------------
	/**
	 * SQL 페이지 구분 rownum
	 */
	private int rownum;
	/**
	 * SQL 페이지 구분 rn
	 */
	private int rn;

	/**
	 * select box list 결과
	 */
	private List<Map<String, Object>> select = null;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Constructor
	 * @param page_no
	 */
	public PageVO(int page_no) {
		//--- init
		init();

		if (page_no > 0) {  this.pageNo = page_no;  }
	}

	public PageVO() {
		//--- init
		init();
	}

	// ----- Constructor
	/**
	 * Constructor
	 *
	 * @param page_no
	 * @param lineCnt
	 * @param pageCnt
	 * @throws Exception
	 */
	public PageVO(int page_no, int lineCnt, int pageCnt) throws Exception {
		//--- init
		init();

		if (page_no > 0) {  this.pageNo = page_no;  }
		if (lineCnt > 0) {  this.lineCnt = lineCnt; }
		if (pageCnt > 0) {  this.pageCnt = pageCnt;  }

	}

	/**
	 * init
	 * @return void
	 */
	private void init() {
		//---------------------------
		totalCnt = 0;
		pageNo = 1;
		lineCnt = 10;
		pageCnt = 10;
		preBlock = 1;
		nextBlock = 1;
		firstPage = 1;
		lastPage = 1;
		nowBlock = 1;
		totalBlock = 1;
	}

	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;

		pageSetting();
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getLineCnt() {
		return lineCnt;
	}

	public void setLineCnt(int lineCnt) {
		this.lineCnt = lineCnt;
	}

	public int getPageCnt() {
		return pageCnt;
	}

	public void setPageCnt(int pageCnt) {
		this.pageCnt = pageCnt;
	}

	public int getPreBlock() {
		return preBlock;
	}

	public void setPreBlock(int preBlock) {
		this.preBlock = preBlock;
	}

	public int getNextBlock() {
		return nextBlock;
	}

	public void setNextBlock(int nextBlock) {
		this.nextBlock = nextBlock;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getNowBlock() {
		return nowBlock;
	}

	public void setNowBlock(int nowBlock) {
		this.nowBlock = nowBlock;
	}

	public int getTotalBlock() {
		return totalBlock;
	}

	public void setTotalBlock(int totalBlock) {
		this.totalBlock = totalBlock;
	}

	public int getNotReadCnt() {
		return notReadCnt;
	}

	public void setNotReadCnt(int notReadCnt) {
		this.notReadCnt = notReadCnt;
	}

	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}

	public int getRn() {
		return rn;
	}

	public void setRn(int rn) {
		this.rn = rn;
	}

	public List<Map<String, Object>> getSelect() {
		return select;
	}

	public void setSelect(List<Map<String, Object>> select) {
		this.select = select;
	}

	private void pageSetting() {
		int totalPageNum = 0;
		// 총 페이지 수 구하기
		if ((totalCnt%lineCnt)!=0) {
			totalPageNum = totalCnt/lineCnt+1;
		} else {
			totalPageNum = totalCnt/lineCnt;
			if (totalCnt != 0 && pageNo > totalPageNum) pageNo = totalPageNum;
		}
		// 총 블럭 수 구하기
		if ((totalPageNum%pageCnt)!=0) {
			totalBlock = totalPageNum/pageCnt+1;
		} else {
			totalBlock = totalPageNum/pageCnt;
		}
		// 현재 블럭 구하기
		if ((pageNo%pageCnt)!= 0) {
			nowBlock = pageNo/pageCnt+1;
		} else {
			nowBlock = pageNo/pageCnt;
		}
		if (nowBlock >= totalBlock) {
			nextBlock = totalPageNum;
		} else {
			nextBlock = nowBlock * pageCnt;
		}
		preBlock = ((nowBlock - 1) * pageCnt) + 1;
		lastPage = totalPageNum;

		// -----------------------
		rownum = (pageNo - 1) * lineCnt + lineCnt;
		rn = (pageNo - 1) * lineCnt;

		int page_fr=0, page_to=0;

		select = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for(int blk=0; blk < totalBlock; blk++) {
			map = new HashMap<String, Object>();
			page_fr = (blk*pageCnt)+1;
			page_to = (blk*pageCnt)+pageCnt;

			map.put("value", String.valueOf(page_fr));
			map.put("string", page_fr + " ~ " + page_to);

			if (pageNo >= page_fr && pageNo <= page_to) map.put("selected", "selected");

			select.add(map);
		}
	}

	/**
	 * 총 데이터 건수를 구한다.
	 * @param connectionPool String
	 * @return void
	 */
	public int totalPageCount(String query, Connection conn) throws Exception {

		Statement stmt= conn.createStatement();
		query = "SELECT COUNT(*) CNT FROM ("+query+") A";

		if (logger.isDebugEnabled()) logger.debug("Count Query : " + query);

		ResultSet rs = stmt.executeQuery(query);

		if (rs.next()) { return rs.getInt(1);}
		else { return 0; }
	}

	public String wrappingPage(String sql_id) {

		return null;
	}

}
