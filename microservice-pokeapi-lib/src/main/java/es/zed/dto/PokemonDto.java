package es.zed.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pokemon dto.
 */
@Data
@NoArgsConstructor
public class PokemonDto {

  /**
   * id.
   */
  private String id;

  /**
   * name.
   */
  private String name;
}
