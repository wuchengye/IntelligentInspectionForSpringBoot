package com.bda.bdaqm.common.controller;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bda.bdaqm.util.FileUtils;

@Controller
@RequestMapping({ "common/downloadFile" })
public class DownloadFileController extends com.bda.common.controller.BaseController{

	@RequestMapping("downloadReport")
	@ResponseBody
	public void downloadReport(@RequestParam(required=false)String fileName) throws IOException{
		FileUtils.downloadFile(request.getSession().getServletContext().getRealPath("/upload/temp"), fileName, response);
	}
	
}
