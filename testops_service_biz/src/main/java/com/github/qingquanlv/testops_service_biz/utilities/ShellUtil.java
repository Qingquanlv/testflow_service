package com.github.qingquanlv.testops_service_biz.utilities;

import java.io.*;
import java.util.List;

/**
 * @Author: qingquan.lv
 * @Date: 2022/2/8 下午6:09
 */
public class ShellUtil {

        public static String run(List<String> commandList, String path) throws IOException {
                // ProcessBuilder是一个用于创建操作系统进程的类，它的start()方法用于启动一个进行
                ProcessBuilder processBuilder = new ProcessBuilder(commandList).directory(new File(path));
                // 启动进程
                Process process = processBuilder.start();
                // 解析输出
                String result = convertStreamToStr(process.getInputStream());
                System.out.println(result);
                return result;
        }

        private static String convertStreamToStr(InputStream is) throws IOException {
                if (is != null) {
                        Writer writer = new StringWriter();
                        char[] buffer = new char[1024];
                        try {
                                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                                int n;
                                while ((n = reader.read(buffer)) != -1) {
                                        writer.write(buffer, 0, n);
                                }
                        } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                        } catch (IOException e) {
                                e.printStackTrace();
                        } finally {
                                is.close();
                        }
                        return writer.toString();
                } else {
                        return "";
                }
        }
}
