package com.holyeye.demo.commons.hibernate;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;

import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.hibernate.engine.jdbc.internal.Formatter;

public class SchemaExport {

	public static void main(String[] args) {

		boolean drop = false;
		boolean create = false;
		String outFile = null;
		String delimiter = "";
		String unitName = null;

		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("--")) {
				if (args[i].equals("--drop")) {
					drop = true;
				} else if (args[i].equals("--create")) {
					create = true;
				} else if (args[i].startsWith("--output=")) {
					outFile = args[i].substring(9);
				} else if (args[i].startsWith("--delimiter=")) {
					delimiter = args[i].substring(12);
				}
			} else {
				unitName = args[i];
			}
		}

		Formatter formatter = FormatStyle.DDL.getFormatter();
		
		Ejb3Configuration jpaConfiguration = new Ejb3Configuration().configure(unitName, null);
		Configuration hibernateConfiguration = jpaConfiguration.getHibernateConfiguration();

		String[] dropSQL = hibernateConfiguration.generateDropSchemaScript(Dialect.getDialect(hibernateConfiguration.getProperties()));
		String[] createSQL = hibernateConfiguration.generateSchemaCreationScript(Dialect.getDialect(hibernateConfiguration.getProperties()));
		String[] resultSQL = addAll(dropSQL, createSQL);
		
		if(drop && create){
			export(outFile, delimiter, formatter, resultSQL);
		}else if (create) {
			export(outFile, delimiter, formatter, createSQL);
		}else if (drop) {
			export(outFile, delimiter, formatter, dropSQL);
		}
	}

	private static void export(String outFile, String delimiter, Formatter formatter, String[] createSQL) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(outFile);
			for (String string : createSQL) {
				writer.print(formatter.format(string) + "\n" + delimiter + "\n");
			}
		} catch (FileNotFoundException e) {
			System.err.println(e);
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	public static <T> T[] addAll(T[] array1, T... array2) {
		if (array1 == null) {
			return clone(array2);
		} else if (array2 == null) {
			return clone(array1);
		}
		final Class<?> type1 = array1.getClass().getComponentType();
		@SuppressWarnings("unchecked")
		// OK, because array is of type T
		T[] joinedArray = (T[]) Array.newInstance(type1, array1.length + array2.length);
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		try {
			System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		} catch (ArrayStoreException ase) {
			// Check if problem was due to incompatible types
			/*
			 * We do this here, rather than before the copy because: - it would
			 * be a wasted check most of the time - safer, in case check turns
			 * out to be too strict
			 */
			final Class<?> type2 = array2.getClass().getComponentType();
			if (!type1.isAssignableFrom(type2)) {
				throw new IllegalArgumentException("Cannot store " + type2.getName() + " in an array of " + type1.getName(), ase);
			}
			throw ase; // No, so rethrow original
		}
		return joinedArray;
	}

	public static <T> T[] clone(T[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

}