package com.bda.bdaqm.RESTful;

public enum ResultCode {
    /* 成功状态码 */
    SUCCESS(1, "成功"),
    /* 失败状态码 */
    FAILURE(0, "失败"),

    NO_PREMISSION(403,"403"),
    NOT_LOGIN(500,"500"),
    OVERRIDE_NOT_LOGIN_CODE(404,"用户名或密码错误!"),
    OVERRIDE_LOGIN_CODE(200,"已经登录过了"),

    NO_PREMISSION_TO_OPRATION(101,"没有添加或者删除任何权限"),

    GET_ROLE_RESOURCE_FAILURE(2001,"获取权限资源列表"),

    NO_UPDATE(30001,"没有任何更新"),
    UPDATE_FAILURE(30002,"更新失败"),

    SUBMIT_FAILURE(40001,"提交失败"),
    MISSION_HAS_COMPLETED(40002,"任务已经完成"),

    HAVE_ACCOUNT(50001,"账户已存在"),

    NO_SESSION_ID(60001,"sessionId为空"),

    DOWNLOAD_FAILURE(70001,"下载失败"),

    NOT_FIND_ROLE(80001,"未查询到该用户角色信息"),

    WRONG_PASSWORD(90001,"密码错误"),

    FTP_PATH_NULL(10001,"服务器地址为空"),
    FTP_CONNECT_FAILURE(10002,"连接失败"),

    COMMONJOB_RUNNING(11001,"正在运行"),
    COMMONJOB_REMOVE_FAILURE(11002,"移除失败"),

    CREATE_MISSION_REPEAT_NAME(12001,"任务名重复"),

    MISSION_EXIST(130001,"任务存在");

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
