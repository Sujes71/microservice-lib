package es.zed.dto.response;

import es.zed.dto.PokemonDto;
import es.zed.utils.Dto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Pokemon response dto.
 */
@Data
@AllArgsConstructor
public class PokemonResponseDto implements Dto {

  /**
   * data.
   */
  private List<PokemonDto> data;
}
