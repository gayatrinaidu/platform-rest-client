/**
 * Copyright © Altimetrik 2016. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Altimetrik. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Altimetrik.
 */

package com.platform.framework.rest.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.client.RestTemplate;

import com.platform.framework.rest.client.RestClient;

/**
 * Default Factory Implementatipn for RestClientFactory.
 * @author Harish Mangala
 *
 */
public class RestTemplateFactoryDefault implements RestTemplateFactoryAbstract {

	private List<HttpMessageConverter<?>> messageConverters = null;

	@Override
	public RestClient makeJSON(HttpHeaders headers) {
		messageConverters = new ArrayList<>();
		messageConverters.add(new MappingJackson2HttpMessageConverter());
		return new RestClient(new RestTemplate(messageConverters));
	}

	@Override
	public RestClient makeXML(HttpHeaders headers) {
		final MarshallingHttpMessageConverter marshallingConverter = new MarshallingHttpMessageConverter(new XStreamMarshaller());
		messageConverters = new ArrayList<>();
		messageConverters.add(marshallingConverter);
		return new RestClient(new RestTemplate(messageConverters));
	}

}
