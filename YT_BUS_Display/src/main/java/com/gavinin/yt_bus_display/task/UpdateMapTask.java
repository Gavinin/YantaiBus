package com.gavinin.yt_bus_display.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavinin.yt_bus_display.common.BusApiStatus;
import com.gavinin.yt_bus_display.common.ConstStatus;
import com.gavinin.yt_bus_display.entity.Result;
import com.gavinin.yt_bus_display.entity.UpdateResource;
import com.gavinin.yt_bus_display.util.FileUtil;
import com.gavinin.yt_bus_display.util.ZipUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.gavinin.yt_bus_display.task.BusVersionUtil.ORIGIN_VERSION;
import static com.gavinin.yt_bus_display.task.BusVersionUtil.getVersionFromFileName;

/**
 * @ClassName UpdateMapTask
 * @Description TODO
 * @Author gavin
 * @Date 5/7/2022 02:59
 * @Version 1.0
 **/
@Component
public class UpdateMapTask {


    final
    RestTemplate restTemplate;

    final
    ObjectMapper objectMapper;

    public UpdateMapTask(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }


    @Scheduled(cron = "*/1 * * * * ?")
    public void check() throws IOException {

        //get from local
        String fileName = "ytcx2022070401.zip";

        //get From Remote
        Result versionFromRemote = getVersionFromRemote(ORIGIN_VERSION);
        String data = (String) versionFromRemote.getData();
        UpdateResource updateResource = objectMapper.readValue(data, UpdateResource.class);
        String remoteVersion = updateResource.getVersion();
        boolean shuoldUpdate = true;
        if (fileName != null && "".equals(fileName)) {
            String version = getVersionFromFileName(fileName);
            if (remoteVersion.equals(version)) {
                shuoldUpdate = false;
            }
        }
        if (shuoldUpdate) {
            try {
                FileUtil.downloadUsingNIO(updateResource.getDbPath(), ConstStatus.DB_LOCAL);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String currenDBtFileName = "ytcx" + remoteVersion + ".zip";
            Path paths = Paths.get(ConstStatus.DB_LOCAL + "/" + currenDBtFileName);
            ZipUtil.unZip(paths, Paths.get(ConstStatus.DB_LOCAL));

        }
    }

    public void fileNIORename(String filePath,String oldName,String newName) throws IOException {
        File source = new File(filePath+"/"+oldName);
        if (!source.exists()) {
            source.createNewFile();
        }
        Path path = Files.move(source.toPath(), Paths.get(filePath+"/"+newName), StandardCopyOption.REPLACE_EXISTING);
        assert path.equals(Paths.get("/path/to/targetFile"));
    }

    private Result getVersionFromRemote(String originVersion) {
        String uri = BusApiStatus.YANTAI_BUS_DB + originVersion;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String strbody = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class).getBody();
        Result result = null;
        try {
            result = objectMapper.readValue(strbody, Result.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


}
