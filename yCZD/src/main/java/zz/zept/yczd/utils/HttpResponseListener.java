package zz.zept.yczd.utils;

import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

public class HttpResponseListener<T> implements OnResponseListener<T> {

	/**
	 * 当前请求
	 */
	private Request<T> mRequest;

	/**
	 * 结果回调
	 */
	private HttpListener<T> callback;

	/**
	 * 
	 * @param request
	 *            请求对象
	 * @param httpCallback
	 *            回调对象
	 * 
	 */

	public HttpResponseListener(Request<T> request, HttpListener<T> httpCallback) {
		// TODO Auto-generated constructor stub
		this.mRequest = request;
		this.callback = httpCallback;
	}

	/**
	 * 开始请求
	 */
	@Override
	public void onStart(int what) {
	}

	/**
	 * 结束请求
	 */
	@Override
	public void onFinish(int what) {
	}

	/**
	 * 成功回调
	 */
	@Override
	public void onSucceed(int what, Response<T> response) {
		if (callback != null)
			callback.onSucceed(what, response);
	}

	/**
	 * 失败回调
	 */
	@Override
	public void onFailed(int what, Response<T> response) {
		if (callback != null)
			callback.onFailed(what, response);
	}

	public interface HttpListener<T> {

		/**
		 * 请求失败
		 */
		void onSucceed(int what, Response<T> response);

		/**
		 * 请求成功
		 */
		void onFailed(int what, Response<T> response);
	}
}