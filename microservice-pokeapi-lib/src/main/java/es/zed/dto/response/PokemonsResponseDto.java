package es.zed.dto.response;

import es.zed.dto.PokemonDto;
import es.zed.utils.Dto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pokemons response dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonsResponseDto implements Dto {

  /**
   * Items.
   */
  private List<PokemonDto> items;

}
