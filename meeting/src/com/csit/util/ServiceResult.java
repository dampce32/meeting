package com.csit.util;

import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Description: 服务层返回的结果
 * @Copyright: 福州凌智信息有限公司 (c)2011
 * @Created Date : Jul 9, 2011
 * @author longweier
 * @vesion 1.0
 */
public class ServiceResult {

	// 常见异常
	public static final int ECODE_EXCEPTION = 1;
	// 参数错误
	public static final int ECODE_ARGUMENT_ERROR = 2;
	// 数据库错
	public static final int ECODE_DB_ERROR = 3;
	// 对象不存
	public static final int ECODE_NOT_OBJECT = 4;
	// 没有权限
	public static final int ECODE_NOT_AUTH = 5;
	// 未知
	public static final int ECODE_UNKNOWN = 999;

	public static final String PROP_SUCCESS = "success";
	public static final String PROP_ERRCODE = "errcode";
	public static final String PROP_ERROR = "error";
	public static final String PROP_ERRORS = "errors";
	public static final String PROP_MESSAGE = "message";
	public static final String PROP_DATA = "data";

	private Object object;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	private JSONObject json = null;

	public ServiceResult() {
		json = new JSONObject();
		try {
			json.put(PROP_SUCCESS, true);
			json.put(PROP_MESSAGE, "");
		} catch (JSONException ex) {
		}
	}

	public ServiceResult(boolean success) {
		this();
		this.setSuccess(success);
	}

	public ServiceResult(boolean success, String message) {
		this();
		this.setSuccess(success);
		this.setMessage(message);
	}

	public ServiceResult(boolean success, String msg, Object content) {
		this();
		this.setSuccess(success);
		this.setMessage(msg);
		this.setData(content);
	}

	public void setSuccess(boolean bool) {
		try {
			json.put(PROP_SUCCESS, bool);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	public void setErrCode(int code) {
		try {
			json.put(PROP_ERRCODE, code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setErrors(Object errs) {
		try {
			json.put(PROP_ERRORS, errs);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	public void addError(Object err) {
		try {
			json.append(PROP_ERRORS, err);
		} catch (JSONException ex) {
			JSONArray jary = new JSONArray();
			jary.put(getErrors());
			jary.put(err);
			try {
				json.put(PROP_ERRORS, jary);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isSuccess() {
		return json.optBoolean(PROP_SUCCESS, false);
	}

	public void setMessage(String message) {
		try {
			json.put(PROP_MESSAGE, message);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	public void setData(Object content) {
		if (content == null)
			return;
		try {
			json.put(PROP_DATA, content);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	public void setDataByJSONString(String jstr) {
		try {
			if (jstr != null) {
				jstr = jstr.trim();
				if (jstr.charAt(0) == '[')
					json.put(PROP_DATA, new JSONArray(jstr));
				else if (jstr.charAt(0) == '{')
					json.put(PROP_DATA, new JSONObject(jstr));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public void addData(Object content) {
		if (content == null)
			return;
		try {
			json.append(PROP_DATA, content);
		} catch (JSONException ex) {
			Object value = getData();
			try {
				JSONArray jary = null;
				if (value instanceof Collection) {
					jary = new JSONArray((Collection) value);
				} else if (value.getClass().isArray()) {
					jary = new JSONArray(value);
				} else {
					jary = new JSONArray();
					jary.put(value);
				}
				jary.put(content);
				json.put(PROP_DATA, jary);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public void addDataByJSONString(String jstr) {
		try {
			if (jstr != null) {
				jstr = jstr.trim();
				if (jstr.charAt(0) == '[')
					addData(new JSONArray(jstr));
				else if (jstr.charAt(0) == '{')
					addData(new JSONObject(jstr));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setProperty(String key, Object value) {
		try {
			json.put(key, value);
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	public String getMessage() {
		return json.optString(PROP_MESSAGE, "");
	}

	public Object getData() {
		return json.opt(PROP_DATA);
	}

	public Object getErrors() {
		return json.opt(PROP_ERRORS);
	}

	public int getErrCode() {
		return json.optInt(PROP_ERRCODE);
	}

	public Object getProperty(String key) {
		return json.opt(key);
	}

	public void removeProperty(String key) {
		json.remove(key);
	}

	public String toString() {
		return json.toString();
	}

	public String toJSON() {
		Object obj = getData();
		if (obj == null) {
			try {
				json.put(PROP_DATA, new JSONArray());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		String str = toString();

		return str;
	}

}
