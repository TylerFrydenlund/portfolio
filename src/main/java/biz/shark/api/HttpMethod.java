package biz.shark.api;

public enum HttpMethod {
	/**
	 * 
	 */
	CONNECT(false, false, true, false, false),
	/**
	 * 
	 */
	DELETE(false, true, true, true, false),

	/**
	 * 
	 */
	GET(true, true, true, false, true),
	/**
	 * 
	 */
	HEAD(true, true, false, false, true),
	/**
	 * 
	 */
	OPTIONS(false, true, true, false, true),
	/**
	* 
	*/
	PATCH(false, false, true, true, false),
	/**
	 * 
	 */
	POST(true, false, true, true, false),
	/**
	* 
	*/
	PUT(false, true, true, true, false),
	/**
	* 
	*/
	TRACE(false, true, true, false, true);

	private final boolean cachable, impotent, responseBody, requestBody, safe;

	private HttpMethod(boolean cachable, boolean impotent, boolean responseBody, boolean requestBody, boolean safe) {
		this.cachable = cachable;
		this.impotent = impotent;
		this.responseBody = responseBody;
		this.requestBody = requestBody;
		this.safe = safe;
	}

	/**
	 * Returns {@code true} if method name is equal to provided one.
	 */
	public boolean equalsName(final String name) {
		return name().equalsIgnoreCase(name);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isCachable() {
		return cachable;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isImpotent() {
		return impotent;
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasResponseBody() {
		return responseBody;
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasRequestBody() {
		return requestBody;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSafe() {
		return safe;
	}

}
