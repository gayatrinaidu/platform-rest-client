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

/**
 * HttpHeadersFactoryAbstract Interface.
 * @author Harish Mangala
 *
 */
public interface HttpHeadersFactoryAbstract {

	/**
	 * Accept JSON.
	 * @return HttpHeaders
	 */
	HttpHeaders acceptJSON();

	/**
	 * Accept XML.
	 * @return HttpHeaders
	 */
	HttpHeaders acceptXML();
}
