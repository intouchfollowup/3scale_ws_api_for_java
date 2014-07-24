package threescale.v3.xml.response.error;

import static threescale.v3.utils.ObjectUtils.isNotNull;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "errors")
public class Errors {

	private List<Error> errors;

	public boolean hasErrors() {
		return isNotNull(errors) && !errors.isEmpty();
	}

	@XmlElement(name = "error")
	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> error) {
		this.errors = error;
	}
}
