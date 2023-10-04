package com.example.demo.mock;

import com.example.demo.common.service.post.UuidHolder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestUuidHolder implements UuidHolder {

    private final String uuid;
    @Override
    public String random() {
        return uuid;
    }
}
