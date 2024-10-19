package es.zed.event.pokedb;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import es.zed.event.common.AbstractEventBody;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Poke created event body.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
public class AbilityCreatedEventBody extends AbstractEventBody {

  /**
   * name.
   */
  private String name;

  /**
   * url.
   */
  private String url;

  /**
   * Poke created event body.
   *
   * @param name name.
   * @param url url.
   */
  @Builder
  public AbilityCreatedEventBody(String name, String url) {
    super();
    this.name = name;
    this.url = url;
  }
}
