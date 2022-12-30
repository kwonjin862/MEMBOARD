package com.icia.mbp.dto;

import lombok.Data;
import oracle.sql.DATE;

@Data
public class REPQUE {
	private int rqNum;
	private String rqTitle;
	private String rqContent;
	private DATE rqDate;
	private String rqHit;
}
