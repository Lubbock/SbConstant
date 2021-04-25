package com.lame.sbconstant.detect.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClassMeta {

    private String name;

    private String packageName;

    private List<ClassField> fields = new ArrayList<>();

    public void addFiled(ClassField field) {
        fields.add(field);
    }
}
