package com.bda.bdaqm.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


import org.apache.cxf.message.Message;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;

public class CAbstractPhaseInterceptor extends AbstractPhaseInterceptor<Message>{

	public CAbstractPhaseInterceptor(String phase) {
        super(phase);
    }

	@Override
	public void handleMessage(Message message) throws Fault {
		InputStream is = message.getContent(InputStream.class);
        if (is != null) {
            try {
                String str = IOUtils.toString(is);
                // 原请求报文
                System.out.println("====> request xml=\r\n" + str);
                
                // 把siebel格式的报文替换成符合cxf带前缀的命名空间
                str = str.replace("<el:queryTicket>", "<el:queryTicketRequset>").replace("</el:queryTicket>", "</el:queryTicketRequset>"); 
                				
                // 替换后的报文
                System.out.println("====> replace xml=\r\n" + str);
                
                InputStream ism = new ByteArrayInputStream(str.getBytes("UTF-8"));
                message.setContent(InputStream.class, ism);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

	}

}
