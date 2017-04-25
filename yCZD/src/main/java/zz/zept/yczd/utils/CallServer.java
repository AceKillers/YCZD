package zz.zept.yczd.utils;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;

public class CallServer {

	private static CallServer callServer;

	/**
	 * 请求队列
	 */
	private RequestQueue requestQueue;

	private CallServer() {
		requestQueue = NoHttp.newRequestQueue();
	}

	/**
	 * 请求队列
	 */
	public synchronized static CallServer getRequestInstance() {
		if (callServer == null)
			callServer = new CallServer();
		return callServer;
	}

	/**
	 * 添加一个请求到请求队列
	 *
	 * @param what
	 *            用来标志请求，在回调方法中会返回这个what，类似handler的what
	 * @param request
	 *            请求对象
	 * @param callback
	 *            结果回调对象
	 * 
	 */
	public <T> void add(int what, Request<T> request, zz.zept.yczd.utils.HttpResponseListener.HttpListener<T> callback) {
		requestQueue.add(what, request, new HttpResponseListener<T>(request, callback));
	}

	/**
	 * 取消这个sign标记的所有请求
	 */
	public void cancelBySign(Object sign) {
		requestQueue.cancelBySign(sign);
	}

	/**
	 * 取消队列中所有请求
	 */
	public void cancelAll() {
		requestQueue.cancelAll();
	}

	/**
	 * 退出app时停止所有请求
	 */
	public void stopAll() {
		requestQueue.stop();
	}
}