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

package com.hikmat30ce.workday.integrator.hr.db.security;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

/**
 *
 * @author Hikmat Ullah
 * @skype huk9791
 * @email huk9191@gmail.com
 */
public class SecurityHeader implements WebServiceMessageCallback {

    private Security authentication;

    public SecurityHeader(Security authentication) {
        this.authentication = authentication;
    }

    @Override
    public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
        SOAPMessage soapMessage = ((SaajSoapMessage) message).getSaajMessage();
        SOAPHeader header;
        try {
            header = soapMessage.getSOAPHeader();

            SOAPHeaderElement security = header.addHeaderElement(new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security", "wsse"));
            SOAPElement usernameToken = security.addChildElement("UsernameToken", "wsse");
            SOAPElement username = usernameToken.addChildElement("Username", "wsse");
            SOAPElement password = usernameToken.addChildElement("Password", "wsse");
            username.setTextContent(authentication.getUsername());
            password.setTextContent(authentication.getPassword());
        } catch (SOAPException ex) {
            Logger.getLogger(SecurityHeader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}