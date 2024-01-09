package com.message.api.entity;

import java.util.stream.Stream;

public enum TagEnum {
    TAG1("Tag1"),
    TAG2("Tag2"),
    TAG3("Tag3");

    private final String value;

    public String getValue() {
        return value;
    }

    private TagEnum(String value) {
        this.value = value;
    }

 //   @Override
//    public String toString() {
//        return value;
//    }

    public static TagEnum fromValue(String name) {
        for (TagEnum t : values()) {
            if (t.value.equals(name)) {
                return t;
            }
        }
        return null;
    }
}
