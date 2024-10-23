package es.zed.respmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Request response mode.
 *
 * @param <T> generic.
 */
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@Data
public class ReqRespModel<T> implements IReqRespModel<T> {

  /**
   * data.
   */
  private T data;

  /**
   * message.
   */
  private String message;

  /**
   * Get data.
   *
   * @return getData.
   */
  @Override
  public T getData() {
    return this.data;
  }

  /**
   * Get message.
   *
   * @return message.
   */
  @Override
  public String getMessage() {
    return this.message;
  }
}
