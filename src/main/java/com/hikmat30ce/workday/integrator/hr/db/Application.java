package com.hikmat30ce.workday.integrator.hr.db;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.hikmat30ce.workday.integrator.hr.db.clients.WorkdayHRClient;
import com.hikmat30ce.workday.integrator.hr.db.config.WorkdayHRConfiguration;
import com.hikmat30ce.workday.integrator.hr.generated.CountryObjectIDType;
import com.hikmat30ce.workday.integrator.hr.generated.WorkerObjectIDType;
import com.hikmat30ce.workday.integrator.hr.generated.WorkerType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.hikmat30ce.workday.integrator.hr.generated.WorkersResponseDataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Hikmat Ullah
 * @skype huk9791
 * @email huk9191@gmail.com
 */
@SpringBootApplication
@ComponentScan
@EnableScheduling
@EnableAutoConfiguration
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WorkdayHRConfiguration.class);
        WorkdayHRClient client = context.getBean(WorkdayHRClient.class);
        WorkersResponseDataType response = client.GetWorkers();

        for (WorkerType worker : response.getWorker()) {
            log.info("Worker_Reference Descriptor: " + worker.getWorkerReference().getDescriptor());

            for (WorkerObjectIDType Worker_ID : worker.getWorkerReference().getID()) {
                switch (Worker_ID.getType()) {
                    case "WID":
                        log.info("Worker_Reference WID: " + Worker_ID.getValue());
                        break;
                    case "Employee_ID":
                        log.info("Worker_Reference Employee_ID: " + Worker_ID.getValue());
                        break;
                }
            }
            log.info("UserID: " + worker.getWorkerData().getUserID());
            log.info("WorkerID: " + worker.getWorkerData().getWorkerID());
            log.info("Country_Reference Descriptor: " + worker.getWorkerData()
                    .getPersonalData().getNameData()
                    .getLegalNameData().getNameDetailData()
                    .getCountryReference().getDescriptor()
            );

            for (CountryObjectIDType ID : worker.getWorkerData()
                    .getPersonalData().getNameData()
                    .getLegalNameData().getNameDetailData()
                    .getCountryReference().getID()) {
                switch (ID.getType()) {
                    case "WID":
                        log.info("Country_Reference WID: " + ID.getValue());
                        break;

                    case "ISO_3166-1_Alpha-2_Code":
                        log.info("Country_Reference ISO_3166-1_Alpha-2_Code: " + ID.getValue());
                        break;
                    case "ISO_3166-1_Alpha-3_Code":
                        log.info("Country_Reference ISO_3166-1_Alpha-3_Code: " + ID.getValue());
                        break;
                    case "ISO_3166-1_Numeric-3_Code":
                        log.info("Country_Reference ISO_3166-1_Numeric-3_Code: " + ID.getValue());
                        break;
                }
            }

            log.info("First_Name: " + worker.getWorkerData()
                    .getPersonalData().getNameData()
                    .getLegalNameData().getNameDetailData().getFirstName()
            );

            log.info("Last_Name: " + worker.getWorkerData()
                    .getPersonalData().getNameData()
                    .getLegalNameData().getNameDetailData().getLastName()
            );
            
            //Preferred_Name_Data
             log.info("Preferred_Name_Data Name_Detail_Data Country_Reference Descriptor: " + worker.getWorkerData()
                    .getPersonalData().getNameData()
                    .getPreferredNameData().getNameDetailData().getCountryReference()
                     .getDescriptor()
            );
             for (CountryObjectIDType ID : worker.getWorkerData()
                    .getPersonalData().getNameData()
                    .getPreferredNameData().getNameDetailData()
                    .getCountryReference().getID()) {
                switch (ID.getType()) {
                    case "WID":
                        log.info("Country_Reference WID: " + ID.getValue());
                        break;

                    case "ISO_3166-1_Alpha-2_Code":
                        log.info("Preferred_Name_Data Name_Detail_Data Country_Reference ISO_3166-1_Alpha-2_Code: " + ID.getValue());
                        break;
                    case "ISO_3166-1_Alpha-3_Code":
                        log.info("Preferred_Name_Data Name_Detail_Data Country_Reference ISO_3166-1_Alpha-3_Code: " + ID.getValue());
                        break;
                    case "ISO_3166-1_Numeric-3_Code":
                        log.info("Preferred_Name_Data Name_Detail_Data Country_Reference ISO_3166-1_Numeric-3_Code: " + ID.getValue());
                        break;
                }
            }
            

        }

    }
}
