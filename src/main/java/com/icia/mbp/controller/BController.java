package com.icia.mbp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.icia.mbp.dto.BOARD;
import com.icia.mbp.dto.SEARCH;
import com.icia.mbp.service.BService;


@Controller
public class BController {
	
	// ModelAndView 객체 생성
	private ModelAndView mav = new ModelAndView();
	
	// Service에 연결
	@Autowired
	private BService bsvc;
		
	
	// bWriteForm : 게시글 작성 페이지로 이동 → Bwrite.jsp
		@RequestMapping(value = "/bWriteForm", method = RequestMethod.GET)
		public String bWriteForm(@RequestParam("loginId") String loginId) {
			return "B_Write";
		}
		// bWrite : 게시글 등록
		@RequestMapping(value = "/bWrite", method = RequestMethod.POST)
		public ModelAndView boWrite(@ModelAttribute BOARD board) throws IllegalStateException, IOException {
			//System.out.println("[1] jsp → controller \n board : " + board);
			mav = bsvc.bWrite(board);
			
			//System.out.println("[5] service → controller \n mav : " + mav);
			return mav;
		}
		
		@RequestMapping(value="bList", method = RequestMethod.GET)
		public ModelAndView bList(@RequestParam(value="page", required=false, defaultValue="1") int page) {
			

			System.out.println("[1] jsp → controller \n page : " + page);
			mav = bsvc.bList(page);
			
			System.out.println("[5] service → controller \n mav : " + mav);
			return mav;
		}
		
		@RequestMapping(value="bView", method = RequestMethod.GET)
		public ModelAndView bView(@RequestParam("boNum") int boNum) {
			System.out.println("[1] jsp → controller \n bNum : " + boNum);
			mav = bsvc.bView(boNum);
			
			System.out.println("[5] service → controller \n mav : " + mav);
			return mav;
		}
		
		@RequestMapping(value="bModifyForm", method = RequestMethod.GET)
		public ModelAndView bModifyForm(@RequestParam("boNum") int boNum) {
			System.out.println("[1] jsp → controller \n bNum : " + boNum);
			mav = bsvc.bModifyForm(boNum);
			
			System.out.println("[5] service → controller \n mav : " + mav);
			return mav;
		}
		
		// bModify : 게시글 수정
		@RequestMapping(value = "/bModify", method = RequestMethod.POST)
		public ModelAndView bModify(@ModelAttribute BOARD board) throws IllegalStateException, IOException {
			System.out.println("[1] jsp → controller \n board : " + board);
			mav = bsvc.bModify(board);
			
			System.out.println("[5] service → controller \n mav : " + mav);
			return mav;
		}
		
		// bDelete : 게시글 삭제
		@RequestMapping(value="/bDelete", method = RequestMethod.GET)
		public ModelAndView bDelete(@RequestParam(value="boNum", required=true) int boNum) {
			System.out.println("[1] jsp → controller \n bNum : " + boNum);
			mav = bsvc.bDelete(boNum);
			
			System.out.println("[5] service → controller \n mav : " + mav);
			return mav;
		}
		
		// bSearch : 게시글 검색
		@RequestMapping(value = "/bSearch", method = RequestMethod.GET)
		public ModelAndView bSearch(@ModelAttribute SEARCH search) {
			mav = bsvc.bSearch(search);
			return mav;
		}
}
