package prueba.repository;

import org.springframework.stereotype.Repository;
import prueba.micellaneus.dto.CharacterDto;
import prueba.micellaneus.dto.LocationDto;
import prueba.micellaneus.util.MessageExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class PruebaRepository {

    @Autowired
    private JdbcTemplate template;

    @Autowired
    MessageExceptionUtil messageExceptionDtoUtil;

    public int existEpisode(int idEpisode) {
        String sql = "select count(episode_id) from episode where episode_id= ?  ";
        return template.queryForObject(sql, new Object[]{idEpisode},Integer.class);
    }
    public int insertEpisode(int idEpisode, String name) {
        String sql = "INSERT INTO episode (episode_id, episode_name) VALUES (?,?)";
        return template.update(sql,new Object[]{idEpisode,name});
    }
    public int existCharacter(int idCharacter) {
        String sql = "select count(character_id) from character where character_id= ?  ";
        return template.queryForObject(sql, new Object[]{idCharacter},Integer.class);
    }
    //Implementar metodo para insertar Character
    public int insertCharacter(int idCharacter, CharacterDto characterDto,int idEpisode){
        String sql = "INSERT INTO character (name, species, gender, image, character_id, episode_id) VALUES (?, ?, ?, ?, ?, ?) ";
        return template.update(sql, new Object[]{characterDto.getName(), characterDto.getSpecies(), characterDto.getGender(), characterDto.getImage(),idCharacter,idEpisode});
    }
    //implementar metodo para saber si exite una lcoation
    public int existLocation(int idLocation) {
        String sql = "select count(location_id) from location where location_id= ?  ";
        return template.queryForObject(sql, new Object[]{idLocation},Integer.class);
    }
    //Implementar metodo para insertar Character
    public int insertLocation(int idLocation, LocationDto locationDto,int idEpisode,int idCharacter){
        String sql = "INSERT INTO location (location_id, name, type, dimension,episode_id,character_id) VALUES (?,?,?,?,?,?)";
        return template.update(sql, new Object[]{idLocation, locationDto.getName(), locationDto.getType(), locationDto.getDimension(),idEpisode,idCharacter});
    }






}
