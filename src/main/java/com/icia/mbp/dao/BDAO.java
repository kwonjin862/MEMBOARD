package com.icia.mbp.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.icia.mbp.dto.BOARD;
import com.icia.mbp.dto.PAGE;
import com.icia.mbp.dto.SEARCH;

@Repository
public class BDAO {
	
	@Autowired
	SqlSessionTemplate sql;
	
	public int bWrite(BOARD board) {
		return sql.insert("Board.bWrite", board);
	}
	
	public int bCount() {
		return sql.selectOne("Board.bCount");
	}

	public List<BOARD> bList(PAGE paging) {
		return sql.selectList("Board.bList", paging);
	}

	public BOARD bView(int boNum) {
		System.out.println("[3] service → dao \n boNum : " + boNum);
		
		// 조회수 증가
		sql.update("Board.bHit", boNum);
		
		// 게시글 정보 가져가기
		return sql.selectOne("Board.bView", boNum);
	}

	public int bDelete(int boNum) {
		System.out.println("[3] service → dao \n bNum : " + boNum);
		return sql.delete("Board.bDelete", boNum);
	}

	public int bModify(BOARD board) {
		System.out.println("[3] service → dao \n board : " + board);
		return sql.update("Board.bModify", board);
	}

	public List<BOARD> bSearch(SEARCH search) {
		return sql.selectList("Board.bSearch", search);
	}
}
