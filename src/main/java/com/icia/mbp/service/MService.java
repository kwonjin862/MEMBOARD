package com.icia.mbp.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.icia.mbp.dao.MDAO;
import com.icia.mbp.dto.MEMBER;
import com.icia.mbp.dto.PAGE;
import com.icia.mbp.dto.SEARCH;

@Service
public class MService {

	private ModelAndView mav = new ModelAndView();

	@Autowired
	private MDAO mdao;

	@Autowired
	private HttpSession session;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private BCryptPasswordEncoder pwEnc;
	
	@Autowired
	private JavaMailSender mailSender;
	


	public ModelAndView idCheck(String memId) {

		String checkId = mdao.idCheck(memId);

		if (checkId == null) {
			// 아이디 사용가능
			mav.setViewName("M_Join1");
			mav.addObject("checkId", memId);
		} else {
			// 이미 사용중인 아이디
			mav.setViewName("M_Join2");
			mav.addObject("checkId", memId);
		}

		return mav;
	}

	public ModelAndView mJoin(MEMBER member) throws IllegalStateException, IOException {
		System.out.println("암호화 전 : " + member);
		// [1] 파일업로드
		// - 파일 이름 생성
		// - (식별자 uuid + 파일원래 이름)
		// - profile폴더 생성 후 업로드

		MultipartFile memProfile = member.getMemProfile();

		UUID uuid = UUID.randomUUID();

		String profileName = uuid.toString().substring(0, 8) + "_" + memProfile.getOriginalFilename();

		String savePath = "D:/spring/MEMBOARD/src/main/webapp/resources/profile/";
		// String savePath = request.getSession().getServletContext().getRealPath("/resources/profile/");

		if (!memProfile.isEmpty()) {
			memProfile.transferTo(new File(savePath + profileName));
			member.setMemProfileName(profileName);
		}

		// [2] 주소 결합
		// - (addr1) + addr2 + addr3
		// ex) (22223) 인천 미추홀구 매소홀로488번길 6-32, 태승빌딩 4층 // 과 같이 출력
		// - setter 사용해서 memAddr에 저장하기

		String memAddr = "(" + member.getAddr1() + ") " + member.getAddr2() + ", " + member.getAddr3();
		member.setMemAddr(memAddr);

		// 비밀번호 암호화
		member.setMemPw(pwEnc.encode(member.getMemPw()));

		System.out.println("암호화 후 : " + member);

		

		// 가입

		int result = mdao.mJoin(member);

		if (result > 0) {
			mav.setViewName("M_Login");
		} else {
			mav.setViewName("M_Join");
		}

		return mav;
	}

	public ModelAndView mLogin(MEMBER member) {

		String encPw = mdao.mLogin(member);

		if (pwEnc.matches(member.getMemPw(), encPw)) {
			session.setAttribute("loginId", member.getMemId());
			mav.setViewName("index");
		} else {
			mav.setViewName("M_Login");
		}

		return mav;
	}

	public ModelAndView mList(int page) {
		
		// 페이지번호 갯수
		int block = 5;
		
		// 회원목록 갯수
		int limit = 5;
		
		// 전체회원수
		int mCount = mdao.mCount();
		
		int maxPage = (int) (Math.ceil((double)mCount / limit));
		
		// 현재 페이지가 maxPage보다 클경우 현재 페이지를 maxPage의 값으로 대체
		if(page > maxPage) {
			page = maxPage;
		}
		
		int startRow = (page - 1) * limit + 1;
		int endRow = page * limit;
		
		int startPage = (((int)(Math.ceil((double) page / block ))) - 1) * block + 1;
		int endPage = startPage + block - 1;
		
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		PAGE paging = new PAGE();
		
		paging.setPage(page);
		paging.setStartRow(startRow);
		paging.setEndRow(endRow);
		paging.setMaxPage(maxPage);
		paging.setStartPage(startPage);
		paging.setEndPage(endPage);
		paging.setLimit(limit);
		
		List<MEMBER> memberList = mdao.mList(paging);
		
		mav.addObject("memberList", memberList);
		mav.addObject("paging", paging);
		
		mav.setViewName("M_List");
		
		
		return mav;
	}

	public ModelAndView mSearch(SEARCH search) {
		
		List<MEMBER> memberList = mdao.mSearch(search);
		
		mav.addObject("memberList", memberList);
		mav.setViewName("S_MList");
		
		return mav;
	}

	public ModelAndView mView(String memId) {
		
		MEMBER member = mdao.mView(memId);
		
		mav.addObject("member", member);
		mav.setViewName("M_View");
		
		return mav;
	}

	public ModelAndView mModiForm(String memId) {
		MEMBER member = mdao.mView(memId);
		
		mav.addObject("member", member);
		mav.setViewName("M_Modify");
		
		return mav;
	}

	public ModelAndView mModify(MEMBER member) throws IllegalStateException, IOException {
		
		MultipartFile memProfile = member.getMemProfile();

		UUID uuid = UUID.randomUUID();

		String profileName = uuid.toString().substring(0, 8) + "_" + memProfile.getOriginalFilename();

		String savePath = "D:/spring/MEMBOARD/src/main/webapp/resources/profile/";
		// String savePath = request.getSession().getServletContext().getRealPath("/resources/profile/");

		
		
		
		if (!memProfile.isEmpty()) {
			// 기존 파일 삭제
			String deletePath = "D:/spring/MEMBOARD/src/main/webapp/resources/profile/"
					+ member.getMemProfileName();
			
			File deleteFile = new File(deletePath);
			
			if(deleteFile.exists()) {
				deleteFile.delete();
				System.out.println("기존 파일 삭제 성공!");
			} else {
				System.out.println("기존 파일 삭제 실패!");
			}
			
			memProfile.transferTo(new File(savePath + profileName));
			member.setMemProfileName(profileName);
		}

		// [2] 주소 결합
		// - (addr1) + addr2 + addr3
		// ex) (22223) 인천 미추홀구 매소홀로488번길 6-32, 태승빌딩 4층 // 과 같이 출력
		// - setter 사용해서 memAddr에 저장하기

		String memAddr = "(" + member.getAddr1() + ") " + member.getAddr2() + ", " + member.getAddr3();
		member.setMemAddr(memAddr);

		// 비밀번호 암호화
		member.setMemPw(pwEnc.encode(member.getMemPw()));

		System.out.println("암호화 후 : " + member);

		

		// 수정

		int result = mdao.mModify(member);

		if (result > 0) {
			mav.setViewName("redirect:/mView?memId=" + member.getMemId());
		} else {
			mav.setViewName("index");
		}

		return mav;
	}

	public ModelAndView mDelete(String memId) {
		
		int result = mdao.mDelete(memId);
		
		if(result>0) {
			mav.setViewName("redirect:/mList");
		} else {
			mav.setViewName("index");
		}
		
		return mav;
	}

	public String mCheckEmail(String memEmail) {
		String uuid = UUID.randomUUID().toString().substring(0, 6);
		
		MimeMessage mail = mailSender.createMimeMessage();
		
		String mailContent = "<h2>안녕하세요. 인천일보 아카데미입니다.</h2><br/>"
				+ "<h3>인증번호는 "+ uuid + "입니다.</h3>";
		
		try {
			mail.setSubject("[이메일 인증] 인천일보 아카데미 이메일 인증", "UTF-8");
			mail.setText(mailContent,"UTF-8", "html");
			mail.addRecipient(RecipientType.TO, new InternetAddress(memEmail));
			
			mailSender.send(mail);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
		return uuid;
	}
	
	public String idoverlap(String memId) {
		
		String checkId = mdao.idCheck(memId);
		String result;

		if (checkId == null) {
			// 아이디 사용가능
			result = "OK";
		} else {
			// 이미 사용중인 아이디
			result = "NO";
		}
		
		return result;
	}
}
