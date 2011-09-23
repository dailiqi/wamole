package com.baidu.wamole.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import com.baidu.wamole.exception.TestException;


public class FileUtil {
	private List<String> filelist = new ArrayList<String>();

	/**
	 * Factory method.
	 */
	public static FileUtil newFileUtils() {
		return new FileUtil();
	}

	/**
	 * Empty constructor.
	 */
	public FileUtil() {
	}

	public static boolean isDirectory(String path) {
		File f = new File(path);
		try {
			if (f.isDirectory()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public static void deleteFile(String path) {
		File f = new File(path);
		if(f.exists()) {
			f.delete();
		}
	}

	public static boolean existsFile(String path) {
		File file = new File(path);
		return file.exists() && file.isFile() && file.length() > 0;
	}

	public List<String> getFilesByPath(String path) {
		if (isDirectory(path)) {
			File dir = new File(path);
			File[] files = dir.listFiles();
			if (files == null) {
			}
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					getFilesByPath(files[i].getAbsolutePath());
				} else {
					String strFileName = files[i].getAbsolutePath().replace(
							"\\", "/");
					filelist.add(strFileName);
				}
			}
			return filelist;
		} else
			return null;
	}

	/**
	 * 默认以utf-8读取文件
	 * 
	 * @param path 文件路径
	 * @return 文件内容
	 */
	public static String readFile(String path) {
		return readFile(path, "utf-8");
	}

	public static String readFile(File file) {
		return readFile(file.getAbsolutePath());
	}

	public static void writeFile(String path, String content) {
		File file = new File(path);
		BufferedWriter writer = null;
		try {
			
			// 建立文件
			if (!file.exists()) {
				//建立文件夹
				if( path.lastIndexOf("/") > 0 ) {
					path = path.substring(0, path.lastIndexOf("/"));
					File dir = new File(path);
					dir.mkdirs();
				}
				file.createNewFile();
			}
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "UTF-8"));
			writer.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 以指定编码方式读取文件
	 * 
	 * @param path 路径
	 * @param charset 文件编码
	 * @return 文件内容
	 */
	public static String readFile(String path, String charset) {
		StringBuilder content = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(path), charset));
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// 关闭 Reader 出现的异常一般不需要处理。
					e.printStackTrace();
				}
			}
		}
		return content.toString();
	}

	/**
	 * Interpret the filename as a file relative to the given file - unless the
	 * filename already represents an absolute filename.
	 * 
	 * @param file the "reference" file for relative paths. This instance must
	 *            be an absolute file and must not contain &quot;./&quot; or
	 *            &quot;../&quot; sequences (same for \ instead of /). If it is
	 *            null, this call is equivalent to
	 *            <code>new java.io.File(filename)</code>.
	 * 
	 * @param filename a file name
	 * 
	 * @return an absolute file that doesn't contain &quot;./&quot; or
	 *         &quot;../&quot; sequences and uses the correct separator for the
	 *         current platform.
	 * @throws TestException
	 */
	public File resolveFile(File file, String filename) throws TestException {
		filename = filename.replace('/', File.separatorChar).replace('\\',
				File.separatorChar);

		// deal with absolute files
		if (filename.startsWith(File.separator)
				|| (filename.length() >= 2
						&& Character.isLetter(filename.charAt(0)) && filename
						.charAt(1) == ':')) {
			return new File(normalize(filename));
		}

		if (file == null) {
			return new File(filename);
		}

		File helpFile = new File(file.getAbsolutePath());
		StringTokenizer tok = new StringTokenizer(filename, File.separator);
		while (tok.hasMoreTokens()) {
			String part = tok.nextToken();
			if (part.equals("..")) {
				helpFile = getParentFile(helpFile);
				if (helpFile == null) {
					String msg = "The file or path you specified (" + filename
							+ ") is invalid relative to " + file.getPath();
					throw new TestException(msg);
				}
			} else if (part.equals(".")) {
				// Do nothing here
			} else {
				helpFile = new File(helpFile, part);
			}
		}

		return new File(helpFile.getAbsolutePath());
	}

	/**
	 * &quot;normalize&quot; the given absolute path.
	 * 
	 * <p>
	 * This includes:
	 * <ul>
	 * <li>Uppercase the drive letter if there is one.</li>
	 * <li>Remove redundant slashes after the drive spec.</li>
	 * <li>resolve all ./, .\, ../ and ..\ sequences.</li>
	 * <li>DOS style paths that start with a drive letter will have \ as the
	 * separator.</li>
	 * </ul>
	 * 
	 * @throws TestException
	 * 
	 * @throws java.lang.NullPointewrException if the file path is equal to null.
	 */
	@SuppressWarnings("unchecked")
	public static String normalize(String path) throws TestException {
		String orig = path;

		path = path.replace('/', File.separatorChar).replace('\\',
				File.separatorChar);

		// make sure we are dealing with an absolute path
		int colon = path.indexOf(":");

		if (!path.startsWith(File.separator)
				&& !(path.length() >= 2 && Character.isLetter(path.charAt(0)) && colon == 1)) {
			String msg = path + " is not an absolute path";
			throw new TestException(msg);
		}

		boolean dosWithDrive = false;
		String root = null;
		// Eliminate consecutive slashes after the drive spec
		if ((path.length() >= 2 && Character.isLetter(path.charAt(0)) && path
				.charAt(1) == ':')) {

			dosWithDrive = true;

			char[] ca = path.replace('/', '\\').toCharArray();
			StringBuffer sbRoot = new StringBuffer();
			for (int i = 0; i < colon; i++) {
				sbRoot.append(Character.toUpperCase(ca[i]));
			}
			sbRoot.append(':');
			if (colon + 1 < path.length()) {
				sbRoot.append(File.separatorChar);
			}
			root = sbRoot.toString();

			// Eliminate consecutive slashes after the drive spec
			StringBuffer sbPath = new StringBuffer();
			for (int i = colon + 1; i < ca.length; i++) {
				if ((ca[i] != '\\') || (ca[i] == '\\' && ca[i - 1] != '\\')) {
					sbPath.append(ca[i]);
				}
			}
			path = sbPath.toString().replace('\\', File.separatorChar);

		} else {
			if (path.length() == 1) {
				root = File.separator;
				path = "";
			} else if (path.charAt(1) == File.separatorChar) {
				// UNC drive
				root = File.separator + File.separator;
				path = path.substring(2);
			} else {
				root = File.separator;
				path = path.substring(1);
			}
		}

		@SuppressWarnings("rawtypes")
		Stack s = new Stack();
		s.push(root);
		StringTokenizer tok = new StringTokenizer(path, File.separator);
		while (tok.hasMoreTokens()) {
			String thisToken = tok.nextToken();
			if (".".equals(thisToken)) {
				continue;
			} else if ("..".equals(thisToken)) {
				if (s.size() < 2) {
					throw new TestException("Cannot resolve path " + orig);
				} else {
					s.pop();
				}
			} else { // plain component
				s.push(thisToken);
			}
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.size(); i++) {
			if (i > 1) {
				// not before the filesystem root and not after it, since root
				// already contains one
				sb.append(File.separatorChar);
			}
			sb.append(s.elementAt(i));
		}

		path = sb.toString();
		if (dosWithDrive) {
			path = path.replace('/', '\\');
		}
		return path;
	}

	public File getParentFile(File f) {
		if (f != null) {
			String p = f.getParent();
			if (p != null) {
				return new File(p);
			}
		}
		return null;
	}

	/**
	 * 从当前编译路径中读取资源文件
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String readJarFile(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				FileUtil.class.getResourceAsStream(path), "UTF-8"));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		return sb.toString();
	}
}
