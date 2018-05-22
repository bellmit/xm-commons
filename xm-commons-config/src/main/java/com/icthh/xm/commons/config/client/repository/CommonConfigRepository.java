package com.icthh.xm.commons.config.client.repository;

import static com.icthh.xm.commons.config.client.utils.RequestUtils.createAuthHeaders;
import static com.icthh.xm.commons.config.client.utils.RequestUtils.createSimpleHeaders;

import com.icthh.xm.commons.config.client.config.XmConfigProperties;
import com.icthh.xm.commons.config.domain.Configuration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class CommonConfigRepository {

    private static final String URL = "/api/private/config_map";
    private static final String VERSION = "version";
    private final RestTemplate restTemplate;
    private final XmConfigProperties xmConfigProperties;

    public Map<String, Configuration> getConfig(String commit) {
        ParameterizedTypeReference<Map<String, Configuration>> typeRef = new ParameterizedTypeReference<Map<String, Configuration>>() {};
        HttpEntity<String> entity = new HttpEntity<>(createSimpleHeaders());
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(getServiceConfigUrl()).queryParam(VERSION, commit);
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, typeRef).getBody();
    }

    private String getServiceConfigUrl() {
        return xmConfigProperties.getXmConfigUrl() + URL;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class GetConfigRequest {
        private String version;
        private Collection<String> paths;
    }

    public Map<String,Configuration> getConfig(String version, Collection<String> paths) {
        ParameterizedTypeReference<Map<String, Configuration>> typeRef = new ParameterizedTypeReference<Map<String, Configuration>>() {};
        HttpEntity<GetConfigRequest> entity = new HttpEntity<>(new GetConfigRequest(version, paths), createSimpleHeaders());
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(getServiceConfigUrl());
        return restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, typeRef).getBody();
    }
}
