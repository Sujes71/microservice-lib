package es.zed.event.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import lombok.Data;

/**
 * Base class for any platform event.
 *
 * @param <B> the type of the event's body attribute.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractEvent<B extends AbstractEventBody> implements Event {

  /**
   * Default offset to use internally for ANY date and dateTime.
   */
  public static final ZoneOffset UTC = ZoneOffset.UTC;

  /**
   * The functional context in which the event was created.
   */
  @NotBlank
  private final String context;
  /**
   * Event type identifier.
   */
  @NotBlank
  @JsonProperty("type_id")
  private final String typeId;
  /**
   * The service that sent the event.
   */
  @NotBlank
  private String origin;
  /**
   * Arbitrary application-specific identifier for the message/event.
   */
  @NotBlank
  @JsonProperty("message_id")
  private String messageId;
  /**
   * Event version.
   */
  @NotBlank
  private String version;
  /**
   * Event creation timestamp (UTC), milliseconds from EPOCH.
   */
  @JsonProperty("creation_ts")
  private Long creationTs;
  /**
   * Event creation timestamp (UTC), ISO_8601 UTC relative date.
   */
  @JsonProperty("creation_date")
  private ZonedDateTime creationDate;

  /**
   * Pokemon id.
   */
  @JsonProperty("pokemonId")
  private String pokemonId;

  /**
   * Event body.
   */
  @NotNull
  @Valid
  private B body;

  /**
   * Minimal constructor.
   *
   * @param context {@link #context}
   * @param typeId {@link #typeId}
   */
  public AbstractEvent(String context, String typeId) {
    this.context = context;
    this.typeId = typeId;
  }

  /**
   * State constructor, {@link #creationDate} is created from {@link #creationTs}.
   *
   * @param origin {@link #origin}
   * @param context {@link #context}
   * @param typeId {@link #typeId}
   * @param messageId {@link #messageId}
   * @param version {@link #version}
   * @param creationTs {@link #creationTs}
   * @param pokemonId {@link #pokemonId}
   * @param body {@link #body}
   */
  public AbstractEvent(final String origin, final String context, final String typeId,
      final String messageId, final String version, final Long creationTs, final String pokemonId, final B body) {
    this.origin = origin;
    this.context = context;
    this.typeId = typeId;
    this.messageId = messageId;
    this.version = version;
    this.creationTs = creationTs;
    this.creationDate = creationTs == null ? null : ZonedDateTime.ofInstant(Instant.ofEpochMilli(creationTs), UTC);
    this.body = body;
    this.pokemonId = pokemonId;
  }

}