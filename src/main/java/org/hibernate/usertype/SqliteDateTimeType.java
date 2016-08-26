package org.hibernate.usertype;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class SqliteDateTimeType implements UserType {
  static final String sqliteTextTimeStamp = "yyyy-MM-dd kk:mm:ss"; // e.g. 2016-04-27 15:40:18

  @Override
  public int[] sqlTypes() {
    return new int[]{Types.TIMESTAMP};
  }

  @Override
  public Class returnedClass() {
    return java.util.Date.class;
  }

  @Override
  public boolean equals(final Object object, final Object other) throws HibernateException {
    return Objects.equals(object, other);
  }

  @Override
  public int hashCode(final Object object) throws HibernateException {
    return Objects.hashCode(object);
  }

  @Override
  public Object nullSafeGet(final ResultSet rs, final String[] names,
                            final SharedSessionContractImplementor session, final Object owner)
      throws HibernateException, SQLException {
    assert names.length == 1;
    final String dateStr = rs.getString(names[0]);
    final Date date;
    try {
      date = new SimpleDateFormat(sqliteTextTimeStamp).parse(dateStr);
    } catch (final ParseException | NullPointerException exception) {
      exception.printStackTrace();
      throw new HibernateException(exception.getMessage());
    }
    return date;
  }

  @Override
  public void nullSafeSet(final PreparedStatement st, final Object value, final int index,
                          final SharedSessionContractImplementor session)
      throws HibernateException, SQLException {
    try {
      st.setString(index, new SimpleDateFormat(sqliteTextTimeStamp).format(value));
    } catch (final IllegalArgumentException exception) {
      exception.printStackTrace();
      throw new HibernateException(exception.getMessage());
    }
  }

  @Override
  public Object deepCopy(final Object value) throws HibernateException {
    return value;
  }

  @Override
  public boolean isMutable() {
    return false;
  }

  @Override
  public Serializable disassemble(final Object value) throws HibernateException {
    return ((Date) value).getTime();
  }

  @Override
  public Object assemble(Serializable cached, Object owner) throws HibernateException {
    throw new HibernateException("Currently not supported");
  }

  @Override
  public Object replace(Object original, Object target, Object owner) throws HibernateException {
    throw new HibernateException("Currently not supported");
  }
}