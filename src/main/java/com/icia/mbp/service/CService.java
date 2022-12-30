package com.icia.mbp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icia.mbp.dao.CDAO;
import com.icia.mbp.dto.COMMENT;

@Service
public class CService {
	
	@Autowired
	private CDAO cdao;
	
	public List<COMMENT> cList(int cbNum) {
		
		List<COMMENT> commentList = cdao.cList(cbNum);
		
		return commentList;
	}

	public List<COMMENT> cmtWrite(COMMENT comment) {
		
		List<COMMENT> commentList;
		
		int result = cdao.cmtWrite(comment);
		
		if(result>0) {
			commentList = cdao.cList(comment.getCbNum());
		} else {
			commentList = null;
		}
		
		return commentList;
	}

	public List<COMMENT> cmtDelete(COMMENT comment) {
		List<COMMENT> commentList;
		
		int result = cdao.cmtDelete(comment);
		
		if(result>0) {
			commentList = cdao.cList(comment.getCbNum());
		} else {
			commentList = null;
		}
		
		return commentList;
	}

	public List<COMMENT> cmtModify(COMMENT comment) {
		List<COMMENT> commentList;
		
		int result = cdao.cmtModify(comment);
		
		if(result>0) {
			commentList = cdao.cList(comment.getCbNum());
		} else {
			commentList = null;
		}
		
		return commentList;
	}

}
