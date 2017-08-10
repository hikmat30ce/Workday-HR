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

package com.hikmat30ce.workday.integrator.hr.db.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hikmat Ullah
 * @skype huk9791
 * @email huk9191@gmail.com
 */
@Entity
@Table(catalog = "dbworkdayhr", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tblgetworkers.findAll", query = "SELECT t FROM Tblgetworkers t"),
    @NamedQuery(name = "Tblgetworkers.findById", query = "SELECT t FROM Tblgetworkers t WHERE t.id = :id"),
    @NamedQuery(name = "Tblgetworkers.findByEmployeeid", query = "SELECT t FROM Tblgetworkers t WHERE t.employeeid = :employeeid"),
    @NamedQuery(name = "Tblgetworkers.findByUserid", query = "SELECT t FROM Tblgetworkers t WHERE t.userid = :userid"),
    @NamedQuery(name = "Tblgetworkers.findByFormattedname", query = "SELECT t FROM Tblgetworkers t WHERE t.formattedname = :formattedname"),
    @NamedQuery(name = "Tblgetworkers.findByCountryname", query = "SELECT t FROM Tblgetworkers t WHERE t.countryname = :countryname"),
    @NamedQuery(name = "Tblgetworkers.findByFirstname", query = "SELECT t FROM Tblgetworkers t WHERE t.firstname = :firstname"),
    @NamedQuery(name = "Tblgetworkers.findByLastname", query = "SELECT t FROM Tblgetworkers t WHERE t.lastname = :lastname"),
    @NamedQuery(name = "Tblgetworkers.findByGender", query = "SELECT t FROM Tblgetworkers t WHERE t.gender = :gender"),
    @NamedQuery(name = "Tblgetworkers.findByDob", query = "SELECT t FROM Tblgetworkers t WHERE t.dob = :dob"),
    @NamedQuery(name = "Tblgetworkers.findByMaritalstatus", query = "SELECT t FROM Tblgetworkers t WHERE t.maritalstatus = :maritalstatus"),
    @NamedQuery(name = "Tblgetworkers.findByEthnicity", query = "SELECT t FROM Tblgetworkers t WHERE t.ethnicity = :ethnicity"),
    @NamedQuery(name = "Tblgetworkers.findByCitizenshipstatuscode", query = "SELECT t FROM Tblgetworkers t WHERE t.citizenshipstatuscode = :citizenshipstatuscode"),
    @NamedQuery(name = "Tblgetworkers.findBySocialsecuritynumber", query = "SELECT t FROM Tblgetworkers t WHERE t.socialsecuritynumber = :socialsecuritynumber"),
    @NamedQuery(name = "Tblgetworkers.findByPassportid", query = "SELECT t FROM Tblgetworkers t WHERE t.passportid = :passportid"),
    @NamedQuery(name = "Tblgetworkers.findByPositionid", query = "SELECT t FROM Tblgetworkers t WHERE t.positionid = :positionid"),
    @NamedQuery(name = "Tblgetworkers.findByPositiontitle", query = "SELECT t FROM Tblgetworkers t WHERE t.positiontitle = :positiontitle"),
    @NamedQuery(name = "Tblgetworkers.findByBusinesstitle", query = "SELECT t FROM Tblgetworkers t WHERE t.businesstitle = :businesstitle"),
    @NamedQuery(name = "Tblgetworkers.findByWorkertype", query = "SELECT t FROM Tblgetworkers t WHERE t.workertype = :workertype"),
    @NamedQuery(name = "Tblgetworkers.findByStartdate", query = "SELECT t FROM Tblgetworkers t WHERE t.startdate = :startdate"),
    @NamedQuery(name = "Tblgetworkers.findByTimetype", query = "SELECT t FROM Tblgetworkers t WHERE t.timetype = :timetype")})
public class Tblgetworkers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Size(max = 20)
    @Column(length = 20)
    private String employeeid;
    @Size(max = 20)
    @Column(length = 20)
    private String userid;
    @Size(max = 100)
    @Column(length = 100)
    private String formattedname;
    @Size(max = 100)
    @Column(length = 100)
    private String countryname;
    @Size(max = 100)
    @Column(length = 100)
    private String firstname;
    @Size(max = 100)
    @Column(length = 100)
    private String lastname;
    @Size(max = 20)
    @Column(length = 20)
    private String gender;
    @Size(max = 20)
    @Column(length = 20)
    private String dob;
    @Size(max = 100)
    @Column(length = 100)
    private String maritalstatus;
    @Size(max = 100)
    @Column(length = 100)
    private String ethnicity;
    @Size(max = 100)
    @Column(length = 100)
    private String citizenshipstatuscode;
    @Size(max = 50)
    @Column(length = 50)
    private String socialsecuritynumber;
    @Size(max = 50)
    @Column(length = 50)
    private String passportid;
    @Size(max = 50)
    @Column(length = 50)
    private String positionid;
    @Size(max = 50)
    @Column(length = 50)
    private String positiontitle;
    @Size(max = 50)
    @Column(length = 50)
    private String businesstitle;
    @Size(max = 50)
    @Column(length = 50)
    private String workertype;
    @Size(max = 40)
    @Column(length = 40)
    private String startdate;
    @Size(max = 20)
    @Column(length = 20)
    private String timetype;

    public Tblgetworkers() {
    }

    public Tblgetworkers(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFormattedname() {
        return formattedname;
    }

    public void setFormattedname(String formattedname) {
        this.formattedname = formattedname;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMaritalstatus() {
        return maritalstatus;
    }

    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getCitizenshipstatuscode() {
        return citizenshipstatuscode;
    }

    public void setCitizenshipstatuscode(String citizenshipstatuscode) {
        this.citizenshipstatuscode = citizenshipstatuscode;
    }

    public String getSocialsecuritynumber() {
        return socialsecuritynumber;
    }

    public void setSocialsecuritynumber(String socialsecuritynumber) {
        this.socialsecuritynumber = socialsecuritynumber;
    }

    public String getPassportid() {
        return passportid;
    }

    public void setPassportid(String passportid) {
        this.passportid = passportid;
    }

    public String getPositionid() {
        return positionid;
    }

    public void setPositionid(String positionid) {
        this.positionid = positionid;
    }

    public String getPositiontitle() {
        return positiontitle;
    }

    public void setPositiontitle(String positiontitle) {
        this.positiontitle = positiontitle;
    }

    public String getBusinesstitle() {
        return businesstitle;
    }

    public void setBusinesstitle(String businesstitle) {
        this.businesstitle = businesstitle;
    }

    public String getWorkertype() {
        return workertype;
    }

    public void setWorkertype(String workertype) {
        this.workertype = workertype;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getTimetype() {
        return timetype;
    }

    public void setTimetype(String timetype) {
        this.timetype = timetype;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tblgetworkers)) {
            return false;
        }
        Tblgetworkers other = (Tblgetworkers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hikmat30ce.workday.integrator.hr.db.models.Tblgetworkers[ id=" + id + " ]";
    }

}
