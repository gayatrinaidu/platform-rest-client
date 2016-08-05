/**
 * Copyright © Altimetrik 2016. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Altimetrik. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Altimetrik.
 */

package com.platform.framework.rest.client.test;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.platform.framework.rest.client.RestClient;

/**
 * RestClientTest Test Class.
 * @author Harish Mangala
 *
 */
public class RestClientTest {

	private RestClient restClient;
	private RestTemplate template;
	private ClientHttpRequestFactory requestFactory;
	private ClientHttpRequest request;
	private ClientHttpResponse response;
	private ResponseErrorHandler errorHandler;
	@SuppressWarnings("rawtypes")
	private HttpMessageConverter converter;

	@Before
	public void setUp() {
		requestFactory = mock(ClientHttpRequestFactory.class);
		request = mock(ClientHttpRequest.class);
		response = mock(ClientHttpResponse.class);
		errorHandler = mock(ResponseErrorHandler.class);
		converter = mock(HttpMessageConverter.class);
		template = new RestTemplate(Collections.<HttpMessageConverter<?>>singletonList(converter));
		template.setRequestFactory(requestFactory);
		template.setErrorHandler(errorHandler);
		restClient = new RestClient(template);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getForEntity() throws Exception {
		given(converter.canRead(String.class, null)).willReturn(true);
		MediaType textPlain = new MediaType("text", "plain");
		given(converter.getSupportedMediaTypes()).willReturn(Collections.singletonList(textPlain));
		given(requestFactory.createRequest(new URI("http://example.com"), HttpMethod.GET)).willReturn(request);
		HttpHeaders requestHeaders = new HttpHeaders();
		given(request.getHeaders()).willReturn(requestHeaders);
		given(request.execute()).willReturn(response);
		given(errorHandler.hasError(response)).willReturn(false);
		String expected = "Hello World";
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(textPlain);
		responseHeaders.setContentLength(10);
		given(response.getStatusCode()).willReturn(HttpStatus.OK);
		given(response.getHeaders()).willReturn(responseHeaders);
		given(response.getBody()).willReturn(new ByteArrayInputStream(expected.getBytes()));
		given(converter.canRead(String.class, textPlain)).willReturn(true);
		given(converter.read(eq(String.class), any(HttpInputMessage.class))).willReturn(expected);
		given(response.getStatusCode()).willReturn(HttpStatus.OK);
		HttpStatus status = HttpStatus.OK;
		given(response.getStatusCode()).willReturn(status);
		given(response.getStatusText()).willReturn(status.getReasonPhrase());

		ResponseEntity<String> result = restClient.getForEntity(String.class, "http://example.com");
		Assert.isTrue(expected.equals(result.getBody()));
		Assert.isTrue(textPlain.toString().equals(requestHeaders.getFirst("Accept")));
		Assert.isTrue(textPlain.equals(result.getHeaders().getContentType()));
		Assert.isTrue(status.equals(result.getStatusCode()));

		verify(response).close();
	}

}
