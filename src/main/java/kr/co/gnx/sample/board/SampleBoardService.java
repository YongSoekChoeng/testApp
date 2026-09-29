package kr.co.gnx.sample.board;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.gnx.base.BaseService;

/**
 * 샘플게시판 서비스
 * 이름이 *Service 라서 context-transaction.xml 포인트컷에 걸린다 - 메서드 하나가 트랜잭션 하나.
 * 여러 건 저장 중 한 건이라도 실패하면 전부 롤백되는지도 이 화면으로 확인할 수 있다.
 */
@Service
public class SampleBoardService extends BaseService {

	@Autowired
	private SampleBoardDAO sampleBoardDAO;

	public List<Map<String, Object>> getSampleBoardList(SampleBoardVO vo) {
		return sampleBoardDAO.selectSampleBoardList(vo);
	}

	public Map<String, Object> getSampleBoardView(SampleBoardVO vo) {
		return sampleBoardDAO.selectSampleBoardView(vo);
	}

	public int insertSampleBoard(List<SampleBoardVO> models) {
		int cnt = 0;
		for(SampleBoardVO vo : models) {
			cnt += sampleBoardDAO.insertSampleBoard(vo);
		}
		return cnt;
	}

	public int updateSampleBoard(List<SampleBoardVO> models) {
		int cnt = 0;
		for(SampleBoardVO vo : models) {
			cnt += sampleBoardDAO.updateSampleBoard(vo);
		}
		return cnt;
	}

	public int deleteSampleBoard(List<SampleBoardVO> models) {
		int cnt = 0;
		for(SampleBoardVO vo : models) {
			cnt += sampleBoardDAO.deleteSampleBoard(vo);
		}
		return cnt;
	}
}
