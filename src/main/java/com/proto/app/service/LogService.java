package com.proto.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
//@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Slf4j
public class LogService {
    private Date serviceCreationDate =  Calendar.getInstance().getTime() ;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public LogService() {
        this.serviceCreationDate = Calendar.getInstance().getTime();
    }


    public void log(String text) {
        log.info("service create date :" + formatter.format(serviceCreationDate) + ", log date :" + formatter.format(Calendar.getInstance().getTime()) + ", text : " + text);
    }

}
