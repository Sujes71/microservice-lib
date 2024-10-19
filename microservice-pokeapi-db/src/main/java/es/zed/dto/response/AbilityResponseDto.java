package es.zed.dto.response;

import es.zed.common.service.utils.Dto;
import es.zed.dto.AbilityParentDto;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ability response dto.
 */
@Data
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
