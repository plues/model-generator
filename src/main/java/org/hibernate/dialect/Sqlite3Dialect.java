/*
 * https://gist.github.com/virasak/54436
 *
 * The author disclaims copyright to this source code.  In place of
 * a legal notice, here is a blessing:
 *
 *    May you do good and not evil.
 *    May you find forgiveness for yourself and forgive others.
 *    May you share freely, never taking more than you give.
 *
 */

package org.hibernate.dialect;

import java.sql.Types;

@SuppressWarnings("unused")
public class Sqlite3Dialect extends Dialect {

  /**
   * Create instance of SQlite3 Dialect for Hibernate.
   */
  public Sqlite3Dialect() {
    registerColumnType(Types.BIT, "integer");
    registerColumnType(Types.TINYINT, "tinyint");
    registerColumnType(Types.SMALLINT, "smallint");
    registerColumnType(Types.INTEGER, "integer");
    registerColumnType(Types.BIGINT, "bigint");
    registerColumnType(Types.FLOAT, "float");
    registerColumnType(Types.REAL, "real");
    registerColumnType(Types.DOUBLE, "double");
    registerColumnType(Types.NUMERIC, "numeric");
    registerColumnType(Types.DECIMAL, "decimal");
    registerColumnType(Types.CHAR, "char");
    registerColumnType(Types.VARCHAR, "varchar");
    registerColumnType(Types.LONGVARCHAR, "longvarchar");
    registerColumnType(Types.DATE, "date");
    registerColumnType(Types.TIME, "time");
    registerColumnType(Types.TIMESTAMP, "timestamp");
    registerColumnType(Types.BINARY, "blob");
    registerColumnType(Types.VARBINARY, "blob");
    registerColumnType(Types.LONGVARBINARY, "blob");
    registerColumnType(Types.BLOB, "blob");
    registerColumnType(Types.CLOB, "clob");
    registerColumnType(Types.BOOLEAN, "integer");
  }

  public boolean supportsIdentityColumns() {
    return true;
  }

  public boolean hasDataTypeInIdentityColumn() {
    return false; // As specify in NHibernate dialect
  }

  public String getIdentityColumnString() {
    return "integer";
  }

  public String getIdentitySelectString() {
    return "select last_insert_rowid()";
  }

  public boolean supportsTemporaryTables() {
    return true;
  }

  public String getCreateTemporaryTableString() {
    return "create temporary table if not exists";
  }

  public boolean dropTemporaryTableAfterUse() {
    return false;
  }

  public boolean supportsCurrentTimestampSelection() {
    return true;
  }

  public boolean isCurrentTimestampSelectStringCallable() {
    return false;
  }

  public String getCurrentTimestampSelectString() {
    return "select current_timestamp";
  }

  public boolean supportsUnionAll() {
    return true;
  }

  public boolean hasAlterTable() {
    return false; // As specify in NHibernate dialect
  }

  public boolean dropConstraints() {
    return false;
  }

  public String getAddColumnString() {
    return "add column";
  }

  public String getForUpdateString() {
    return "";
  }

  public boolean supportsOuterJoinForUpdate() {
    return false;
  }

  public String getDropForeignKeyString() {
    throw new UnsupportedOperationException(
        "No drop foreign key syntax supported by SQLiteDialect");
  }

  public String getAddForeignKeyConstraintString(final String constraintName,
                                                 final String[] foreignKey,
                                                 final String referencedTable,
                                                 final String[] primaryKey,
                                                 final boolean referencesPrimaryKey) {
    throw new UnsupportedOperationException("No add foreign key syntax supported by SQLiteDialect");
  }

  public String getAddPrimaryKeyConstraintString(final String constraintName) {
    throw new UnsupportedOperationException("No add primary key syntax supported by SQLiteDialect");
  }

  public boolean supportsIfExistsBeforeTableName() {
    return true;
  }

  public boolean supportsCascadeDelete() {
    return false;
  }
}
