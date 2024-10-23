package es.zed.dto.response;

import es.zed.dto.AbilityParentDto;
import es.zed.utils.Dto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ability response dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbilityResponseDto implements Dto {

  /**
   * id.
   */
  private String id;

  /**
   * abilities.
   */
  private List<AbilityParentDto> abilities;
}
