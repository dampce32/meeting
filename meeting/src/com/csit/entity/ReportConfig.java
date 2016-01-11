package com.csit.entity;

import java.io.Serializable;


/**
 * @Description: 报表配置
 * @Copyright: 福州骏华科技信息有限公司 (c)2012
 * @Created Date : 2013-10-27下午3:35:13
 * @author lhy
 * @vesion 1.0
 */
public class ReportConfig implements Serializable{
	// Fields
	private static final long serialVersionUID = 1483112986622755080L;
	/**
	 * 报表编号
	 */
	private String reportCode;
	/**
	 * 报表名称
	 */
	private String reportName;
	/**
	 * 报表明细网格Sql
	 */
	private String reportDetailSql;
	/**
	 * 报表参数Sql
	 */
	private String reportParamsSql;
	public String getReportCode() {
		return reportCode;
	}
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getReportDetailSql() {
		return reportDetailSql;
	}
	public void setReportDetailSql(String reportDetailSql) {
		this.reportDetailSql = reportDetailSql;
	}
	public String getReportParamsSql() {
		return reportParamsSql;
	}
	public void setReportParamsSql(String reportParamsSql) {
		this.reportParamsSql = reportParamsSql;
	}
	
}