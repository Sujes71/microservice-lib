package es.zed.respmodel;

/**
 * Response model interface.
 * @param <T> param.
 */
public interface IReqRespModel<T> {

  /**
   * Get data.
   *
   * @return generic.
   */
  T getData();

  /**
   * Get message.
   *
   * @return message.
   */
  String getMessage();
}
