import request from "@/utils/request";

const INTERVAL_MS = 30 * 1000;   // 间隔 30 秒
let timer = null;
let inFlight = false;

async function beat() {
    // 没登录不发；上一次请求还没返回也不重复发
    if (inFlight || !sessionStorage.getItem("token")) return;
    inFlight = true;
    try {
        await request.get("/survival");
    } catch (e) {
        // 401 已由 request.js 的响应拦截器统一提示并跳转；
        // 网络错误、5xx 等其它错误静默忽略，不能因为一次抖动就让用户下线
    } finally {
        inFlight = false;
    }
}

export function startHeartbeat() {
    if (timer) return;                          // 防止重复启动
    timer = setInterval(beat, INTERVAL_MS);
    // 标签页切回前台时立即检查一次（后台标签页的定时器会被浏览器降频）
    document.addEventListener("visibilitychange", () => {
        if (!document.hidden) beat();
    });
}

export function stopHeartbeat() {
    clearInterval(timer);
    timer = null;
}