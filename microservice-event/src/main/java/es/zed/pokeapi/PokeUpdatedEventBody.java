package es.zed.pokeapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import es.zed.common.AbstractEventBody;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Poke updated event body.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
public class PokeUpdatedEventBody extends AbstractEventBody {

  /**
   * id.
   */
  private String id;

  /**
   * name.
   */
  private String name;

  /**
   * Poke created event body.
   *
   * @param id id.
   * @param name name.
   */
  @Builder
  public PokeUpdatedEventBody(String id, String name) {
    super();
    this.id = id;
    this.name = name;
  }
}
