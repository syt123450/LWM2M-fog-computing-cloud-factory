package com.bootstrapServer.presenter;

import com.bootstrapServer.model.entity.clientEntity.ReportRequestBean;
import com.bootstrapServer.model.entity.serverEntity.ClientOperationResultBean;
import com.bootstrapServer.model.serverObject.ReportProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by ss on 2017/4/25.
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/reporter")
class ReporterPresenter {

    private Logger logger = Logger.getLogger(ReporterPresenter.class);
    private Gson gson = new GsonBuilder().create();
    private ServletContext servletContext;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    private String createReport(@RequestBody String body) {

        logger.info("Create report for specific client.");

        ReportRequestBean reportRequestBean = gson.fromJson(body, ReportRequestBean.class);
        ReportProvider reportProvider = new ReportProvider();
        ClientOperationResultBean clientOperationResultBean = reportProvider.generate(reportRequestBean);
        String response = gson.toJson(clientOperationResultBean);

        return response;
    }

    @RequestMapping("/download")
    private String downloadReport(String clientName, HttpServletResponse response) {

        logger.info("Download report for client");

        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName="+"ReportLogFile.pdf");
        ServletOutputStream out;

        String filePath = "/tmp/" + clientName + ".pdf";
        File file = new File(filePath);

        try {
            FileInputStream inputStream = new FileInputStream(file);
            out = response.getOutputStream();

            int b = 0;
            byte[] buffer = new byte[512];
            while (b != -1){
                b = inputStream.read(buffer);
                out.write(buffer,0,b);
            }
            inputStream.close();
            out.close();
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}