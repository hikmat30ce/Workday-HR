/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hikmat30ce.workday.integrator.hr.db.repository;


import com.hikmat30ce.workday.integrator.hr.db.models.Tblgetworkers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;


/**
 *
 * @author Hikmat Ullah
 * @skype huk9791
 * @email huk9191@gmail.com
 */
@Component
public interface TblgetworkersRepository extends CrudRepository<Tblgetworkers, Long> {

}
