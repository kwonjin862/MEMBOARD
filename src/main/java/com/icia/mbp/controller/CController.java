package com.icia.mbp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icia.mbp.dto.COMMENT;
import com.icia.mbp.service.CService;

@Controller
@RequestMapping("/comment")
public class CController {
	
	@Autowired
	private CService csvc;
	
	@RequestMapping(value="/commentList", method = RequestMethod.POST)
	public @ResponseBody List<COMMENT> cList(@RequestParam("cbNum") int cbNum ){
		
		List<COMMENT> commentList = csvc.cList(cbNum);
		
		return commentList;
	}
	
	@RequestMapping(value="/cmtWrite", method = RequestMethod.POST)
	public @ResponseBody List<COMMENT> cmtWrite(@ModelAttribute COMMENT comment){
		
		List<COMMENT> commentList = csvc.cmtWrite(comment);
		
		return commentList;
	}
	
	@RequestMapping(value="/cmtDelete", method = RequestMethod.POST)
	public @ResponseBody List<COMMENT> cmtDelete(@ModelAttribute COMMENT comment){
		
		List<COMMENT> commentList = csvc.cmtDelete(comment);
		
		return commentList;
	}
	
	@RequestMapping(value="/cmtModify", method = RequestMethod.POST)
	public @ResponseBody List<COMMENT> cmtModify(@ModelAttribute COMMENT comment){
		
		List<COMMENT> commentList = csvc.cmtModify(comment);
		
		return commentList;
	}
}
