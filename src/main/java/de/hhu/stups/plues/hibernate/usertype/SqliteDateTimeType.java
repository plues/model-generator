package org.hibernate.usertype;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class SqliteDateTimeType implements UserType {
  // e.g. 2016-04-27 15:40:18
  private static final String SQLITE_TEXT_TIME_STAMP = "yyyy-MM-dd kk:mm:ss";

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final DateTimeFormatter dateTimeFormatter
      = DateTimeFormatter.ofPattern(SQLITE_TEXT_TIME_STAMP);

  @Override
  public int[] sqlTypes() {
    return new int[]{Types.TIMESTAMP};
  }

  @Override
  public Class returnedClass() {
    return java.time.LocalDateTime.class;
  }

  @Override
  public boolean equals(final Object object, final Object other) {
    return Objects.equals(object, other);
  }

  @Override
  public int hashCode(final Object object) {
    return Objects.hashCode(object);
  }

  @Override
  public Object nullSafeGet(final ResultSet rs, final String[] names,
                            final SharedSessionContractImplementor session, final Object owner)
      throws SQLException {
    assert names.length == 1;
    final String dateStr = rs.getString(names[0]);

    final DateTimeFormatter formatter = dateTimeFormatter.withZone(ZoneId.of("UTC"));
    try {
      final ZonedDateTime utcDate = ZonedDateTime.parse(dateStr, formatter);
      return utcDate.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    } catch (final DateTimeParseException | NullPointerException exception) {
      logger.error("Exception was thrown", exception);
      throw new HibernateException(exception.getMessage());
    }
  }

  @Override
  public void nullSafeSet(final PreparedStatement st, final Object value, final int index,
                          final SharedSessionContractImplementor session)
      throws SQLException {
    final ZonedDateTime local = ((LocalDateTime) value).atZone(ZoneId.systemDefault());
    final ZonedDateTime dateTime = local.withZoneSameInstant(ZoneId.of("UTC"));
    try {
      st.setString(index, dateTimeFormatter.format(dateTime));
    } catch (final IllegalArgumentException exception) {
      logger.error("Exception was thrown", exception);
      throw new HibernateException(exception.getMessage());
    }
  }

  @Override
  public Object deepCopy(final Object value) {
    return value;
  }

  @Override
  public boolean isMutable() {
    return false;
  }

  @Override
  public Serializable disassemble(final Object value) {
    return (LocalDateTime) value;
  }

  @Override
  public Object assemble(final Serializable cached, final Object owner) {
    return cached;
  }

  @Override
  public Object replace(final Object original, final Object target, final Object owner) {
    throw new HibernateException("Currently not supported");
  }
}
