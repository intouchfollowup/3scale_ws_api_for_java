package threescale.v3.api.http.response.service;

import threescale.v3.api.HttpResponse;
import threescale.v3.api.ServerError;
import threescale.v3.api.http.response.AbstractResponse;
import threescale.v3.xml.elements.application.Application;
import threescale.v3.xml.elements.service.Service;

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
