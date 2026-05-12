package com.zjsh.common.utils;

@FunctionalInterface
public interface BizCall<T> {
    T execute();
}