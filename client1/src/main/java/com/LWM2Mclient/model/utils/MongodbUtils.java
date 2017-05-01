package com.LWM2Mclient.model.utils;

import com.LWM2Mclient.model.entity.clientEntity.register.NewRegistrationBean;
import com.LWM2Mclient.model.entity.clientEntity.register.UpdateRegistrationBean;
import com.LWM2Mclient.model.entity.serverEntity.ServerInfoBean;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;

/**
 * Created by ss on 2017/3/3.
 */

public class MongodbUtils {

    static private String HOST = "localhost";
    static private int PORT = 27017;
    static private String DATABASE_NAME = "client1";
    static private String BOOTSTRAPINFO_COLLECTION_NAME = "bootstrapInfo";
    static private String REGISTRATIONINFO_COLLECTION_NAME = "registrationInfo";
    static private MongoClient mongoClient = new MongoClient(HOST, PORT);
    static private MongoDatabase mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);
    static private MongoCollection<Document> bootstrapInfoCollection = mongoDatabase.getCollection(BOOTSTRAPINFO_COLLECTION_NAME);
    static private MongoCollection<Document> registrationInfoCollection = mongoDatabase.getCollection(REGISTRATIONINFO_COLLECTION_NAME);

    static public void deleteAllServer() {

        ArrayList<String> serverNameList = getAllServerName();
        for (int i = 0; i < serverNameList.size(); i++) {
            bootstrapInfoCollection.deleteOne(Filters.eq("serverName", serverNameList.get(i)));
        }
    }

    static public void deleteServer(String serverName) {
        bootstrapInfoCollection.deleteOne(Filters.eq("serverName", serverName));
    }

    static public void persistAllServer(ArrayList<ServerInfoBean> serverInfoBeans) {
        for (int i = 0; i < serverInfoBeans.size(); i++) {
            ServerInfoBean serverInfoBean = serverInfoBeans.get(i);
            Document document = new Document();
            document.append("serverName", serverInfoBean.getServerName());
            document.append("newRegistrationUrl", serverInfoBean.getNewRegistrationUrl());
            document.append("updateRegistrationUrl", serverInfoBean.getUpdateRegistrationUrl());
            document.append("deRegistrationUrl", serverInfoBean.getDeRegistrationUrl());
            bootstrapInfoCollection.insertOne(document);
        }
    }

    static public boolean checkServer(String serverName) {

        BasicDBObject queryObject = new BasicDBObject("serverName", serverName);
        FindIterable<Document> findIterable = bootstrapInfoCollection.find(queryObject);
        MongoCursor<Document> mongoCursor = findIterable.iterator();

        return mongoCursor.hasNext();
    }

    static public ArrayList<ServerInfoBean> getBootstrapInfo() {


        FindIterable<Document> findIterable = bootstrapInfoCollection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();

        ArrayList<ServerInfoBean> serverInfoBeans = new ArrayList<>();
        while (mongoCursor.hasNext()) {
            ServerInfoBean serverInfoBean = new ServerInfoBean();
            Document result = mongoCursor.next();
            serverInfoBean.setServerName(result.get("serverName").toString());
            serverInfoBean.setNewRegistrationUrl(result.get("newRegistrationUrl").toString());
            serverInfoBean.setUpdateRegistrationUrl(result.get("updateRegistrationUrl").toString());
            serverInfoBean.setDeRegistrationUrl(result.get("deRegistrationUrl").toString());
            serverInfoBeans.add(serverInfoBean);
        }

        return serverInfoBeans;
    }

    static public String getOneServerInfo(String serverName, String infoName) {

        BasicDBObject queryObject = new BasicDBObject("serverName", serverName);
        FindIterable<Document> findIterable = bootstrapInfoCollection.find(queryObject);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            Document result = mongoCursor.next();
            return result.get(infoName).toString();
        }

        return "";
    }

    static public NewRegistrationBean getNewRegistrationData() {

        FindIterable<Document> findIterable = registrationInfoCollection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        NewRegistrationBean newRegistrationBean = new NewRegistrationBean();
        while (mongoCursor.hasNext()) {
            Document result = mongoCursor.next();
            newRegistrationBean.setClientName(result.get("clientName").toString());
//            newRegistrationBean.setLifeTime(Float.parseFloat(result.get("lifeTime").toString()));
            newRegistrationBean.setBindingMode(result.get("bindingMode").toString());
            newRegistrationBean.setVersion(result.get("version").toString());

            return newRegistrationBean;
        }

        return null;
    }

    static public void updateRegistrationInfo(UpdateRegistrationBean updateRegistrationBean) {

        Document document = new Document();
        String newBindingMode = updateRegistrationBean.getBindingMode();
        String newVersion = updateRegistrationBean.getVersion();
        if (!newBindingMode.equals("")) {
            document.append("bindingMode", newBindingMode);
        }
        if (!newVersion.equals("")) {
            document.append("version", newVersion);
        }

        System.out.println(document);

        registrationInfoCollection.updateMany(Filters.eq("clientName", PropertiesUtils.getProperty("name")), new Document("$set", document));
    }

    static private ArrayList<String> getAllServerName() {

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
}