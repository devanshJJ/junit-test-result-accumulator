package io.timeline.junitTest.result;
import io.timeline.junitTest.annotation.TestOwner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;
import io.timeline.junitTest.result.JunitTestResultObject;

@RunListener.ThreadSafe
public class TheJunit4Listener extends RunListener {


    String PATH_OF_FOLDER = "build/test-results/individual-test-results";

    HashMap<String,JunitTestResultObject> storeTestsResult=new HashMap<>();



    @Override
    public void testRunStarted(final Description description) {
    }

    @Override
    public void testRunFinished(final Result result) {
    }

    @Override
    public void testStarted(final Description description) {

        JunitTestResultObject currTest =new JunitTestResultObject();
        currTest.test_startTime =System.currentTimeMillis();
        currTest.test_ThreadName =ManagementFactory.getRuntimeMXBean().getName()+"."+Thread.currentThread().getName()+"("+Thread.currentThread().getId()+")";
        currTest.testName =description.getMethodName();
        currTest.test_ClassName =description.getClassName();
        currTest.test_status ="SUCCESS";
        currTest.testOwner="";
        currTest.test_errorDescription="";
        if(description.getAnnotation(TestOwner.class)!=null){
            currTest.testOwner=description.getAnnotation(TestOwner.class).value();
        }

        storeTestsResult.put(currTest.test_ThreadName,currTest);


    }

    @Override
    public void testFinished(final Description description) {
        String mapKey=ManagementFactory.getRuntimeMXBean().getName()+"."+Thread.currentThread().getName()+"("+Thread.currentThread().getId()+")";
        JunitTestResultObject currTest=storeTestsResult.get(mapKey);
        currTest.test_endTime =System.currentTimeMillis();

        String json="{\"name\":\""+ currTest.testName +"\" ," +
                    " \"className\":\""+ currTest.test_ClassName +"\" ," +
                     " \"testOwner\":\""+ currTest.testOwner +"\" ," +
                    " \"result\":\""+ currTest.test_status +"\" ," +
                    " \"StartTime\":"+ currTest.test_startTime +" ,"+
                    " \"EndTime\":"+ currTest.test_endTime +" ,"+
                    " \"ErrorDescription\": \""+ currTest.test_errorDescription +"\","+
                    "\"Thread\":\" "+ currTest.test_ThreadName +" \" }";

        File myReportDir = new File(PATH_OF_FOLDER);
        if (! Files. exists(Paths. get(PATH_OF_FOLDER))) {
            myReportDir. mkdir();
        }

        UUID uuid = UUID.randomUUID();
        String jsonFile_pathName ="build/test-results/individual-test-results/"+ currTest.testName +"-"+uuid.toString()+"-result.json";

        File testResult_file = new File(jsonFile_pathName);
        try {
            testResult_file.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred while creating test result json files");
            e.printStackTrace();
        }
        try {
            FileWriter testResult_Writer = new FileWriter(jsonFile_pathName);
            testResult_Writer.write(json);
            testResult_Writer.close();
//            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing test result");
            e.printStackTrace();
        }

    }
    @Override
    public void testFailure(final Failure failure) {
        String mapKey=ManagementFactory.getRuntimeMXBean().getName()+"."+Thread.currentThread().getName()+"("+Thread.currentThread().getId()+")";
        JunitTestResultObject currTest=storeTestsResult.get(mapKey);
        currTest.test_status ="FAILURE";

        currTest.test_errorDescription=failure.getMessage();

    }

    @Override
    public void testAssumptionFailure(final Failure failure) {
        String mapKey=ManagementFactory.getRuntimeMXBean().getName()+"."+Thread.currentThread().getName()+"("+Thread.currentThread().getId()+")";
        JunitTestResultObject currTest=storeTestsResult.get(mapKey);

        currTest.test_status ="SKIPPED";
        currTest.test_errorDescription=failure.getMessage();

    }

    @Override
    public void testIgnored(final Description description) {
        JunitTestResultObject currTest=new JunitTestResultObject();

        currTest.test_startTime =System.currentTimeMillis();
        currTest.test_ThreadName =ManagementFactory.getRuntimeMXBean().getName()+"."+Thread.currentThread().getName()+"("+Thread.currentThread().getId()+")";
        currTest.testName =description.getMethodName();
        currTest.test_ClassName =description.getClassName();
        if(description.getAnnotation(TestOwner.class)!=null){
            currTest.testOwner=description.getAnnotation(TestOwner.class).value();
        }
        currTest.test_status ="SKIPPED";
        currTest.test_endTime =System.currentTimeMillis();



        String json="{\"name\":\""+ currTest.testName +"\" ," +
                " \"className\":\""+ currTest.test_ClassName +"\" ," +
                " \"testOwner\":\""+ currTest.testOwner +"\" ," +
                " \"result\":\""+ currTest.test_status +"\" ," +
                " \"StartTime\":"+ currTest.test_startTime +" ,"+
                " \"EndTime\":"+ currTest.test_endTime +" ,"+
                "\"Thread\":\" "+ currTest.test_ThreadName +" \" }";

        File myReportDir = new File(PATH_OF_FOLDER);
        if (! Files. exists(Paths. get(PATH_OF_FOLDER))) {
            myReportDir. mkdir();
        }

        UUID uuid = UUID.randomUUID();
        String jsonFile_pathName ="build/test-results/individual-test-results/"+ currTest.testName +"-"+uuid.toString()+"-result.json";

        File testResult_file = new File(jsonFile_pathName);
        try {
            testResult_file.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred while creating test result json files");
            e.printStackTrace();
        }
        try {
            FileWriter testResult_Writer = new FileWriter(jsonFile_pathName);
            testResult_Writer.write(json);
            testResult_Writer.close();
//            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing test result");
            e.printStackTrace();
        }

    }


}