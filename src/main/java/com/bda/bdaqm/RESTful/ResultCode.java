package com.bda.bdaqm.RESTful;

public enum ResultCode {
    /* 成功状态码 */
    SUCCESS(1, "成功"),
    /* 失败状态码 */
    FAILURE(0, "失败"),

    NO_PREMISSION(403,"403"),
    NOT_LOGIN(500,"500"),
    OVERRIDE_NOT_LOGIN_CODE(404,"暂未登录"),
    OVERRIDE_LOGIN_CODE(200,"已经登录过了"),

    NO_PREMISSION_TO_OPRATION(101,"没有添加或者删除任何权限"),

    GET_ROLE_RESOURCE_FAILURE(2001,"获取权限资源列表"),

    NO_UPDATE(30001,"没有任何更新"),
    UPDATE_FAILURE(30002,"更新失败"),

    SUBMIT_FAILURE(40001,"提交失败"),

    HAVE_ACCOUNT(50001,"账户已存在"),

    NO_SESSION_ID(60001,"sessionId为空"),

    DOWNLOAD_FAILURE(70001,"下载失败");


    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return code;
    }

    public String message() {
        return message;
    }
}
