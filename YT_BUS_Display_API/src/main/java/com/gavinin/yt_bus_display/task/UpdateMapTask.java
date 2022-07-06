package com.gavinin.yt_bus_display.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavinin.yt_bus_display.common.BusApiStatus;
import com.gavinin.yt_bus_display.common.ConstStatus;
import com.gavinin.yt_bus_display.entity.RemoteVersionResult;
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
import java.util.Map;

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


    @Scheduled(cron = "0 1 * * * ?")
    public void check() throws IOException {

        //get from local
        String fileName = "ytcx2022070401.zip";

        //get From Remote
        RemoteVersionResult versionFromRemote = getVersionFromRemote(ORIGIN_VERSION);
        if (versionFromRemote == null) {
            throw new RuntimeException("None remote data!");
        }
//        Map data =  versionFromRemote.getData();
        UpdateResource updateResource = versionFromRemote.getData();
        String remoteVersion = updateResource.getVersion();
        boolean shouldUpdate = true;
        String currenDBtFileName = "ytcx" + remoteVersion;
        String currenDBCompressFileName = "/" + currenDBtFileName + ".zip";

        if (fileName != null && "".equals(fileName)) {
            String version = getVersionFromFileName(fileName);
            if (remoteVersion.equals(version)) {
                shouldUpdate = false;
            }
        }
        if (shouldUpdate) {
            try {
                FileUtil.downloadUsingNIO(updateResource.getDbPath(), ConstStatus.DB_LOCAL + currenDBCompressFileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Path paths = Paths.get(ConstStatus.DB_LOCAL + currenDBCompressFileName);
            ZipUtil.unZip(paths, Paths.get(ConstStatus.DB_LOCAL));
            fileNIORename(ConstStatus.DB_LOCAL, currenDBtFileName + ".db", "ytcx.db");

        }
    }

    public void fileNIORename(String filePath, String oldName, String newName) throws IOException {
        File source = new File(filePath + "/" + oldName);
        if (!source.exists()) {
            source.createNewFile();
        }
        Path path = Files.move(source.toPath(), Paths.get(filePath + "/" + newName), StandardCopyOption.REPLACE_EXISTING);
        assert path.equals(Paths.get("/path/to/targetFile"));
    }

    private RemoteVersionResult getVersionFromRemote(String originVersion) {
        String uri = BusApiStatus.YANTAI_BUS_DB + originVersion;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String strbody = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class).getBody();
        RemoteVersionResult result = null;
        try {
            result = objectMapper.readValue(strbody, RemoteVersionResult.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


}
