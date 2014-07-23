package threescale.v3.api.http.response.service;

import threescale.v3.api.HttpResponse;
import threescale.v3.api.ServerError;
import threescale.v3.api.http.response.AbstractResponse;
import threescale.v3.xml.response.Application;
import threescale.v3.xml.response.service.Service;

/**
 *
 * @author Moncef
 *
 */
public class ServiceResponse extends AbstractResponse<Service> {

	public ServiceResponse(HttpResponse response) throws ServerError {
		super(response, Service.class);
	}
}
