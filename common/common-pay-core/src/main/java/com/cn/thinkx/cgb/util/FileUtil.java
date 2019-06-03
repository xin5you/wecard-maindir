package com.cn.thinkx.cgb.util;


import com.cn.thinkx.cgb.model.BankMappingDTO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class FileUtil {

    public  static List<BankMappingDTO> listBankMapping(File file){
        List<BankMappingDTO> bankMappingDTOS = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                //result.append(System.lineSeparator()+s);
                String str  = System.lineSeparator()+s;
                String[] strs= str.split("#");
                BankMappingDTO bankMappingDTO = new BankMappingDTO();
                bankMappingDTO.setBankCode(strs[0].replace("\n","").replace("\r",""));
                bankMappingDTO.setBankName(strs[1].replace("\n","").replace("\r",""));
                bankMappingDTO.setBankShortName(strs[2].replace("\n","").replace("\r",""));
                bankMappingDTOS.add(bankMappingDTO);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return bankMappingDTOS;
    }

}

