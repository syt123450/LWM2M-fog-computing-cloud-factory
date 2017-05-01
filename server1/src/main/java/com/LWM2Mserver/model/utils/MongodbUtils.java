package com.LWM2Mserver.model.utils;

import com.LWM2Mserver.model.entity.clientEntity.ResourceAddressBean;
import com.LWM2Mserver.model.entity.serverEntity.ClientInfoBean;
import com.LWM2Mserver.model.entity.serverEntity.ReporterInfoBean;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.*;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;

/**
 * Created by ss on 2017/3/3.
 */

public class MongodbUtils {

    static private String HOST = "localhost";
    static private int PORT = 27017;
    static private String DATABASE_NAME = PropertiesUtils.getProperty("databaseName");
    static private String CLIENTINFO_COLLECTION_NAME = "clientInfo";
    static private String RESOURCE_COLLECTION_NAME = "resourceInfo";
    static private String REPORT_COLLECTION_NAME = "reporter";
    static private MongoClient mongoClient = new MongoClient(HOST, PORT);
    static private MongoDatabase mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
    static private MongoCollection<Document> clientInfoCollection = mongoDatabase.getCollection(CLIENTINFO_COLLECTION_NAME);
    static private MongoCollection<Document> resourceInfoCollection = mongoDatabase.getCollection(RESOURCE_COLLECTION_NAME);
    static private MongoCollection<Document> reportCollection = mongoDatabase.getCollection(REPORT_COLLECTION_NAME);

    //delete
    //clientInfoDatabase
    static public long deleteClient(String clientName) {

        DeleteResult deleteResult = clientInfoCollection.deleteOne(Filters.eq("clientName", clientName));
        long count = deleteResult.getDeletedCount();

        return count;
    }

    //delete
    static public void deleteClientResources(String clientName) {
        BasicDBObject queryObject = new BasicDBObject();
        queryObject.put("clientName", clientName);
        resourceInfoCollection.deleteMany(queryObject);
    }

    //update
    static public void updateClient(String clientName, HashMap<String, String> updateAttributes) {

        Iterator iterator = updateAttributes.entrySet().iterator();
        Document document = createInsertDocument(iterator);
        clientInfoCollection.updateMany(Filters.eq("clientName", clientName), new Document("$set", document));
    }

    //create
    static public void insertClient(HashMap newClientInfo) {

        Iterator iterator = newClientInfo.entrySet().iterator();
        Document document = createInsertDocument(iterator);
        clientInfoCollection.insertOne(document);
    }

    //create
    static public void insertResourceOperation(HashMap newResourceInfo) {
        Iterator iterator = newResourceInfo.entrySet().iterator();
        Document document = createInsertDocument(iterator);
        resourceInfoCollection.insertOne(document);
    }

    //read
    static public ArrayList<ClientInfoBean> getAllClientsInfo() {

        FindIterable<Document> findIterable = clientInfoCollection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();

        ArrayList<ClientInfoBean> clientInfoBeans = new ArrayList<>();
        while (mongoCursor.hasNext()) {
            ClientInfoBean clientInfoBean = new ClientInfoBean();
            Document result = mongoCursor.next();
            clientInfoBean.setClientName(result.get("clientName").toString());
            clientInfoBean.setLifeTime(result.get("lifeTime").toString());
            clientInfoBean.setBindingMode(result.get("bindingMode").toString());
            clientInfoBean.setVersion(result.get("version").toString());
            clientInfoBean.setAddress(result.get("address").toString());
            clientInfoBeans.add(clientInfoBean);
        }

        return clientInfoBeans;
    }

    //read
    static public ClientInfoBean getClientInfo(String clientName) {

        BasicDBObject queryObject = new BasicDBObject("clientName", clientName);
        FindIterable<Document> findIterable = clientInfoCollection.find(queryObject);
        MongoCursor<Document> mongoCursor = findIterable.iterator();

        ClientInfoBean clientInfoBean = new ClientInfoBean();
        while (mongoCursor.hasNext()) {
            Document result = mongoCursor.next();
            clientInfoBean.setClientName(result.get("clientName").toString());
            clientInfoBean.setLifeTime(result.get("lifeTime").toString());
            clientInfoBean.setBindingMode(result.get("bindingMode").toString());
            clientInfoBean.setVersion(result.get("version").toString());
            clientInfoBean.setAddress(result.get("address").toString());
            return clientInfoBean;
        }

        return null;
    }

    //read
    static public String getClientAttribute(String clientName, String attributeName) {

        BasicDBObject queryObject = new BasicDBObject("clientName", clientName);
        FindIterable<Document> findIterable = clientInfoCollection.find(queryObject);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        String attributeValue = null;
        while (mongoCursor.hasNext()) {
            attributeValue = mongoCursor.next().get(attributeName).toString();
        }

        return attributeValue;
    }

    //read
    static public ArrayList<String> getAllClientName() {

        ArrayList<String> clientNameList = new ArrayList<>();

        FindIterable<Document> findIterable = clientInfoCollection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            clientNameList.add(mongoCursor.next().get("clientName").toString());
        }

        return clientNameList;
    }

    //read
    static public boolean isClientExist(String clientName) {

        BasicDBObject queryObject = new BasicDBObject("clientName", clientName);
        FindIterable<Document> findIterable = clientInfoCollection.find(queryObject);
        MongoCursor<Document> mongoCursor = findIterable.iterator();

        return mongoCursor.hasNext();
    }

    //read
    static public boolean isResourceOperationExist(String clientName, String resourceID, String operation) {

        BasicDBObject queryObject = new BasicDBObject();
        queryObject.put("clientName", clientName);
        queryObject.put("resourceID", resourceID);
        queryObject.put("operation", operation);
        FindIterable<Document> findIterable = resourceInfoCollection.find(queryObject);
        MongoCursor<Document> mongoCursor = findIterable.iterator();

        return mongoCursor.hasNext();
    }

    //delete
    static public void deleteResourceOperation(String clientName, String resourceID, String operation) {
        BasicDBObject queryObject = new BasicDBObject();
        queryObject.put("clientName", clientName);
        queryObject.put("resourceID", resourceID);
        queryObject.put("operation", operation);
        resourceInfoCollection.deleteMany(queryObject);
    }

    //read
    static public ArrayList<ResourceAddressBean> getClientResources(String clientName) {
        BasicDBObject queryObject = new BasicDBObject();
        queryObject.put("clientName", clientName);
        FindIterable<Document> findIterable = resourceInfoCollection.find(queryObject);
        MongoCursor<Document> mongoCursor = findIterable.iterator();

        ArrayList<ResourceAddressBean> resourceAddressList = new ArrayList<>();
        while (mongoCursor.hasNext()) {
            Document result = mongoCursor.next();
            ResourceAddressBean resourceAddressBean = new ResourceAddressBean();
            resourceAddressBean.setResourceID(Integer.parseInt(result.get("resourceID").toString()));
            resourceAddressBean.setResourceName(result.get("resourceName").toString());
            resourceAddressBean.setOperation(result.get("operation").toString());
            resourceAddressBean.setAddress(result.get("address").toString());

            resourceAddressList.add(resourceAddressBean);
        }

        return resourceAddressList;
    }

    //create
    static private Document createInsertDocument(Iterator iterator) {

        Document document = new Document();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            document.append(key.toString(), value.toString());
        }

        return document;
    }

    //read
    static private String getResourceOperation(String clientName, String resourceName, String operation) {

        BasicDBObject queryObject = new BasicDBObject();
        queryObject.put("clientName", clientName);
        queryObject.put("resourceName", resourceName);
        queryObject.put("operation", operation);
        FindIterable<Document> findIterable = resourceInfoCollection.find(queryObject);
        MongoCursor<Document> mongoCursor = findIterable.iterator();

        return mongoCursor.next().get("address").toString();
    }

    static public String getResourceOperationUrl(String clientName, String resourceName, String operation) {

        String address1 = MongodbUtils.getClientAttribute(clientName, "address");
        String address2 = MongodbUtils.getResourceOperation(clientName, resourceName, operation);
        return address1 + address2;
    }

    static public void persistReport(HashMap report) {
        Iterator iterator = report.entrySet().iterator();
        Document document = createInsertDocument(iterator);
        reportCollection.insertOne(document);
    }

    static public ArrayList<ReporterInfoBean> getReport(String clientName) {

        ArrayList<ReporterInfoBean> reporterInfoBeans = new ArrayList<>();

        BasicDBObject queryObject = new BasicDBObject("clientName", clientName);
        FindIterable<Document> findIterable = reportCollection.find(queryObject);
        MongoCursor<Document> mongoCursor = findIterable.iterator();

        while (mongoCursor.hasNext()) {
            ReporterInfoBean reporterInfoBean = new ReporterInfoBean();
            Document result = mongoCursor.next();
            reporterInfoBean.setClientName(clientName);
            reporterInfoBean.setExecutorStatus(result.getString("executorStatus"));
            reporterInfoBean.setWorkingStatus(result.getString("workingStatus"));
            reporterInfoBean.setJobID(Integer.parseInt(result.getString("jobID")));
            reporterInfoBean.setLeftNum(Integer.parseInt(result.getString("leftNum")));
            long timestamp = Long.parseLong(result.getString("timestamp"));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(timestamp);
            String res = simpleDateFormat.format(date);
            reporterInfoBean.setTime(res);
            reporterInfoBeans.add(reporterInfoBean);
        }

        return reporterInfoBeans;
    }
}