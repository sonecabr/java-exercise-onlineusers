package com.thinkstep.test.onlineusers.request;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RequestCount {

    private Long total;
}
