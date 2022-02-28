package prueba.micellaneus.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDto {
    private int episode;
    private String episodeName;
    private List<CharactersResponseDto> characters;

}
