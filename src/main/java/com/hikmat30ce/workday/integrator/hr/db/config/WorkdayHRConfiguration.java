/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hikmat30ce.workday.integrator.hr.db.config;

import com.hikmat30ce.workday.integrator.hr.db.clients.WorkdayHRClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author Hikmat Ullah
 * @skype huk9791
 * @email huk9191@gmail.com
 */
@PropertySource("workday.properties")
@Configuration
public class WorkdayHRConfiguration {

    @Autowired
    Environment env;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.hikmat30ce.workday.integrator.hr.generated");
        return marshaller;
    }

    @Bean
    public WorkdayHRClient workdayHRClient(Jaxb2Marshaller marshaller) {
        WorkdayHRClient client = new WorkdayHRClient();
        client.setDefaultUri(env.getRequiredProperty("workday.hr.target.host") + "/ccx/service/"
                + env.getRequiredProperty("workday.hr.target.tenant") + "/Human_Resources/"
                + env.getRequiredProperty("workday.hr.target.version"));
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
