package frameworkCore;

public class StructureValidator implements ResponseValidator {

	@Override
	public boolean validate(ProviderResponse response) {
		// Intended: check response.getContent() is non-null and well-formed
        return response != null && response.getContent() != null && !response.getContent().isEmpty();
	}

}
  