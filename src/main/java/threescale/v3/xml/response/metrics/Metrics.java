package threescale.v3.xml.response.metrics;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Moncef
 *
 */
@XmlRootElement(name = "metrics")
public class Metrics {

	private List<Metric> metrics = new ArrayList<Metric>();

	@XmlElement(name = "metric")
	public List<Metric> getMetrics() {
		return metrics;
	}

	public void setMetrics(List<Metric> metrics) {
		this.metrics = metrics;
	}
}
