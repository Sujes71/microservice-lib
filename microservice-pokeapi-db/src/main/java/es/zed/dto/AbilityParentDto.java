package es.zed.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Ability parent response dto.
 */
@Data
public class AbilityParentDto {

  /**
   * ability.
   */
  private AbilityChildDto ability;

  /**
   * is_hidden.
   */
  @JsonProperty("is_hidden")
  private Boolean isHidden;

  /**
   * slot.
   */
  private Integer slot;


}
