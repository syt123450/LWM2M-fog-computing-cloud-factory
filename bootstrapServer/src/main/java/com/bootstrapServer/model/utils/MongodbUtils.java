package com.bootstrapServer.model.utils;

import com.bootstrapServer.model.entity.serverEntity.*;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.*;

/**
 * Created by ss on 2017/3/3.
 */

public class MongodbUtils {

    static private String HOST = "localhost";
    static private int PORT = 27017;
    static private String DATABASE_NAME = "bootstrap";
    static private String BOOTSTRAPINFO_COLLECTION_NAME = "bootstrapInfo";
    static private String CLIENTINFO_COLLECTION_NAME = "clientInfo";
    static private String CENTRALBOOTSTRAPINFO_COLLECTION_NAME = "centralBootstrapInfo";
    static private String PAYMENT_COLLECTION_NAME = "payment";
    static private String MAPPINGINFO_COLLECTION_NAME = "mappingInfo";
    static private MongoClient mongoClient = new MongoClient(HOST, PORT);
    static private MongoDatabase mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
    static private MongoCollection<Document> bootstrapInfoCollection = mongoDatabase.getCollection(BOOTSTRAPINFO_COLLECTION_NAME);
    static private MongoCollection<Document> clientInfoCollection = mongoDatabase.getCollection(CLIENTINFO_COLLECTION_NAME);
    static private MongoCollection<Document> centralBootstrapInfoCollection = mongoDatabase.getCollection(CENTRALBOOTSTRAPINFO_COLLECTION_NAME);
    static private MongoCollection<Document> paymentCollection = mongoDatabase.getCollection(PAYMENT_COLLECTION_NAME);
    static private MongoCollection<Document> mappingInfoCollection = mongoDatabase.getCollection(MAPPINGINFO_COLLECTION_NAME);

    //bootstrapDatabase
    static public ArrayList<ServerInfoBean> getAllBootstrapData() {

        FindIterable<Document> findIterable = bootstrapInfoCollection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        ArrayList<ServerInfoBean> bootstrapDataList = new ArrayList<>();

        while (mongoCursor.hasNext()) {
            Document result = mongoCursor.next();
            ServerInfoBean serverInfoBean = new ServerInfoBean();
            serverInfoBean.setServerName(result.get("serverName").toString());
            serverInfoBean.setNewRegistrationUrl(result.get("newRegistrationUrl").toString());
            serverInfoBean.setUpdateRegistrationUrl(result.get("updateRegistrationUrl").toString());
            serverInfoBean.setDeRegistrationUrl(result.get("deRegistrationUrl").toString());
            serverInfoBean.setReporterUrl(result.get("reporterUrl").toString());
            bootstrapDataList.add(serverInfoBean);
        }

        return bootstrapDataList;
    }

    static public ServerInfoBean getBootstrapData(String serverName) {

        BasicDBObject queryObject = new BasicDBObject("serverName", serverName);
        FindIterable<Document> findIterable = bootstrapInfoCollection.find(queryObject);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        ServerInfoBean serverInfoBean = new ServerInfoBean();
        while (mongoCursor.hasNext()) {
            Document result = mongoCursor.next();
            serverInfoBean.setServerName(result.get("serverName").toString());
            serverInfoBean.setNewRegistrationUrl(result.get("newRegistrationUrl").toString());
            serverInfoBean.setUpdateRegistrationUrl(result.get("updateRegistrationUrl").toString());
            serverInfoBean.setDeRegistrationUrl(result.get("deRegistrationUrl").toString());
            serverInfoBean.setReporterUrl(result.get("reporterUrl").toString());
        }

        return serverInfoBean;
    }

    static public void deleteServer(String serverName) {

        BasicDBObject queryObject = new BasicDBObject("serverName", serverName);
        bootstrapInfoCollection.findOneAndDelete(queryObject);
    }

    static public boolean checkClient(String clientName) {

        BasicDBObject queryObject = new BasicDBObject("clientName", clientName);
        FindIterable<Document> findIterable = clientInfoCollection.find(queryObject);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        int clientSize = 0;
        while (mongoCursor.hasNext()) {
            System.out.println(mongoCursor.next());
            clientSize++;
        }

        return clientSize != 0;
    }

    static public void createServer(HashMap<String, String> serverInfo) {

        Document document = new Document();
        Iterator iterator = serverInfo.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            document.append(key.toString(), value.toString());
        }
        bootstrapInfoCollection.insertOne(document);
    }

    static public boolean findSpecificServer(String serverName) {

        BasicDBObject queryObject = new BasicDBObject("serverName", serverName);
        FindIterable<Document> findIterable = bootstrapInfoCollection.find(queryObject);
        MongoCursor<Document> mongoCursor = findIterable.iterator();

        return mongoCursor.hasNext();
    }

    static public ArrayList<String> getAllServerName() {

        FindIterable<Document> findIterable = bootstrapInfoCollection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        ArrayList<String> serverList = new ArrayList<>();
        String serverName = null;
        while (mongoCursor.hasNext()) {
            Document result = mongoCursor.next();
            serverName = result.get("serverName").toString();
            serverList.add(serverName);
        }

        return serverList;
    }

    static public ArrayList<ClientInfoBean> getAllClientInfo() {

        FindIterable<Document> findIterable = clientInfoCollection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        ArrayList<ClientInfoBean> serverList = new ArrayList<>();
        while (mongoCursor.hasNext()) {
            Document result = mongoCursor.next();
            ClientInfoBean clientInfoBean = new ClientInfoBean();
            clientInfoBean.setClientName(result.get("clientName").toString());
            clientInfoBean.setBootstrapUrl(result.get("bootstrapUrl").toString());
            serverList.add(clientInfoBean);
        }

        return serverList;
    }

    static public ClientInfoBean getOneClientInfo(String clientName) {

        BasicDBObject queryObject = new BasicDBObject("clientName", clientName);
        FindIterable<Document> findIterable = clientInfoCollection.find(queryObject);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        ClientInfoBean clientInfoBean = new ClientInfoBean();
        while (mongoCursor.hasNext()) {
            Document result = mongoCursor.next();
            clientInfoBean.setClientName(result.get("clientName").toString());
            clientInfoBean.setBootstrapUrl(result.get("bootstrapUrl").toString());
        }

        return clientInfoBean;
    }

    static public CentralServerInfoBean getCentralServerInfo() {

        CentralServerInfoBean centralServerInfoBean = new CentralServerInfoBean();

        FindIterable<Document> findIterable = centralBootstrapInfoCollection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            Document result = mongoCursor.next();
            centralServerInfoBean.setPaymentUrl(result.get("paymentUrl").toString());
            centralServerInfoBean.setCreateReportUrl(result.get("createReportUrl").toString());
            centralServerInfoBean.setDownLoadReportUrl(result.get("downLoadReportUrl").toString());
        }

        return centralServerInfoBean;
    }

    static public void updateLeftRequestTime(String clientName) {

        int requestTimes = 0;
        BasicDBObject queryObject = new BasicDBObject("clientName", clientName);
        FindIterable<Document> findIterable = paymentCollection.find(queryObject);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            Document result = mongoCursor.next();
            requestTimes = result.getInteger("requestTime");
        }

        Document document = new Document();
        document.append("requestTime", --requestTimes);
        paymentCollection.updateMany(Filters.eq("clientName", clientName), new Document("$set", document));
    }

    static public PaymentInfoBean getPaymentInfo(String clientName) {

        PaymentInfoBean paymentInfoBean = new PaymentInfoBean();

        BasicDBObject queryObject = new BasicDBObject("clientName", clientName);
        FindIterable<Document> findIterable = paymentCollection.find(queryObject);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            Document result = mongoCursor.next();
            paymentInfoBean.setClientName(clientName);
            paymentInfoBean.setRequestTime(result.getInteger("requestTime"));
            paymentInfoBean.setFormula(result.getString("formula"));
            paymentInfoBean.setDueDate(result.getLong("dueDate"));
        }

        return paymentInfoBean;
    }

    static public void changeBasicUserStatus(String clientName) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);

        Document document = new Document();
        document.append("formula", "Premium");
        document.append("dueDate", calendar.getTimeInMillis());
        paymentCollection.updateOne(Filters.eq("clientName", clientName), new Document("$set", document));
    }

    static public void updatePremiumUser(String clientName, long oldTimeStamp) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(oldTimeStamp);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);

        Document document = new Document();
        document.append("dueDate", calendar.getTimeInMillis());
        paymentCollection.updateOne(Filters.eq("clientName", clientName), new Document("$set", document));
    }

    static public ArrayList<ServerMappingBean> getReportMappingUrl() {

        ArrayList<ServerMappingBean> serverMappingBeans = new ArrayList<>();

        FindIterable<Document> findIterable = mappingInfoCollection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            ServerMappingBean serverMappingBean = new ServerMappingBean();
            Document result = mongoCursor.next();
            serverMappingBean.setServerName(result.getString("serverName"));
            serverMappingBean.setMappingUrl(result.getString("collectReportUrl"));
            serverMappingBeans.add(serverMappingBean);
        }

        return serverMappingBeans;
    }
}