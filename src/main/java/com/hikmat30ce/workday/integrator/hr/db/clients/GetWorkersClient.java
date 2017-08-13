/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hikmat30ce.workday.integrator.hr.db.clients;

import com.hikmat30ce.workday.integrator.hr.db.security.Security;
import com.hikmat30ce.workday.integrator.hr.db.security.SecurityHeader;
import com.hikmat30ce.workday.integrator.hr.generated.BenefitPlanObjectIDType;
import com.hikmat30ce.workday.integrator.hr.generated.BenefitPlanObjectType;
import com.hikmat30ce.workday.integrator.hr.generated.CountryObjectIDType;
import com.hikmat30ce.workday.integrator.hr.generated.CountryObjectType;
import com.hikmat30ce.workday.integrator.hr.generated.EffectiveAndUpdatedDateTimeDataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hikmat30ce.workday.integrator.hr.generated.ObjectFactory;
import com.hikmat30ce.workday.integrator.hr.generated.GetWorkersRequestType;
import com.hikmat30ce.workday.integrator.hr.generated.WorkerRequestReferencesType;
import com.hikmat30ce.workday.integrator.hr.generated.WorkerObjectType;
import com.hikmat30ce.workday.integrator.hr.generated.WorkerObjectIDType;
import com.hikmat30ce.workday.integrator.hr.generated.GetWorkersResponseRootType;
import com.hikmat30ce.workday.integrator.hr.generated.OrganizationObjectIDType;
import com.hikmat30ce.workday.integrator.hr.generated.OrganizationObjectType;
import com.hikmat30ce.workday.integrator.hr.generated.PositionElementObjectIDType;
import com.hikmat30ce.workday.integrator.hr.generated.PositionElementObjectType;
import com.hikmat30ce.workday.integrator.hr.generated.ResponseFilterType;
import com.hikmat30ce.workday.integrator.hr.generated.TransactionLogCriteriaType;
import com.hikmat30ce.workday.integrator.hr.generated.TransactionLogObjectIDType;
import com.hikmat30ce.workday.integrator.hr.generated.TransactionLogObjectType;
import com.hikmat30ce.workday.integrator.hr.generated.WorkerRequestCriteriaType;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.core.env.Environment;

/**
 *
 * @author Hikmat Ullah
 * @skype huk9791
 * @email huk9191@gmail.com
 */
public class GetWorkersClient extends WebServiceGatewaySupport {

    @Autowired
    private Environment env;

    // Logger defination
    private static final Logger log = LoggerFactory.getLogger(GetWorkersClient.class);

    private List<String> employeeIDList;

    private String workertype = "Employee_ID";
    private boolean excludeInactiveWorkers = true;
    private boolean excludeEmployees = false;
    private boolean excludeContingentWorkers = false;
    private boolean includeSubordinateOrganizations = false;
    private boolean transactionLogEnabled = false;
    private String updated_From;
    private String updated_Through;
    private String effective_From;
    private String effective_Through;

    private boolean organization_ReferenceAdded = false;
    private String organization_ReferenceType;
    private String organization_ReferenceValue;

    private boolean country_ReferenceAdded = false;
    private String country_ReferenceType;
    private String country_ReferenceValue;

    private boolean position_ReferenceAdded = false;
    private String position_ReferenceType;
    private String position_ReferenceValue;

    private boolean event_ReferenceAdded = false;
    private String event_ReferenceType;
    private String event_ReferenceValue;

    private boolean benefit_Plan_ReferenceAdded = false;
    private String benefit_Plan_ReferenceType;
    private String benefit_Plan_ReferenceValue;

    private boolean responseFilterTypeEnabled = false;
    private int count;
    private int currentPage;

    private String asOfEffectiveDate = "empty";
    private String asOfEntryDateTime = "empty";

    private String timeZone;

    public GetWorkersResponseRootType GetWorkers() throws DatatypeConfigurationException, ParseException {

        log.info("Current per page count : " + env.getRequiredProperty("workday.hr.getworkers.count"));

        TimeZone _timeZone = this.getTimeZone().length() > 0 ? TimeZone.getTimeZone(this.getTimeZone()) : TimeZone.getDefault();

        ObjectFactory objectFactory = new ObjectFactory();

        //Get_Workers_Request
        GetWorkersRequestType getWorkersRequestType = new GetWorkersRequestType();
        //@bsvc:version
        getWorkersRequestType.setVersion(env.getRequiredProperty("workday.hr.target.version"));

        //Request_References
        if (this.getEmployeeIDList() != null && !this.getEmployeeIDList().isEmpty()) {
            WorkerRequestReferencesType workerRequestReferencesType = new WorkerRequestReferencesType();
            for (String employeeID : this.getEmployeeIDList()) {
                WorkerObjectType workerObjectType = new WorkerObjectType();

                List<WorkerObjectIDType> workerObjectIDTypeList = workerObjectType.getID();
                WorkerObjectIDType workerObjectIDType = new WorkerObjectIDType();
                workerObjectIDType.setType(this.getWorkertype());
                workerObjectIDType.setValue(employeeID);
                workerObjectIDTypeList.add(workerObjectIDType);

                List criteriaList = workerRequestReferencesType.getWorkerReference();
                criteriaList.add(workerObjectType);
            }
            getWorkersRequestType.setRequestReferences(workerRequestReferencesType);
        }

        //Request_Criteria
        WorkerRequestCriteriaType workerRequestCriteriaType = new WorkerRequestCriteriaType();
        workerRequestCriteriaType.setExcludeInactiveWorkers(this.isExcludeInactiveWorkers());
        workerRequestCriteriaType.setExcludeEmployees(this.isExcludeEmployees());
        workerRequestCriteriaType.setExcludeContingentWorkers(this.isExcludeContingentWorkers());
        workerRequestCriteriaType.setIncludeSubordinateOrganizations(excludeEmployees);

        if (this.isTransactionLogEnabled()) {

            //Transaction_Date_Range_Data
            EffectiveAndUpdatedDateTimeDataType effectiveAndUpdatedDateTimeDataType = new EffectiveAndUpdatedDateTimeDataType();
            GregorianCalendar gregorianCalendar = new GregorianCalendar();

            //Effective_From
            gregorianCalendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.getEffective_From()));
            XMLGregorianCalendar xMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
            gregorianCalendar.setTimeZone(_timeZone);
            effectiveAndUpdatedDateTimeDataType.setEffectiveFrom(xMLGregorianCalendar);

            //Effective_Through
            gregorianCalendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.getEffective_Through()));
            xMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
            
            gregorianCalendar.setTimeZone(_timeZone);
            effectiveAndUpdatedDateTimeDataType.setEffectiveThrough(xMLGregorianCalendar);

            //Updated_From
            gregorianCalendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.getUpdated_From()));
            xMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
            gregorianCalendar.setTimeZone(_timeZone);
            effectiveAndUpdatedDateTimeDataType.setUpdatedFrom(xMLGregorianCalendar);

            //Updated_Through
            gregorianCalendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.getUpdated_Through()));
            xMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
            gregorianCalendar.setTimeZone(_timeZone);
            effectiveAndUpdatedDateTimeDataType.setUpdatedThrough(xMLGregorianCalendar);

            //TransactionLogObjectType
            TransactionLogCriteriaType transactionLogCriteriaType = new TransactionLogCriteriaType();
            transactionLogCriteriaType.setTransactionDateRangeData(effectiveAndUpdatedDateTimeDataType);

            workerRequestCriteriaType.getTransactionLogCriteriaData().add(transactionLogCriteriaType);
        }
        //Organization_Reference
        if (this.isCountry_ReferenceAdded()) {
            OrganizationObjectType organizationObjectType = new OrganizationObjectType();
            OrganizationObjectIDType organizationObjectIDType = new OrganizationObjectIDType();
            organizationObjectIDType.setType(this.getOrganization_ReferenceType());
            organizationObjectIDType.setValue(this.getOrganization_ReferenceValue());
            organizationObjectType.getID().add(organizationObjectIDType);
            workerRequestCriteriaType.getOrganizationReference().add(organizationObjectType);
        }
        //Country_Reference
        if (this.isCountry_ReferenceAdded()) {
            CountryObjectType countryObjectType = new CountryObjectType();
            CountryObjectIDType countryObjectIDType = new CountryObjectIDType();
            countryObjectIDType.setType(this.getCountry_ReferenceType());
            countryObjectIDType.setValue(this.getCountry_ReferenceValue());
            countryObjectType.getID().add(countryObjectIDType);
            workerRequestCriteriaType.getCountryReference().add(countryObjectType);
        }

        //Position_Reference
        if (this.isPosition_ReferenceAdded()) {
            PositionElementObjectType positionElementObjectType = new PositionElementObjectType();
            PositionElementObjectIDType positionElementObjectIDType = new PositionElementObjectIDType();
            positionElementObjectIDType.setType(this.getPosition_ReferenceType());
            positionElementObjectIDType.setValue(this.getPosition_ReferenceValue());
            positionElementObjectType.getID().add(positionElementObjectIDType);
            workerRequestCriteriaType.getPositionReference().add(positionElementObjectType);
        }

        //Event_Reference
        if (this.isEvent_ReferenceAdded()) {
            TransactionLogObjectType transactionLogObjectType = new TransactionLogObjectType();
            TransactionLogObjectIDType transactionLogObjectIDType = new TransactionLogObjectIDType();
            transactionLogObjectIDType.setType(this.getEvent_ReferenceType());
            transactionLogObjectIDType.setValue(this.getEvent_ReferenceValue());
            transactionLogObjectType.getID().add(transactionLogObjectIDType);
            workerRequestCriteriaType.setEventReference(transactionLogObjectType);
        }

        //Benefit_Plan_Reference
        if (this.isBenefit_Plan_ReferenceAdded()) {
            BenefitPlanObjectType benefitPlanObjectType = new BenefitPlanObjectType();
            BenefitPlanObjectIDType benefitPlanObjectIDType = new BenefitPlanObjectIDType();
            benefitPlanObjectIDType.setType(this.getEvent_ReferenceType());
            benefitPlanObjectIDType.setValue(this.getEvent_ReferenceValue());
            benefitPlanObjectType.getID().add(benefitPlanObjectIDType);
            workerRequestCriteriaType.getBenefitPlanReference().add(benefitPlanObjectType);
        }

        getWorkersRequestType.setRequestCriteria(workerRequestCriteriaType);

        if (this.isResponseFilterTypeEnabled()) {
            ResponseFilterType responseFilterType = new ResponseFilterType();
            responseFilterType.setCount(BigDecimal.valueOf(this.getCount()));
            responseFilterType.setPage(BigDecimal.valueOf(this.getCurrentPage()));

            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            if (!this.getAsOfEffectiveDate().equals("empty")) {
                gregorianCalendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(this.getAsOfEffectiveDate()));
                gregorianCalendar.setTimeZone(_timeZone);
                responseFilterType.setAsOfEffectiveDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
            }
            if (!this.getAsOfEntryDateTime().equals("empty")) {
                gregorianCalendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(this.getAsOfEntryDateTime()));
                gregorianCalendar.setTimeZone(_timeZone);
                responseFilterType.setAsOfEntryDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
            }
            getWorkersRequestType.setResponseFilter(responseFilterType);
        }

        //Create request body
        Object createGetWorkersRequest = objectFactory.createGetWorkersRequest(getWorkersRequestType);
        //new SecurityHeader()

//        objectFactory.createGetWorkersResponseRootType();
        GetWorkersResponseRootType response = (GetWorkersResponseRootType) ((JAXBElement) getWebServiceTemplate()
                .marshalSendAndReceive(createGetWorkersRequest,
                        new SecurityHeader(
                                new Security(env.getRequiredProperty("workday.hr.target.username") + "@"
                                        + env.getRequiredProperty("workday.hr.target.tenant"),
                                        env.getRequiredProperty("workday.hr.target.password"))))).getValue();

        log.info("Current page: " + Integer.toString(this.getCurrentPage()));
        return response;
    }

    /**
     *
     * <pre>
     * get employee list of type String
     * @return employeeIDList of type String
     * </pre>
     * 
     */
    public List<String> getEmployeeIDList() {
        return employeeIDList;
    }

     /**
      * Set Employee or Contingent Worker List
      * @param employeeIDList 
      */
    public void setEmployeeIDList(List<String> employeeIDList) {
        this.employeeIDList = employeeIDList;
    }

    public String getWorkertype() {
        return workertype;
    }

    public void setWorkertype(String workertype) {
        this.workertype = workertype;
    }

    public boolean isExcludeInactiveWorkers() {
        return excludeInactiveWorkers;
    }

    public void setExcludeInactiveWorkers(boolean excludeInactiveWorkers) {
        this.excludeInactiveWorkers = excludeInactiveWorkers;
    }

    public boolean isExcludeEmployees() {
        return excludeEmployees;
    }

    public void setExcludeEmployees(boolean excludeEmployees) {
        this.excludeEmployees = excludeEmployees;
    }

    public boolean isExcludeContingentWorkers() {
        return excludeContingentWorkers;
    }

    public void setExcludeContingentWorkers(boolean excludeContingentWorkers) {
        this.excludeContingentWorkers = excludeContingentWorkers;
    }

    public boolean isIncludeSubordinateOrganizations() {
        return includeSubordinateOrganizations;
    }

    public void setIncludeSubordinateOrganizations(boolean includeSubordinateOrganizations) {
        this.includeSubordinateOrganizations = includeSubordinateOrganizations;
    }

    public boolean isTransactionLogEnabled() {
        return transactionLogEnabled;
    }

    public void setTransactionLogEnabled(boolean transactionLogEnabled) {
        this.transactionLogEnabled = transactionLogEnabled;
    }

    public String getUpdated_From() {
        return updated_From;
    }

    public void setUpdated_From(String updated_From) {
        this.updated_From = updated_From;
    }

    public String getUpdated_Through() {
        return updated_Through;
    }

    public void setUpdated_Through(String updated_Through) {
        this.updated_Through = updated_Through;
    }

    public String getEffective_From() {
        return effective_From;
    }

    public void setEffective_From(String effective_From) {
        this.effective_From = effective_From;
    }

    public String getEffective_Through() {
        return effective_Through;
    }

    public void setEffective_Through(String effective_Through) {
        this.effective_Through = effective_Through;
    }

    public boolean isOrganization_ReferenceAdded() {
        return organization_ReferenceAdded;
    }

    public void setOrganization_ReferenceAdded(boolean organization_ReferenceAdded) {
        this.organization_ReferenceAdded = organization_ReferenceAdded;
    }

    public String getOrganization_ReferenceType() {
        return organization_ReferenceType;
    }

    public void setOrganization_ReferenceType(String organization_ReferenceType) {
        this.organization_ReferenceType = organization_ReferenceType;
    }

    public String getOrganization_ReferenceValue() {
        return organization_ReferenceValue;
    }

    public void setOrganization_ReferenceValue(String organization_ReferenceValue) {
        this.organization_ReferenceValue = organization_ReferenceValue;
    }

    public boolean isCountry_ReferenceAdded() {
        return country_ReferenceAdded;
    }

    public void setCountry_ReferenceAdded(boolean country_ReferenceAdded) {
        this.country_ReferenceAdded = country_ReferenceAdded;
    }

    public String getCountry_ReferenceType() {
        return country_ReferenceType;
    }

    public void setCountry_ReferenceType(String country_ReferenceType) {
        this.country_ReferenceType = country_ReferenceType;
    }

    public String getCountry_ReferenceValue() {
        return country_ReferenceValue;
    }

    public void setCountry_ReferenceValue(String country_ReferenceValue) {
        this.country_ReferenceValue = country_ReferenceValue;
    }

    public boolean isPosition_ReferenceAdded() {
        return position_ReferenceAdded;
    }

    public void setPosition_ReferenceAdded(boolean position_ReferenceAdded) {
        this.position_ReferenceAdded = position_ReferenceAdded;
    }

    public String getPosition_ReferenceType() {
        return position_ReferenceType;
    }

    public void setPosition_ReferenceType(String position_ReferenceType) {
        this.position_ReferenceType = position_ReferenceType;
    }

    public String getPosition_ReferenceValue() {
        return position_ReferenceValue;
    }

    public void setPosition_ReferenceValue(String position_ReferenceValue) {
        this.position_ReferenceValue = position_ReferenceValue;
    }

    public boolean isEvent_ReferenceAdded() {
        return event_ReferenceAdded;
    }

    public void setEvent_ReferenceAdded(boolean event_ReferenceAdded) {
        this.event_ReferenceAdded = event_ReferenceAdded;
    }

    public String getEvent_ReferenceType() {
        return event_ReferenceType;
    }

    public void setEvent_ReferenceType(String event_ReferenceType) {
        this.event_ReferenceType = event_ReferenceType;
    }

    public String getEvent_ReferenceValue() {
        return event_ReferenceValue;
    }

    public void setEvent_ReferenceValue(String event_ReferenceValue) {
        this.event_ReferenceValue = event_ReferenceValue;
    }

    public boolean isBenefit_Plan_ReferenceAdded() {
        return benefit_Plan_ReferenceAdded;
    }

    public void setBenefit_Plan_ReferenceAdded(boolean benefit_Plan_ReferenceAdded) {
        this.benefit_Plan_ReferenceAdded = benefit_Plan_ReferenceAdded;
    }

    public String getBenefit_Plan_ReferenceType() {
        return benefit_Plan_ReferenceType;
    }

    public void setBenefit_Plan_ReferenceType(String benefit_Plan_ReferenceType) {
        this.benefit_Plan_ReferenceType = benefit_Plan_ReferenceType;
    }

    public String getBenefit_Plan_ReferenceValue() {
        return benefit_Plan_ReferenceValue;
    }

    public void setBenefit_Plan_ReferenceValue(String benefit_Plan_ReferenceValue) {
        this.benefit_Plan_ReferenceValue = benefit_Plan_ReferenceValue;
    }

    public int getCount() {
        return count != 0 ? count : Integer.parseInt(env.getRequiredProperty("workday.hr.getworkers.count"));
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCurrentPage() {
        return currentPage != 0 ? currentPage : 1;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isResponseFilterTypeEnabled() {
        return responseFilterTypeEnabled;
    }

    public void setResponseFilterTypeEnabled(boolean responseFilterTypeEnabled) {
        this.responseFilterTypeEnabled = responseFilterTypeEnabled;
    }

    public String getAsOfEffectiveDate() {
        return asOfEffectiveDate;
    }

    public void setAsOfEffectiveDate(String asOfEffectiveDate) {
        this.asOfEffectiveDate = asOfEffectiveDate;
    }

    public String getAsOfEntryDateTime() {
        return asOfEntryDateTime;
    }

    public void setAsOfEntryDateTime(String asOfEntryDateTime) {
        this.asOfEntryDateTime = asOfEntryDateTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

}
