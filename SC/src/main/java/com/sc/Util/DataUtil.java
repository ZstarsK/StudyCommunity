package com.sc.Util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sc.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class DataUtil {
    //获取对应表中的查询包装器
    public static <T> QueryWrapper<T> getQueried( Class<T> clazz,
                                                String tableField,String queryValue) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(tableField, queryValue);
        return queryWrapper;
    }
    //获取对应表中的更新包装器
    public static <T,U> UpdateWrapper<T> getUpdated(Class<T> clazz,
                                           String queryTableField,String queryValue,
                                           String updateTableField,U updateValue) {

        UpdateWrapper<T> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq(queryTableField,queryValue);
        updateWrapper.set(updateTableField,updateValue);
        return updateWrapper;
    }

    //保存文件到服务器
    public static String saveFiles( MultipartFile file, String path,String fileName) {
        String filePath,alterFileName;
        String pType = getFileType(file);
        //用户先传图片再传其他信息,需要自己生成文件名
        if (fileName==null||fileName.isEmpty()) {
            alterFileName = UUID.randomUUID().toString();
            //"D:/"+alterFileName + "." + pType;
            filePath = path + alterFileName + "." + pType;

        }
        //用户先传其他信息，获取保存到服务器的路径并保存
        else{
            //path + fileName + "." + pType;
            filePath = path + fileName + "." + pType;
            System.out.println("filePath: "+filePath);
            alterFileName=fileName;
        }
        System.out.println(filePath);

        try {
            file.transferTo(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //获取输送给前端的url
        String urlPath=path.substring(path.indexOf("/")+16);

        return "http://" + "1.117.52.175:8084" + "/Pic/"+urlPath + alterFileName + "."+pType;


    }
    //更新文件
    public static String updatePostFile(MultipartFile file, String fullPath, String path, String fileName) {
        String pType = getFileType(file);
        //用户第一次上传文件,fullPath=null;
        if (!file.isEmpty()&&fullPath.length()==0) {
            return (saveFiles(file,path,fileName));
        }
        //用户非第一次上传文件
        else{
            //fullPath="Http://ip:port/Pic/path/postId.pType";


            int indexStart = fullPath.indexOf("/Pic/") + 5;
            int indexEnd = fullPath.length() - new StringBuilder(fullPath).reverse().toString().indexOf(".");


            //"/StudyCommunity/"+ fullPath.substring(index);
            String origFilePath ="/StudyCommunity/"+ fullPath.substring(indexStart);;


            File originFile = new File(origFilePath);
            //动态之前包含文件，用户删除文件
            if (originFile.exists()) {
                if (file.isEmpty()) {
                    originFile.delete();
                    return null;
                }
            }
            //用户修改文件
            if (!file.isEmpty()) {
                String filePath ="/StudyCommunity/"+ fullPath.substring(indexStart,indexEnd)+pType;
                fullPath=fullPath.substring(0,indexEnd)+pType;
                try {
                    originFile.delete();
                    file.transferTo(new File(filePath));
                    return fullPath;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    //获取文件类型
    public static String getFileType(MultipartFile file) {
        String pType = file.getContentType();
        pType = pType.substring(pType.indexOf("/") + 1);
        if ("jpeg".equals(pType)){
            pType = "jpg";
        }
        return pType;
    }


}
