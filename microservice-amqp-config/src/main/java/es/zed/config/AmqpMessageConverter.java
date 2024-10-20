package es.zed.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import es.zed.common.utils.CustomObjectMapper;
import es.zed.event.common.AbstractEvent;
import es.zed.event.common.spring.EventParserManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.parser.txt.CharsetDetector;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * Amqp message converter for the request queue.
 */
@Slf4j
public class AmqpMessageConverter implements MessageConverter {

  /**
   * Jackson object mapper to read/write json.
   */
  private final CustomObjectMapper customObjectMapper;

  /**
   * Caminos business event managers.
   */
  private final EventParserManager eventParserManager;

  /**
   * State constructor.
   *
   * @param eventParserManager {@link #eventParserManager}.
   * @param customObjectMapper {@link #customObjectMapper}.
   */
  public AmqpMessageConverter(
      EventParserManager eventParserManager,
      CustomObjectMapper customObjectMapper) {
    this.eventParserManager = eventParserManager;
    this.customObjectMapper = customObjectMapper;
  }

  @Override
  public Message toMessage(Object object, MessageProperties messageProperties)
      throws MessageConversionException {
    if (!(object instanceof AbstractEvent)) {
      throw new MessageConversionException(
          "The message is not of the expected type (Abstract Event Event)");
    }
    try {
      return new Message(customObjectMapper.writeValueAsBytes(object), messageProperties);
    } catch (JsonProcessingException e) {
      throw new MessageConversionException(
          "Unexpected error found writing the Abstract Event Request: " + e.getMessage(), e);
    }
  }

  @Override
  public Object fromMessage(Message message) throws MessageConversionException {
    final JsonNode jsonNode = jsonMessageContent(message);
    return (AbstractEvent) eventParserManager.findParser(jsonNode).parse(jsonNode);
  }

  /**
   * Converts message content to a {@link JsonNode}. If it is not possible, then throws a MessageConversionException.
   *
   * @param message the message which content is to be converted.
   * @return the {@link JsonNode} produced by content parsing.
   * @throws MessageConversionException if the content is ot a valid JSON.
   */
  private JsonNode jsonMessageContent(final Message message) throws MessageConversionException {
    try {
      return customObjectMapper.readTree(message.getBody());
    } catch (JsonParseException e) {
      if (e.getMessage() != null && e.getMessage().contains("Invalid UTF-8")) {
        log.warn("Invalid UTF-8 message detected, trying to detect encoding");
        return jsonFromBytesWithEncodingDetection(message.getBody());
      } else {
        throw new MessageConversionException("The message is not a valid JSON", e);
      }
    } catch (IOException e) {
      throw new MessageConversionException("The message is not a valid JSON", e);
    }
  }

  /**
   * Converts bytes to a {@link JsonNode} trying to detect string encoding first. If it is not possible, then throws a MessageConversionException.
   *
   * @param message the message which content is to be converted.
   * @return the {@link JsonNode} produced by content parsing.
   * @throws MessageConversionException if the content is ot a valid JSON.
   */
  private JsonNode jsonFromBytesWithEncodingDetection(final byte[] message) throws MessageConversionException {
    try {
      final String charset = charset(message);
      log.info("Detected charset: {}", charset);
      return customObjectMapper.readTree(new String(message, charset).getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw new MessageConversionException("The message is not a valid JSON", e);
    }
  }

  private String charset(final byte[] message) throws IOException {
    final CharsetDetector charsetDetector = new CharsetDetector();
    charsetDetector.setText(message);
    try {
      return charsetDetector.detect().getName();
    } catch (Exception e) {
      throw new IOException("Could not detect message charset", e);
    }

  }
}