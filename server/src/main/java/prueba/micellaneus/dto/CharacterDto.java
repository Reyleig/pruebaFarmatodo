package prueba.micellaneus.dto;

import lombok.Data;

import java.util.List;

@Data
public class CharacterDto {
    private int id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private OriginCharacterDto origin;
    private LocationCharacterDto location;
    private String image;
    private List<String> episode;
    private String url;
    private String created;
}
