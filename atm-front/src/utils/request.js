import axios from "axios";
import router from "@/router";
import { ElMessage } from "element-plus";
const request = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
});

//  请求拦截：自动注入 token，不用每个页面手动写
request.interceptors.request.use(config => {
    const token = sessionStorage.getItem("token");
    if (token) {
        config.headers["token"] = token;
    }
    return config;
});

// 响应拦截：统一处理错误响应，提取后端自定义错误消息
let handling401 = false;   // 多个请求同时 401 时，只提示和跳转一次

request.interceptors.response.use(
    res => res,
    err => {
        const status = err.response?.status;
        const msg = err.response?.data?.message;

        // 登录接口自己的 401（卡号或密码错误）由登录页显示，不算"登录失效"
        const isLoginRequest = err.config?.url?.endsWith("/login");

        if (status === 401 && !isLoginRequest && !handling401) {
            handling401 = true;
            ElMessage.warning(msg || "登录已失效，请重新登录");
            sessionStorage.clear();
            router.push("/login").finally(() => { handling401 = false; });
        }

        // 把后端的错误消息挂到错误对象上，业务代码里 e.message 就是后端提示
        if (msg) {
            err.message = msg;
        }

        return Promise.reject(err);
    }
);

export default request;