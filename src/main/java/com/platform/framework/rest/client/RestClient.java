/**
 * Copyright (C) Altimetrik 2016. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Altimetrik. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Altimetrik.
 */

package com.platform.framework.rest.client;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * RestClient class.
 * @author Harish Mangala
 */
public class RestClient {

	private static final String HEADER_LOCATION = "Location";
	private RestTemplate template;
	private HttpHeaders headers;
	private Long id;

	/**
	 * Default Constructor.
	 */
	public RestClient() {
		template = new RestTemplate();
		headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_PLAIN));
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
	}

	/**
	 * Constructor with specific RestTemplate.
	 * @param template RestTemplate
	 */
	public RestClient(RestTemplate template) {
		this();
		if (template != null) {
			this.template = template;
		}
	}

	/**
	 * Constructor with specific RestTemplate and HttpHeaders.
	 * @param template RestTemplate
	 * @param headers HttpHeaders
	 */
	public RestClient(RestTemplate template, HttpHeaders headers) {
		this(template);
		if (headers != null) {
			this.headers = headers;
		}
	}

	/**
	 * Get Entity.
	 * @param <T> template
	 * @param c Class of response
	 * @param uri for request
	 * @return Entity
	 */
	public <T> ResponseEntity<T> getForEntity(Class<T> c, String uri) {
		return template.getForEntity(uri, c);
	}

	/**
	 * Get Entity.
	 * @param <T> template
	 * @param c Class of response
	 * @param uri for request
	 * @param variables request parameters
	 * @return Entity
	 */
	public <T> T getEntity(Class<T> c, String uri, Object... variables) {
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<T> response = template.exchange(uri, HttpMethod.GET, entity, c, variables);
		return response.getBody();
	}

	/**
	 * Post Entity.
	 * @param <T> template
	 * @param c Class of response
	 * @param uri for request
	 * @param post Entity class
	 * @return Entity
	 */
	public <T> T postEntity(Class<T> c, String uri, T post) {
		HttpEntity<T> entity = new HttpEntity<>(post, headers);
		ResponseEntity<T> response = template.exchange(uri, HttpMethod.POST, entity, c, Void.class);
		saveIdIfNotNull(uri, response.getHeaders().getFirst(HEADER_LOCATION));
		return response.getBody();
	}

	private void saveIdIfNotNull(String uri, String location) {
		if (location != null) {
			String match = uri;
			if (uri.endsWith("/")) {
				match = uri.substring(0, uri.length() - 1);
			}
			match = match.substring(match.lastIndexOf('/'));
			try {
				this.id = Long.parseLong(location.substring(location.lastIndexOf(match) + match.length() + 1));
			}
			catch (Exception e) {
				// Do not throw this
			}

		}
	}

	/**
	 * Put Entity.
	 * @param <T> template
	 * @param c Class of response
	 * @param uri for request
	 * @param post Entity class
	 * @return Entity class
	 */
	public <T> T putEntity(Class<T> c, String uri, T post) {
		HttpEntity<T> entity = new HttpEntity<>(post, headers);
		ResponseEntity<T> response = template.exchange(uri, HttpMethod.PUT, entity, c, Void.class);
		return response.getBody();
	}

	public Long getId() {
		return id;
	}

}
