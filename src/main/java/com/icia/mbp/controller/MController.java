package com.icia.mbp.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.icia.mbp.dto.MEMBER;
import com.icia.mbp.dto.SEARCH;
import com.icia.mbp.service.MService;

@Controller
public class MController {

	private ModelAndView mav = new ModelAndView();

	@Autowired
	private MService msvc;

	@Autowired
	private HttpSession session;
	
	// 프로젝트 시작 페이지
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
	}
		// 프로젝트 홈페이지
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index1() {
		return "index";
	}

	// mJoinForm : 회원가입 페이지
	@RequestMapping(value = "/mJoinForm", method = RequestMethod.GET)
	public String mJoinForm() {
		return "M_Join";
	}

	// mLoginForm : 로그인 페이지
	@RequestMapping(value = "/mLoginForm", method = RequestMethod.GET)
	public String mLoginForm() {
		return "M_Login";
	}

	// idCheck : 아이디 중복체크
	@RequestMapping(value = "/idCheck", method = RequestMethod.GET)
	public ModelAndView idCheck(@RequestParam("memId") String memId) {
		mav = msvc.idCheck(memId);
		return mav;
	}

	// mJoin : 회원가입
	@RequestMapping(value = "/mJoin", method = RequestMethod.POST)
	public ModelAndView mJoin(@ModelAttribute MEMBER member) throws IllegalStateException, IOException {
		mav = msvc.mJoin(member);
		return mav;
	}

	// mLogin : 로그인
	@RequestMapping(value = "/mLogin", method = RequestMethod.POST)
	public ModelAndView mLogin(@ModelAttribute MEMBER member) {
		mav = msvc.mLogin(member);
		return mav;
	}
	
	@RequestMapping(value = "/mLogout", method = RequestMethod.GET)
	public String mLogout() {
		session.invalidate();
		return "index";
	}
	
	// mList : 회원목록(페이징)
	@RequestMapping(value = "/mList", method = RequestMethod.GET)
	public ModelAndView mList(@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
		mav = msvc.mList(page);
		return mav;
	}

	// mSearch : 회원검색
	@RequestMapping(value = "/mSearch", method = RequestMethod.GET)
	public ModelAndView mSearch(@ModelAttribute SEARCH search) {
		mav = msvc.mSearch(search);
		return mav;
	}

	// mView : 회원상세보기
	@RequestMapping(value = "/mView", method = RequestMethod.GET)
	public ModelAndView mView(@RequestParam("memId") String memId) {
		mav = msvc.mView(memId);
		return mav;
	}

	// mModiForm : 회원수정 페이지
	@RequestMapping(value = "/mModiForm", method = RequestMethod.GET)
	public ModelAndView mModiForm(@RequestParam("memId") String memId) {
		mav = msvc.mModiForm(memId);
		return mav;
	}

	// mModify : 회원수정
	@RequestMapping(value = "/mModify", method = RequestMethod.POST)
	public ModelAndView mModify(@ModelAttribute MEMBER member) throws IllegalStateException, IOException {
		mav = msvc.mModify(member);
		return mav;
	}
	
	// mDelete : 회원삭제
	@RequestMapping(value = "/mDelete", method = RequestMethod.GET)
	public ModelAndView mDelete(@RequestParam("memId") String memId) {
		mav = msvc.mDelete(memId);
		return mav;
	}
	
	@RequestMapping(value = "/mCheckEmail", method = RequestMethod.POST)
	public @ResponseBody String mCheckEmail(@RequestParam("memEmail") String memEmail) {
		String uuid = msvc.mCheckEmail(memEmail);
		return uuid;
	}
	
	// idoverlap : 아이디 중복체크 ajax
		@RequestMapping(value = "/idoverlap", method = RequestMethod.POST)
		public @ResponseBody String idoverlap(@RequestParam("memId") String memId) {
			System.out.println("ajax로 넘어온 memId : " + memId);
			String result = msvc.idoverlap(memId);
			
			System.out.println("db에서 확인한 result메세지 : " + result);
			return result;
		}
}
