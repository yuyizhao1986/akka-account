package com.ameba.account.repository;

import com.google.common.collect.Lists;

import com.ameba.core.utils.CollectionHelper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

@Slf4j
@AllArgsConstructor
public class MybatisMapperGenerator {
  private DataSource dataSource;
  private String schema;
  private String enumPackage;

  public void generateMapper(String table) {
    generate(table, false);
  }

  public void generateFull(String table) {
    generate(table, false);
  }

  private void generate(String table, boolean autoId) {
    try {
      Connection connection = dataSource.getConnection();
      Statement statement = connection.createStatement();
      String sql = String.format("select COLUMN_NAME,COLUMN_TYPE from information_schema.COLUMNS "
          + "where TABLE_SCHEMA='%s' and TABLE_NAME='%s'", schema, table);
      ResultSet resultSet = statement.executeQuery(sql);
      List<String> columns = Lists.newArrayList();
      List<String> fields = Lists.newArrayList();
      int row = 1;
      String keyName = null;
      String keyJava = null;
      while (resultSet.next()) {
        String name = resultSet.getString(1);
        String type = resultSet.getString(2);
        if (keyName == null) {
          keyName = name;
          keyJava = toJavaField(name);
        }
        if (!(autoId && name.equals("id") && type.startsWith("bigint"))) {
          columns.add(name);
          fields.add(toJavaField(name));
        }
        row++;
      }
      StringBuilder insert = new StringBuilder(String.format("@Insert(\"insert into %s (\"\n", table));
      buildColumn(insert, columns);
      insert.append("\n+\" values(\"\n");
      buildColumn(insert, fields.stream()
          .map(it -> String.format("#{%s}", it)).collect(Collectors.toList()));
      insert.append(")");
      System.out.println(insert.toString());

      StringBuilder get = new StringBuilder(String.format("@Select(\"SELECT * FROM %s WHERE %s = #{%s}\")\n", table,
          keyName, keyJava));
      get.append("@Results({\n");
      for (int index = 0; index < fields.size(); index++) {
        if (!fields.get(index).equals(columns.get(index))) {
          String enumType = isJavaEnum(fields.get(index));
          if (enumType == null) {
            get.append(String.format("@Result(property = \"%s\", column = \"%s\")",
                fields.get(index), columns.get(index)));
          } else {
            get.append(String.format("@Result(property = \"%s\", column = \"%s\", javaType = %s)",
                fields.get(index), columns.get(index), enumType));
          }
          if (index < fields.size() - 1) {
            get.append(",\n");
          }
        }
      }
      get.append("})");
      System.out.println(get.toString());
    } catch (SQLException ex) {
      log.error("", ex);
    }
  }

  private void buildColumn(StringBuilder insert, List<String> list) {
    List<String> subList = Lists.newArrayList();
    for (int index = 0; index < list.size(); index++) {
      subList.add(list.get(index));
      if (index > 0 && index % 4 == 0 && index != list.size() - 1) {
        insert.append("+\"" + CollectionHelper.joinToString(subList) + ",\"\n");
        subList.clear();
      }
    }
    if (!subList.isEmpty()) {
      insert.append("+\"" + CollectionHelper.joinToString(subList) + ")\"");
    }
  }

  private String toJavaField(String column) {
    String[] split = column.split("_");
    if (split.length < 2) {
      return column;
    }
    String field = split[0];
    for (int index = 1; index < split.length; index++) {
      field = field + split[index].substring(0, 1).toUpperCase() + split[index].substring(1);
    }
    return field;
  }

  private String isJavaEnum(String javaField) {
    try {
      String className = javaField.substring(0, 1).toUpperCase() + javaField.substring(1);
      Class<?> name = Class.forName(enumPackage + "." + className);
      if (name != null && name.isEnum()) {
        return className + ".class";
      }
      return null;
    } catch (Exception ex) {
      return null;
    }
  }
}
