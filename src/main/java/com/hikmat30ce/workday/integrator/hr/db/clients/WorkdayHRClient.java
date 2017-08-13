/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hikmat30ce.workday.integrator.hr.db.clients;

import com.hikmat30ce.workday.integrator.hr.db.security.Security;
import com.hikmat30ce.workday.integrator.hr.db.security.SecurityHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hikmat30ce.workday.integrator.hr.generated.ObjectFactory;
import com.hikmat30ce.workday.integrator.hr.generated.WorkersResponseDataType;
import com.hikmat30ce.workday.integrator.hr.generated.GetWorkersRequestType;
import com.hikmat30ce.workday.integrator.hr.generated.WorkerRequestReferencesType;
import com.hikmat30ce.workday.integrator.hr.generated.WorkerObjectType;
import com.hikmat30ce.workday.integrator.hr.generated.WorkerObjectIDType;
import com.hikmat30ce.workday.integrator.hr.generated.GetWorkersResponseRootType;
import com.hikmat30ce.workday.integrator.hr.generated.ResponseFilterType;
import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.core.env.Environment;

/**
 *
 * @author Hikmat Ullah
 * @skype huk9791
 * @email huk9191@gmail.com
 */
public class WorkdayHRClient extends WebServiceGatewaySupport {

    @Autowired
    Environment env;
    private static final Logger log = LoggerFactory.getLogger(WorkdayHRClient.class);

    public GetWorkersResponseRootType GetWorkers( int currentPage) {
        log.info("Current per page count : " + env.getRequiredProperty("workday.hr.getworkers.count"));
        ObjectFactory objectFactory = new ObjectFactory();
        GetWorkersRequestType req = new GetWorkersRequestType();

        WorkerRequestReferencesType workerRequestReferencesType = new WorkerRequestReferencesType();

        workerRequestReferencesType.setSkipNonExistingInstances(Boolean.FALSE);

        //WorkerObjectType
        WorkerObjectType workerObjectType = new WorkerObjectType();

        //WorkerObjectIDType
        WorkerObjectIDType workerObjectIDType = new WorkerObjectIDType();

        workerObjectIDType.setType("Employee_ID");
        workerObjectIDType.setValue("21005");

        workerObjectType.getID().add(workerObjectIDType);

        workerRequestReferencesType.getWorkerReference().add(workerObjectType);

        //Response_Filter Start
        ResponseFilterType responseFilterType = new ResponseFilterType();
        responseFilterType.setCount(BigDecimal.valueOf(Integer.parseInt(env.getRequiredProperty("workday.hr.getworkers.count")))); //count set to 999 per page
        responseFilterType.setPage(BigDecimal.valueOf(currentPage));
        //Response_Filter End

        // req.setRequestReferences(workerRequestReferencesType); // for now single user call is dissabled 
        req.setResponseFilter(responseFilterType);
        req.setVersion(env.getRequiredProperty("workday.hr.target.version"));

        Object ss = objectFactory.createGetWorkersRequest(req);
        //new SecurityHeader()

        objectFactory.createGetWorkersResponseRootType();

        GetWorkersResponseRootType response = (GetWorkersResponseRootType) ((JAXBElement) getWebServiceTemplate()
                .marshalSendAndReceive(ss,
                        new SecurityHeader(
                                new Security(env.getRequiredProperty("workday.hr.target.username") + "@"
                                        + env.getRequiredProperty("workday.hr.target.tenant"),
                                        env.getRequiredProperty("workday.hr.target.password"))))).getValue();

        log.info("Current page: " + Integer.toString(currentPage));
        return response;
    }
}
