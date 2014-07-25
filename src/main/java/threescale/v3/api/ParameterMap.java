package threescale.v3.api;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static threescale.v3.api.ParameterConstants.APPLICATION_ID_PARAMETER;
import static threescale.v3.api.ParameterConstants.APP_ID_PARAMETER;
import static threescale.v3.api.ParameterConstants.HITS_PARAMETER;
import static threescale.v3.api.ParameterConstants.PROVIDER_KEY_PARAMETER;
import static threescale.v3.api.ParameterConstants.SERVICE_ID_PARAMETER;
import static threescale.v3.api.ParameterConstants.TRANSACTIONS_ID_PARAMETER;
import static threescale.v3.api.ParameterConstants.USAGE_PARAMETER;
import static threescale.v3.utils.ObjectUtils.isNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * Hold a set of parameter and metrics for an AuthRep, Authorize, OAuth Authorize or Report.
 * <p/>
 * Each item consists of a name/value pair, where the value can be a String, An Array of ParameterMaps or another Parameter Map.
 * <p/>
 * E.g.  For an AuthRep:
 * <code>
 * ParameterMap params = new ParameterMap();
 * params.add("app_id", "app_1234");
 * <p/>
 * ParameterMap usage = new ParameterMap();
 * usage.add("hits", "3");
 * <p/>
 * params.add("usage", usage);
 * <p/>
 * AuthorizeResponse response = serviceApi.authrep(params);
 * </code>
 * <p/>
 * An example for a report might be:
 * <code>
 * ParameterMap params = new ParameterMap();
 * params.add("app_id", "foo");
 * params.add("timestamp", fmt.print(new DateTime(2010, 4, 27, 15, 0)));
 * <p/>
 * ParameterMap usage = new ParameterMap();
 * usage.add("hits", "1");
 * params.add("usage", usage);
 * <p/>
 * ReportResponse response = serviceApi.report(params);
 * </code>
 */
public class ParameterMap {

    private HashMap<String, Object> data;

    /**
     * Construct and empty ParameterMap
     */
    public ParameterMap() {
        data = new HashMap<String, Object>();
    }

    /**
     * Add a string value
     *
     * @param key
     * @param value
     */
    public void add(String key, String value) {
        data.put(key, value);
    }

    /**
     * Add another ParameterMap
     *
     * @param key
     * @param map
     */
    public void add(String key, ParameterMap map) {
        data.put(key, map);
    }

    /**
     * Add an array of parameter maps
     *
     * @param key
     * @param array
     */
    public void add(String key, ParameterMap[] array) {
        data.put(key, array);
    }

    /**
     * Add a string value if value is not blank
     *
     * @param key
     * @param value
     */
    public void addIfNotBlank(String key, String value) {
    	if(isNotBlank(value)) {
    		add(key, value);
    	}
    }

    /**
     * Add a string value if Object is not null and the Object toString() is not blank
     *
     * @param key
     * @param value
     */
    public void addIfNotNull(String key, Object object) {
    	if(isNotNull(object)) {
    		addIfNotBlank(key, object.toString());
    	}
    }

    /**
     * Return the keys in a ParameterMap
     *
     * @return
     */
    public Set<String> getKeys() {
        return data.keySet();
    }

    /**
     * Get the type of data item associated with the key
     *
     * @param key
     * @return STRING, MAP, ARRAY
     */
    public ParameterMapType getType(String key) {
        Class<?> clazz = data.get(key).getClass();
        if (clazz == String.class) {
            return ParameterMapType.STRING;
        }
        if (clazz == ParameterMap[].class) {
            return ParameterMapType.ARRAY;
        }
        if (clazz == ParameterMap.class) {
            return ParameterMapType.MAP;
        }
        throw new RuntimeException("Unknown object in parameters");
    }

    /**
     * Get the String associated with a key
     *
     * @param key
     * @return
     */
    public String getStringValue(String key) {
        return (String) data.get(key);
    }

    /**
     * Get the map associated with a key
     *
     * @param key
     * @return
     */
    public ParameterMap getMapValue(String key) {
        return (ParameterMap) data.get(key);
    }

    /**
     * Get the array associated with a key.
     *
     * @param key
     * @return
     */
    public ParameterMap[] getArrayValue(String key) {
        return (ParameterMap[]) data.get(key);
    }

    /**
     * Return the number of elements in the map.
     *
     * @return
     */
    public int size() {
        return data.size();
    }

    /**
     * Returns a String, with a format  "Key(Value)" separated by comma's
     *
     * @return {@link String}
     */
    public String dataToString() {
    	StringBuilder sb = new StringBuilder();

    	Set<String> keySet = data.keySet();
    	int i = 0;
    	for (String key : keySet) {
    		sb.append(key);
    		sb.append("(");
    		sb.append(data.get(key));
    		sb.append(")");

    		// add a nice comma separated version of it
			if (i < (keySet.size() - 1)) {
				sb.append(", ");
			}
			i++;
		}

    	return sb.toString();
    }

    public static class ParameterMapBuilder {

		private ParameterMap tempParameterMap = new ParameterMap();

		public ParameterMapBuilder add(String key, String value) {
			tempParameterMap.addIfNotBlank(PROVIDER_KEY_PARAMETER, value);
			return this;
		}

		public ParameterMapBuilder add(String key, ParameterMap map) {
			tempParameterMap.add(key, map);
			return this;
		}

		public ParameterMapBuilder add(String key, ParameterMap[] array) {
			tempParameterMap.add(key, array);
			return this;
		}

		public ParameterMapBuilder addIfNotBlank(String key, String value) {
			tempParameterMap.addIfNotBlank(key, value);
			return this;
		}

		public ParameterMapBuilder addIfNotNull(String key, Object object) {
			tempParameterMap.addIfNotNull(key, object);
			return this;
		}

		public ParameterMapBuilder addProviderKey(String value) {
			tempParameterMap.addIfNotBlank(PROVIDER_KEY_PARAMETER, value);
			return this;
		}

		public ParameterMapBuilder addApplicationId(String value) {
			tempParameterMap.addIfNotBlank(APPLICATION_ID_PARAMETER, value);
			return this;
		}

		public ParameterMapBuilder addAppId(String value) {
			tempParameterMap.addIfNotBlank(APP_ID_PARAMETER, value);
			return this;
		}

		public ParameterMapBuilder addServiceId(String value) {
			tempParameterMap.addIfNotBlank(SERVICE_ID_PARAMETER, value);
			return this;
		}

		public ParameterMapBuilder addTransactions(ParameterMap... transactions) {
			tempParameterMap.add(TRANSACTIONS_ID_PARAMETER, transactions);
			return this;
		}

		public ParameterMapBuilder addTransaction(String appId, String hits) {

			ParameterMap[] transactions = tempParameterMap.getArrayValue(TRANSACTIONS_ID_PARAMETER);
			if(isNotNull(transactions) && transactions.length > 0) {
				// transactions parameter existed, append this as another transaction
				ArrayList<ParameterMap> list = new ArrayList<ParameterMap>(Arrays.asList(transactions));

				ParameterMap usage = new ParameterMap();
				usage.add(HITS_PARAMETER, hits);

				ParameterMap transaction = new ParameterMap();
				transaction.add(APP_ID_PARAMETER, appId);
				transaction.add(USAGE_PARAMETER, usage);

				list.add(transaction);
				addTransactions(list.toArray(new ParameterMap[list.size()]));
			} else {
				// no transactions parameter existed, we are just a root map holding a transaction
				ParameterMap usage = new ParameterMap();
				usage.add(HITS_PARAMETER, hits);

				tempParameterMap.add(APP_ID_PARAMETER, appId);
				tempParameterMap.add(USAGE_PARAMETER, usage);
			}

			return this;
		}

		public ParameterMapBuilder addHits(String value) {
			tempParameterMap.addIfNotBlank(HITS_PARAMETER, value);
			return this;
		}

		public ParameterMap build() {
			// return a new version
			ParameterMap parameterMap = new ParameterMap();
			parameterMap.data = tempParameterMap.data;

			// reset it this for new usage
			tempParameterMap = new ParameterMap();
			return parameterMap;
		}
    }
}
