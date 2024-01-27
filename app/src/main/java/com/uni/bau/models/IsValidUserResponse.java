package com.uni.bau.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "IsValidUserResponse", strict = false)
public  class IsValidUserResponse {
    @Element(name = "IsValidUserResult")
    public String result;
}
