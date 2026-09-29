package kr.co.gnx.sample.board;

import kr.co.gnx.base.BaseVO;

/**
 * 샘플게시판 VO
 * seq / mb_id / use_yn / in_emp_cd / in_dtm / up_emp_cd / up_dtm / search_word / json_string 은 BaseVO에 있다.
 */
public class SampleBoardVO extends BaseVO {

	private String title;		/* 제목 */
	private String content;		/* 내용 */
	private String writer_nm;	/* 작성자명 */

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter_nm() {
		return writer_nm;
	}
	public void setWriter_nm(String writer_nm) {
		this.writer_nm = writer_nm;
	}
}
