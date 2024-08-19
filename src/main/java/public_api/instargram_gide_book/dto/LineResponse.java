package public_api.instargram_gide_book.dto;

import java.util.Map;


public class LineResponse implements OAuth2Response{

    private final Map<String, Object> attribute;

    public LineResponse(Map<String, Object> attribute) {

        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "Line ";
    }

    @Override
    public String getProviderId() {
        return attribute.get("userId").toString();
    }

    @Override
    public String getName() {
        return attribute.get("displayName").toString();
    }
}
