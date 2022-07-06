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
import java.util.UUID;


@RunListener.ThreadSafe
public class TheJunit4Listener extends RunListener {

    String testName;
    String test_ClassName;
    String test_status;
    String test_ThreadName;
    long test_startTime;
    long test_endTime;
    String PATH_OF_FOLDER = "build/test-results/individual-test-results";
    String testOwner="";
    String test_errorDescription="";





    @Override
    public void testRunStarted(final Description description) {
    }

    @Override
    public void testRunFinished(final Result result) {
    }

    @Override
    public void testStarted(final Description description) {

        test_startTime =System.currentTimeMillis();
        test_ThreadName =ManagementFactory.getRuntimeMXBean().getName()+"."+Thread.currentThread().getName()+"("+Thread.currentThread().getId()+")";
        testName =description.getMethodName();
        test_ClassName =description.getClassName();
        test_status ="SUCCESS";
        testOwner="";
        test_errorDescription="";
        if(description.getAnnotation(TestOwner.class)!=null){
            testOwner=description.getAnnotation(TestOwner.class).value();
        }


    }

    @Override
    public void testFinished(final Description description) {
        test_endTime =System.currentTimeMillis();

        String json="{\"name\":\""+ testName +"\" ," +
                    " \"className\":\""+ test_ClassName +"\" ," +
                     " \"testOwner\":\""+ testOwner +"\" ," +
                    " \"result\":\""+ test_status +"\" ," +
                    " \"StartTime\":"+ test_startTime +" ,"+
                    " \"EndTime\":"+ test_endTime +" ,"+
                    " \"ErrorDescription\": \""+ test_errorDescription +"\","+
                    "\"Thread\":\" "+ test_ThreadName +" \" }";

        File myReportDir = new File(PATH_OF_FOLDER);
        if (! Files. exists(Paths. get(PATH_OF_FOLDER))) {
            myReportDir. mkdir();
        }

        UUID uuid = UUID.randomUUID();
        String jsonFile_pathName ="build/test-results/individual-test-results/"+ testName +"-"+uuid.toString()+"-result.json";

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
        test_status ="FAILURE";

        test_errorDescription=failure.getMessage();

    }

    @Override
    public void testAssumptionFailure(final Failure failure) {

        test_status ="SKIPPED";
        test_errorDescription=failure.getMessage();

    }

    @Override
    public void testIgnored(final Description description) {
        test_startTime =System.currentTimeMillis();
        test_ThreadName =ManagementFactory.getRuntimeMXBean().getName()+"."+Thread.currentThread().getName()+"("+Thread.currentThread().getId()+")";
        testName =description.getMethodName();
        test_ClassName =description.getClassName();
        if(description.getAnnotation(TestOwner.class)!=null){
            testOwner=description.getAnnotation(TestOwner.class).value();
        }
        test_status ="SKIPPED";
        test_endTime =System.currentTimeMillis();



        String json="{\"name\":\""+ testName +"\" ," +
                " \"className\":\""+ test_ClassName +"\" ," +
                " \"testOwner\":\""+ testOwner +"\" ," +
                " \"result\":\""+ test_status +"\" ," +
                " \"StartTime\":"+ test_startTime +" ,"+
                " \"EndTime\":"+ test_endTime +" ,"+
                "\"Thread\":\" "+ test_ThreadName +" \" }";

        File myReportDir = new File(PATH_OF_FOLDER);
        if (! Files. exists(Paths. get(PATH_OF_FOLDER))) {
            myReportDir. mkdir();
        }

        UUID uuid = UUID.randomUUID();
        String jsonFile_pathName ="build/test-results/individual-test-results/"+ testName +"-"+uuid.toString()+"-result.json";

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