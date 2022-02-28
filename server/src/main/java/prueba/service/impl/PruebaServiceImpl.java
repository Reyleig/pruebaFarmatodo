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
import prueba.micellaneus.dto.EpisodeDto;
import prueba.micellaneus.dto.GenericDto;
import prueba.micellaneus.exeption.BusinessException;
import prueba.micellaneus.util.MessageExceptionUtil;
import prueba.repository.PruebaRepository;
import prueba.service.PruebaService;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    public GenericDto getDataEpisode(int idEpisode) {
        try {
            Map<String, Integer> params = new HashMap<>();
            params.put(ID, idEpisode);

            URI uri = UriComponentsBuilder.fromUriString(URL_EPISODE)
                    .buildAndExpand(params)
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            Map<String, Object> map = new HashMap<>();
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
            ResponseEntity<GenericDto> data = restTemplate.exchange(uri,
                    HttpMethod.GET, entity, new ParameterizedTypeReference<GenericDto>() {
                    });
            
            return data.getBody();
        } catch (Exception e) {
            throw new BusinessException(
                    messageExceptionDtoUtil.resolveMessage(ValidationMessageEnum.ERROR_DELETE_USER));
        }
    }

}
