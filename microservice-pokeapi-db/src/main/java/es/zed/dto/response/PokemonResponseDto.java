package es.zed.dto.response;

import es.zed.dto.PokemonDto;
import es.zed.utils.Dto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pokemon response dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonResponseDto implements Dto {

  /**
   * List of pokemons.
   */
  private List<PokemonDto> items;
}
