package com.holyeye.demo.commons.enumeration.usertype;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.CharacterType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

import com.holyeye.demo.commons.enumeration.PersistentEnum;
import com.holyeye.demo.commons.enumeration.PersistentEnumUtil;

public class GenericEnumUserType<E extends PersistentEnum> implements UserType {

	protected Class<E> enumClass;
	private String typeName;

	@SuppressWarnings("unchecked")
	public GenericEnumUserType() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		Type type = genericSuperclass.getActualTypeArguments()[0];
		if (type instanceof ParameterizedType) {
			this.enumClass = (Class<E>) ((ParameterizedType) type).getRawType();
		} else {
			this.enumClass = (Class<E>) type;
		}
	}

	public GenericEnumUserType(Class<E> enumClass) {
		this.enumClass = enumClass;
	}

	public int[] sqlTypes() {
		try {
			Field valueField = enumClass.getDeclaredField("value");
			typeName = valueField.getType().getSimpleName();
			if (typeName.equals("String"))
				return new int[] { StringType.INSTANCE.sqlType() };
			if (typeName.equals("Integer") || typeName.equals("int"))
				return new int[] { IntegerType.INSTANCE.sqlType() };
			if (typeName.equals("Character") || typeName.equals("char"))
				return new int[] { CharacterType.INSTANCE.sqlType() };
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
		return new int[] { IntegerType.INSTANCE.sqlType() };
	}

	public Class<?> returnedClass() {
		return enumClass;
	}

	public boolean isMutable() {
		return true;
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		return x == y;
	}

	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
		Object value = rs.getObject(names[0]);
		return rs.wasNull() ? null : PersistentEnumUtil.valueOf(enumClass, value);
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {

		if (value == null) {
			st.setNull(index, IntegerType.INSTANCE.sqlType());
		} else {
			try {
				Field valueField = value.getClass().getDeclaredField("value");
				valueField.setAccessible(true);
				Object fieldValue = valueField.get(value);
				if (fieldValue instanceof Integer)
					st.setInt(index, (Integer) fieldValue);
				if (fieldValue instanceof String)
					st.setString(index, (String) fieldValue);
				if (fieldValue instanceof Character)
					st.setString(index, "'" + (Character) fieldValue + "'");
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			} catch (NoSuchFieldException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}

		}
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException {
//		log.debug("original={}, Object target={}, Object owner{}", new Object[]{original, target, owner});
		return original;
	}

}