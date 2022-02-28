package prueba.service;

import prueba.micellaneus.dto.EpisodeDto;
import prueba.micellaneus.dto.GenericDto;
import prueba.micellaneus.dto.ResponseDto;
import prueba.micellaneus.dto.UserDto;

import java.util.List;


public interface PruebaService {


    ResponseDto getDataEpisode(int idEpisode);

}
