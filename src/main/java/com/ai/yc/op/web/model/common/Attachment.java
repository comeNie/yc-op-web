package com.ai.yc.op.web.model.common;

import java.io.Serializable;

public class Attachment implements Serializable{
	
	private static final long serialVersionUID = 1130151603761530029L;

	/**
	 * 文件名称
	 */
	private String fileName;
	
	/**
	 * 文件ID
	 */
	private String fileId;
	
	/**
	 * 文件大小
	 */
	private long size;
	
	

	public Attachment() {
		super();
	}

	public Attachment(String fileName, String fileId, long size) {
		super();
		this.fileName = fileName;
		this.fileId = fileId;
		this.size = size;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	
	

}
