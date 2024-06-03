package com.wamogu.exception;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @Author Armin
 * @date 24-05-28 21:43
 */
public interface ErrorKit {
    /**
     * 常规错误，如参数校验等
     */
    @ExceptionMapper(code = "400", msg = "错误参数", msgReplaceable = true)
    final class IllegalParam extends RuntimeException {
        public IllegalParam(String msg) {
            super(msg);
        }
        public IllegalParam(String msg, Throwable e) {
            super(msg, e);
        }
    }
    /**
     * 需要登录
     */
    @ExceptionMapper(code = "401", msg = "需要登录")
    final class Offline extends RuntimeException {
        public Offline(String msg) {
            super(msg);
        }
        public Offline(String msg, Throwable e) {
            super(msg, e);
        }
    }
    /**
     * 权限异常，没有权限等
     */
    @ExceptionMapper(code = "403", msg = "权限异常", msgReplaceable = true)
    final class Forbidden extends RuntimeException {
        public Forbidden(String msg) {
            super(msg);
        }
        public Forbidden(String msg, Throwable e) {
            super(msg, e);
        }
    }
    /**
     * 业务异常，如封号、操作不存在的资源等
     */
    @ExceptionMapper(code = "404", msg = "业务异常", msgReplaceable = true)
    final class BizError extends RuntimeException {
        public BizError(String msg) {
            super(msg);
        }
        public BizError(String msg, Throwable e) {
            super(msg, e);
        }
    }

    /**
     * 系统异常，需要开发人员或管理人员处理的事，如IO 错误、代码错误、配置错误、第三方对接错误
     */
    @ExceptionMapper(code = "5000", msg = "系统异常", msgReplaceable = true)
    final class Fatal extends RuntimeException {
        public Fatal(String msg) {
            super(msg);
        }
        public Fatal(String msg, Throwable e) {
            super(msg, e);
        }
    }
}