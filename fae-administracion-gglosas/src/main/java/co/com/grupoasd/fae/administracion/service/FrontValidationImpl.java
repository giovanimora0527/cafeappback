/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.grupoasd.fae.administracion.service;

import co.com.grupoasd.fae.motoreglas.interfaces.FrontValidacion;
import org.springframework.stereotype.Component;

/**
 *
 * @author jose
 */
@Component
public class FrontValidationImpl implements FrontValidacion{

    @Override
    public String getExpReg(String campoFront) {
        return "";
    }
    
}
