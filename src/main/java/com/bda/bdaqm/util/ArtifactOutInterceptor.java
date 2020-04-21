package com.bda.bdaqm.util;


import java.io.ByteArrayInputStream; 
import java.io.IOException; 
import java.io.InputStream; 
import java.io.OutputStream; 
 
import org.apache.commons.io.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream; 
import org.apache.cxf.message.Message; 
import org.apache.cxf.phase.AbstractPhaseInterceptor; 
import org.apache.cxf.phase.Phase; 

public class ArtifactOutInterceptor extends AbstractPhaseInterceptor<Message>{
	
	 public ArtifactOutInterceptor() { 
	        //这儿使用pre_stream，意思为在流关闭之前 
	        super(Phase.PRE_STREAM); 
	 }

	@Override
	public void handleMessage(Message message) throws Fault {
		try { 
			 
            OutputStream os = message.getContent(OutputStream.class); 
 
            CachedStream cs = new CachedStream(); 
 
            message.setContent(OutputStream.class, cs); 
 
            message.getInterceptorChain().doIntercept(message); 
 
            CachedOutputStream csnew = (CachedOutputStream) message.getContent(OutputStream.class); 
            InputStream in = csnew.getInputStream(); 
             
            String xml = IOUtils.toString(in,"UTF-8");
            
              xml=xml.replace("<ns2:queryTicketRequestResponse xmlns:ns2=\"http://el.soa.csg.cn/\">", "<ns2:queryTicketResponse xmlns:ns2=\"http://el.soa.csg.cn/\">")
                     .replace("</ns2:queryTicketRequestResponse>", "</ns2:queryTicketResponse>");
   
             		
            //这里对xml做处理，处理完后同理，写回流中 
            IOUtils.copy(new ByteArrayInputStream(xml.getBytes("UTF-8")), os); 
             
            cs.close(); 
            os.flush(); 
 
            message.setContent(OutputStream.class, os); 
 
        } catch (Exception e) { 
            System.out.println("CLJ");
        } 

	}
	
	
	private class CachedStream extends CachedOutputStream { 
		 
        public CachedStream() { 
 
            super(); 
 
        } 
 
        protected void doFlush() throws IOException { 
 
            currentStream.flush(); 
 
        } 
 
        protected void doClose() throws IOException { 
 
        } 
 
        protected void onWrite() throws IOException { 
 
        } 
 
    
	}

}
