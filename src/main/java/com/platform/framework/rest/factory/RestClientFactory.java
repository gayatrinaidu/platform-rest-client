/**
 * Copyright (C) Altimetrik 2016. All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Altimetrik. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Altimetrik.
 */

package com.platform.framework.rest.factory;

import com.platform.framework.rest.client.RestClient;

/**
 * RestClientFactory class.
 * @author Harish Mangala
 */
public final class RestClientFactory {

	/**
	 * DEFAULT Client Type.
	 */
	public static final int CLIENT_DEFAULT = 1;
	/**
	 * JSON Client Type.
	 */
	public static final int CLIENT_JSON = 2;
	/**
	 * XML Client Type.
	 */
	public static final int CLIENT_XML = 3;

	/**
	 * Template Factory.
	 */
	public static final RestTemplateFactoryAbstract templateFactory = new RestTemplateFactoryDefault();
	/**
	 * Header Factory.
	 */
	public static final HttpHeadersFactoryAbstract headerFactory = new HttpHeadersFactoryDefault();

	private RestClientFactory() {

	}

	/**
	 * Instantiate Client specfic RestClient.
	 * @param clientType int
	 * @return RestClient class
	 */
	public static RestClient instantiate(int clientType) {
		if (clientType == CLIENT_JSON) {
			return templateFactory.makeJSON(headerFactory.acceptJSON());
		}

		if (clientType == CLIENT_XML) {
			return templateFactory.makeXML(headerFactory.acceptXML());
		}

		return new RestClient();
	}

	/**
	 * Instantiate Default RestClient.
	 * @return RestClient class
	 */
	public static RestClient instantiate() {
		return instantiate(CLIENT_DEFAULT);
	}
}
