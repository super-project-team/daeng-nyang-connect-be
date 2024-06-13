package com.daengnyang.jooq.custom;


import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.impl.DSL;
import org.jooq.meta.Definition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;


@Configuration
public class JPrefixGeneratorStrategy extends DefaultGeneratorStrategy {

    @Override
    public String getJavaClassName(Definition definition, Mode mode) {
        if(mode == Mode.RECORD || mode==Mode.DAO || mode==Mode.DEFAULT){
            return "J" + super.getJavaClassName(definition, mode);
        }
        return super.getJavaClassName(definition, mode);
    }

}
