package com.icia.mbp.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.icia.mbp.dao.BDAO;
import com.icia.mbp.dto.BOARD;
import com.icia.mbp.dto.PAGE;
import com.icia.mbp.dto.SEARCH;

@Service
public class BService {
	
	// ModelAndView 객체 mav 생성
		private ModelAndView mav = new ModelAndView();

		// DAO로 연결
		@Autowired
		private BDAO bdao;
	

	public ModelAndView bWrite(BOARD board) throws IllegalStateException, IOException {
		// System.out.println("[2] controller → service \n board : " + board);

				// (1) 파일정보 가져오기
				// MultipartFile bFile = board.getbFile();
				MultipartFile boFile = board.getBoFile();

				// (2) 파일이름 설정하기
				UUID uuid = UUID.randomUUID();

				// 만약에 업로드 파일이 없다면
				if (boFile != null) {

					// 랜덤한 식별 문자 uuid.toString().substring(0,8)과 실제 파일이름을 합친 것
					String fileName = uuid.toString().substring(0, 8) + "_" + boFile.getOriginalFilename();

					// board객체에 생성한 업로드한 파일 이름을 저장한다
					board.setBoFileName(fileName);

					// 저장 경로에 파일을 업로드한다.
					String savePath = "D:/spring/MEMBOARD/src/main/webapp/resources/fileUpload/" + fileName;
					boFile.transferTo(new File(savePath));

				}

				int result = bdao.bWrite(board);

				// System.out.println("[4] dao → service \n result : " + result);

				if (result > 0) {
					mav.setViewName("index");
				} else {
					mav.setViewName("B_Write");
				}

				return mav;
	}


	public ModelAndView bList(int page) {
		System.out.println("[2] controller → service \n page : " + page);
		
		// 한 화면에 보여줄 페이지 번호 갯수
		int block = 5;
		
		// 한 화면에 보여줄 게시글 갯수
		int limit = 5;
		
		// 전체 게시글 갯수 : 7
		int bCount = bdao.bCount();
		
		
		int startRow = (page - 1) * limit + 1;
		int endRow = page * limit;
		
		int maxPage = (int)(Math.ceil((double) bCount / limit ));
		int startPage = (((int)(Math.ceil((double) page / block))) - 1) * block + 1;
		int endPage = startPage + block - 1;
		
		if(endPage < maxPage) {
			endPage = maxPage;
		}
		
		// 페이징 객체 생성
		PAGE paging = new PAGE();
		
		paging.setPage(page);
		paging.setStartRow(startRow);
		paging.setEndRow(endRow);
		paging.setMaxPage(maxPage);
		paging.setStartPage(startPage);
		paging.setEndPage(endPage);
		paging.setLimit(limit);
		
		List<BOARD> pagingList = bdao.bList(paging);
		
		// model
		mav.addObject("pagingList", pagingList);
		mav.addObject("paging", paging);
		
		// view
		mav.setViewName("B_List");
		
		return mav;
	}


	public ModelAndView bView(int boNum) {
		System.out.println("[2] controller → service \n bNum : " + boNum);
		BOARD board = bdao.bView(boNum);

		System.out.println("[4] dao → service \n board : " + board);

		// M
		mav.addObject("view", board);

		// V
		mav.setViewName("B_View");

		return mav;
	}


	public ModelAndView bModifyForm(int boNum) {
		System.out.println("[2] controller → service \n bNum : " + boNum);
		BOARD board = bdao.bView(boNum);

		System.out.println("[4] dao → service \n board : " + board);

		// M
		mav.addObject("modify", board);

		// V
		mav.setViewName("B_Modify");

		return mav;
	}


	public ModelAndView bDelete(int boNum) {
		System.out.println("[2] controller → service \n bNum : " + boNum);
		int result = bdao.bDelete(boNum);
		
		if(result > 0) {
			mav.setViewName("redirect:/bList");
		} else {
			mav.setViewName("redirect:/bView?boNum=" + boNum);
		}

		return mav;
	}


	public ModelAndView bModify(BOARD board) throws IllegalStateException, IOException {
System.out.println("[2] controller → service \n board : " + board);
		
		// (1) 파일정보 가져오기
		MultipartFile bFile = board.getBoFile();
		
		System.out.println("파일 이름 확인 : " + bFile.getOriginalFilename());

		// (2) 파일이름 설정하기
		UUID uuid = UUID.randomUUID();

		// 만약에 업로드 파일이 없다면
		if (!bFile.getOriginalFilename().isEmpty()) {
			
			String deletePath = "D:/spring/MEMBOARD/src/main/webapp/resources/fileUpload/" + board.getBoFileName();
			File deleteFile = new File(deletePath);
			
			if(deleteFile.exists()) {
				deleteFile.delete();
				System.out.println("기존 파일 삭제 성공!");
			} else {
				System.out.println("파일존재x, 삭제 실패!");
			}

			// 랜덤한 식별 문자 uuid.toString().substring(0,8)과 실제 파일이름을 합친 것
			String fileName = uuid.toString().substring(0, 8) + "_" + bFile.getOriginalFilename();

			// board객체에 생성한 업로드한 파일 이름을 저장한다
			board.setBoFileName(fileName);

			// 저장 경로에 파일을 업로드한다.
			String savePath = "D:/spring/MEMBOARD/src/main/webapp/resources/fileUpload/" + fileName;
			bFile.transferTo(new File(savePath));

			
		}

		int result = bdao.bModify(board);

		System.out.println("[4] dao → service \n result : " + result);

		if (result > 0) {
			mav.setViewName("redirect:/bView?boNum="+board.getBoNum());
		} else {
			mav.setViewName("redirect:/bList");
		}

		return mav;
	}


	public ModelAndView bSearch(SEARCH search) {

		List<BOARD> boardList = bdao.bSearch(search);
		
		mav.addObject("boardList", boardList);
		mav.setViewName("S_BList");
		
		return mav;
	}

}
