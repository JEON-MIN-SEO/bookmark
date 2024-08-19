package public_api.instargram_gide_book.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import public_api.instargram_gide_book.dto.CustomOAuth2User;
import public_api.instargram_gide_book.dto.LineResponse;
import public_api.instargram_gide_book.dto.OAuth2Response;
import public_api.instargram_gide_book.dto.UserDTO;

@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);

        //카카오인지 라인인지 알 수 있음
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("line")) {
            oAuth2Response = new LineResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

    String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();

        UserDTO userDTO = new UserDTO();
        userDTO.setName(oAuth2Response.getName());
        userDTO.setUsername(username);
        userDTO.setRole("ROLE");

    return new CustomOAuth2User(userDTO);
    }
}
