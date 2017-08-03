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

    public WorkersResponseDataType GetWorkers() {
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

        req.setRequestReferences(workerRequestReferencesType);
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
        return response.getResponseData();
    }
}
