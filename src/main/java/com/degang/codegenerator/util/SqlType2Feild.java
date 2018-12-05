/**
 * 
 */
package com.degang.codegenerator.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SqlType2Feild {

	private static Map<Integer,String> sqltype2Feild = new HashMap<>();
	static {
		sqltype2Feild.put(Types.VARCHAR, "String");
		// java.util.Date
		sqltype2Feild.put(Types.DATE, "Date");
		//mysql
		sqltype2Feild.put(Types.BIT, "Boolean");
		sqltype2Feild.put(Types.TINYINT, "Byte");
		sqltype2Feild.put(Types.SMALLINT, "Integer");
		sqltype2Feild.put(Types.INTEGER, "Integer");
		sqltype2Feild.put(Types.BIGINT, "Long");
		sqltype2Feild.put(Types.REAL, "Float");
		sqltype2Feild.put(Types.DOUBLE, "Double");
		
		sqltype2Feild.put(Types.CHAR, "String");
		sqltype2Feild.put(Types.LONGVARCHAR, "String");

		// java.sql.Blob
		sqltype2Feild.put(Types.BINARY, "Blob");
		sqltype2Feild.put(Types.VARBINARY, "Blob");
		sqltype2Feild.put(Types.LONGVARBINARY, "Blob");

		// java.util.Date
		sqltype2Feild.put(Types.TIME, "Date");
		// java.util.Date
		sqltype2Feild.put(Types.TIMESTAMP, "Date");
		// oracle
		sqltype2Feild.put(Types.NUMERIC, "Long");
		// java.math.BigDecimal
		sqltype2Feild.put(Types.FLOAT, "BigDecimal");
		sqltype2Feild.put(Types.DECIMAL, "BigDecimal");
		// java.sql.Clob
		sqltype2Feild.put(Types.CLOB, "Clob");
	}

	public static String mapJavaType(int sqltype) {
		String javaType = sqltype2Feild.get(sqltype);
		if(!StringUtils.isEmpty(javaType)){
			return javaType;
		}else{
			log.error("字段没有对应的Java类型，SQL_TYPE：" + sqltype);
			return "String";
		}
	}
}
