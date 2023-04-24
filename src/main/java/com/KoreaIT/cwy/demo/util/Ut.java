package com.KoreaIT.cwy.demo.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Ut {

	private HttpServletRequest req;
	private HttpServletResponse resp;

	public static boolean empty(Object obj) {

		if (obj == null) {
			return true;
		}

		if (obj instanceof String == false) {
			return true;
		}

		String str = (String) obj;

		return str.trim().length() == 0;
	}

	public static String f(String format, Object... args) {
		return String.format(format, args);
	}

	public static String jsHitoryBack(String resultCode, String msg) {

		if (msg == null) {
			msg = "";
		}

		return Ut.f("""
				<script>
					const msg = '%s'.trim();
					if ( msg.length > 0 ){
						alert(msg);
					}
					history.back();
				</script>
				""", msg);
	}

	public static String jsReplace(String msg, String uri) {
		if (msg == null) {
			msg = "";
		}
		if (uri == null) {
			uri = "/";
		}

		return Ut.f("""
					<script>
					const msg = '%s'.trim();
					if ( msg.length > 0 ){
						alert(msg);
					}
					location.replace('%s');
				</script>
				""", msg, uri);

	}

	public static String jsReplace(String resultCode, String msg, String uri) {
		if (msg == null) {
			msg = "";
		}
		if (uri == null) {
			uri = "/";
		}

		return Ut.f("""
					<script>
					const msg = '%s'.trim();
					if ( msg.length > 0 ){
						alert(msg);
					}
					location.replace('%s');
				</script>
				""", msg, uri);

	}

	public static String jsHitoryBackOnView(HttpServletRequest req, String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);

		return "usr/common/js";
	}

}
