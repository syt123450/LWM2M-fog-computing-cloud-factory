package com.bootstrapServer.model.serverObject;

import com.bootstrapServer.model.entity.clientEntity.ClientIdentityBean;
import com.bootstrapServer.model.entity.clientEntity.ReportRequestBean;
import com.bootstrapServer.model.entity.serverEntity.ClientOperationResultBean;
import com.bootstrapServer.model.entity.serverEntity.ReporterInfoBean;
import com.bootstrapServer.model.entity.serverEntity.ServerMappingBean;
import com.bootstrapServer.model.utils.MongodbUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by ss on 2017/4/25.
 */
public class ReportProvider {

    static private String GENERATE_SUCCESS_MESSAGE = "Successfully generate the latest report.";
    static private String GENERATE_FAILED_MESSAGE = "Failed to generate the report.";
    private Logger logger = Logger.getLogger(ReportProvider.class);
    private Gson gson = new GsonBuilder().create();

    public ClientOperationResultBean generate(ReportRequestBean reportRequestBean) {

        ClientOperationResultBean clientOperationResultBean = new ClientOperationResultBean();
        String clientName = reportRequestBean.getClientName();
        ClientIdentityBean clientIdentityBean = new ClientIdentityBean();
        clientIdentityBean.setClientName(clientName);

        ArrayList<ServerMappingBean> serverMappingBeans = MongodbUtils.getReportMappingUrl();

        logger.info("Collect report from: " + serverMappingBeans);

        try {
            Document document = new Document();
            String fileOutputPath = "/tmp/" + clientName + ".pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileOutputPath));
            document.open();

            for (int i = 0; i < serverMappingBeans.size(); i++) {
                ServerMappingBean serverMappingBean = serverMappingBeans.get(i);

                String serverName = serverMappingBean.getServerName();
                String serverMappingUrl = serverMappingBean.getMappingUrl();

                try {
                    String message = gson.toJson(clientIdentityBean);

                    HttpEntity httpEntity = new StringEntity(message);
                    String responseContent = Request.Post(serverMappingUrl).body(httpEntity).execute().returnContent().asString();
                    ArrayList<ReporterInfoBean> reporterInfoBeans = gson.fromJson(responseContent, new TypeToken<ArrayList<ReporterInfoBean>>(){}.getType());
                    System.out.println(reporterInfoBeans);

                    if (reporterInfoBeans.size() > 0) {
                        document.add(new Paragraph("History report in " + serverName + " :"));
                        for (int j = 0; j < reporterInfoBeans.size(); j++) {
                            ReporterInfoBean reporterInfoBean = reporterInfoBeans.get(j);
                            String paragraph = createParagraph(reporterInfoBean);
                            System.out.println(paragraph);
                            document.add(new Paragraph(paragraph));
                        }
                    }
                } catch (Exception e) {
                    logger.info("Failed to connect to server: " + serverName);
                }
            }
            document.close();
            clientOperationResultBean.setResultMessage(GENERATE_SUCCESS_MESSAGE);
        } catch (Exception e) {
            clientOperationResultBean.setResultMessage(GENERATE_FAILED_MESSAGE);
        }

        return clientOperationResultBean;
    }

    private String createParagraph(ReporterInfoBean reporterInfoBean) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("clientName: ").append(reporterInfoBean.getClientName()).append("; ").
                append("executorStatus: ").append(reporterInfoBean.getExecutorStatus()).append("; ").
                append("workingStatus: ").append(reporterInfoBean.getWorkingStatus()).append("; ").
                append("JobID: ").append(reporterInfoBean.getJobID()).append("; ").
                append("leftNum: ").append(reporterInfoBean.getLeftNum()).append("; ").
                append("time: ").append(reporterInfoBean.getTime()).append(";");

        return stringBuilder.toString();
    }
}
