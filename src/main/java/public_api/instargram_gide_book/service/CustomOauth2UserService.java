package public_api.instargram_gide_book.service;

import org.apache.catalina.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import public_api.instargram_gide_book.dto.CustomOAuth2User;
import public_api.instargram_gide_book.dto.LineResponse;
import public_api.instargram_gide_book.dto.OAuth2Response;
import public_api.instargram_gide_book.dto.UserDTO;
import public_api.instargram_gide_book.entity.UserEntity;
import public_api.instargram_gide_book.repository.UserRepository;

@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOauth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


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
    UserEntity existData = userRepository.findByUsername(username);

    if (existData==null) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setName(oAuth2Response.getName());
        userEntity.setRole("ROLE_USER");

        userRepository.save(userEntity);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setName(oAuth2Response.getName());
        userDTO.setRole("ROLE_USER");

        return new CustomOAuth2User(userDTO);
    } else {
        existData.setName(oAuth2Response.getName());

        userRepository.save(existData);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(existData.getUsername());
        userDTO.setName(oAuth2Response.getName());
        userDTO.setRole(existData.getRole());

        return new CustomOAuth2User(userDTO);

    }
    }
}
