package com.vilma.core.entity;

import java.util.List;

import lombok.Data;

@Data
public class AttributeRangeDef<T> {

    private List<T> options;

    private Between<T> between;

    private Between<T> betweenWith;

    private String program;
}

@Data
class Between<T> {
    private T start;
    private T end; 
}