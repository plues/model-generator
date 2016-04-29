package org.hibernate.usertype;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class SQLiteDateTimeType implements UserType {
    static final SimpleDateFormat sqliteTextTimeStamp = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss"); // e.g. 2016-04-27 15:40:18
    @Override
    public int[] sqlTypes() {
        return new int[] { Types.TIMESTAMP };
    }

    @Override
    public Class returnedClass() {
        return java.util.Date.class;
   }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return Objects.equals(x, y);
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        return Objects.hashCode(x);
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        assert names.length == 1;
        String dateStr = rs.getString(names[0]);
        Date d;
        try {
            d = sqliteTextTimeStamp.parse(dateStr.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new HibernateException(e.getMessage());
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new HibernateException(e.getMessage());
        }
        return d;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        try {
            st.setString(index, sqliteTextTimeStamp.format(value));
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
            throw new HibernateException(e.getMessage());
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        throw new HibernateException("Currently not supported");
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
