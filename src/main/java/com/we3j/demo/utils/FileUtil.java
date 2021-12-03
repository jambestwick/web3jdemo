package com.we3j.demo.utils;

import org.springframework.util.StringUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>文件描述：文件工具类<p>
 * <p>作者：jambestwick<p>
 * <p>创建时间：2020/5/28<p>
 * <p>更新时间：2020/5/28<p>
 * <p>版本号：${VERSION}<p>
 * <p>邮箱：jambestwick@126.com<p>
 */
public class FileUtil {

    private static final String TAG = FileUtil.class.getName();

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!StringUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将字符串写入文件
     *
     * @param file    文件
     * @param content 写入内容
     * @param append  是否追加在文件末
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean writeFileFromString(File file, String content, boolean append) {
        if (file == null || content == null) return false;
        if (!createOrExistsFile(file)) return false;
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(bw);
        }
    }

    public static boolean writeFileFromLineString(File file, String content, boolean append) {
        if (file == null || content == null) return false;
        if (!createOrExistsFile(file)) return false;
        BufferedWriter bw = null;
        try {

            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(content);
            bw.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(bw);
        }
    }

    /**
     * 将输入流写入文件
     *
     * @param file   文件
     * @param is     输入流
     * @param append 是否追加在文件末
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean writeFileFromIS(File file, InputStream is, boolean append) {
        if (file == null || is == null) return false;
        if (!createOrExistsFile(file)) return false;
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte data[] = new byte[1024];
            int len;
            while ((len = is.read(data, 0, 1024)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(is, os);
        }
    }


    /**
     * 将输入流写入文件
     *
     * @param file   文件
     * @param data   输入流
     * @param append 是否追加在文件末
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean writeFileFromByte(File file, byte[] data, boolean append) {
        if (file == null || data == null) return false;
        if (!createOrExistsFile(file)) return false;
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            os.write(data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(os);
        }
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(File file) {
        if (file == null) return false;
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public static File getFileByPath(String filePath) {
        return StringUtils.isEmpty(filePath) ? null : new File(filePath);
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 指定编码按行读取文件到字符串中
     *
     * @param file        文件
     * @param charsetName 编码格式
     * @return 字符串
     */
    public static String readFile2String(File file, String charsetName) {
        if (file == null) return null;
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            if (StringUtils.isEmpty(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\r\n");// windows系统换行为\r\n，Linux为\n
            }
            // 要去除最后的换行符
            return sb.delete(sb.length() - 2, sb.length()).toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(reader);
        }
    }

    /**
     * 指定编码按行读取文件到字符串中
     *
     * @param file        文件
     * @param charsetName 编码格式
     * @return 字符串
     */
    public static String readFileNull2String(File file, String charsetName) {
        if (file == null) return null;
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            if (StringUtils.isEmpty(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\r\n");// windows系统换行为\r\n，Linux为\n
            }
            // 要去除最后的换行符
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(reader);
        }
    }

    /**
     * 删除目录
     *
     * @param dirPath 目录路径
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteDir(String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    /**
     * 删除目录
     *
     * @param dirPath 目录路径
     * @param days    多少天前创建的文件
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteDirBeforeDays(String dirPath, long days) {
        return deleteDir(getFileByPath(dirPath), days);
    }

    private static boolean deleteDir(File dir, long days) {
        if (dir == null) return false;
        // 目录不存在返回true
        if (!dir.exists()) return true;
        // 不是目录返回false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null) {
            Date clearDay = TimeUtil.getBeforeDay(new Date(), days);
            for (int i = 0; i < files.length; i++) {
                long createMillTime = FileUtil.getFileCreateTime(files[i].getAbsolutePath());
                if (createMillTime <= clearDay.getTime()) {
                    files[i].delete();
                }
            }
        }
        return true;
    }

    /**
     * 删除目录
     *
     * @param dir 目录
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteDir(File dir) {
        if (dir == null) return false;
        // 目录不存在返回true
        if (!dir.exists()) return true;
        // 不是目录返回false
        if (!dir.isDirectory()) return false;
        // 现在文件存在且是文件夹
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!deleteFile(file)) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 删除文件
     *
     * @param srcFilePath 文件路径
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteFile(String srcFilePath) {
        return deleteFile(getFileByPath(srcFilePath));
    }

    /**
     * 删除文件
     *
     * @param file 文件
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteFile(File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    /**
     * 删除目录下的所有文件
     *
     * @param dirPath 目录路径
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteFilesInDir(String dirPath) {
        return deleteFilesInDir(getFileByPath(dirPath));
    }

    /**
     * 删除目录下的所有文件
     *
     * @param dir 目录
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteFilesInDir(File dir) {
        if (dir == null) return false;
        // 目录不存在返回true
        if (!dir.exists()) return true;
        // 不是目录返回false
        if (!dir.isDirectory()) return false;
        // 现在文件存在且是文件夹
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!deleteFile(file)) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return true;
    }

    /**
     * 定时删除日志
     *
     * @param path  日志路径
     * @param day   最近几天
     * @param pdate 计划日期
     */
    public static void deleteLogFileMyself(String path, int day, String pdate) {
        File file = new File(path);
        Calendar calendar = Calendar.getInstance();

        DateFormat df = new SimpleDateFormat(TimeUtil.DEFAULT_HOUR_FORMAT);
        Date currentDate = null;
        try {
            currentDate = df.parse(pdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, -day); //得到前几天
        Date date = calendar.getTime();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return;
            }
            for (File f : files) {//xxxx_2019-09-05_2325
                try {
                    Date logdate = df.parse(f.getName().split("Log")[0]);
                    //若是文件夹且在设定日期之前
                    if (logdate.before(date)) {
                        f.delete();
                    }
                } catch (ParseException e) {
                    try {
                        f.delete();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    //e.printStackTrace();
                }
            }
        }

    }

    /***
     * 查询指定日期文件的集合
     *
     * **/
    public static List<File> getLogFileAcrossDay(String rootPath, int day) {
        List<File> resultFiles = new ArrayList<>();
        File file = new File(rootPath);
        Calendar calendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");

        Date currentDate = new Date();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, -day); //得到前几天
        Date date = calendar.getTime();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {//xxxx_2019-09-05_2325
                Date logdate = null;
                try {
                    logdate = df.parse(f.getName().split("Log")[0]);
                    //若是文件夹且在设定日期之前
                    if (logdate.after(date)) {
                        resultFiles.add(f);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        return resultFiles;

    }

    /***
     * 查询指定日期文件的集合
     * @param days 指定的日期集合
     *
     * **/
    public static List<File> getLogFileAcrossDay(String rootPath, List<String> days) {
        List<File> resultFiles = new ArrayList<>();
        File file = new File(rootPath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {//xxxx_2019-09-05_2325
                for (int i = 0; i < days.size(); i++) {
                    try {
                        String logdate = f.getName().split("Log")[0];
                        if (logdate.contains(days.get(i))) {
                            resultFiles.add(f);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return resultFiles;

    }

    public static List<File> getLogFilesWithOutExist(String rootPath, List<String> days) {
        List<File> resultFiles = new ArrayList<>();
        File file = new File(rootPath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < days.size(); i++) {
                for (int j = 0; j < files.length; j++) {//xxxx_2019-09-05_2325
                    try {
                        String logdate = files[i].getName().split("Log")[0].replaceAll(" ", "");
                        if (logdate.contains(days.get(i))) {
                            resultFiles.add(files[i]);
                            break;
                        }
                        if (j == files.length - 1) {
                            Date logDate = TimeUtil.str2Date(days.get(i), TimeUtil.INTEGER_HOUR_FORMAT);
                            String filePath = file.getAbsoluteFile() + "/" + TimeUtil.date2Str(logDate, TimeUtil.DEFAULT_HOUR_FORMAT) + "Log.txt";
                            FileUtil.createOrExistsFile(filePath);
                            resultFiles.add(new File(filePath));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return resultFiles;

    }


    /***
     * 获取文件的时间
     * **/
    public static Long getFileCreateTime(String filePath) {
        File file = new File(filePath);
        try {
            Path path = Paths.get(filePath);
            BasicFileAttributeView basicView = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
            BasicFileAttributes attr = basicView.readAttributes();
            return attr.creationTime().toMillis();
        } catch (Exception e) {
            e.printStackTrace();
            return file.lastModified();
        }
    }

    public static ArrayList<File> findFileByStartEndTime(String dir, long startTime, long endTime) {
        File file = new File(dir);
        if (!file.exists()) {
            return null;
        }
        ArrayList<File> resultList = new ArrayList<>();
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            long createTime = getFileCreateTime(files[i].getAbsolutePath());
            if (createTime >= startTime && createTime <= endTime) {
                resultList.add(files[i]);
            }
        }
        return resultList;
    }

    public static ArrayList<String> findFilePathByStartEndTime(String dir, long startTime, long endTime) {
        File file = new File(dir);
        if (!file.exists()) {
            return null;
        }
        ArrayList<String> resultList = new ArrayList<>();
        File[] files = file.listFiles();
        for (File value : files) {
            long createTime = getFileCreateTime(value.getAbsolutePath());
            if (createTime >= startTime && createTime <= endTime) {
                resultList.add(value.getAbsolutePath());
            }
        }
        return resultList;
    }
    /***
     * 获取文件夹里的所有在指定时间内的文件
     * **/
    public static List<File> getFilesByCreateTime(String filePath, int day) {
        List<File> resultFile = new ArrayList<>();
        long current = System.currentTimeMillis();
        File file = new File(filePath);
        if (file.isDirectory() && file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                long fileCreateTime = getFileCreateTime(files[i].getAbsolutePath());
                if (current - fileCreateTime < day * 24 * 3600 * 1000L) {
                    resultFile.add(files[i]);
                }
            }
        }
        return resultFile;
    }



}
