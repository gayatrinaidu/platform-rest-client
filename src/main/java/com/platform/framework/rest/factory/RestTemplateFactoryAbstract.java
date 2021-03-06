/**
 * Copyright (C) Altimetrik 2016. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Altimetrik. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Altimetrik.
 */

package com.platform.framework.rest.factory;

import org.springframework.http.HttpHeaders;

import com.platform.framework.rest.client.RestClient;

/**
 * RestClientFactoryAbstract Interface.
 * @author Harish Mangala
 */
public interface RestTemplateFactoryAbstract {
	/**
	 * Add JSON capabilities.
	 * @param headers HttpHeaders
	 * @return RestClient
	 */
	RestClient makeJSON(HttpHeaders headers);

	/**
	 * Add XML capabilities.
	 * @param headers HttpHeaders
	 * @return RestClient
	 */
	RestClient makeXML(HttpHeaders headers);

}
