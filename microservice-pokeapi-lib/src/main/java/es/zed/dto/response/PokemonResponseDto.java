package es.zed.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import es.zed.common.service.utils.Dto;
import es.zed.dto.AbilityParentDto;
import es.zed.dto.FormDto;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * pokemon response dto.
 */
@Data
@NoArgsConstructor
public class PokemonResponseDto implements Dto {

  /**
   * id.
   */
  private String id;

  /**
   * name.
   */
  private String name;

  /**
   * abilities.
   */
  private List<AbilityParentDto> abilities;

  /**
   * base experience.
   */
  @JsonProperty("base_experience")
  private Integer baseExperience;

  /**
   * forms.
   */
  private List<FormDto> forms;

}
