package com.icia.mbp.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.icia.mbp.dto.COMMENT;

@Repository
public class CDAO {
	
	@Autowired
	private SqlSessionTemplate sql;
	
	public List<COMMENT> cList(int cbNum) {
		
		return sql.selectList("Comment.cList", cbNum);
	}

	public int cmtWrite(COMMENT comment) {
		
		return sql.insert("Comment.cmtWrite", comment);
	}

	public int cmtDelete(COMMENT comment) {
		
		return sql.delete("Comment.cmtDelete",comment);
	}

	public int cmtModify(COMMENT comment) {
		
		return sql.update("Comment.cmtModify", comment);
	}

}
