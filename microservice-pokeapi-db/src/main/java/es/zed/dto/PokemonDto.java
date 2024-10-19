package es.zed.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * pokemon dto.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
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
