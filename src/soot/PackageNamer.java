/* Soot - a J*va Optimization Framework
 * Copyright (C) 2003 Jerome Miecznikowski
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package soot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

public class PackageNamer
{
	static boolean has_FixedNames(){
		return fixed;
	}

	static String get_FixedClassName(String originalFullClassName)
	{
		if (!fixed)
			return originalFullClassName;

		for (NameHolder h : appRoots) {
			if (h.contains_OriginalName(new StringTokenizer(originalFullClassName, "."), true))
				return h.get_FixedName(new StringTokenizer(originalFullClassName, "."), true);
		}

		return originalFullClassName.substring( originalFullClassName.lastIndexOf( ".") + 1);
	}

	static String get_FixedPackageName(String originalPackageName)
	{
		if (!fixed)
			return originalPackageName;

		if (originalPackageName.equals( ""))
			return "";

		for (NameHolder h : appRoots) {
			if (h.contains_OriginalName(new StringTokenizer(originalPackageName, "."), false))
				return h.get_FixedName(new StringTokenizer(originalPackageName, "."), false);
		}

		return originalPackageName;
	}


	private class NameHolder
	{
		private final String originalName;
		private String packageName, className;
		private final ArrayList<NameHolder> children;
		private NameHolder parent;
		private boolean isClass;


		NameHolder(String name, NameHolder parent, boolean isClass)
		{
			originalName = name;
			className = name;
			packageName = name;

			this.parent = parent;
			this.isClass = isClass;

			children = new ArrayList<>();
		}

		void set_ClassAttr() {
			isClass = true;
		}

		boolean is_Class() {
			return children.isEmpty() || isClass;
		}

		boolean is_Package() {
			return (!children.isEmpty());
		}

		String get_PackageName() {
			return packageName;
		}

		String get_ClassName() {
			return className;
		}

		String get_OriginalName() {
			return originalName;
		}

		ArrayList<NameHolder> get_Children() {
			return children;
		}

		String get_FixedPackageName() {
			if (parent == null)
				return "";

			return parent.retrieve_FixedPackageName();
		}

		String retrieve_FixedPackageName()
		{
			if (parent == null)
				return packageName;

			return parent.get_FixedPackageName() + "." + packageName;
		}

		String get_FixedName(StringTokenizer st, boolean forClass)
		{
			if (!st.nextToken().equals(originalName))
				throw new RuntimeException( "Unable to resolve naming.");

			return retrieve_FixedName( st, forClass);
		}

		private String retrieve_FixedName( StringTokenizer st, boolean forClass)
		{
			if (!st.hasMoreTokens()) {
				if (forClass)
					return className;
				else
					return packageName;
			}

			String subName = st.nextToken();
			for (NameHolder h : children) {
				if (h.get_OriginalName().equals(subName)) {
					if (forClass)
						return h.retrieve_FixedName(st, forClass);
					else
						return packageName + "." + h.retrieve_FixedName(st, forClass);
				}
			}
			throw new RuntimeException( "Unable to resolve naming.");
		}

		boolean contains_OriginalName(StringTokenizer st, boolean forClass)
		{
			if (!get_OriginalName().equals(st.nextToken()))
				return false;

			return finds_OriginalName( st, forClass);
		}

		private boolean finds_OriginalName( StringTokenizer st, boolean forClass)
		{
			if (!st.hasMoreTokens())
				return (((forClass) && (is_Class())) || ((!forClass) && (is_Package())));

			String subName = st.nextToken();
			for (NameHolder h : children) {
				if (h.get_OriginalName().equals(subName))
					return h.finds_OriginalName(st, forClass);
			}

			return false;
		}

		void fix_ClassNames(String curPackName)
		{
			if ((is_Class()) && (keywords.contains( className))) {
				String tClassName = className;

				if (Character.isLowerCase( className.charAt( 0))) {
					tClassName = tClassName.substring( 0, 1).toUpperCase() + tClassName.substring( 1);
					className = tClassName;
				}

				for (int i=0; keywords.contains( className); i++)
					className = tClassName + "_c" + i;
			}

			for (NameHolder aChildren : children) aChildren.fix_ClassNames(curPackName + "." + packageName);
		}

		void fix_PackageNames()
		{
			if ((is_Package()) && (!verify_PackageName())) {
				String tPackageName = packageName;

				if (Character.isUpperCase( packageName.charAt( 0))) {
					tPackageName = tPackageName.substring( 0, 1).toLowerCase() + tPackageName.substring( 1);
					packageName = tPackageName;
				}

				for (int i = 0; !verify_PackageName(); i++)
					packageName = tPackageName + "_p" + i;
			}

			for (NameHolder aChildren : children) aChildren.fix_PackageNames();
		}


		boolean verify_PackageName()
		{
			return ((!keywords.contains(packageName)) &&
					(!siblingClashes(packageName)) &&
					((!is_Class()) || (!className.equals(packageName))));
		}

		boolean siblingClashes(String name)
		{
			Iterator<NameHolder> it;

			if (parent == null) {

				if (appRoots.contains( this))
					it = appRoots.iterator();
				else
					throw new RuntimeException( "Unable to find package siblings.");
			}
			else
				it = parent.get_Children().iterator();

			while (it.hasNext()) {
				NameHolder sibling = it.next();

				if (sibling == this)
					continue;

				if (((sibling.is_Package()) && (sibling.get_PackageName().equals( name))) ||
						((sibling.is_Class()) && (sibling.get_ClassName().equals( name))))

					return true;
			}

			return false;
		}

		void dump(String indentation)
		{
			System.out.print( indentation + "\"" + originalName + "\", \"" + packageName + "\", \"" + className + "\" (");
			if (is_Class())
				System.out.print("c");
			if (is_Package())
				System.out.print("p");
			System.out.println( ")");

			for (NameHolder aChildren : children) aChildren.dump(indentation + "  ");
		}
	}

	private static boolean fixed = false;
	private static final ArrayList<NameHolder> appRoots = new ArrayList<>();
	private final ArrayList<NameHolder> otherRoots = new ArrayList<>();
	private final HashSet<String> keywords = new HashSet<>();
	private static char fileSep;
	private static String classPath;
	private static String pathSep;

	public void fixNames()
	{
		if (fixed)
			return;

		String[] keywordArray =
				{
						"abstract",	    "default",	    "if",            "private",	    "this",	    "boolean",
						"do",	    "implements",	    "protected",	    "throw",	    "break",
						"double",	    "import",	    "public",	    "throws",	    "byte",	    "else",
						"instanceof",	    "return",	    "transient",	    "case",	    "extends",
						"int",	    "short",	    "try",	    "catch",	    "final",	    "interface",
						"static",	    "void",             "char",	    "finally",	    "long",	    "strictfp",
						"volatile",	    "class",	    "float",	    "native",	    "super",	    "while",
						"const",	    "for",	    "new",	    "switch",	    "continue",	    "goto",
						"package",	    "synchronized",	    "true",	    "false",	    "null"
				};

		for (String element : keywordArray)
			keywords.add( element);

		Iterator classIt = Scene.getInstance().getLibraryClasses().iterator();
		while (classIt.hasNext())
			add_ClassName( ((SootClass) classIt.next()).getName(), otherRoots);

		classIt = Scene.getInstance().getApplicationClasses().iterator();
		while (classIt.hasNext())
			add_ClassName( ((SootClass) classIt.next()).getName(), appRoots);

		Iterator<NameHolder> arit = appRoots.iterator();
		while (arit.hasNext())
			arit.next().fix_ClassNames( "");

		arit = appRoots.iterator();
		while (arit.hasNext())
			arit.next().fix_PackageNames();

		fileSep = System.getProperty( "file.separator").charAt(0);
		pathSep = System.getProperty( "path.separator");
		classPath = System.getProperty( "java.class.path");

		fixed = true;
	}

	private void add_ClassName( String className, ArrayList<NameHolder> roots)
	{
		ArrayList<NameHolder> children = roots;
		NameHolder curNode = null;

		StringTokenizer st = new StringTokenizer( className, ".");
		while (st.hasMoreTokens()) {
			String curName = st.nextToken();

			NameHolder child = null;
			boolean found = false;
			Iterator<NameHolder> lit = children.iterator();

			while (lit.hasNext()) {
				child = lit.next();

				if (child.get_OriginalName().equals( curName)) {

					if (st.hasMoreTokens() == false)
						child.set_ClassAttr();

					found = true;
					break;
				}
			}

			if (!found) {
				child = new NameHolder( curName, curNode, st.hasMoreTokens() == false);
				children.add( child);
			}

			curNode = child;
			children = child.get_Children();
		}
	}

}
