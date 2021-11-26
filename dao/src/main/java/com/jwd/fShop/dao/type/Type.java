package com.jwd.fShop.dao.type;

import com.jwd.fShop.dao.exception.FatalDaoException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Type {
    private final String typeName;
    private HashMap<String, parameterType> parameters;

    public Type(final String typeName, HashMap<String, parameterType> parameters) throws FatalDaoException {
        if(Objects.nonNull(typeName)){
            this.typeName = typeName;
        }else {
            throw new FatalDaoException("in " + this.getClass().getName() + " in constructor while setting type name");
        }
        if(Objects.nonNull(parameters)){
            /*for(Map.Entry<String, parameterType> entry: parameters){

            }*/
            this.parameters = parameters;
        }
    }


    public String getTypeName() {
        return typeName;
    }

    public List<String> getParametersNames(){
        return null;
    }
}
enum parameterType{
    INTEGER,
    FLOAT,
    STRING,
    DATE;
}
