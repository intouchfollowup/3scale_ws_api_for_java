package threescale.v3.api;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static threescale.v3.api.ParameterConstants.APPLICATION_ID_PARAMETER;
import static threescale.v3.api.ParameterConstants.APP_ID_PARAMETER;
import static threescale.v3.api.ParameterConstants.HITS_PARAMETER;
import static threescale.v3.api.ParameterConstants.PROVIDER_KEY_PARAMETER;
import static threescale.v3.api.ParameterConstants.SERVICE_ID_PARAMETER;
import static threescale.v3.api.ParameterConstants.TRANSACTIONS_ID_PARAMETER;
import static threescale.v3.api.ParameterConstants.USAGE_PARAMETER;

import org.junit.Test;

import threescale.v3.api.ParameterMap.ParameterMapBuilder;

public class ParameterMapTest {

	@Test
	public void testParameterMapBuilder() {
		ParameterMapBuilder builder = new ParameterMapBuilder();
		ParameterMap parameterMap = builder.build();

		assertThat(parameterMap.getKeys().size(), equalTo(0));

		ParameterMap testMap = new ParameterMap();
		builder.add("testMap", testMap);

		// didn't call build should have nothing
		assertThat(parameterMap.getKeys().size(), equalTo(0));
		assertThat(parameterMap.getMapValue("testMap"), nullValue());

		parameterMap = builder.build();
		assertThat(parameterMap.getKeys().size(), equalTo(1));
		assertThat(parameterMap.getMapValue("testMap"), equalTo(testMap));

		builder.addAppId("appId");
		builder.addApplicationId("applicationId");
		builder.addProviderKey("providerKey");
		builder.addServiceId("serviceId");

		parameterMap = builder.build();
		assertThat(parameterMap.getStringValue(APP_ID_PARAMETER), equalTo("appId"));
		assertThat(parameterMap.getStringValue(APPLICATION_ID_PARAMETER), equalTo("applicationId"));
		assertThat(parameterMap.getStringValue(PROVIDER_KEY_PARAMETER), equalTo("providerKey"));
		assertThat(parameterMap.getStringValue(SERVICE_ID_PARAMETER), equalTo("serviceId"));

		builder.addTransaction("appId", "10");
		parameterMap = builder.build();

		ParameterMap usage = parameterMap.getMapValue(USAGE_PARAMETER);
		assertThat(usage.getStringValue(HITS_PARAMETER), equalTo("10"));

		ParameterMap transaction1 = new ParameterMap();
		builder.addTransactions(transaction1);
		builder.addTransaction("appId", "10");
		parameterMap = builder.build();

		ParameterMap[] transactions = parameterMap.getArrayValue(TRANSACTIONS_ID_PARAMETER);
		usage = transactions[1].getMapValue(USAGE_PARAMETER);
		assertThat(usage.getStringValue(HITS_PARAMETER), equalTo("10"));
	}
}
