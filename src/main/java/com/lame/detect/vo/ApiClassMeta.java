package com.lame.detect.vo;


import java.util.ArrayList;
import java.util.List;

public class ApiClassMeta extends ClassMeta{
    private String baseApiPath;

    private List<String> apis = new ArrayList<>();

    public void addApi(String api) {
        apis.add(api);
    }

    public List<String> getApis() {
        return apis;
    }

    public void setApis(List<String> apis) {
        this.apis = apis;
    }

    public String getBaseApiPath() {
        return baseApiPath;
    }

    public void setBaseApiPath(String baseApiPath) {
        this.baseApiPath = baseApiPath;
    }
}
