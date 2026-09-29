package kr.co.gnx.sample.board;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.co.gnx.base.BaseDAO;

@Repository(value="SampleBoardDAO")
public class SampleBoardDAO extends BaseDAO {

	private static final String sampleBoardMapper = "SampleBoard.";	//sampleboard-mapper.xml

	public List<Map<String, Object>> selectSampleBoardList(SampleBoardVO vo) {
		return getSqlSession().selectList(sampleBoardMapper + "selectSampleBoardList", vo);
	}

	public Map<String, Object> selectSampleBoardView(SampleBoardVO vo) {
		return getSqlSession().selectOne(sampleBoardMapper + "selectSampleBoardView", vo);
	}

	public int insertSampleBoard(SampleBoardVO vo) {
		return getSqlSession().insert(sampleBoardMapper + "insertSampleBoard", vo);
	}

	public int updateSampleBoard(SampleBoardVO vo) {
		return getSqlSession().update(sampleBoardMapper + "updateSampleBoard", vo);
	}

	public int deleteSampleBoard(SampleBoardVO vo) {
		return getSqlSession().delete(sampleBoardMapper + "deleteSampleBoard", vo);
	}
}
