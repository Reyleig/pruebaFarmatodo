package prueba.service.impl;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import prueba.micellaneus.constantes.ValidationMessageEnum;
import prueba.micellaneus.dto.*;
import prueba.micellaneus.exeption.BusinessException;
import prueba.micellaneus.util.MessageExceptionUtil;
import prueba.repository.PruebaRepository;
import prueba.service.PruebaService;

import java.net.URI;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
    private MessageExceptionUtil messageExceptionDtoUtil;

    public PruebaServiceImpl(PruebaRepository pruebaRepository, MessageExceptionUtil messageExceptionDtoUtil) {
        this.pruebaRepository = pruebaRepository;
        this.messageExceptionDtoUtil = messageExceptionDtoUtil;
    }
    public PruebaServiceImpl() {
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
                    HttpMethod.GET, entity, EpisodeDto.class);

            if (!dataEpisode.getStatusCode().equals(HttpStatus.OK)) {
                throw new BusinessException(
                        messageExceptionDtoUtil.resolveMessage(ValidationMessageEnum.ERROR_GET_EPISODE));
            }
            EpisodeDto episodeDto = dataEpisode.getBody();
            ResponseDto responseDto = new ResponseDto();

            responseDto.setEpisode(episodeDto.getId());
            responseDto.setEpisodeName(episodeDto.getEpisode());

            List<CharactersResponseDto> characters = new ArrayList<>();
            AtomicInteger count = new AtomicInteger(pruebaRepository.existEpisode(episodeDto.getId()));

            if (count.get() == 0) {
                pruebaRepository.insertEpisode(episodeDto.getId(), episodeDto.getEpisode());
            }

            episodeDto.getCharacters().forEach(character -> {
                ResponseEntity<CharacterDto> dataCharacter = restTemplate.exchange(character,
                        HttpMethod.GET, entity, CharacterDto.class);

                if (!dataCharacter.getStatusCode().equals(HttpStatus.OK)) {
                    log.info("Error al obtener el personaje");
                }

                String[] split = character.split("/");
                int idCharacter = Integer.parseInt(split[split.length - 1]);
                count.set(pruebaRepository.existCharacter(idCharacter));

                CharacterDto characterDto = dataCharacter.getBody();
                if (count.get() == 0) {
                    pruebaRepository.insertCharacter(idCharacter, characterDto, idEpisode);
                }

                if (!Objects.equals(characterDto.getOrigin().getUrl(), "")) {
                    ResponseEntity<LocationDto> dataLocation = restTemplate.exchange(characterDto.getOrigin().getUrl()
                            , HttpMethod.GET, entity, LocationDto.class);

                    if (!dataCharacter.getStatusCode().equals(HttpStatus.OK)) {
                        log.info("Error al obtener el personaje");
                    }

                    split = characterDto.getOrigin().getUrl().split("/");
                    int idLocation = Integer.parseInt(split[split.length - 1]);
                    count.set(pruebaRepository.existLocation(idLocation));

                    LocationDto locationDto = dataLocation.getBody();

                    if (count.get() == 0) {
                        pruebaRepository.insertLocation(idLocation, locationDto, idEpisode, idCharacter);
                    }

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
            e.printStackTrace();
            throw new BusinessException(
                    messageExceptionDtoUtil.resolveMessage(ValidationMessageEnum.ERROR_GET_EPISODE));
        }
    }

    public static int[] digitsNumbers(int iNumber) {

        String x = Integer.toString(iNumber);
        int[] iNumbers = new int[x.length()];

        int iDigit = 0;
        while (iNumber > 0) {
            iNumbers[iDigit] = iNumber % 10;
            iNumber = iNumber / 10;
            iDigit++;
        }

        return iNumbers;

    }

    public Numbers happyNumber(NumberListDto list) {

        List<NumbersDto> numbers = new ArrayList<>();
        Numbers numbersResponseDto = new Numbers();

        for (int i = 0; i < list.getNumbers().size(); i++) {
            Set<Integer> iCalculated = new HashSet<Integer>();
            boolean bRepeated = false;
            NumbersDto numbersDto = new NumbersDto();

            int iNumber = list.getNumbers().get(i);
            int[] iNumbers = digitsNumbers(iNumber);
            int iSum = 0;

            while ((iSum != 1) && !(bRepeated)) {
                iSum = 0;
                for (int x = 0; x < iNumbers.length; x++) {
                    iSum += Math.pow(iNumbers[x], 2);
                }
                iNumbers = digitsNumbers(iSum);
                if (iCalculated.contains(new Integer(iSum))) {
                    bRepeated = true;
                } else {
                    iCalculated.add(new Integer(iSum));
                }
            }
            if (bRepeated) {
                numbersDto.setNumber(iNumber);
                numbersDto.setHappy(false);
            } else {
                numbersDto.setNumber(iNumber);
                numbersDto.setHappy(true);
            }
            numbers.add(numbersDto);
        }
        numbersResponseDto.setNumbers(numbers);

        return numbersResponseDto;

    }

    public SumNumber sumNaturalNumbers(int number) {
        int num = number, i = 1, sum = 0;
        while (i <= num) {
            sum = sum + i;
            i++;
        }
        SumNumber sumNumber = new SumNumber();
        sumNumber.setResult(sum);
        return sumNumber;
    }


}
