package com.aduanas.msusuarios.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "AUTH-SERVICE")
public interface AuthClient {
}
