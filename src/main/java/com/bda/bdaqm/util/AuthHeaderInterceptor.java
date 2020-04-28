package com.bda.bdaqm.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class AuthHeaderInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
	//private Logger log = LoggerFactory.getLogger(AuthHeaderInterceptor.class);

	public AuthHeaderInterceptor() {
		super(Phase.PRE_INVOKE);
	}

	@Override
	public void handleMessage(SoapMessage msg) throws Fault {
		List<Header> headers = new ArrayList<Header>();
		try {
			headers = msg.getHeaders();
		} catch (Exception e) {
			throw new Fault(new IllegalArgumentException("没有Header，禁止调用！"));
		}
		if (headers == null || headers.size() < 1) {
			throw new Fault(new IllegalArgumentException("没有Header，禁止调用！"));
		}

		Header firstHeader = headers.get(0);
		Element ele = (Element) firstHeader.getObject();
		NodeList userIds = ele.getElementsByTagName("username");
		NodeList passwords = ele.getElementsByTagName("password");
		if (userIds == null || userIds.getLength() != 1) {
			throw new Fault(new IllegalArgumentException("用户名格式不正确！"));
		}
		if (passwords == null || passwords.getLength() != 1) {
			throw new Fault(new IllegalArgumentException("密码格式不正确！"));
		}
		String userId = userIds.item(0).getTextContent();
		String password = passwords.item(0).getTextContent();

		if (!"admin".equals(userId) && !"admin".equals(password)) {
			throw new Fault(new IllegalArgumentException("用户名、密码不正确！"));
		}

	}

}
