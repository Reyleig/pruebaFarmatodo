package prueba.service;

import prueba.micellaneus.dto.*;

import java.util.List;


public interface PruebaService {


    ResponseDto getDataEpisode(int idEpisode);
    Numbers happyNumber(NumberListDto list);
    SumNumber sumNaturalNumbers(int idEpisode);

}
