package kr.co.gnx.system.file;
import kr.co.gnx.base.BaseVO;

public class FileVO extends BaseVO{
	
	
	public FileVO() {
        super();
    }
	
	public FileVO(String FileNo){
        super();
        this.setFile_no(FileNo);
    }
    
    private String file_no = "";     // 파일번호-VARCHAR2 
    private String file_path = "";   // 파일저장(절대)경로 VARCHAR2 
    private String file_url = "";    // 파일URL(상대경로)  VARCHAR2
    private String file_nm  = "";    // 원본파일명-VARCHAR2 
    private String file_size = "";   // 파일사이즈-NUMBER 
    private String file_mime = "";   // MIME 타입-VARCHAR2 
    private String file_format = ""; // 파일 확장자-VARCHAR2
    private String doc_gbn = "";  	 // 문서구분(사진, 주민등록증...)
    
    private String board_no;		// 게시판구분 게시판번호
    
    private String charset;			// 파일의 캐릭터셋
    
    private String thumbnail;
    
	public String getFile_no() {
		return file_no;
	}

	public void setFile_no(String file_no) {
		this.file_no = file_no;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getFile_url() {
		return file_url;
	}

	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}

	public String getFile_nm() {
		return file_nm;
	}

	public void setFile_nm(String file_nm) {
		this.file_nm = file_nm;
	}

	public String getFile_size() {
		return file_size;
	}

	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}

	public String getFile_mime() {
		return file_mime;
	}

	public void setFile_mime(String file_mime) {
		this.file_mime = file_mime;
	}

	public String getFile_format() {
		return file_format;
	}

	public void setFile_format(String file_format) {
		this.file_format = file_format;
	}

	public String getBoard_no() {
		return board_no;
	}

	public void setBoard_no(String board_no) {
		this.board_no = board_no;
	}

	public String getDoc_gbn() {
		return doc_gbn;
	}

	public void setDoc_gbn(String doc_gbn) {
		this.doc_gbn = doc_gbn;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	@Override
	public String toString() {
		return "FileVO [file_no=" + file_no + ", file_path=" + file_path + ", file_url=" + file_url + ", file_nm="
				+ file_nm + ", file_size=" + file_size + ", file_mime=" + file_mime + ", file_format=" + file_format
				+ ", doc_gbn=" + doc_gbn + ", board_no=" + board_no + ", charset=" + charset + ", thumbnail="
				+ thumbnail + "]";
	}
    
}
