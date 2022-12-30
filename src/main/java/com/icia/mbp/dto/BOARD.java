package com.icia.mbp.dto;


import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BOARD {
	// 게시판 필드
	private int boNum;			// 게시글 번호
	private String boWriter;		// 작성자
	private String boTitle;		// 글제목
	private String boContent;	// 글내용
	private Date boDate;			// 작성일
	private int boHit;			// 조회수
	
	private MultipartFile boFile;	// 업로드 파일
	private String	boFileName;		// 파일이름
	
	
}
