package prueba.service.impl;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import prueba.micellaneus.constantes.ValidationMessageEnum;
import prueba.micellaneus.dto.*;
import prueba.micellaneus.exeption.BusinessException;
import prueba.micellaneus.util.MessageExceptionUtil;
import prueba.repository.PruebaRepository;
import prueba.service.PruebaService;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.util.*;

@Log4j2
@Slf4j
@Service
public class PruebaServiceImpl implements PruebaService {

    @Value("${urls.getEpisode}")
    private String URL_EPISODE;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PruebaRepository pruebaRepository;

    private static final String ID = "id";
    private final MessageExceptionUtil messageExceptionDtoUtil;

    public PruebaServiceImpl(PruebaRepository pruebaRepository, MessageExceptionUtil messageExceptionDtoUtil) {
        this.pruebaRepository = pruebaRepository;
        this.messageExceptionDtoUtil = messageExceptionDtoUtil;
    }

    @Override
    public ResponseDto getDataEpisode(int idEpisode) {
        try {
            Map<String, Integer> params = new HashMap<>();
            params.put(ID, idEpisode);

            URI uri = UriComponentsBuilder.fromUriString(URL_EPISODE)
                    .buildAndExpand(params)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<Object> entity = new HttpEntity<>(headers);
            ResponseEntity<EpisodeDto> dataEpisode = restTemplate.exchange(uri.toString(),
                    HttpMethod.GET, entity, EpisodeDto.class );
            EpisodeDto episodeDto = dataEpisode.getBody();

            ResponseDto responseDto = new ResponseDto();

            responseDto.setEpisode(episodeDto.getId());
            responseDto.setEpisodeName(episodeDto.getEpisode());
            responseDto.setEpisodeName(episodeDto.getEpisode());

            List<CharactersResponseDto> characters = new ArrayList<>();

            episodeDto.getCharacters().forEach(character -> {
                ResponseEntity<CharacterDto> dataCharacter = restTemplate.exchange(character,
                        HttpMethod.GET, entity, CharacterDto.class );

                CharacterDto characterDto = dataCharacter.getBody();
                if (!Objects.equals(characterDto.getOrigin().getUrl(), "")) {
                    ResponseEntity<LocationDto> dataLocation = restTemplate.exchange(characterDto.getOrigin().getUrl()
                            , HttpMethod.GET, entity, LocationDto.class);

                    LocationDto locationDto = dataLocation.getBody();
                    CharactersResponseDto charactersResponseDto = new CharactersResponseDto();

                    charactersResponseDto.setName(characterDto.getName());
                    charactersResponseDto.setGender(characterDto.getGender());
                    charactersResponseDto.setSpecies(characterDto.getSpecies());
                    charactersResponseDto.setImage(characterDto.getImage());

                    LocationResponseDto locationResponseDto = new LocationResponseDto();

                    locationResponseDto.setName(locationDto.getName());
                    locationResponseDto.setDimension(locationDto.getDimension());
                    locationResponseDto.setType(locationDto.getType());

                    charactersResponseDto.setLocation(locationResponseDto);
                    characters.add(charactersResponseDto);
                }

            });
            responseDto.setCharacters(characters);


            return responseDto;
        } catch (Exception e) {
            throw new BusinessException(
                    messageExceptionDtoUtil.resolveMessage(ValidationMessageEnum.ERROR_GET_EPISODE));
        }
    }

}
