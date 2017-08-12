/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hikmat30ce.workday.integrator.hr.db.helpers;

import com.hikmat30ce.workday.integrator.hr.generated.CitizenshipStatusObjectIDType;
import com.hikmat30ce.workday.integrator.hr.generated.CitizenshipStatusObjectType;
import com.hikmat30ce.workday.integrator.hr.generated.CountryObjectType;
import com.hikmat30ce.workday.integrator.hr.generated.EthnicityObjectType;
import com.hikmat30ce.workday.integrator.hr.generated.GenderObjectType;
import com.hikmat30ce.workday.integrator.hr.generated.LegalNameDataType;
import com.hikmat30ce.workday.integrator.hr.generated.MaritalStatusObjectType;
import com.hikmat30ce.workday.integrator.hr.generated.NationalIDDataType;
import com.hikmat30ce.workday.integrator.hr.generated.NationalIDType;
import com.hikmat30ce.workday.integrator.hr.generated.PassportIDDataType;
import com.hikmat30ce.workday.integrator.hr.generated.PassportIDType;
import com.hikmat30ce.workday.integrator.hr.generated.PersonIdentificationDataType;
import com.hikmat30ce.workday.integrator.hr.generated.PersonNameDataType;
import com.hikmat30ce.workday.integrator.hr.generated.PersonNameDetailDataType;
import com.hikmat30ce.workday.integrator.hr.generated.PersonalInformationDataType;
import com.hikmat30ce.workday.integrator.hr.generated.WorkerDataType;
import com.hikmat30ce.workday.integrator.hr.generated.WorkerObjectIDType;
import com.hikmat30ce.workday.integrator.hr.generated.WorkerType;

/**
 *
 * @author Hikmat Ullah
 * @skype huk9791
 * @email huk9191@gmail.com
 */

public class GetWorkersHelper {

    private WorkerType _workerType;
    private String _employeeid;
    private String _userid;
    private String _dob;
    private String _formattedname;
    private String _firstname;
    private String _lastname;
    private String _countryname;
    private String _gender;
    private String _maritalstatus;
    private String _Ethnicity;
    private String _citizenshipstatuscode;
    private String _socialsecuritynumber;
    private String _passportid;

    public GetWorkersHelper(WorkerType workerType) {
        this._workerType = workerType;
        this.LoadWorkers();
    }

    private void LoadWorkers() {

        //log.info("Worker_Reference Descriptor: " + _workerType.getWorkerReference().getDescriptor());
        for (WorkerObjectIDType Worker_ID : this._workerType.getWorkerReference().getID()) {
            switch (Worker_ID.getType()) {

                case "Employee_ID":
                    this.setEmployeeid(Worker_ID.getValue());
                    //log.info("Worker_Reference Employee_ID: " + Worker_ID.getValue());
                    break;
            }
        }
        if (this._workerType.getWorkerData() != null) {

            WorkerDataType workerdata = this._workerType.getWorkerData();

            //Worker_Data>Worker_ID
            this.setUserid(workerdata.getUserID() != null
                    ? workerdata.getUserID()
                    : null);

            //Worker_Data>Personal_Data
            if (workerdata.getPersonalData() != null) {
                PersonalInformationDataType personalInformationDataType = workerdata.getPersonalData();

                //Birth_Date
                this.setDob(personalInformationDataType.getBirthDate() != null
                        ? personalInformationDataType.getBirthDate().toString()
                        : null);
                //Worker_Data>Personal_Data>Name_Data
                if (personalInformationDataType.getNameData() != null) {
                    PersonNameDataType personNameDataType = personalInformationDataType.getNameData();

                    //Worker_Data>Personal_Data>Name_Data>Name_Detail_Data
                    if (personNameDataType.getLegalNameData() != null) {

                        LegalNameDataType legalNameDataType = personNameDataType.getLegalNameData();

                        if (legalNameDataType.getNameDetailData() != null) {
                            PersonNameDetailDataType personNameDetailDataType = legalNameDataType.getNameDetailData();

                            //Formatted_Name 
                            this.setFormattedname(personNameDetailDataType.getFormattedName() != null
                                    ? personNameDetailDataType.getFormattedName()
                                    : null);

                            //First_Name
                            this.setFirstname(personNameDetailDataType.getLastName() != null
                                    ? personNameDetailDataType.getLastName()
                                    : null);

                            //Last_Name
                            this.setLastname(
                                    personNameDetailDataType.getFirstName() != null
                                            ? personNameDetailDataType.getFirstName()
                                            : null);

                            if (personNameDetailDataType.getCountryReference() != null) {
                                CountryObjectType countryObjectType = personNameDetailDataType.getCountryReference();

                                //Country_Reference/@Descriptor
                                this.setCountryname(countryObjectType.getDescriptor() != null
                                        ? countryObjectType.getDescriptor()
                                        : null);
                            }

                        }

                    }
                }

                //Gender
                if (personalInformationDataType.getGenderReference() != null) {
                    GenderObjectType genderObjectType = personalInformationDataType.getGenderReference();

                    this.setGender(genderObjectType.getDescriptor() != null
                            ? genderObjectType.getDescriptor()
                            : null);
                }

                //Marital_Status_Reference
                if (personalInformationDataType.getMaritalStatusReference() != null) {
                    MaritalStatusObjectType maritalStatusObjectType = personalInformationDataType.getMaritalStatusReference();
                    this.setMaritalstatus(maritalStatusObjectType.getDescriptor() != null
                            ? maritalStatusObjectType.getDescriptor()
                            : null);
                }

                //Ethnicity_Reference
                if (personalInformationDataType.getEthnicityReference() != null) {
                    //List of ethnicity
                    for (EthnicityObjectType ethnicityObjectType
                            : personalInformationDataType.getEthnicityReference()) {
                        this.setEthnicity(ethnicityObjectType.getDescriptor() != null
                                ? ethnicityObjectType.getDescriptor()
                                : null);
                        break;
                    }
                }
                //Citizenship_Status_Reference
                if (personalInformationDataType.getCitizenshipStatusReference() != null) {
                    for (CitizenshipStatusObjectType citizenshipStatusObjectType
                            : personalInformationDataType.getCitizenshipStatusReference()) {
                        for (CitizenshipStatusObjectIDType citizenshipStatusObjectIDType
                                : citizenshipStatusObjectType.getID()) {
                            if (citizenshipStatusObjectIDType.getType() != null
                                    && "Citizenship_Status_Code".equals(citizenshipStatusObjectIDType.getType())) {
                                this.setCitizenshipstatuscode(citizenshipStatusObjectIDType.getValue() != null
                                        ? citizenshipStatusObjectIDType.getValue()
                                        : null);
                            }
                        }

                    }
                }
                //Identification_Data
                if (personalInformationDataType.getIdentificationData() != null) {
                    PersonIdentificationDataType personIdentificationDataType = personalInformationDataType.getIdentificationData();

                    //Social Security Number
                    for (NationalIDType nationalIDType : personIdentificationDataType.getNationalID()) {
                        if (nationalIDType.getNationalIDData() != null) {
                            NationalIDDataType nationalIDDataType = nationalIDType.getNationalIDData();
                            if (nationalIDDataType.getIDTypeReference() != null
                                    && nationalIDDataType.getIDTypeReference()
                                    .getDescriptor()
                                    .toLowerCase()
                                    .contains("ssn")) {
                                this.setSocialsecuritynumber(nationalIDDataType.getID() != null
                                        ? nationalIDDataType.getID()
                                        : null);
                            }
                        }
                    }

                    //Passport_ID
                    for (PassportIDType passportIDType : personIdentificationDataType.getPassportID()) {
                        if (passportIDType.getPassportIDData() != null) {
                            PassportIDDataType passportIDDataType = passportIDType.getPassportIDData();
                            this.setPassportid(passportIDDataType.getID() != null
                                    ? passportIDDataType.getID()
                                    : null);
                        }
                    }

                }

            }
        }
    }

    public String getFormattedname() {
        return _formattedname;
    }

    public void setFormattedname(String _formattedname) {
        this._formattedname = _formattedname;
    }

    public String getDob() {
        return _dob;
    }

    public void setDob(String _dob) {
        this._dob = _dob;
    }

    public String getUserid() {
        return _userid;
    }

    public void setUserid(String _userid) {
        this._userid = _userid;
    }

    public String getEmployeeid() {
        return _employeeid;
    }

    public void setEmployeeid(String _employeeid) {
        this._employeeid = _employeeid;
    }

    public String getFirstname() {
        return _firstname;
    }

    public void setFirstname(String _firstname) {
        this._firstname = _firstname;
    }

    public String getLastname() {
        return _lastname;
    }

    public void setLastname(String _lastname) {
        this._lastname = _lastname;
    }

    public String getCountryname() {
        return _countryname;
    }

    public void setCountryname(String _countryname) {
        this._countryname = _countryname;
    }

    public String getGender() {
        return _gender;
    }

    public void setGender(String _gender) {
        this._gender = _gender;
    }

    public String getMaritalstatus() {
        return _maritalstatus;
    }

    public void setMaritalstatus(String _maritalstatus) {
        this._maritalstatus = _maritalstatus;
    }

    public String getEthnicity() {
        return _Ethnicity;
    }

    public void setEthnicity(String _Ethnicity) {
        this._Ethnicity = _Ethnicity;
    }

    public String getSocialsecuritynumber() {
        return _socialsecuritynumber;
    }

    public void setSocialsecuritynumber(String _socialsecuritynumber) {
        this._socialsecuritynumber = _socialsecuritynumber;
    }

    public String getPassportid() {
        return _passportid;
    }

    public void setPassportid(String _passportid) {
        this._passportid = _passportid;
    }

    public String getCitizenshipstatuscode() {
        return _citizenshipstatuscode;
    }

    public void setCitizenshipstatuscode(String _citizenshipstatuscode) {
        this._citizenshipstatuscode = _citizenshipstatuscode;
    }

}
