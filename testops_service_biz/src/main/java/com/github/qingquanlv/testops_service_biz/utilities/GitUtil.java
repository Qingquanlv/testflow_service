package com.github.qingquanlv.testops_service_biz.utilities;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

/**
 * @Author: qingquan.lv
 * @Date: 2022/2/9 下午7:21
 */
public class GitUtil {

        public static void cloneRepo(String clientPath, String repoPath, String user, String password) throws InvalidRemoteException, TransportException, GitAPIException {
                File client = new File(clientPath);
                CloneCommand clone = org.eclipse.jgit.api.Git.cloneRepository().setURI(repoPath).setDirectory(client);
                if (null != user && null != password) {
                        clone.setCredentialsProvider(new UsernamePasswordCredentialsProvider(user, password));
                }
                clone.call();
        }
}
