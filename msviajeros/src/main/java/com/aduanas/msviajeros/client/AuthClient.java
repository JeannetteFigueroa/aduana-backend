package com.aduanas.msviajeros.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "AUTH-SERVICE")
public interface AuthClient {
}
