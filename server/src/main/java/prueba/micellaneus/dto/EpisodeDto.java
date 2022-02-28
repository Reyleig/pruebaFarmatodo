package prueba.micellaneus.dto;

import lombok.Data;

import java.util.List;

@Data
public class EpisodeDto {
    private int episode;
    private String episodeName;
    private List<charactersDto> characters;
}
